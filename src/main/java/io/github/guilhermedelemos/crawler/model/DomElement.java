package io.github.guilhermedelemos.crawler.model;

import io.github.guilhermedelemos.crawler.util.CrawlerObject;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DomElement extends CrawlerObject {
    private String id;
    private String tagName;
    private WebElement webElement;
    private List<DomElement> children;
    private String ariaLandmark;
    private String html5Tag;

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

    public String getTagName() {
        return tagName;
    }

    public String getRole() {
        if(this.webElement != null) {
            return this.webElement.getAttribute("role");
        } else {
            return "";
        }
    }

    public int getX() {
        if(this.webElement != null) {
            return this.webElement.getLocation().getX();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public int getY() {
        if(this.webElement != null) {
            return this.webElement.getLocation().getY();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public int getHeight() {
        if(this.webElement != null) {
            return this.webElement.getSize().getHeight();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public int getWidth() {
        if(this.webElement != null) {
            return this.webElement.getSize().getWidth();
        } else {
            return Integer.MIN_VALUE;
        }
    }

    public boolean isDisplayed() {
        return this.webElement != null && this.webElement.isDisplayed();
    }

    public boolean isEnabled() {
        return this.webElement != null && this.webElement.isEnabled();
    }

    public String getText() {
        if(this.webElement != null) {
            return this.webElement.getText();
        } else {
            return "";
        }
    }

    /**
     * Calculated.
     * @return Area
     */
    public int getArea() {
        return this.getHeight() * this.getWidth();
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAriaLandmark() {
        return ariaLandmark;
    }

    public void setAriaLandmark(String ariaLandmark) {
        this.ariaLandmark = ariaLandmark;
    }

    public String getHtml5Tag() {
        return html5Tag;
    }

    public void setHtml5Tag(String html5Tag) {
        this.html5Tag = html5Tag;
    }

}
