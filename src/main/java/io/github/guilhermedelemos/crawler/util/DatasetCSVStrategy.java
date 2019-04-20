package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.DomElement;
import io.github.guilhermedelemos.crawler.model.WebPage;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DatasetCSVStrategy extends DatasetStrategy {

    public static final String COLUMN_SEPARATOR = ";";

    private int counter;

    public boolean createDataset(List<JSONObject> jsonSamples, String outputFile) {
        if (jsonSamples == null || jsonSamples.size() < 1) {
            return false;
        }

        String file = null;
        if (outputFile.isEmpty()) {
            file = "dataset_" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date()) + ".csv";
        }
        Path path = Paths.get(file);
        this.deleteFile(file);

        log.info("Criando dataset");

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("id;url;qtdeFilhos;tag;domId;posX;posY;height;width;area;enabled;visible;class");
            writer.newLine();

            long count = 0;
            Iterator<JSONObject> it = jsonSamples.iterator();
            while (it.hasNext()) {
                JSONObject sample = it.next();
                // log.info("Sample " + sample);
                StringBuilder line = new StringBuilder();
                line.append(count).append(COLUMN_SEPARATOR);
                line.append(sample.getString("url")).append(COLUMN_SEPARATOR);
                // line.append(sample.getString("qtdeElementosPagina")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "qtdeFilhos")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tag")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "domId")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "posX")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "posY")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "height")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "width")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "area")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "enabled")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "visible")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "classs")).append(COLUMN_SEPARATOR);

                writer.write(line.toString());
                writer.newLine();
                count++;
            }
        } catch (IOException e) {
            log.error("Erro ao criar o arquivo do dataset: " + file, e);
            return false;
        }
        return true;
    }

    public String getJSONValue(JSONObject obj, String key) {
        if (obj == null || key == null || key.isEmpty() || obj.isEmpty()) {
            return "null";
        }
        if ((key.equals("qtdeFilhos") || key.equals("posX") || key.equals("posY") || key.equals("height")
                || key.equals("width") || key.equals("area")) && obj.get(key) == JSONObject.NULL) {
            return "0";
        } else if (obj.get(key) == JSONObject.NULL) {
            return "null";
        }
        return obj.get(key).toString();
    }

    public void writeDomElementRecursive(WebPage webPage, DomElement domElement, BufferedWriter writer) {
        if (domElement == null) {
            return;
        }
        this.writeDomElement(webPage, domElement, writer);

        if (domElement.getChildren().size() > 0) {
            Iterator<DomElement> it = domElement.getChildren().iterator();
            while (it.hasNext()) {
                this.writeDomElementRecursive(webPage, it.next(), writer);
            }
        }
    }

    public void writeDomElement(WebPage webPage, DomElement domElement, BufferedWriter writer) {
        try {
            StringBuilder line = new StringBuilder();
            line.append(this.counter).append(COLUMN_SEPARATOR);
            line.append(webPage.getUrlAfterRequest()).append(COLUMN_SEPARATOR);
            line.append(webPage.getHttpStatusCode()).append(COLUMN_SEPARATOR);
            line.append(webPage.getElements().size()).append(COLUMN_SEPARATOR);
            line.append(domElement.getId()).append(COLUMN_SEPARATOR);
            line.append(domElement.getTagName()).append(COLUMN_SEPARATOR);
            line.append(domElement.getChildren().size()).append(COLUMN_SEPARATOR);
            if (domElement.getAriaLandmark() == null) {
                line.append(0).append(COLUMN_SEPARATOR);
            } else {
                line.append(domElement.getAriaLandmark().getRole()).append(COLUMN_SEPARATOR);
            }
            line.append(domElement.getHtml5Tag()).append(COLUMN_SEPARATOR);
            // line.append("XPATH").append(COLUMN_SEPARATOR);
            line.append(domElement.getPosX()).append(COLUMN_SEPARATOR);
            line.append(domElement.getPosY()).append(COLUMN_SEPARATOR);
            line.append(domElement.getHeight()).append(COLUMN_SEPARATOR);
            line.append(domElement.getWidth()).append(COLUMN_SEPARATOR);
            line.append(domElement.isDisplayed()).append(COLUMN_SEPARATOR);
            line.append(domElement.isEnabled()).append(COLUMN_SEPARATOR);
            line.append(domElement.getArea()).append(COLUMN_SEPARATOR);
            // line.append(domElement.getAriaLandmark().getDatasetClass()).append(COLUMN_SEPARATOR);
            line.append(domElement.getAriaLandmark().getRole()).append(COLUMN_SEPARATOR);

            writer.write(line.toString());
            writer.newLine();
        } catch (IOException e) {
            log.error("Erro ao escrever no arquivo do dataset", e);
        }
        this.counter++;
    }

    public boolean deleteFile(String file) {
        Path path = Paths.get(file);
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
        } catch (IOException e) {
            log.error("Erro ao deletar o arquivo do dataset", e);
            return false;
        }
        return true;
    }
}
