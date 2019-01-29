package io.github.guilhermedelemos.crawler.model;

import java.util.ArrayList;
import java.util.List;

public class WebPage {
    private String url;
    private String urlAfterRequest;
    private int httpStatusCode;
    private List<DomElement> elements;

    public WebPage() {
        super();
        this.elements = new ArrayList<>();
    }

    public void addElement(DomElement e) {
        this.elements.add(e);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlAfterRequest() {
        return urlAfterRequest;
    }

    public void setUrlAfterRequest(String urlAfterRequest) {
        this.urlAfterRequest = urlAfterRequest;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public List<DomElement> getElements() {
        return elements;
    }

    public void setElements(List<DomElement> elements) {
        this.elements = elements;
    }
}
