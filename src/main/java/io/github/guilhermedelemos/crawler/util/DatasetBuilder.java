package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.WebPage;
import org.slf4j.Logger;

public class DatasetBuilder extends Thread {

    private WebPage webPage;
    private Logger log;

    public void run(){
        log.info("Thread");
        DatasetCSVStrategy dataset = new DatasetCSVStrategy();
        dataset.createDataset(this.getWebPage());
        log.info("Thread done");
    }

    public WebPage getWebPage() {
        return webPage;
    }

    public void setWebPage(WebPage webPage) {
        this.webPage = webPage;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }
}
