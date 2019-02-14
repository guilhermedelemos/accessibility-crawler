package io.github.guilhermedelemos.crawler;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.guilhermedelemos.crawler.model.DomElement;
import io.github.guilhermedelemos.crawler.model.HTML5Tag;
import io.github.guilhermedelemos.crawler.model.Site;
import io.github.guilhermedelemos.crawler.model.WebPage;
import io.github.guilhermedelemos.crawler.util.*;
import org.openqa.selenium.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Crawler extends CrawlerObject {

    public static final String LOG_PARAMS = "%s%s%s";

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

            WebDriverManager.firefoxdriver().setup();
            WebDriver webDriver = WebDriverBuilder.buildFirefoxDriver(true);

            List<WebPage> webPages = this.scanSites(sites, webDriver, ariaLandmarks, html5Tags);

            DatasetCSVStrategy dataset = new DatasetCSVStrategy();
            boolean datasetCreated = dataset.createDataset(webPages, "dataset.csv");
            if(datasetCreated) {
                log.info("Dataset created");
            } else {
                log.info("Erro ao criar o dataset");
            }
        } catch (Exception e) {
            log.error("Error processing sites", e);
            return false;
        }
        return true;
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

    public List<WebPage> scanSites(List<Site> sites, WebDriver webDriver, List<String> ariaLandmarks, List<HTML5Tag> html5Tags) {
        try {
            List<WebPage> webPages = new ArrayList<>();
            boolean result = true;
            Iterator<Site> it = sites.iterator();
            while (it.hasNext()) {
                Site site = it.next();
                WebPage webPage = this.scanSite(new URL(site.getUrl()), webDriver, ariaLandmarks, html5Tags);
                if(webPage != null) {
                    webPages.add(webPage);
                }
//                result = (this.scanSite(new URL(site.getUrl()), webDriver, ariaLandmarks, html5Tags)) ? result : false;
            }
            return webPages;
        } catch (Exception e) {
            log.error("Error scanning sites", e);
            return new ArrayList<>();
        }
    }

    public WebPage scanSite(URL site, WebDriver webDriver, List<String> ariaLandmarks, List<HTML5Tag> html5Tags) {
        try {
            WebPage webPage = this.getWebPageMetadata(site);

            log.info("==========");
            log.info(new StringBuilder().append("Site: ").append(webPage.getUrl()).toString());
            log.info(new StringBuilder().append("HTTP Status Code: ").append(webPage.getHttpStatusCode()).toString());

            if (!this.siteIsValid(webPage)) {
                log.info("Invalid site skipped.");
                return null;
            }

            webDriver.get(webPage.getUrl());
            webPage.setUrlAfterRequest(webDriver.getCurrentUrl());
            log.info(new StringBuilder().append("URL after request: ").append(webPage.getUrlAfterRequest()).toString());

            this.scanSiteForLandmarks(webPage, ariaLandmarks, webDriver);
            this.scanSiteForHTML5Tags(webPage, html5Tags, webDriver);

            Log.logWebPage(webPage, log);

            Sceenshot.printscr(webDriver);

            return webPage;
        } catch (Exception e) {
            log.error("Error scanning sites", e);
            return null;
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
                        domElement.setAriaLandmark(landmark);
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

    public boolean scanSiteForHTML5Tags(WebPage webPage, List<HTML5Tag> html5Tags, WebDriver webDriver) {
        if(webPage == null || html5Tags == null || webDriver == null) {
            return false;
        }

        int count = 0;
        Iterator<HTML5Tag> it = html5Tags.iterator();
        while (it.hasNext()) {
            HTML5Tag tag = it.next();

        }

        return false;
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
//        Log.logWebElement(element.getWebElement(), level, log);
        //subElements
        List<WebElement> children = element.getWebElement().findElements(By.xpath(".//*"));
        Iterator<WebElement> it = children.iterator();
        while (it.hasNext()) {
            WebElement targetElement = it.next();
            DomElement domElement = new DomElement();
            domElement.setId(targetElement.getAttribute("id"));
            domElement.setTagName(targetElement.getTagName());
            domElement.setWebElement(targetElement);
            domElement.setAriaLandmark(element.getAriaLandmark());
            domElement.setHtml5Tag(element.getHtml5Tag());
            element.addChild(domElement);
            this.extractElements(domElement, level + 1);
        }
    }

}
