package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.DomElement;
import io.github.guilhermedelemos.crawler.model.WebPage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class DatasetCSVStrategy extends DatasetStrategy {

    public static final String COLUMN_SEPARATOR = ";";

    private int counter;

    @Override
    public boolean createDataset(List<WebPage> webPages, String outputFile) {
        this.counter = 1;
        if(webPages == null || webPages.size() == 0 || outputFile == null || outputFile.isEmpty()) {
            return false;
        }
        log.info("Criando dataset");

        Path path = Paths.get(outputFile);
        try {
            Files.delete(path);
        } catch(IOException e) {
            log.error("Erro ao deletar o arquivo do dataset", e);
        }

//        Files.write(Paths.get("c:/output.txt"), content.getBytes());
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
//            writer.write("id;url;httpStatusCode;qtdeElementosPagina;domElementId;domTagName;domQtdeFilhos;ariaLandmark;html5tag;xpath;posX;posY;height;width;visivel;habilitado;area;classe");
            writer.write("id;url;httpStatusCode;qtdeElementosPagina;domElementId;domTagName;domQtdeFilhos;ariaLandmark;html5tag;posX;posY;height;width;visivel;habilitado;area;classe");
            writer.newLine();

            Iterator<WebPage> it = webPages.iterator();
            while (it.hasNext()) {
                WebPage webPage = it.next();

                Iterator<DomElement> itDom = webPage.getElements().iterator();
                while (itDom.hasNext()) {
                    DomElement domElement = itDom.next();
                    this.writeDomElementRecursive(webPage, domElement, writer);
//                    this.writeDomElement(webPage, domElement, writer);
//
//                    Iterator<DomElement> itDomChildren = domElement.getChildren().iterator();
//                    while(itDomChildren.hasNext()) {
//                        this.writeDomElement(webPage, itDomChildren.next(), writer);
//                    }
                }
            }
        } catch(IOException e) {
            log.error("Erro ao criar o arquivo do dataset", e);
        }
        return true;
    }

    public void writeDomElementRecursive(WebPage webPage, DomElement domElement, BufferedWriter writer) {
        if(domElement == null) {
            return;
        }
        this.writeDomElement(webPage, domElement, writer);

        if(domElement.getChildren().size() > 0) {
            Iterator<DomElement> it = domElement.getChildren().iterator();
            while(it.hasNext()) {
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
            if(domElement.getAriaLandmark() == null) {
                line.append(0).append(COLUMN_SEPARATOR);
            } else {
                line.append(domElement.getAriaLandmark().getRole()).append(COLUMN_SEPARATOR);
            }
            line.append(domElement.getHtml5Tag()).append(COLUMN_SEPARATOR);
//            line.append("XPATH").append(COLUMN_SEPARATOR);
            line.append(domElement.getPosX()).append(COLUMN_SEPARATOR);
            line.append(domElement.getPosY()).append(COLUMN_SEPARATOR);
            line.append(domElement.getHeight()).append(COLUMN_SEPARATOR);
            line.append(domElement.getWidth()).append(COLUMN_SEPARATOR);
            line.append(domElement.isDisplayed()).append(COLUMN_SEPARATOR);
            line.append(domElement.isEnabled()).append(COLUMN_SEPARATOR);
            line.append(domElement.getArea()).append(COLUMN_SEPARATOR);
            line.append(domElement.getAriaLandmark().getDatasetClass()).append(COLUMN_SEPARATOR);

            writer.write(line.toString());
            writer.newLine();
        } catch(IOException e) {
            log.error("Erro ao escrever no arquivo do dataset", e);
        }
        this.counter++;
    }
}
