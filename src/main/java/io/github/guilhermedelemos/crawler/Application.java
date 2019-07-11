package io.github.guilhermedelemos.crawler;

import com.google.common.io.Resources;
import io.github.guilhermedelemos.crawler.model.Site;
import io.github.guilhermedelemos.crawler.util.CrawlerObject;
import io.github.guilhermedelemos.crawler.util.StrategySiteCSV;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Application extends CrawlerObject {

    public static final String TITLE = "Accessibility Crawler";
    public static final String GREETING = "Starting " + TITLE;
    public static final String FAREWELL = TITLE + " ended";

    public static void main(String[] args) {
        Application app = new Application();
//        app.execute();
        app.executeWithJavaScript();
    }

    public void executeWithJavaScript() {
        log.info(GREETING);

        List<Site> sites = this.loadSites(true);

        Crawler crawler = new Crawler();
        boolean execucao = crawler.executeJavascript(sites);

        if(execucao) {
            log.info("Success");
        } else {
            log.info("Failure");
        }

        log.info(FAREWELL);
    }

    public void execute() {
        log.info(GREETING);

        List<Site> sites = this.loadSites(true);

        Crawler crawler = new Crawler();
        boolean execucao = crawler.execute(sites);

        if(execucao) {
            log.info("Success");
        } else {
            log.info("Failure");
        }

        log.info(FAREWELL);
    }

    public String buildSampleFilePath(String filename) {
        String folder = "sites";
        return String.format("%s%s%s", folder, File.separator, filename);
    }

    public List<Site> loadSites(boolean unique) {
        log.info("Loading sites list");

        List<Site> controlSitesAria;
        List<Site> controlSitesHTML5;
        List<Site> alexaSites;
        List<Site> alexaSitesBrazil;
        List<Site> alexaSitesUsa;
        List<Site> sample;

        try {
            controlSitesAria = (new StrategySiteCSV()).read(Resources.getResource(this.buildSampleFilePath("control-sample-aria.csv")).toURI());
            controlSitesHTML5 = (new StrategySiteCSV()).read(Resources.getResource(this.buildSampleFilePath("control-sample-html5.csv")).toURI());
            alexaSites = (new StrategySiteCSV()).read(Resources.getResource(this.buildSampleFilePath("alexa-top-50.csv")).toURI());
            alexaSitesBrazil = (new StrategySiteCSV()).read(Resources.getResource(this.buildSampleFilePath("alexa-top-50-Brazil.csv")).toURI());
            alexaSitesUsa = (new StrategySiteCSV()).read(Resources.getResource(this.buildSampleFilePath("alexa-top-50-USA.csv")).toURI());
            sample = (new StrategySiteCSV()).read(Resources.getResource(this.buildSampleFilePath("sample.csv")).toURI());
        } catch (Exception e) {
            log.error("Error on loading sites list", e);
            return new ArrayList<>();
        }

        List<Site> sites = new ArrayList<>();

        if(unique) {
            Set<Site> setSites = new LinkedHashSet<>();
//            setSites.addAll(controlSitesAria);
//            setSites.addAll(controlSitesHTML5);
//            setSites.addAll(alexaSites);
//            setSites.addAll(alexaSitesBrazil);
//            setSites.addAll(alexaSitesUsa);
            setSites.addAll(sample);
            sites.addAll(setSites);
        } else {
            sites.addAll(controlSitesAria);
//            sites.addAll(controlSitesHTML5);
//            sites.addAll(alexaSites);
//            sites.addAll(alexaSitesBrazil);
//            sites.addAll(alexaSitesUsa);
        }

        return sites;
    }

}
