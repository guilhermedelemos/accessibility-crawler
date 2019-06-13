const ARIA_LANDMARKS = ['banner', 'complementary', 'contentinfo', 'form', 'main', 'navigation', 'region',
    'search'
];
const CLASS_OTHER = 'other';

class Sample {
    constructor({
        id,
        url,
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
        isVisible,
        isEnabled,
        classs
    } = {}) {
        this.id = id || null;
        this.url = url || null;
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

    execute(childrenClass=false, json=true) {
        let result = [];

        let JQueryLoaded = this.isJqueryLoadded();
        if(!JQueryLoaded) {
            console.log("JQuery is not enabled");
            return result;
        }

        let elements = $('body').children();
        if (elements.length < 1) {
            return result;
        }
        let scanResult = this.scanElements(elements, childrenClass);
        result.push(...scanResult);
        if (json) {
            return this.toJSON(result);
        } else {
            return result;
        }
    }

    scanElements(elements, ancestralClass=false) {
        if (elements.length < 1) {
            return [];
        }

        let result = [];
        for (let element of elements) {
            if($(element).is('script') || $(element).is('style')) {
                continue;
            }
            let sample = this.buildSample(element, ancestralClass);
            result.push(sample);
            let elementClass;
            if (element.children.length > 0) {
                if(ancestralClass === true) {
                    elementClass = sample.classs;
                } else if(ancestralClass === false) {
                    elementClass = false;
                } else {
                    elementClass = ancestralClass
                }
                result.push(...this.scanElements(element.children, elementClass));
            }
        }
        return result;
    }

    buildSample(element, elementClass) {
        let sample = new Sample({
            url: this.getReferer(),
            xpath: '',
            domId: this.getElementId(element),
            tag: this.getElementTagName(element),
            childrenCount: this.getElementChildrenCount(element),
            posX: this.getElementPosX(element),
            posY: this.getElementPosY(element),
            offsetX: this.getElementOffsetX(element),
            offsetY: this.getElementOffsetY(element),
            height: this.getElementHeight(element),
            width: this.getElementWidth(element),
            innerHeight: this.getElementInnerHeight(element),
            innerWidth: this.getElementInnerWidth(element),
            outerHeight: this.getElementOuterHeight(element),
            outerWidth: this.getElementOuterWidth(element),
            area: this.getElementArea(element),
            isVisible: this.getElementVisibility(element),
            isEnabled: this.getElementEnabled(element),
            classs: this.getElementClass(element, elementClass)
        });
        return sample;
    }

    getElementClass(element, elementClass) {
        let role = element.getAttribute('role');
        if(elementClass === true || elementClass === false) {
            if(role == undefined || role == null) {
                return CLASS_OTHER;
            }
            let idxRole = ARIA_LANDMARKS.indexOf(role.toLowerCase());
            if(idxRole > -1) {
                return ARIA_LANDMARKS[idxRole];
            } else {
                return CLASS_OTHER;
            }
        } else {
            if(role !== undefined && role !== null) {
                let idxRole = ARIA_LANDMARKS.indexOf(role.toLowerCase());
                if(idxRole > -1) {
                    return ARIA_LANDMARKS[idxRole];
                } else {
                    return elementClass
                }
            } else {
                return elementClass
            }
        }
    }

    getReferer() {
        return window.location.href;
    }

    getElementId(element) {
        return element.id;
    }

    getElementTagName(element) {
        return element.tagName;
    }

    getElementChildrenCount(element) {
        return element.children.length;
    }

    getElementHeight(element) {
        return $(element).innerHeight();
    }

    getElementWidth(element) {
        return $(element).innerWidth();
    }

    getElementInnerHeight(element) {
        return $(element).innerHeight();
    }

    getElementInnerWidth(element) {
        return $(element).innerWidth();
    }

    getElementOuterHeight(element) {
        return $(element).outerHeight();
    }

    getElementOuterWidth(element) {
        return $(element).outerWidth();
    }

    getElementArea(element) {
        return this.getElementHeight(element) * this.getElementWidth(element);
    }

    getElementPosX(element) {
        return $(element).position().left;
    }

    getElementPosY(element) {
        return $(element).position().top;
    }

    getElementOffsetX(element) {
        return $(element).offset().left;
    }

    getElementOffsetY(element) {
        return $(element).offset().top;
    }

    getElementVisibility(element) {
        return element.isVisible();
    }

    getElementEnabled(element) {
        return !$(element).is(":disabled");
    }

    toJSON(obj) {
        return JSON.stringify(obj);
    }
}
