package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.ARIALandmark;

import java.util.ArrayList;
import java.util.List;

public class ARIALandmarkBuilder extends CrawlerObject {

    public static List<ARIALandmark> buildAll() {
        List<ARIALandmark> landmarks = new ArrayList<>();
        landmarks.add(buildBanner());
        landmarks.add(buildComplementary());
        landmarks.add(buildContentinfo());
        landmarks.add(buildForm());
        landmarks.add(buildMain());
        landmarks.add(buildNavigation());
        landmarks.add(buildRegion());
        landmarks.add(buildSearch());
        return landmarks;
    }

    public static ARIALandmark buildLandmark(String landmark, int datasetClass) {
        ARIALandmark ariaLandmark = new ARIALandmark();
        ariaLandmark.setDatasetClass(datasetClass);
        ariaLandmark.setRole(landmark);
        return ariaLandmark;
    }

    public static ARIALandmark buildBanner() {
        return buildLandmark(ARIALandmark.ARIA_BANNER, 1);
    }

    public static ARIALandmark buildComplementary() {
        return buildLandmark(ARIALandmark.ARIA_COMPLEMENTARY, 2);
    }

    public static ARIALandmark buildContentinfo() {
        return buildLandmark(ARIALandmark.ARIA_CONTENTINFO, 3);
    }

    public static ARIALandmark buildForm() {
        return buildLandmark(ARIALandmark.ARIA_FORM, 4);
    }

    public static ARIALandmark buildMain() {
        return buildLandmark(ARIALandmark.ARIA_MAIN, 5);
    }

    public static ARIALandmark buildNavigation() {
        return buildLandmark(ARIALandmark.ARIA_NAVIGATION, 6);
    }

    public static ARIALandmark buildRegion() {
        return buildLandmark(ARIALandmark.ARIA_REGION, 7);
    }

    public static ARIALandmark buildSearch() {
        return buildLandmark(ARIALandmark.ARIA_SEARCH, 8);
    }

}
