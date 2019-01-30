package io.github.guilhermedelemos.crawler.util;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

public class Log {

    private static final String SEPARATOR = "--";
    private static final String ARROW = "> ";

    private Log() {}

    public static void logWebElement(WebElement element, Logger log) {
        logWebElement(element, 0, log);
    }

    public static void logWebElement(WebElement element, int level, Logger log) {
        StringBuilder indentation = new StringBuilder();
        for(int i=0;i<level*2;i++) {
            indentation.append(SEPARATOR);
        }
        if(indentation.length() > 0) {
            indentation.append(ARROW);
        }
        log.info("-----");
        log.info(String.format("%s%s%s", indentation, "id", element.getAttribute("id")));
        log.info(String.format("%s%s%s", indentation, "TagName ", element.getTagName()));
        log.info(String.format("%s%s%s", indentation, "Location X ", element.getLocation().getX()));
        log.info(String.format("%s%s%s", indentation, "Location Y ", element.getLocation().getY()));
        log.info(String.format("%s%s%s", indentation, "Height ", element.getSize().getHeight()));
        log.info(String.format("%s%s%s", indentation, "Width ", element.getSize().getWidth()));
        log.info(String.format("%s%s%s", indentation, "Displayed ", element.isDisplayed()));
        log.info(String.format("%s%s%s", indentation, "Enabled ", element.isEnabled()));
        log.info(String.format("%s%s%s", indentation, "Selected ", element.isSelected()));
        log.info(String.format("%s%s%s", indentation, "Text ", element.getText()));
    }

}
