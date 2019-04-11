# Accessibility Crawler
[![Build Status](https://travis-ci.org/guilhermedelemos/accessibility-crawler.svg?branch=master)](https://travis-ci.org/guilhermedelemos/accessibility-crawler)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.guilhermedelemos.crawler&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.guilhermedelemos.crawler)

## Execute
* Run `./gradlew run`
* Test `./gradlew test`

## JavaScript
* var script = document.createElement('script');
* script.type = 'text/javascript';
* script.src = 'https://guilhermedelemos.github.io/accessible/examples/crawler.js';
* document.head.appendChild(script);
* let crawler = new AccessibilityCrawler();
* crawler.searchAriaLandmarks(ARIA_LANDMARKS, false);
* crawler.execute(ARIA_LANDMARKS, false);