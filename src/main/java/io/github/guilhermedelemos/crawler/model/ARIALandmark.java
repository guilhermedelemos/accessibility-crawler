package io.github.guilhermedelemos.crawler.model;

import io.github.guilhermedelemos.crawler.util.CrawlerObject;

public class ARIALandmark extends CrawlerObject {

    public static final String ARIA_BANNER = "banner";
    public static final String ARIA_COMPLEMENTARY = "complementary";
    public static final String ARIA_CONTENTINFO = "contentinfo";
    public static final String ARIA_FORM = "form";
    public static final String ARIA_MAIN = "main";
    public static final String ARIA_NAVIGATION = "navigation";
    public static final String ARIA_REGION = "region";
    public static final String ARIA_SEARCH = "search";

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
