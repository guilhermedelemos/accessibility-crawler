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

    isJqueryLoadded() {
        return !!window.jQuery;
    }

    findElements(landmark, jquery) {
        if(jquery) {
            return $(`[role="${landmark}"]`);
        } else {
            return document.querySelectorAll(`[role="${landmark}"]`);
        }
    }

    execute(landmarks, jquery = true, json = true) {
        let result = [];
        
        let JQueryLoaded = this.isJqueryLoadded();
        if(!JQueryLoaded && jquery) {
            console.log("JQuery is not enabled");
            return result;
        }        

        for (let landmark of landmarks) {
            let elements = this.findElements(landmark, jquery);
            if (elements.length < 1) {
                continue;
            }
            let scanResult = this.scanElements(elements, landmark, jquery);
            result.push(...scanResult);
        }
        if (json) {
            return this.toJSON(result);
        } else {
            return result;
        }
    }

    scanElements(elements, landmark, jquery) {
        if (elements.length < 1) {
            return [];
        }
        let result = [];
        for (let element of elements) {
            let sample = this.buildSample(element, landmark, jquery);
            result.push(sample);
            if (element.children.length > 0) {
                result.push(...this.scanElements(element.children, landmark, jquery));
            }
        }
        return result;
    }

    buildSample(element, sampleClass, jquery) {
        let sample = new Sample({
            url: this.getReferer(),
            elementCount: 0,
            xpath: '',
            domId: this.getElementId(element, jquery),
            tag: this.getElementTagName(element, jquery),
            childrenCount: this.getElementChildrenCount(element, jquery),
            posX: this.getElementPosX(element, jquery),
            posY: this.getElementPosY(element, jquery),
            offsetX: this.getElementOffsetX(element, jquery),
            offsetY: this.getElementOffsetY(element, jquery),
            height: this.getElementHeight(element, jquery),
            width: this.getElementWidth(element, jquery),
            innerHeight: 0,
            innerWidth: 0,
            outerHeight: 0,
            outerWidth: 0,
            area: this.getElementArea(element, jquery),
            isVisible: this.getElementVisibility(element, jquery),
            isEnabled: this.getElementEnabled(element, jquery),
            classs: sampleClass
        });
        return sample;
    }

    getReferer() {
        return window.location.href;
    }

    getElementId(element, jquery=true) {
        if(jquery) {
            return $(element).attr("id");
        } else {
            return element.id;
        }
    }

    getElementTagName(element, jquery=true) {
        if(jquery) {
            return $(element).prop("tagName");
        } else {
            return element.tagName;
        }
    }

    getElementChildrenCount(element, jquery=true) {
        if(jquery) {
            return $(element).children().length;
        } else {
            return element.children.length;
        }        
    }

    getElementHeight(element, jquery=true) {
        if(jquery) {
            return $(element).innerHeight();
        } else {
            return element.offsetHeight;
        }        
    }

    getElementWidth(element, jquery=true) {
        if(jquery) {
            return $(element).innerWidth();
        } else {
            return element.offsetWidth;
        }        
    }

    getElementArea(element) {
        return this.getElementHeight(element) * this.getElementWidth(element);
    }

    getElementPosX(element, jquery=true) {
        if(jquery) {
            return $(element).position().left;
        } else {
            return element.offsetLeft;
        }
    }

    getElementPosY(element, jquery=true) {
        if(jquery) {
            return $(element).position().top;
        } else {
            return element.offsetTop;
        }
    }

    getElementOffsetX(element, jquery=true) {
        if(jquery) {
            return $(element).offset().left;
        } else {
            return element.offsetTop;
        }
    }

    getElementOffsetY(element, jquery=true) {
        if(jquery) {
            return $(element).offset().top;
        } else {
            return element.offsetTop;
        }
    }

    getElementVisibility(element, jquery=true) {
        if(jquery) {
            return $(element).is(":visible")
        } else {
            return element.display != 'none';
        }        
    }

    getElementEnabled(element, jquery=true) {
        if(jquery) {
            return !$(element).is(":disabled");
        } else {
            return !element.disabled;
        }        
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
