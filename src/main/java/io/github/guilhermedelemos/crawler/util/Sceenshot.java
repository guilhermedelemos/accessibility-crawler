package io.github.guilhermedelemos.crawler.util;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sceenshot extends CrawlerObject {

    public static void printscr(WebDriver driver) {
        printscr(
                driver,
                "/tmp/crawler"+(new SimpleDateFormat("yyyyMMdd_HHmmssSSS")).format(new Date())+".png"
        );
    }

    public static void printscr(WebDriver driver, String file) {
        if(file == null || file.isEmpty()) {
            return;
        }
        File imageFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile( imageFile, new File(file) );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
