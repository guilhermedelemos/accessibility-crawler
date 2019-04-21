const ARIA_LANDMARKS = ['banner', 'complementary', 'contentinfo', 'form', 'main', 'navigation', 'region',
    'search'
];

class Sample {
    constructor({
        id,
        url,
        httpStatusCode,
        elementCount,
        isVisible,
        isEnabled,
        xpath,
        domId,
        tag,
        childrenCount,
        posX,
        posY,
        offsetX,
        offsetY,
        height,
        width,
        innerHeight,
        innerWidth,
        outerHeight,
        outerWidth,
        area,
        classs
    } = {}) {
        this.id = id || null;
        this.url = url || null;
        this.httpStatusCode = httpStatusCode || null;
        this.elementCount = elementCount|| 0;
        this.isVisible = isVisible || false;
        this.isEnabled = isEnabled || false;
        this.xpath = xpath || null;
        this.domId = domId || null;
        this.tag = tag || null;
        this.childrenCount = childrenCount || 0;
        this.posX = posX || 0;
        this.posY = posY || 0;
        this.offsetX = offsetX || 0;
        this.offsetY = offsetY | 0;
        this.height = height || 0;
        this.width = width || 0;
        this.innerHeight = innerHeight || 0;
        this.innerWidth = innerWidth || 0;
        this.outerHeight = outerHeight || 0;
        this.outerWidth = outerWidth || 0;
        this.area = area || 0;
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
        for (let landmark of landmarks) {
            let elements = document.querySelectorAll(`[role="${landmark}"]`);
            if (elements.length < 1) {
                continue;
            }
            let scanResult = this.scanElements(elements, landmark);
            result.push(...scanResult);
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
            if (element.children.length > 0) {
                result.push(...this.scanElements(element.children, landmark));
            }
        }
        return result;
    }

    buildSample(element, sampleClass) {
        let sample = new Sample({
            url: window.location.href,
            domId: element.id,
            tag: element.tagName,
            childrenCount: element.children.length,
            posX: element.offsetLeft,
            posY: element.offsetTop,
            height: element.offsetHeight,
            width: element.offsetWidth,
            area: element.offsetHeight * element.offsetWidth,
            isVisible: element.display != 'none',
            isEnabled: !element.disabled,
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