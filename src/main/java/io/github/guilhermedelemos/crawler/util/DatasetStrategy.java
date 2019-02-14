package io.github.guilhermedelemos.crawler.util;

import io.github.guilhermedelemos.crawler.model.WebPage;

import java.util.List;

public abstract class DatasetStrategy extends CrawlerObject {

    public abstract boolean createDataset(List<WebPage> webPages, String outputFile);

}
