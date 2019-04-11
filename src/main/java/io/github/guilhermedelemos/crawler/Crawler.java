package io.github.guilhermedelemos.crawler;

import org.json.*;
import com.google.common.io.Resources;
import io.github.guilhermedelemos.crawler.model.*;
import io.github.guilhermedelemos.crawler.util.*;
import org.openqa.selenium.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Crawler extends CrawlerObject {

    public static final String LOG_PARAMS = "%s%s%s";
    public static final String LOG_SEPARATOR = "==========";
    public static final String LOG_SCANNING = "= Scanning";
    public static final String LOG_SITE = "Site: ";
    public static final String LOG_HTTP_STATUS_CODE = "HTTP Status Code: ";
    public static final String LOG_INVALID_SITE = "Invalid site skipped.";
    public static final String LOG_URL_AFTER_REQUEST = "URL after request: ";
    public static final String LOG_ERROR_SCANNING_SITES = "Error scanning sites";
    public static final String LOG_ERROR_PROCESSING_SITE = "Error processing site";

    public void printSites(List<Site> sites) {
        log.info("Sites list:");
        sites.forEach(item -> {
            log.info(new StringBuilder().append("url: ").append(item.getUrl()).toString());
        });
    }

    public String loadJavaScriptFile(String filePath) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            String dados = new String(encoded, StandardCharsets.UTF_8);
            return dados;
        } catch (Exception e) {
            return "";
        }
    }

    public boolean executeJavascript(List<Site> sites) {
        if (sites.size() < 1) {
            log.info("Nenhum site para processar.");
            return true;
        }

        long startTime = System.currentTimeMillis();

        // #1 WebDriver
        log.info("Load Web Driver");
        long startTimeWebDriver = System.currentTimeMillis();
        WebDriver webDriver = WebDriverBuilder.buildChromeDriver(true, WebDriverBuilder.LANGUAGE_EN_US);
        JavascriptExecutor js = (JavascriptExecutor) webDriver;
        long endTimeWebDriver = System.currentTimeMillis();

        // #2 Load crawler.js
        String crawlerJS = this.loadJavaScriptFile(Resources.getResource("crawler.js").getPath());
        if (crawlerJS.isEmpty()) {
            log.info("crawler.js não encontrado.");
            return false;
        }

        // #3 Scan
        long startTimeScan = System.currentTimeMillis();
        List<JSONObject> samples = new ArrayList<>();
        sites.stream().forEach(site -> {
            long startSiteScan = System.currentTimeMillis();
            log.info("Varrendo " + site.getUrl());
            webDriver.get(site.getUrl());

            Object retorno = js
                    .executeScript(crawlerJS + "return (new AccessibilityCrawler()).execute(ARIA_LANDMARKS, true)");

            if (retorno.toString().isEmpty()) {
                log.info("Nenhuma landmark encontrada");
            } else {
                JSONArray jsonArray = new JSONArray(retorno.toString());
                log.info(jsonArray.length() + " samples extracted");
                for (int i = 0; i < jsonArray.length(); i++) {
                    samples.add(jsonArray.getJSONObject(i));
//                    JSONObject jsonObj = jsonArray.getJSONObject(i);
//                    log.info("sample: " + jsonObj.toString());
                }
            }
            long endSiteScan = System.currentTimeMillis();
            log.info("Tempo para varrer o site: " + (endSiteScan - startSiteScan));
        });

        // #4 Build dataset
        log.info("Criando dataset");
        DatasetCSVStrategy dataset = new DatasetCSVStrategy();
        boolean datasetCreated = dataset.createDataset(samples, "");
        if(!datasetCreated) {
            log.info("Erro ao criar o dataset");
        }

        long endTimeScan = System.currentTimeMillis();

        log.info("Tempo para carregar o WebDriver: " + (endTimeWebDriver - startTimeWebDriver));
        log.info("Tempo para varrer os sites: " + (endTimeScan - startTimeScan));
        log.info("Tempo de execução: " + (System.currentTimeMillis() - startTime));
        log.info("END");
        return true;
    }

    public boolean scanJavaScript(String site, List<ARIALandmark> ariaLandmarks, WebDriver webDriver) {
        long startTime = System.currentTimeMillis();
        log.info("Site: " + site);
        webDriver.get(site);

        ariaLandmarks.stream().forEach(landmark -> {
            log.info("Buscando Landmark: " + landmark.getRole());
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            Object teste = js.executeScript("document.querySelectorAll('[role=\"" + landmark.getRole() + "\"]')");
            System.out.println(teste);
        });

        long endTime = System.currentTimeMillis();
        log.info("Tempo para processar o site: " + (endTime - startTime));
        return true;
    }

    public boolean execute(List<Site> sites) {
        if (sites == null || sites.isEmpty()) {
            return false;
        }

        this.printSites(sites);

        try {
            List<ARIALandmark> ariaLandmarks = this.loadAriaLandmarks();
            List<HTML5Tag> html5Tags = this.loadHtml5Tags();

            WebDriver webDriver = WebDriverBuilder.buildChromeDriver(true, WebDriverBuilder.LANGUAGE_EN_US);

            List<WebPage> webPages = this.scanSites(sites, webDriver, ariaLandmarks, html5Tags);

//            DatasetCSVStrategy dataset = new DatasetCSVStrategy();
//            boolean datasetCreated = dataset.createDataset(webPages,
//                    "dataset" + new SimpleDateFormat("yyyyMMdd_HHmmssSSS").format(new Date()) + ".csv");
//            if (datasetCreated) {
//                log.info("Dataset created");
//            } else {
//                log.info("Erro ao criar o dataset");
//            }
        } catch (Exception e) {
            log.error("Error processing sites", e);
            return false;
        }
        return true;
    }

    public List<ARIALandmark> loadAriaLandmarks() {
        // return ARIALandmarkBuilder.buildAll();
        return ARIALandmarkBuilder.build(false, false, false, true, false, true, false, true);
    }

    public List<HTML5Tag> loadHtml5Tags() {
        return HTML5TagBuilder.buildTags();
    }

    public List<WebPage> scanSites(List<Site> sites, WebDriver webDriver, List<ARIALandmark> ariaLandmarks,
                                   List<HTML5Tag> html5Tags) {
        try {
            List<WebPage> webPages = new ArrayList<>();
            Iterator<Site> it = sites.iterator();
            while (it.hasNext()) {
                Site site = it.next();
                WebPage webPage = this.scanSite(new URL(site.getUrl()), webDriver, ariaLandmarks, html5Tags);
                if (webPage != null) {
                    webPages.add(webPage);
                    // DatasetCSVStrategy dataset = new DatasetCSVStrategy();
                    // boolean datasetCreated = dataset.createDataset(webPage);
                    DatasetBuilder datasetThread = new DatasetBuilder();
                    datasetThread.setWebPage(webPage);
                    datasetThread.setLog(log);
                    datasetThread.start();
                }
            }
            return webPages;
        } catch (Exception e) {
            log.error("Error scanning sites", e);
            return new ArrayList<>();
        }
    }

    public WebPage scanSite(URL site, WebDriver webDriver, List<ARIALandmark> ariaLandmarks, List<HTML5Tag> html5Tags) {
        try {
            WebPage webPage = this.getWebPageMetadata(site);

            log.info(LOG_SEPARATOR);
            log.info(LOG_SCANNING);
            log.info(new StringBuilder().append(LOG_SITE).append(webPage.getUrl()).toString());
            log.info(new StringBuilder().append(LOG_HTTP_STATUS_CODE).append(webPage.getHttpStatusCode()).toString());

            if (!this.siteIsValid(webPage)) {
                log.info(LOG_INVALID_SITE);
                return null;
            }

            webDriver.get(webPage.getUrl());
            webPage.setUrlAfterRequest(webDriver.getCurrentUrl());
            log.info(new StringBuilder().append(LOG_URL_AFTER_REQUEST).append(webPage.getUrlAfterRequest()).toString());

            this.scanSiteForLandmarks(webPage, ariaLandmarks, webDriver);
            // this.scanSiteForHTML5Tags(webPage, html5Tags, webDriver);

            Log.logWebPage(webPage, log);

            Sceenshot.printscr(webDriver);

            return webPage;
        } catch (Exception e) {
            log.error(LOG_ERROR_SCANNING_SITES, e);
            return null;
        }
    }

    public boolean scanSiteForLandmarks(WebPage webPage, List<ARIALandmark> ariaLandmarks, WebDriver webDriver) {
        try {
            Iterator<ARIALandmark> it = ariaLandmarks.iterator();
            int count = 0;
            while (it.hasNext()) {
                ARIALandmark landmark = it.next();
                List<WebElement> elements = webDriver.findElements(By.cssSelector(
                        new StringBuilder().append("[role=").append(landmark.getRole()).append("]").toString()));
                if (!elements.isEmpty()) {
                    count++;
                    log.info(new StringBuilder().append("# Landmark ").append(landmark.getRole()).append(" found")
                            .toString());
                    Iterator<WebElement> itWe = elements.iterator();
                    while (itWe.hasNext()) {
                        WebElement targetElement = itWe.next();
                        DomElement domElement = new DomElement();
                        domElement.setId(targetElement.getAttribute("id"));
                        domElement.setTagName(targetElement.getTagName());
                        domElement.setWebElement(targetElement);
                        domElement.setAriaLandmark(landmark);
                        webPage.addElement(domElement);
                        this.extractElements(domElement, targetElement);
                    }
                }
            }
            if (count == 0) {
                log.info("No ARIA landmarks was found");
            }
            return true;
        } catch (Exception e) {
            log.error(LOG_ERROR_PROCESSING_SITE, e);
            return false;
        }
    }

    public boolean scanSiteForHTML5Tags(WebPage webPage, List<HTML5Tag> html5Tags, WebDriver webDriver) {
        if (webPage == null || html5Tags == null || webDriver == null) {
            return false;
        }

        int count = 0;
        Iterator<HTML5Tag> it = html5Tags.iterator();
        while (it.hasNext()) {
            HTML5Tag target = it.next();
            List<WebElement> elements;

            switch (target.getARIAEquivalent()) {
                case ARIALandmark.ARIA_BANNER:
                    log.info("Finding HTML5 equivalent to ARIA banner");
                    if (!HTML5TagValidator.validateBanner(target)) {
                        continue;
                    }
                    elements = webDriver.findElements(By.tagName(target.getTag()));
                    break;
                case ARIALandmark.ARIA_COMPLEMENTARY:
                    log.info("Finding HTML5 equivalent to ARIA complementary");
                    if (!HTML5TagValidator.validateComplementary(target)) {
                        continue;
                    }
                    elements = webDriver.findElements(By.tagName(target.getTag()));
                    break;
                case ARIALandmark.ARIA_CONTENTINFO:
                    log.info("Finding HTML5 equivalent to ARIA contentinfo");
                    if (!HTML5TagValidator.validateContentinfo(target)) {
                        continue;
                    }
                    elements = webDriver.findElements(By.tagName(target.getTag()));
                    break;
                case ARIALandmark.ARIA_FORM:
                    log.info("Finding HTML5 equivalent to ARIA form");
                    if (!HTML5TagValidator.validateForm(target)) {
                        continue;
                    }
                    elements = webDriver.findElements(By.tagName(target.getTag()));
                    break;
                case ARIALandmark.ARIA_MAIN:
                    log.info("Finding HTML5 equivalent to ARIA main");
                    if (!HTML5TagValidator.validateMain(target)) {
                        continue;
                    }
                    elements = webDriver.findElements(By.tagName(target.getTag()));
                    break;
                case ARIALandmark.ARIA_NAVIGATION:
                    log.info("Finding HTML5 equivalent to ARIA navigation");
                    if (!HTML5TagValidator.validateNavigation(target)) {
                        continue;
                    }
                    elements = webDriver.findElements(By.tagName(target.getTag()));
                    break;
                case ARIALandmark.ARIA_REGION:
                    log.info("Finding HTML5 equivalent to ARIA region");
                    if (!HTML5TagValidator.validateRegion(target)) {
                        continue;
                    }
                    if (target.getTag() == null) {
                        elements = webDriver.findElements(By.cssSelector(
                                new StringBuilder().append("[role=").append(target.getRole()).append("]").toString()));
                    } else {
                        elements = webDriver.findElements(By.tagName(target.getTag()));
                    }
                    break;
                default:
                    log.error("HTML5 tag without ARIA equivalent");
                    elements = new ArrayList<>();
                    break;
            }

            Iterator<WebElement> itWe = elements.iterator();
            while (itWe.hasNext()) {
                WebElement targetElement = itWe.next();
                DomElement domElement = new DomElement();
                domElement.setId(targetElement.getAttribute("id"));
                domElement.setTagName(targetElement.getTagName());
                domElement.setWebElement(targetElement);
                domElement.setHtml5Tag(target);
                webPage.addElement(domElement);
                this.extractElements(domElement, targetElement);
            }

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

    public void extractElements(DomElement element, WebElement webElement) {
        this.extractElements(element, webElement, 0);
    }

    public void extractElements(DomElement element, WebElement webElement, int level) {
        if (element == null) {
            return;
        }
        // Log.logWebElement(element.getWebElement(), level, log);
        // subElements
        int count = 1;
        List<WebElement> children = webElement.findElements(By.xpath(".//*"));
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

            StringBuilder logLine = new StringBuilder();
            logLine.append("Extracting child (").append(count++).append("/").append(children.size()).append("): ")
                    .append(domElement.getTagName());
            log.info(logLine.toString());
            this.extractElements(domElement, targetElement, level + 1);
        }
    }

}