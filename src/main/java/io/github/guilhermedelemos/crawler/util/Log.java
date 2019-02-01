package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.WebPage;
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

    public void logWebPage(WebPage webPage) {
        if(webPage == null) {
            return;
        }
        webPage.getElements().forEach(element -> {
            log.info(Messages.LOG_ELEMENT_FOUND);
            log.info(String.format(LOG_PARAMS, Messages.LOG_ID, element.getId()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_TAG_NAME, element.getTagName()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_POSITION_X, element.getX()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_POSITION_Y, element.getY()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_HEIGHT, element.getHeight()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_WIDTH, element.getWidth()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_DISPLAYED, element.isDisplayed()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_ENABLED, element.isEnabled()));
            log.info(String.format(LOG_PARAMS, Messages.LOG_TEXT, element.getText()));
        });
    }

}
