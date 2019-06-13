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
    let elements = $('[role="banner"]');
    elements.each(function(index){
        console.log('==============================');
        console.log('#' + index);
        console.log('', $(this));

        // https://api.jquery.com/visible-selector/
        console.log("Visible:", $(this).is(":visible")); // Considerado visível se possuir wifth e height maior que zero
        // https://api.jquery.com/disabled-selector/ & https://html.spec.whatwg.org/multipage/semantics-other.html#disabled-elements
        console.log("Enabled:", !$(this).is(":disabled"));
        // https://api.jquery.com/position/
        console.log("posLeft:", $(this).position().left); // relativo ao elemento pai
        console.log("posTop:", $(this).position().top); // relativo ao elemento pai
        // http://api.jquery.com/offset/
        console.log("offetLeft:", $(this).offset().left); // referente ao documento
        console.log("offetTop:", $(this).offset().top); // referente ao documento
        console.log("width", $(this).width());
        console.log("height", $(this).height());
        console.log("innerWidth", $(this).innerWidth());
        console.log("innerHeight", $(this).innerHeight());
        console.log("outerWidth", $(this).outerWidth());
        console.log("outerHeight", $(this).outerHeight());
        console.log("childrenCount", $(this).children().length);
        // https://api.jquery.com/length/
        console.log("length", $(this).length);
    });
</script>
```
### JS on console

#### JQuery
`
var script = document.createElement('script');
script.type = 'text/javascript';
script.src = 'https://code.jquery.com/jquery-3.4.1.min.js';
document.head.appendChild(script);
`

## true visibility
`
var scriptV = document.createElement('script');
scriptV.type = 'text/javascript';
scriptV.src = 'https://guilhermedelemos.github.io/accessible/examples/visibility.js';
document.head.appendChild(scriptV);
`


## TODO isVisible()
25;http://fatosdesconhecidos.com.br;2019-01-29;alexa-top-50-brasil
46;http://letras.mus.br;2019-01-29;alexa-top-50-brasil
21;http://cnn.com;2019-01-30;alexa-top-50-usa
28;http://livejasmin.com;2019-01-30;alexa-top-50-usa
34;http://hulu.com;2019-01-30;alexa-top-50-usa
