package io.github.guilhermedelemos.crawler.model;

import io.github.guilhermedelemos.crawler.util.CrawlerObject;

import java.util.List;

public class HTML5Tag extends CrawlerObject {

    public static final String TAG_ARTICLE = "article";
    public static final String TAG_ASIDE = "aside";
    public static final String TAG_MAIN = "main";
    public static final String TAG_NAV = "nav";
    public static final String TAG_SECTION = "section";
    public static final String TAG_HEADER = "header";
    public static final String TAG_BODY = "body";
    public static final String TAG_FOOTER = "footer";
    public static final String TAG_FORM = "form";
    public static final String ROLE_REGION = "region";

    private String tag;
    private String context;
    private List<String> invalidElements;
    private boolean ariaLabelledby;
    private boolean ariaLabel;
    private boolean title;
    private String role;

    public HTML5Tag() {
        super();
        this.ariaLabelledby = false;
    }

    public HTML5Tag(String tag, String context, List<String> invalidElements, boolean ariaLabelledby, boolean ariaLabel, boolean title, String role) {
        this.tag = tag;
        this.context = context;
        this.invalidElements = invalidElements;
        this.ariaLabelledby = ariaLabelledby;
        this.ariaLabel = ariaLabel;
        this.title = title;
        this.role = role;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<String> getInvalidElements() {
        return invalidElements;
    }

    public void setInvalidElements(List<String> invalidElements) {
        this.invalidElements = invalidElements;
    }

    public boolean isAriaLabelledby() {
        return ariaLabelledby;
    }

    public void setAriaLabelledby(boolean ariaLabelledby) {
        this.ariaLabelledby = ariaLabelledby;
    }

    public boolean isAriaLabel() {
        return ariaLabel;
    }

    public void setAriaLabel(boolean ariaLabel) {
        this.ariaLabel = ariaLabel;
    }

    public boolean isTitle() {
        return title;
    }

    public void setTitle(boolean title) {
        this.title = title;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
