package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.HTML5Tag;

import java.util.ArrayList;
import java.util.List;

public class HTML5TagBuilder extends CrawlerObject {

    public static List<HTML5Tag> buildTags() {
        List<HTML5Tag> tags = new ArrayList<>();
        tags.add(buildBanner());
        tags.add(buildComplementary());
        tags.add(buildContentinfo());
        tags.add(buildForm());
        tags.add(buildMain());
        tags.add(buildNavigation());
        tags.addAll(buildRegion());

        return tags;
    }

    public static HTML5Tag buildBanner() {
        List<String> invalid = new ArrayList<>();
        invalid.add("article");
        invalid.add("aside");
        invalid.add("main");
        invalid.add("nav");
        invalid.add("section");

        return new HTML5Tag("header", "body", invalid, false, false, false, null);
    }

    public static HTML5Tag buildComplementary() {
        return new HTML5Tag("aside", null, null, false, false, false, null);
    }

    public static HTML5Tag buildContentinfo() {
        List<String> invalid = new ArrayList<>();
        invalid.add("article");
        invalid.add("aside");
        invalid.add("main");
        invalid.add("nav");
        invalid.add("section");

        return new HTML5Tag("footer", "body", invalid, false, false, false, null);
    }

    public static HTML5Tag buildForm() {
        return new HTML5Tag("form", null, null, true, false, false, null);
    }

    public static HTML5Tag buildMain() {
        return new HTML5Tag("main", null, null, false, false, false, null);
    }

    public static HTML5Tag buildNavigation() {
        return new HTML5Tag("nav", null, null, false, false, false, null);
    }

    public static List<HTML5Tag> buildRegion() {
        List<HTML5Tag> tags = new ArrayList<>();
        tags.add(new HTML5Tag(null, null, null, true, false, false,"region"));
        tags.add(new HTML5Tag("section", null, null, true, false, false, null));
        tags.add(new HTML5Tag("section", null, null, false, true, false, null));
        tags.add(new HTML5Tag("section", null, null, false, false, true, null));

        return tags;
    }

}
