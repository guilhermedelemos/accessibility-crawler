package io.github.guilhermedelemos.crawler.model;

import io.github.guilhermedelemos.crawler.util.CrawlerObject;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class DomElement extends CrawlerObject {
    private String id;
    private String tagName;
    private String role;
    private int posX;
    private int posY;
    private int height;
    private int width;
    private boolean displayed;
    private boolean enabled;
    private List<DomElement> children;
    private String ariaLandmark;
    private String html5Tag;

    public DomElement() {
        super();
        this.children = new ArrayList<>();
        this.posX = Integer.MAX_VALUE;
        this.posY = Integer.MAX_VALUE;
        this.height = Integer.MAX_VALUE;
        this.width = Integer.MAX_VALUE;
        this.displayed = false;
        this.enabled = false;
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

    public int getX() {
        return this.getPosX();
    }

    public int getY() {
        return this.getPosY();
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isDisplayed() {
        return this.displayed;
    }

    public boolean isEnabled() {
        return this.enabled;
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

    public void setWebElement(WebElement webElement) {
        this.id = webElement.getAttribute("id");
        this.tagName = webElement.getTagName();
        this.posX = webElement.getLocation().getX();
        this.posY = webElement.getLocation().getY();
        this.height = webElement.getSize().getHeight();
        this.width = webElement.getSize().getWidth();
        this.displayed = webElement.isDisplayed();
        this.enabled = webElement.isEnabled();
        this.role = webElement.getAttribute("role");
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

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
