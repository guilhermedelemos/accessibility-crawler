package io.github.guilhermedelemos.crawler.model;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DomElement {
    private String id;
    private String tagName;
    private WebElement webElement;
    private List<DomElement> children;

    public DomElement() {
        super();
        this.children = new ArrayList<>();
    }

    public void addChild(DomElement e) {
        if (e != null) {
            children.add(e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public WebElement getWebElement() {
        return webElement;
    }

    public void setWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    public List<DomElement> getChildren() {
        return children;
    }

    public void setChildren(List<DomElement> children) {
        this.children = children;
    }
}
