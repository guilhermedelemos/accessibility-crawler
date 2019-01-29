package io.github.guilhermedelemos.crawler;

import io.github.guilhermedelemos.crawler.util.Log;

public class Application {

    public static final String GREETING = "Welcome to Accessibility Crawler";
    public static final String FAREWELL = "Done";

    public static void main(String[] args) {
        Log.log(GREETING);
        Log.log(FAREWELL);
    }
}
