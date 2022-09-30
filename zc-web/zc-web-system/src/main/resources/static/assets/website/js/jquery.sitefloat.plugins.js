;
(function($) {
    $.fn.floats = function() {
        return this.each(function(i, el) {
            var $this = $(this).append($('<div/>', {
                text: '×',
                'class': 'floatclose',
                title: '关闭',
                css: {
                    width: 20,
                    height: 20,
                    position: 'absolute',
                    top: '0px',
                    right: '0px',
                    background: 'rgba(255, 255, 255, 0.5)',
                    textAlign: 'center',
                    cursor: 'pointer'
                }
            }).click(function() {
                $(this).closest('.site-float').remove();
            }));

            function addEvent(obj, evtType, func, cap) {
                cap = cap || false;
                if (obj.addEventListener) {
                    obj.addEventListener(evtType, func, cap);
                    return true;
                } else if (obj.attachEvent) {
                    if (cap) {
                        obj.setCapture();
                        return true;
                    } else {
                        return obj.attachEvent("on" + evtType, func);
                    }
                } else {
                    return false;
                }
            }

            function getPageScroll() {
                var xScroll, yScroll;
                if (self.pageXOffset) {
                    xScroll = self.pageXOffset;
                } else if (document.documentElement && document.documentElement.scrollLeft) {
                    xScroll = document.documentElement.scrollLeft;
                } else if (document.body) {
                    xScroll = document.body.scrollLeft;
                }
                if (self.pageYOffset) {
                    yScroll = self.pageYOffset;
                } else if (document.documentElement && document.documentElement.scrollTop) {
                    yScroll = document.documentElement.scrollTop;
                } else if (document.body) {
                    yScroll = document.body.scrollTop;
                }
                arrayPageScroll = new Array(xScroll, yScroll);
                return arrayPageScroll;
            }

            function GetPageSize() {
                var xScroll, yScroll;
                if (window.innerHeight && window.scrollMaxY) {
                    xScroll = document.body.scrollWidth;
                    yScroll = window.innerHeight + window.scrollMaxY;
                } else if (document.body.scrollHeight > document.body.offsetHeight) {
                    xScroll = document.body.scrollWidth;
                    yScroll = document.body.scrollHeight;
                } else {
                    xScroll = document.body.offsetWidth;
                    yScroll = document.body.offsetHeight;
                }
                var windowWidth, windowHeight;
                if (self.innerHeight) {
                    windowWidth = self.innerWidth;
                    windowHeight = self.innerHeight;
                } else if (document.documentElement && document.documentElement.clientHeight) {
                    windowWidth = document.documentElement.clientWidth;
                    windowHeight = document.documentElement.clientHeight;
                } else if (document.body) {
                    windowWidth = document.body.clientWidth;
                    windowHeight = document.body.clientHeight;
                }
                if (yScroll < windowHeight) {
                    pageHeight = windowHeight;
                } else {
                    pageHeight = yScroll;
                }
                if (xScroll < windowWidth) {
                    pageWidth = windowWidth;
                } else {
                    pageWidth = xScroll;
                }
                arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight)
                return arrayPageSize;
            }

            var AdMoveConfig = new Object();
            AdMoveConfig.IsInitialized = false;
            AdMoveConfig.ScrollX = 0;
            AdMoveConfig.ScrollY = 0;
            AdMoveConfig.MoveWidth = 0;
            AdMoveConfig.MoveHeight = 0;
            AdMoveConfig.Resize = function() {
                var winsize = GetPageSize();
                AdMoveConfig.MoveWidth = winsize[2];
                AdMoveConfig.MoveHeight = winsize[3];
                AdMoveConfig.Scroll();
            }
            AdMoveConfig.Scroll = function() {
                var winscroll = getPageScroll();
                AdMoveConfig.ScrollX = winscroll[0];
                AdMoveConfig.ScrollY = winscroll[1];
            }
            addEvent(window, "resize", AdMoveConfig.Resize);
            addEvent(window, "scroll", AdMoveConfig.Scroll);

            function AdMove(options) {
                if (!AdMoveConfig.IsInitialized) {
                    AdMoveConfig.Resize();
                    AdMoveConfig.IsInitialized = true;
                }
                var obj = $this.get(0);
                obj.style.position = "absolute";
                var W = (AdMoveConfig.MoveWidth - obj.offsetWidth),
                    H = (AdMoveConfig.MoveHeight - obj.offsetHeight),
                    x = W * Math.random(),
                    y = H * Math.random(),
                    rad = (Math.random() + 1) * Math.PI / 6,
                    kx = Math.sin(rad),
                    ky = Math.cos(rad),
                    dirx = (Math.random() < 0.5 ? 1 : -1),
                    diry = (Math.random() < 0.5 ? 1 : -1),
                    step = (options.step ? options.step : 1),
                    interval, _this = this;

                this.SetLocation = function(vx, vy) {
                    x = vx;
                    y = vy;
                }
                this.SetDirection = function(vx, vy) {
                    dirx = vx;
                    diry = vy;
                }
                obj.CustomMethod = function() {
                    obj.style.left = (x + AdMoveConfig.ScrollX) + "px";
                    obj.style.top = (y + AdMoveConfig.ScrollY) + "px";
                    rad = (Math.random() + 1) * Math.PI / 6;
                    W = AdMoveConfig.MoveWidth - obj.offsetWidth;
                    H = AdMoveConfig.MoveHeight - obj.offsetHeight;
                    x = x + step * kx * dirx;
                    if (x < 0) {
                        dirx = 1;
                        x = 0;
                        kx = Math.sin(rad);
                        ky = Math.cos(rad);
                    }
                    if (x > W) {
                        dirx = -1;
                        x = W;
                        kx = Math.sin(rad);
                        ky = Math.cos(rad);
                    }
                    y = y + step * ky * diry;
                    if (y < 0) {
                        diry = 1;
                        y = 0;
                        kx = Math.sin(rad);
                        ky = Math.cos(rad);
                    }
                    if (y > H) {
                        diry = -1;
                        y = H;
                        kx = Math.sin(rad);
                        ky = Math.cos(rad);
                    }
                }

                ;
                (function(_this, obj, interval, options) {
                    var delay = (options.delay ? options.delay : 30);
                    interval = setInterval(obj.CustomMethod, delay);
                    obj.onmouseover = function() {
                        clearInterval(interval);
                        /*$this.find('.floatclose').stop(true, true).animate({
                            opacity: '1'
                        });*/
                    }
                    obj.onmouseout = function() {
                        interval = setInterval(obj.CustomMethod, delay);
                        /*$this.find('.floatclose').stop(true, true).animate({
                            opacity: '0'
                        });*/
                    }
                }(_this, obj, interval, options));
            }

            setTimeout(function() {
                new AdMove({
                    step: 2,
                    delay: 20
                });
            }, i > 0 ? 2000 * i : 1);
        });
    };
}(jQuery));

$(function() {
    var _siteId = $('script[src*="jquery.sitefloat.plugins.js"]').data('siteId');
    if (!_siteId || location.href.indexOf('/' + _siteId + '/layout.html') != -1) {
        return false;
    }
    $.post('/api/po/nologin/news/floats', {
        siteId: _siteId
    }, function(res) {
        if (200 != res.status) {
            return false;
        }
        $.each(res.data || [], function(i, news) {
            if (!news.cover) {
                return true;
            }
            $('body').prepend('\
                <div id="' + news.id + '" class="site-float" style="z-index: 9999;position: absolute;padding-top: 20px;">\
                    <a target="_blank" href="' + news.url + '" title="' + news.name + '">\
                        <img src="/api/storage/show/thumbnail/' + news.cover + '"/>\
                    </a>\
                </div>');
        });
        $('.site-float').floats();
    }, 'json');
});