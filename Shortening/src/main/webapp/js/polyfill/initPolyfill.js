//IE 일때만 polyfill.js 추가
//if (navigator.userAgent.indexOf('Trident/') > -1) {
    const script = document.createElement('script');
    script.setAttribute('src', '/js/polyfill/polyfill.min.js');
    script.setAttribute('defer', '');

    document.querySelector('head script[src="/js/polyfill/initPolyfill.js"]').insertAdjacentElement('afterend', script);
//}
