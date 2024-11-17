window.addEventListener('load', function() {
    const iframe = document.querySelector('iframe');

    function adjustIframeHeight() {
        const iframeDocument = iframe.contentDocument || iframe.contentWindow.document;
        iframe.style.height = iframeDocument.documentElement.scrollHeight + 'px';
    }

    if (iframe.contentDocument) {
        adjustIframeHeight();
    } else {
        iframe.onload = function() {
            adjustIframeHeight();
        };
    }
});
