package io.github.guilhermedelemos.crawler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CrawlerObject {

    protected Logger log;

    public CrawlerObject() {
        this.log = LoggerFactory.getLogger(this.getClass().getName());
    }

}
