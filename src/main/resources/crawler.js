const ARIA_LANDMARKS = ['banner', 'complementary', 'contentinfo', 'form', 'main', 'navigation', 'region',
    'search'
];

class Sample {
    constructor({
        id,
        url,
        httpStatusCode,
        qtdeElementosPagina,
        xpath,
        domId,
        tag,
        qtdeFilhos,
        posX,
        posY,
        height,
        width,
        area,
        visible,
        enabled,
        classs
    } = {}) {
        this.id = id || null;
        this.url = url || null;
        this.httpStatusCode = httpStatusCode || null;
        this.qtdeElementosPagina = qtdeElementosPagina || null;
        this.xpath = xpath || null;
        this.domId = domId || null;
        this.tag = tag || null;
        this.qtdeFilhos = qtdeFilhos || null;
        this.posX = posX || null;
        this.posY = posY || null;
        this.height = height || null;
        this.width = width || null;
        this.area = area || null;
        this.visible = visible || null;
        this.enabled = enabled || null;
        this.classs = classs || null;
    }
}

class SearchSample {
    constructor({
        landmark,
        exists,
        count
    } = {}) {
        this.landmark = landmark || null;
        this.exists = exists || false;
        this.count = count || 0;
    }
}

class AccessibilityCrawler {

    execute(landmarks, json = true) {
        let result = [];
        let counter = 0;
        for (let landmark of landmarks) {
            // console.log('LANDMARK', landmark);
            let elements = document.querySelectorAll(`[role="${landmark}"]`);
            if (elements.length < 1) {
                continue;
            }
            result.push(...this.scanElements(elements, landmark));
        }
        if (json) {
            return this.toJSON(result);
        } else {
            return result;
        }
    }

    scanElements(elements, landmark) {
        if (elements.length < 1) {
            return [];
        }
        let result = [];
        for (let element of elements) {
            let sample = this.buildSample(element, landmark);
            result.push(sample);
            // console.log("sample", sample);
            if (element.children.length > 0) {
                result.push(...this.scanElements(element.children));
            }
        }
        return result;
    }

    buildSample(element, sampleClass) {
        let sample = new Sample({
            // id: counter,
            // qtdeElementosPagina: elements.length,
            url: window.location.href,
            // httpStatusCode,
            // xpath,
            domId: element.id,
            tag: element.tagName,
            qtdeFilhos: element.children.length,
            posX: element.offsetLeft,
            posY: element.offsetTop,
            height: element.offsetHeight,
            width: element.offsetWidth,
            area: element.offsetHeight * element.offsetWidth,
            visible: element.display != 'none', // visibility: hidden
            enabled: !element.disabled,
            classs: sampleClass
        });
        return sample;
    }

    searchAriaLandmarks(ariaLandmarks) {
        let result = [];
        for (let landmark of ariaLandmarks) {
            let elements = document.querySelectorAll(`[role="${landmark}"]`);
            let sample = new SearchSample({
                landmark: landmark,
                exists: elements.length > 0,
                count: elements.length
            });
            result.push(sample);
        }
        return result;
    }

    toJSON(obj) {
        return JSON.stringify(obj);
    }
}
// let crawler = new AccessibilityCrawler();
// let landmarks = crawler.searchAriaLandmarks(ARIA_LANDMARKS);
// console.log('Landmarks', landmarks);

// let samples = crawler.execute(ARIA_LANDMARKS, false);
// console.log('Samples', samples);