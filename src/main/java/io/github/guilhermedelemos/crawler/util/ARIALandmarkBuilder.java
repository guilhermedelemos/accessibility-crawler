package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.ARIALandmark;

import java.util.ArrayList;
import java.util.List;

public class ARIALandmarkBuilder extends CrawlerObject {

    public static List<ARIALandmark> buildAll() {
        List<ARIALandmark> landmarks = new ArrayList<>();
        landmarks.add(buildLandmark("banner"));
        landmarks.add(buildLandmark("complementary"));
        landmarks.add(buildLandmark("contentinfo"));
        landmarks.add(buildLandmark("form"));
        landmarks.add(buildLandmark("main"));
        landmarks.add(buildLandmark("navigation"));
        landmarks.add(buildLandmark("region"));
        landmarks.add(buildLandmark("search"));
        return landmarks;
    }

    public static ARIALandmark buildLandmark(String landmark) {
        ARIALandmark ariaLandmark = new ARIALandmark();
        ariaLandmark.setDatasetClass(1);
        ariaLandmark.setRole(landmark);
        return ariaLandmark;
    }

}
