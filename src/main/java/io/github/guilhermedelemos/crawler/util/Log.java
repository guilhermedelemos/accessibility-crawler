package io.github.guilhermedelemos.crawler.util;

import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

    private Log() {}

    public static void log(String message) {
        log(message, true);
    }

    public static void log(String message, boolean date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        if(date) {
            sb.append(dateFormat.format(new Date()));
            sb.append(" ");
        }
        sb.append(message);
        System.out.println(sb.toString());
    }

    public static void separator() {
        System.out.println("==========");
    }

    public static void logWebElement(WebElement element) {
        logWebElement(element, 0);
    }

    public static void logWebElement(WebElement element, int level) {
        StringBuilder indentation = new StringBuilder();
        for(int i=0;i<level*2;i++) {
            indentation.append("--");
        }
        if(indentation.length() > 0) {
            indentation.append("> ");
        }
        log("-----");
        log(indentation + "id " + element.getAttribute("id"));
        log(indentation + "TagName " + element.getTagName());
        log(indentation + "Location X " + element.getLocation().getX());
        log(indentation + "Location Y " + element.getLocation().getY());
        log(indentation + "Height " + element.getSize().getHeight());
        log(indentation + "Width " + element.getSize().getWidth());
        log(indentation + "Displayed " + element.isDisplayed());
        log(indentation + "Enabled " + element.isEnabled());
        log(indentation + "Selected " + element.isSelected());
        log(indentation + "Text " + element.getText());
    }

}
