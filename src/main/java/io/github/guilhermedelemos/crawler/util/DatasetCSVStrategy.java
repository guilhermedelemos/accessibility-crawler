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

    public static final String COLUMN_SEPARATOR = ",";

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

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            //writer.write("id;url;domId;tag;childrenCount;posX;posY;offsetX;offsetY;height;width;innerHeight;innerWidth;outerHeight;outerWidth;area;enabled;visible;class");
            writer.write("id,url,xpath,domId,tag,role,childrenCount,posX,posY,offsetX,offsetY,height,width,innerHeight,innerWidth,outerHeight,outerWidth,area,enabled,visible,tagACountLv1,tagACountAll,tagArticleCountLv1,tagArticleCountAll,tagAsideCountLv1,tagAsideCountAll,tagBrCountLv1,tagBrCountAll,tagButtonCountLv1,tagButtonCountAll,tagCanvasCountLv1,tagCanvasCountAll,tagDivCountLv1,tagDivCountAll,tagFooterCountLv1,tagFooterCountAll,tagFormCountLv1,tagFormCountAll,tagH1CountLv1,tagH1CountAll,tagH2CountLv1,tagH2CountAll,tagH3CountLv1,tagH3CountAll,tagH4CountLv1,tagH4CountAll,tagH5CountLv1,tagH5CountAll,tagH6CountLv1,tagH6CountAll,tagHeaderCountLv1,tagHeaderCountAll,tagHrCountLv1,tagHrCountAll,tagIframeCountLv1,tagIframeCountAll,tagImgCountLv1,tagImgCountAll,tagInputCountLv1,tagInputCountAll,tagLabelCountLv1,tagLabelCountAll,tagLiCountLv1,tagLiCountAll,tagMainCountLv1,tagMainCountAll,tagNavCountLv1,tagNavCountAll,tagObjectCountLv1,tagObjectCountAll,tagOlCountLv1,tagOlCountAll,tagPCountLv1,tagPCountAll,tagSectionCountLv1,tagSectionCountAll,tagSelectCountLv1,tagSelectCountAll,tagSmallCountLv1,tagSmallCountAll,tagStrongCountLv1,tagStrongCountAll,tagSubCountLv1,tagSubCountAll,tagSupCountLv1,tagSupCountAll,tagSvgCountLv1,tagSvgCountAll,tagTableCountLv1,tagTableCountAll,tagTextareaCountLv1,tagTextareaCountAll,tagUlCountLv1,tagUlCountAll,class");
            writer.newLine();

            long count = 0;
            Iterator<JSONObject> it = jsonSamples.iterator();
            while (it.hasNext()) {
                JSONObject sample = it.next();
                // log.info("Sample " + sample);
                StringBuilder line = new StringBuilder();
                line.append(count).append(COLUMN_SEPARATOR); // id
                line.append(sample.getString("url")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "xpath")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "domId")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tag")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "role")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "childrenCount")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "posX")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "posY")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "offsetX")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "offsetY")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "height")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "width")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "innerHeight")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "innerWidth")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "outerHeight")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "outerWidth")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "area")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "isEnabled")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "isVisible")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagACountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagACountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagArticleCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagArticleCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagAsideCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagAsideCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagBrCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagBrCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagButtonCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagButtonCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagCanvasCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagCanvasCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagDivCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagDivCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagFooterCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagFooterCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagFormCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagFormCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH1CountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH1CountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH2CountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH2CountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH3CountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH3CountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH4CountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH4CountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH5CountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH5CountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH6CountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagH6CountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagHeaderCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagHeaderCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagHrCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagHrCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagIframeCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagIframeCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagImgCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagImgCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagInputCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagInputCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagLabelCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagLabelCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagLiCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagLiCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagMainCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagMainCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagNavCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagNavCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagObjectCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagObjectCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagOlCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagOlCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagPCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagPCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSectionCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSectionCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSelectCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSelectCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSmallCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSmallCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagStrongCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagStrongCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSubCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSubCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSupCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSupCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSvgCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagSvgCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagTableCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagTableCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagTextareaCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagTextareaCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagUlCountLv1")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "tagUlCountAll")).append(COLUMN_SEPARATOR);
                line.append(this.getJSONValue(sample, "classs"));
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
