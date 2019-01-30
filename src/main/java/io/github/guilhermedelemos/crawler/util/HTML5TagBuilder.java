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
        invalid.add(HTML5Tag.TAG_ARTICLE);
        invalid.add(HTML5Tag.TAG_ASIDE);
        invalid.add(HTML5Tag.TAG_MAIN);
        invalid.add(HTML5Tag.TAG_NAV);
        invalid.add(HTML5Tag.TAG_SECTION);
        return new HTML5Tag(HTML5Tag.TAG_HEADER, HTML5Tag.TAG_BODY, invalid, false, false, false, null);
    }

    public static HTML5Tag buildComplementary() {
        return new HTML5Tag(HTML5Tag.TAG_ASIDE, null, null, false, false, false, null);
    }

    public static HTML5Tag buildContentinfo() {
        List<String> invalid = new ArrayList<>();
        invalid.add(HTML5Tag.TAG_ARTICLE);
        invalid.add(HTML5Tag.TAG_ASIDE);
        invalid.add(HTML5Tag.TAG_MAIN);
        invalid.add(HTML5Tag.TAG_NAV);
        invalid.add(HTML5Tag.TAG_SECTION);
        return new HTML5Tag(HTML5Tag.TAG_FOOTER, HTML5Tag.TAG_BODY, invalid, false, false, false, null);
    }

    public static HTML5Tag buildForm() {
        return new HTML5Tag(HTML5Tag.TAG_FORM, null, null, true, false, false, null);
    }

    public static HTML5Tag buildMain() {
        return new HTML5Tag(HTML5Tag.TAG_MAIN, null, null, false, false, false, null);
    }

    public static HTML5Tag buildNavigation() {
        return new HTML5Tag(HTML5Tag.TAG_NAV, null, null, false, false, false, null);
    }

    public static List<HTML5Tag> buildRegion() {
        List<HTML5Tag> tags = new ArrayList<>();
        tags.add(new HTML5Tag(null, null, null, true, false, false,HTML5Tag.ROLE_REGION));
        tags.add(new HTML5Tag(HTML5Tag.TAG_SECTION, null, null, true, false, false, null));
        tags.add(new HTML5Tag(HTML5Tag.TAG_SECTION, null, null, false, true, false, null));
        tags.add(new HTML5Tag(HTML5Tag.TAG_SECTION, null, null, false, false, true, null));
        return tags;
    }

}
