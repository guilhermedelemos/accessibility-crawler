package io.github.guilhermedelemos.crawler.util;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private Log() {}

    public static void logWebElement(WebElement element, Logger log) {
        logWebElement(element, 0, log);
    }

    public static void logWebElement(WebElement element, int level, Logger log) {
        StringBuilder indentation = new StringBuilder();
        for(int i=0;i<level*2;i++) {
            indentation.append("--");
        }
        if(indentation.length() > 0) {
            indentation.append("> ");
        }
        log.info("-----");
        log.info(indentation + "id " + element.getAttribute("id"));
        log.info(indentation + "TagName " + element.getTagName());
        log.info(indentation + "Location X " + element.getLocation().getX());
        log.info(indentation + "Location Y " + element.getLocation().getY());
        log.info(indentation + "Height " + element.getSize().getHeight());
        log.info(indentation + "Width " + element.getSize().getWidth());
        log.info(indentation + "Displayed " + element.isDisplayed());
        log.info(indentation + "Enabled " + element.isEnabled());
        log.info(indentation + "Selected " + element.isSelected());
        log.info(indentation + "Text " + element.getText());
    }

}
