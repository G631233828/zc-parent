$(function() {
    // 模块初始化
    ;
    (function($) {
        setTimeout(function() {
            $('#_ideConac', '.footerMobile').eq(0).html($('#_ideConac', '.footer').html());
        }, 5e2);
        // 手机端导航弹窗
        $(".topContainerMobile .nav").click(function() {
            $(".popup").fadeIn();
            $(".closebtn").click(function() {
                $(".popup").fadeOut()
            });
        });
        // 导航
        $(".one li").hover(function() {
            $(this).children("ul").show();
        }, function() {
            $(this).children("ul").hide();
        })
        $(".one_en li").hover(function() {
            $(this).children("ul").show();
        }, function() {
            $(this).children("ul").hide();
        });

        // Banner
        $.fn.revolution && $('.site-banner-cont', '.site-banner').each(function() {
            $(this).revolution({
                delay: $(this).data('bannerTime') || 4000,
                startheight: 600,
                startwidth: 1024,

                hideThumbs: 200,

                thumbWidth: 100, // Thumb With and Height and Amount (only if navigation Tyope set to thumb !)
                thumbHeight: 50,
                thumbAmount: 5,

                navigationType: "bullet", //bullet, thumb, none, both       (No Thumbs In FullWidth Version !)
                navigationArrows: "verticalcentered", //nexttobullets, verticalcentered, none
                navigationStyle: "round", //round,square,navbar

                touchenabled: "on", // Enable Swipe Function : on/off
                onHoverStop: "on", // Stop Banner Timet at Hover on Slide on/off

                navOffsetHorizontal: 0,
                navOffsetVertical: 0,

                stopAtSlide: -1,
                stopAfterLoops: -1,

                shadow: 0, //0 = no Shadow, 1,2,3 = 3 Different Art of Shadows  (No Shadow in Fullwidth Version !)
                fullWidth: "on" // Turns On or Off the Fullwidth Image Centering in FullWidth Modus
            });
        });

        // 图片轮播
        $.fn.responsiveSlides && $('.rslides', '.site-imgplay').responsiveSlides({
            auto: true,
            pager: false,
            nav: true,
            random: false,
            pause: true,
            prevText: '<',
            nextText: '>',
            // maxwidth: '',
            namespace: "rslides",
            speed: 500,
            timeout: 4000
        });

        // 文字滚动
        $.fn.Scroll && $('.site-scroll', '.site-word').each(function() {
            $(this).Scroll({
                line: 1,
                speed: 500,
                timer: 3000,
                type: $(this).data('scrollType') || 'up'
            });
        });

        window.ckplayer && $('.site-media-video', '.site-media').each(function() {
            var $this = $(this),
                $list = $this.find('.site-media-list'),
                $content = $this.find('.site-media-content'),
                _width = $this.width() * 0.4,
                _id = ($content.data('modelId') || $.now()).replace(/-/gi, ''),
                _variable = ('videoPlayer' + _id),
                _loadedHandler = ('loadedHandler' + _id);
            $content.attr('id', _id);
            // 播放器加载成功函数
            window[_loadedHandler] = function() {
                //监听播放结束
                window[_variable].addListener('ended', function() {
                    var $next = $list.find('li.playing').next();
                    if (!$next.length) {
                        $next = $list.find('li:first');
                    }
                    $next.triggerHandler('click');
                });
            };
            $this.find('.site-media-list').css({
                width: _width,
                right: '-' + (_width) + 'px',
            }).find('.site-media-listjian').click(function() {
                var $this = $(this);
                $list.stop(true, true);
                if ($this.data('show')) {
                    $this.data('show', false).attr('title', '展开');
                    $list.css({
                        right: '-' + (_width) + 'px'
                    });
                } else {
                    $this.data('show', true).attr('title', '收起');
                    $list.css({
                        right: '0px'
                    });
                }
            }).end().find('li').click(function() {
                var _src = $(this).addClass('playing').siblings('.playing').removeClass('playing').end().data('src');
                window[_variable] = new ckplayer({
                    container: ('#' + _id), //容器的ID或className
                    variable: _variable, //播放函数名称
                    // poster: '../material/poster.jpg',//封面图片
                    flashplayer: false, //如果强制使用flashplayer则设置成true
                    autoplay: true,
                    loaded: _loadedHandler, //当播放器加载后执行的函数
                    mobileCkControls: true, //是否在移动端（包括ios）环境中显示控制栏
                    mobileAutoFull: false, //在移动端播放后是否按系统设置的全屏播放
                    h5container: '#videoplayer', //h5环境中使用自定义容器
                    video: {
                        file: _src,
                        type: 'video/mp4'
                    }
                });
            });
            if ($list.find('li').length > 1) {
                $list.show();
            }
            $list.find('li:first').triggerHandler('click');
        });

    }(jQuery));
});