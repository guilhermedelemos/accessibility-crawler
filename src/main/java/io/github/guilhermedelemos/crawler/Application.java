package io.github.guilhermedelemos.crawler;

import com.google.common.io.Resources;
import io.github.guilhermedelemos.crawler.model.Site;
import io.github.guilhermedelemos.crawler.util.CrawlerObject;
import io.github.guilhermedelemos.crawler.util.StrategySiteCSV;

import java.io.IOException;
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
        app.execute();
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

    public List<Site> loadSites(boolean unique) {
        log.info("Loading sites list");

        List<Site> controlSitesAria;
        List<Site> controlSitesHTML5;
        List<Site> alexaSites;
        List<Site> alexaSitesBrazil;
        List<Site> alexaSitesUsa;

        try {
            controlSitesAria = (new StrategySiteCSV()).read(Resources.getResource("sites/control-sample-aria.csv").getPath());
            controlSitesHTML5 = (new StrategySiteCSV()).read(Resources.getResource("sites/control-sample-html5.csv").getPath());
            alexaSites = (new StrategySiteCSV()).read(Resources.getResource("sites/alexa-top-50.csv").getPath());
            alexaSitesBrazil = (new StrategySiteCSV()).read(Resources.getResource("sites/alexa-top-50-Brazil.csv").getPath());
            alexaSitesUsa = (new StrategySiteCSV()).read(Resources.getResource("sites/alexa-top-50-USA.csv").getPath());
        } catch (IOException e) {
            log.error("Error on loading sites list", e);
            return new ArrayList<>();
        }

        List<Site> sites = new ArrayList<>();

        if(unique) {
            Set<Site> setSites = new LinkedHashSet<>();
            setSites.addAll(controlSitesAria);
            setSites.addAll(controlSitesHTML5);
            setSites.addAll(alexaSites);
            setSites.addAll(alexaSitesBrazil);
            setSites.addAll(alexaSitesUsa);

            sites.addAll(setSites);
        } else {
            sites.addAll(controlSitesAria);
            sites.addAll(controlSitesHTML5);
            sites.addAll(alexaSites);
            sites.addAll(alexaSitesBrazil);
            sites.addAll(alexaSitesUsa);
        }

        return sites;
    }

}
