package io.github.guilhermedelemos.crawler;

import com.google.common.io.Resources;
import io.github.guilhermedelemos.crawler.model.Site;
import io.github.guilhermedelemos.crawler.util.Log;
import io.github.guilhermedelemos.crawler.util.StrategySiteCSV;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Application {

    public static final String GREETING = "Welcome to Accessibility Crawler";
    public static final String FAREWELL = "Done";

    public static void main(String[] args) {
        Application app = new Application();
        app.execute();
    }

    public void execute() {
        Log.log(GREETING);

        List<Site> controlSitesAria = null;
        List<Site> controlSitesHTML5 = null;
        List<Site> alexaSites = null;
        List<Site> alexaSitesBrazil = null;

        try {
            controlSitesAria = (new StrategySiteCSV()).read(Resources.getResource("sites/control-sample-aria.csv").getPath());
            controlSitesHTML5 = (new StrategySiteCSV()).read(Resources.getResource("sites/control-sample-html5.csv").getPath());
            alexaSites = (new StrategySiteCSV()).read(Resources.getResource("sites/alexa-top-50.csv").getPath());
            alexaSitesBrazil = (new StrategySiteCSV()).read(Resources.getResource("sites/alexa-top-50-Brazil.csv").getPath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File not found.");
            System.exit(1);
        }

        Set<Site> setSites = new LinkedHashSet<>();
        setSites.addAll(controlSitesAria);
        setSites.addAll(controlSitesHTML5);
        setSites.addAll(alexaSites);
        setSites.addAll(alexaSitesBrazil);

        List<Site> sites = new ArrayList<>();
        sites.addAll(setSites);

        Crawler crawler = new Crawler();
        boolean execucao = crawler.execute(sites);

        if(execucao) {
            Log.log("Success");
        } else {
            Log.log("Failure");
        }

        Log.log(FAREWELL);
    }

}
