package io.github.guilhermedelemos.crawler.model;

import io.github.guilhermedelemos.crawler.util.CrawlerObject;

public class ARIALandmark extends CrawlerObject {

    private int datasetClass;
    private String role;

    public ARIALandmark() {
        super();
    }

    public int getDatasetClass() {
        return datasetClass;
    }

    public void setDatasetClass(int datasetClass) {
        this.datasetClass = datasetClass;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
