package io.github.guilhermedelemos.crawler.util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Map;

public class WebDriverBuilder extends CrawlerObject {
    public static final String LANGUAGE_PT_BR = "pt-BR";
    public static final String LANGUAGE_EN_US = "en-US";

    private WebDriverBuilder() {
        super();
    }

    public static WebDriver buildChromeDriver() {
        return buildChromeDriver(false, "");
    }

    public static WebDriver buildChromeDriver(boolean headless) {
        return buildChromeDriver(headless, "");
    }

    public static WebDriver buildChromeDriver(boolean headless, String language) {
        ChromeOptions chromeOptions = new ChromeOptions();
        if(headless) {
            chromeOptions.addArguments("headless");
        }
        chromeOptions.addArguments("window-size=1200x600");
        chromeOptions.addArguments("start-maximized");
        if(!language.trim().isEmpty()) {
            DesiredCapabilities jsCapabilities = DesiredCapabilities.chrome();
            Map<String, Object> prefs = new HashMap<>();
            prefs.put("intl.accept_languages", language);
            chromeOptions.setExperimentalOption("prefs", prefs);
            jsCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        }
        return new ChromeDriver(chromeOptions);
    }

    public static WebDriver buildFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.setHeadless(headless);
        return new FirefoxDriver(options);
    }

    public static WebDriver buildFirefoxDriver() {
        return buildFirefoxDriver(false);
    }
}
