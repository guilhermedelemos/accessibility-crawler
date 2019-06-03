# Accessibility Crawler

[![Build Status](https://travis-ci.org/guilhermedelemos/accessibility-crawler.svg?branch=master)](https://travis-ci.org/guilhermedelemos/accessibility-crawler)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.guilhermedelemos.crawler&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.guilhermedelemos.crawler)

## Execute

### Java
- Run `./gradlew run`
- Test `./gradlew test`

### JavaScript

~~~
<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"></script>
<script src="visibility.js"></script>
<script src="crawler.js"></script>
<script>
    const crawler = new Crawler();
    const result = crawler.execute();
</script>
~~~

## Alexa TOP sites

- https://www.alexa.com/topsites
- https://scans.io/study/scott-top-one-million
