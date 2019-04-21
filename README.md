# Accessibility Crawler

[![Build Status](https://travis-ci.org/guilhermedelemos/accessibility-crawler.svg?branch=master)](https://travis-ci.org/guilhermedelemos/accessibility-crawler)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=io.github.guilhermedelemos.crawler&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.github.guilhermedelemos.crawler)

## Execute

- Run `./gradlew run`
- Test `./gradlew test`

## JavaScript

- var script = document.createElement('script');
- script.type = 'text/javascript';
- script.src = 'https://guilhermedelemos.github.io/accessible/examples/crawler.js';
- document.head.appendChild(script);
- let crawler = new AccessibilityCrawler();
- crawler.searchAriaLandmarks(ARIA_LANDMARKS, false);
- crawler.execute(ARIA_LANDMARKS, false);

## Alexa TOP

- https://www.alexa.com/topsites
- https://scans.io/study/scott-top-one-million

## Alvo

..LANDMARK.....SITES...

- Contentinfo..25
- Navigation...
- Form..........2
- Search.......23

Total sites....37

## Jquery

https://api.jquery.com/jquery.noconflict/

```
<script src="jquery-3.4.0.min.js"></script>

<script>
    console.log("jQuery", $.fn.jquery);
    let element = $("#filho");
    console.log(element);
    // https://api.jquery.com/visible-selector/
    console.log("Visible:", element.is(":visible")); // Considerado visível se possuir wifth e height maior que zero
    // https://api.jquery.com/disabled-selector/ & https://html.spec.whatwg.org/multipage/semantics-other.html#disabled-elements
    console.log("Enabled:", !element.is(":disabled"));
    // https://api.jquery.com/position/
    console.log("posLeft:", element.position().left); // relativo ao elemento pai
    console.log("posTop:", element.position().top); // relativo ao elemento pai
    // http://api.jquery.com/offset/
    console.log("offetLeft:", element.offset().left); // referente ao documento
    console.log("offetTop:", element.offset().top); // referente ao documento
    console.log("width", element.width());
    console.log("height", element.height());
    console.log("innerWidth", element.innerWidth());
    console.log("innerHeight", element.innerHeight());
    console.log("outerWidth", element.outerWidth());
    console.log("outerHeight", element.outerHeight());
    console.log("childrenCount", element.children().length);
    // https://api.jquery.com/length/
    console.log("length", element.length);
</script>
```
