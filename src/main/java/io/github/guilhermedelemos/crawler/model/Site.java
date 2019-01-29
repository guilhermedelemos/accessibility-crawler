package io.github.guilhermedelemos.crawler.model;

import java.util.Date;

public class Site {

    private int rank;
    private String url;
    private Date data;
    private String urlAfterRequest;
    private int httpStatusCode;

    public Site() {
        super();
    }

    public Site(int rank, String url, Date data) {
        this.rank = rank;
        this.url = url;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Site{" +
                "rank=" + rank +
                ", url='" + url + '\'' +
                ", data=" + data +
                ", urlAfterRequest='" + urlAfterRequest + '\'' +
                ", httpStatusCode=" + httpStatusCode +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Site) {
            return this.url.equals( ((Site) obj).url );
        } else {
            return false;
        }
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getUrlAfterRequest() {
        return urlAfterRequest;
    }

    public void setUrlAfterRequest(String urlAfterRequest) {
        this.urlAfterRequest = urlAfterRequest;
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

}
