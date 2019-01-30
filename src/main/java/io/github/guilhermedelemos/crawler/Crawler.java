package io.github.guilhermedelemos.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.guilhermedelemos.crawler.model.DomElement;
import io.github.guilhermedelemos.crawler.model.HTML5Tag;
import io.github.guilhermedelemos.crawler.model.Site;
import io.github.guilhermedelemos.crawler.model.WebPage;
import io.github.guilhermedelemos.crawler.util.CrawlerObject;
import io.github.guilhermedelemos.crawler.util.HTML5TagBuilder;
import io.github.guilhermedelemos.crawler.util.Log;
import io.github.guilhermedelemos.crawler.util.WebDriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Crawler extends CrawlerObject {

    public boolean execute(List<Site> sites) {
        if(sites == null || sites.isEmpty()) {
            return false;
        }
        log.info("Sites list:");
        sites.forEach(item -> {
            log.info(new StringBuilder().append("url: ").append(item.getUrl()).toString());
        });

        try {
            List<String> ariaLandmarks = this.loadAriaLandmarks();
            List<HTML5Tag> html5Tags = this.loadHtml5Tags();

            WebDriverManager.chromedriver().setup();
            WebDriver webDriver = WebDriverBuilder.buildChromeDriver(true, WebDriverBuilder.LANGUAGE_EN_US);

            return this.scanSites(sites, webDriver, ariaLandmarks, html5Tags);
        } catch (Exception e) {
            log.error("Error processing sites", e);
            return false;
        }
    }

    public List<String> loadAriaLandmarks() {
        List<String> ariaLandmarks = new ArrayList<>();
        ariaLandmarks.add("banner");
        ariaLandmarks.add("complementary");
        ariaLandmarks.add("contentinfo");
        ariaLandmarks.add("form");
        ariaLandmarks.add("main");
        ariaLandmarks.add("navigation");
        ariaLandmarks.add("region");
        ariaLandmarks.add("search");
        return ariaLandmarks;
    }

    public List<HTML5Tag> loadHtml5Tags() {
        return HTML5TagBuilder.buildTags();
    }

    public boolean scanSites(List<Site> sites, WebDriver webDriver, List<String> ariaLandmarks, List<HTML5Tag> html5Tags) {
        try {
            boolean result = true;
            Iterator<Site> it = sites.iterator();
            while (it.hasNext()) {
                Site site = it.next();
                result = (this.scanSite(new URL(site.getUrl()), webDriver, ariaLandmarks, html5Tags)) ? result : false;
            }
            return result;
        } catch (Exception e) {
            log.error("Error scanning sites", e);
            return false;
        }
    }

    public boolean scanSite(URL site, WebDriver webDriver, List<String> ariaLandmarks, List<HTML5Tag> html5Tags) {
        try {
            WebPage webPage = this.getWebPageMetadata(site);

            log.info("==========");
            log.info(new StringBuilder().append("Site: ").append(webPage.getUrl()).toString());
            log.info(new StringBuilder().append("HTTP Status Code: ").append(webPage.getHttpStatusCode()).toString());

            if (!this.siteIsValid(webPage)) {
                log.info("Invalid site skipped.");
                return false;
            }

            webDriver.get(webPage.getUrl());
            webPage.setUrlAfterRequest(webDriver.getCurrentUrl());
            log.info(new StringBuilder().append("URL after request: ").append(webPage.getUrlAfterRequest()).toString());

            this.scanSiteForLandmarks(webPage, ariaLandmarks, webDriver);

            return true;
        } catch (Exception e) {
            log.error("Error scanning sites", e);
            return false;
        }
    }

    public boolean scanSiteForLandmarks(WebPage webPage, List<String> ariaLandmarks, WebDriver webDriver) {
        try {
            Iterator<String> it = ariaLandmarks.iterator();
            int count = 0;
            while (it.hasNext()) {
                String landmark = it.next();
                List<WebElement> elements = webDriver.findElements(By.cssSelector(new StringBuilder().append("[role=").append(landmark).append("]").toString()));
                if (!elements.isEmpty()) {
                    count++;
                    log.info(new StringBuilder().append("# Landmark ").append(landmark).append(" found").toString());
                    Iterator<WebElement> itWe = elements.iterator();
                    while (itWe.hasNext()) {
                        WebElement targetElement = itWe.next();
                        DomElement domElement = new DomElement();
                        domElement.setId(targetElement.getAttribute("id"));
                        domElement.setTagName(targetElement.getTagName());
                        domElement.setWebElement(targetElement);
                        webPage.addElement(domElement);
                        this.extractElements(domElement);
                    }
                }
            }
            if(count == 0) {
                log.info("No ARIA landmarks was found");
            }
            return true;
        } catch (Exception e) {
            log.error("Error processing site", e);
            return false;
        }
    }

    public WebPage getWebPageMetadata(URL url) {
        WebPage webPage = new WebPage();
        webPage.setUrl(url.toString());
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.connect();
            webPage.setHttpStatusCode(connection.getResponseCode());
            webPage.setUrlAfterRequest(connection.getHeaderField("Location"));
            return webPage;
        } catch (IOException e) {
            log.error("Error retrieving site metadata", e);
            webPage.setHttpStatusCode(404);
            return webPage;
        }
    }

    public boolean siteIsValid(WebPage site) {
        return site.getHttpStatusCode() != 404;
    }

    public void extractElements(DomElement element) {
        this.extractElements(element, 0);
    }

    public void extractElements(DomElement element, int level) {
        if (element == null) {
            return;
        }
        Log.logWebElement(element.getWebElement(), level, log);
        //subElements
        List<WebElement> children = element.getWebElement().findElements(By.xpath(".//*"));
        Iterator<WebElement> it = children.iterator();
        while (it.hasNext()) {
            WebElement targetElement = it.next();
            DomElement domElement = new DomElement();
            domElement.setId(targetElement.getAttribute("id"));
            domElement.setTagName(targetElement.getTagName());
            domElement.setWebElement(targetElement);
            element.addChild(domElement);
            this.extractElements(domElement, level + 1);
        }
    }

}