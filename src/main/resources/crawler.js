const ARIA_LANDMARKS = ['banner', 'complementary', 'contentinfo', 'form', 'main', 'navigation', 'region',
    'search'
];
const CLASS_OTHER = 'other';

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
        let sample = {
            url: this.getReferer(),
            //xpath: '',
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
            // count
            tagACountLv1: this.countElements(element, "a", false),
            tagACountAll: this.countElements(element, "a", true),
            tagArticleCountLv1: this.countElements(element, "article", false),
            tagArticleCountAll: this.countElements(element, "article", true),
            tagAsideCountLv1: this.countElements(element, "aside", false),
            tagAsideCountAll: this.countElements(element, "aside", true),
            tagBrCountLv1: this.countElements(element, "br", false),
            tagBrCountAll: this.countElements(element, "br", true),
            tagButtonCountLv1: this.countElements(element, "button", false),
            tagButtonCountAll: this.countElements(element, "button", true),
            tagCanvasCountLv1: this.countElements(element, "canvas", false),
            tagCanvasCountAll: this.countElements(element, "canvas", true),
            tagDivCountLv1: this.countElements(element, "div", false),
            tagDivCountAll: this.countElements(element, "div", true),
            tagFooterCountLv1: this.countElements(element, "footer", false),
            tagFooterCountAll: this.countElements(element, "footer", true),
            tagFormCountLv1: this.countElements(element, "form", false),
            tagFormCountAll: this.countElements(element, "form", true),
            tagH1CountLv1: this.countElements(element, "h1", false),
            tagH1CountAll: this.countElements(element, "h1", true),
            tagH2CountLv1: this.countElements(element, "h2", false),
            tagH2CountAll: this.countElements(element, "h2", true),
            tagH3CountLv1: this.countElements(element, "h3", false),
            tagH3CountAll: this.countElements(element, "h3", true),
            tagH4CountLv1: this.countElements(element, "h4", false),
            tagH4CountAll: this.countElements(element, "h4", true),
            tagH5CountLv1: this.countElements(element, "h5", false),
            tagH5CountAll: this.countElements(element, "h5", true),
            tagH6CountLv1: this.countElements(element, "h6", false),
            tagH6CountAll: this.countElements(element, "h6", true),
            tagHeaderCountLv1: this.countElements(element, "header", false),
            tagHeaderCountAll: this.countElements(element, "header", true),
            tagHrCountLv1: this.countElements(element, "hr", false),
            tagHrCountAll: this.countElements(element, "hr", true),
            tagIframeCountLv1: this.countElements(element, "iframe", false),
            tagIframeCountAll: this.countElements(element, "iframe", true),
            tagImgCountLv1: this.countElements(element, "img", false),
            tagImgCountAll: this.countElements(element, "img", true),
            tagInputCountLv1: this.countElements(element, "input", false),
            tagInputCountAll: this.countElements(element, "input", true),
            tagLabelCountLv1: this.countElements(element, "label", false),
            tagLabelCountAll: this.countElements(element, "label", true),
            tagLiCountLv1: this.countElements(element, "li", false),
            tagLiCountAll: this.countElements(element, "li", true),
            tagMainCountLv1: this.countElements(element, "main", false),
            tagMainCountAll: this.countElements(element, "main", true),
            tagNavCountLv1: this.countElements(element, "nav", false),
            tagNavCountAll: this.countElements(element, "nav", true),
            tagObjectCountLv1: this.countElements(element, "object", false),
            tagObjectCountAll: this.countElements(element, "object", true),
            tagOlCountLv1: this.countElements(element, "ol", false),
            tagOlCountAll: this.countElements(element, "ol", true),
            tagPCountLv1: this.countElements(element, "p", false),
            tagPCountAll: this.countElements(element, "p", true),
            tagSectionCountLv1: this.countElements(element, "section", false),
            tagSectionCountAll: this.countElements(element, "section", true),
            tagSelectCountLv1: this.countElements(element, "select", false),
            tagSelectCountAll: this.countElements(element, "select", true),
            tagSmallCountLv1: this.countElements(element, "small", false),
            tagSmallCountAll: this.countElements(element, "small", true),
            tagStrongCountLv1: this.countElements(element, "strong", false),
            tagStrongCountAll: this.countElements(element, "strong", true),
            tagSubCountLv1: this.countElements(element, "sub", false),
            tagSubCountAll: this.countElements(element, "sub", true),
            tagSupCountLv1: this.countElements(element, "sup", false),
            tagSupCountAll: this.countElements(element, "sup", true),
            tagSvgCountLv1: this.countElements(element, "svg", false),
            tagSvgCountAll: this.countElements(element, "svg", true),
            tagTableCountLv1: this.countElements(element, "table", false),
            tagTableCountAll: this.countElements(element, "table", true),
            tagTextareaCountLv1: this.countElements(element, "textarea", false),
            tagTextareaCountAll: this.countElements(element, "textarea", true),
            tagUlCountLv1: this.countElements(element, "ul", false),
            tagUlCountAll: this.countElements(element, "ul", true),
            // class
            classs: this.getElementClass(element, elementClass)
        };
        return sample;
    }

    countElements(element, target, inDepth=false) {
        if(!element || !target) {
            return 0;
        }
        if(inDepth) {
            return $(element).find(target).length;
        } else {
            return $(element).children(target).length;
        }
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
