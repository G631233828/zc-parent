var model = { //全平台自定义方法汇总
    innerW: document.documentElement.clientWidth, //当前网页可见区域宽度
    innerH: document.documentElement.clientHeight, //当前网页可见区域高度
    scrollH: "",
    list_change: function() { //标签页切换方法~~
        $(".click_list").hide();
        for (var i = 1; i <= $(".click_list_parent").length; i++) {
            (function() {
                var temp = i;
                $("#click_list_parent" + temp + "").click(function() {
                    $("#click_list_child" + temp + "").show().siblings().hide();
                })
            })(i)
        }
    },
    show_less: function(idOrClass, num) { //字数限制方法，
        for (var i = 0; i < $("" + idOrClass + "").length; i++) {
            if ($("" + idOrClass + "")[i].innerText.length > num) {
                $("" + idOrClass + "")[i].innerHTML = $("" + idOrClass + "")[i].innerText.substr(0, num) + "...";
            }
        }
    },
    alert: function(text, time) {
        var $info = '<div id="tishi" style="border-radius:20px;word-break: keep-all;left: 50%;-webkit-transform: translateX(-50%);transform: translateX(-50%);-ms-transform: translateX(-50%);-moz-transform:translateX(-50%);display:none;-moz-border-radius:20px;-webkit-border-radius:20px;color: white;padding: 10px 10px;background-color: rgba(0,0,0,0.7);position: fixed;bottom: 10%;">' + text + '</div>';
        $("body").append($info);
        $("#tishi").fadeIn(800);
        setTimeout(function() {
            $("#tishi").fadeOut(1000);
            $("#tishi").remove();
        }, time * 1000);
    },
    confirm: function(width, text_head, text_info, callback_yes, callback_no) { //自定义确定取消框
        this.scrollH = document.body.scrollHeight;
        $("body").append('<div class="md_tanchu_mask" id="md_tanchu_mask"></div>');
        $("body").append('<div class="md_tanchu_del" id="md_tanchu_del"><div class="mb_tanchu_head mb_color_green" style="margin-bottom:15px;">' + text_head + '<img src="/assets/images/clo.png" class="right mb_hover mb_margint10 mb_marginr10" id="md_close"/></div><div class="md_content">' + text_info + '</div><div class="mb_tanchu_res"><a class="md_tanchu_yes" id="md_tanchu_yes">确定</a><a class="md_tanchu_no" id="md_tanchu_no">取消</a></div></div>');
        $("#md_tanchu_del").css("width", width + "px");
        $("#md_tanchu_mask").css("height", this.scrollH);
        $("#md_tanchu_del").css("left", (this.innerW - $("#md_tanchu_del").width()) / 2 + "px").css("top", (this.innerH - $("#md_tanchu_del").height()) / 2 + "px");
        $("#md_tanchu_yes").click(function() {
            if (callback_yes() == undefined) {
                $("#md_tanchu_mask").remove();
                $("#md_tanchu_del").remove();
            }
        })
        $("#md_tanchu_no").click(function() {
            $("#md_tanchu_mask").remove();
            $("#md_tanchu_del").remove();
        })
        $("#md_close").click(function() {
            $("#md_tanchu_mask").remove();
            $("#md_tanchu_del").remove();
        })
    },
    wordfilter: function() { //敏感词过滤
        $("input.wordfilter").on("blur", function() {
            var _str = $(this).val();
            var result = _str.diyFilter();
            if (_str.diyFilterArr().length > 0) {
                var _diy_str = _str.diyFilterArr().join(",");
                alert("字段中含有不恰当的词语：" + _diy_str + ", 系统已自动为你修改!");
                $(this).val(result);
            }
        });
        $("textarea.wordfilter").on("blur", function() {
            var _str = $(this).val();
            var result = _str.diyFilter();
            if (_str.diyFilterArr().length > 0) {
                var _diy_str = _str.diyFilterArr().join(",");
                alert("字段中含有不恰当的词语：" + _diy_str + ", 系统已自动为你修改!");
                $(this).val(result);
            }
        });
        var data = "";
        $.ajax({
            url: "#",
            type: "get",
            success: function(result) {
                data = result;
            }
        });
        String.prototype.diyFilter = function(arr) {
            if (!arr) {
                arr = data.split("|");
            }
            var _this = this;
            arr.forEach(function(a, b, c) {
                var _reg = new RegExp(a, "g");
                _this = _this.replace(_reg, "***");
            });
            return _this;
        }
        String.prototype.diyFilterArr = function(arr) { //此方法返回过滤的词语数组
            if (!arr) {
                arr = data.split("|");
            }
            var _this = this;
            var _arrFilter = [];
            arr.forEach(function(a, b, c) {
                if (_this.indexOf(a) > -1) {
                    _arrFilter.push(a)
                }
            });
            return _arrFilter
        }
    }
};
model.wordfilter();

function getTimeDay($time1, $time2) { //计算两个日期之间相差几天方法
    var time1 = arguments[0],
        time2 = arguments[1];
    time1 = Date.parse(time1) / 1000;
    time2 = Date.parse(time2) / 1000;
    var time_ = time1 - time2;
    return (time_ / (3600 * 24));
}

function getToday() { //获取当天的日期
    var dd = new Date();
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1;
    if (m < 10) m = "0" + m;
    var d = dd.getDate();
    if (d < 10) d = "0" + d;
    return y + "-" + m + "-" + d;
}

/*提示框*/
function showTips(txt, time, status) {
    var htmlCon = '';
    if (txt != '') {
        if (status != 0 && status != undefined) {
            htmlCon = '<div class="tipsBox" style="width:220px;padding:10px;background-color:#4AAF33;border-radius:4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;color:#fff;box-shadow:0 0 3px #ddd inset;-webkit-box-shadow: 0 0 3px #ddd inset;text-align:center;position:fixed;top:25%;left:50%;z-index:999999;margin-left:-120px;"><img src="/assets/images/ok.png" style="vertical-align: middle;margin-right:5px;" alt="OK，"/>' + txt + '</div>';
        } else {
            htmlCon = '<div class="tipsBox" style="width:220px;padding:10px;background-color:#D84C31;border-radius:4px;-webkit-border-radius: 4px;-moz-border-radius: 4px;color:#fff;box-shadow:0 0 3px #ddd inset;-webkit-box-shadow: 0 0 3px #ddd inset;text-align:center;position:fixed;top:25%;left:50%;z-index:999999;margin-left:-120px;"><img src="/assets/images/err.png" style="vertical-align: middle;margin-right:5px;" alt="Error，"/>' + txt + '</div>';
        }
        $('body').prepend(htmlCon);
        if (time == '' || time == undefined) {
            time = 1500;
        }
        setTimeout(function() {
            $('.tipsBox').remove();
        }, time);
    }
}
/*鼠标滑动边框变色*/
function border_color_change(className, color, color_before) {
    $("." + className).hover(
        function() {
            $(this).css("border-color", color);
        },
        function() {
            $(this).css("border-color", color_before);
        });
}

/*鼠标滑动字体变色*/
function color_change(className, color, color_before) {
    $("." + className).hover(
        function() {
            $(this).css("color", color);
        },
        function() {
            $(this).css("color", color_before);
        });
}
/*鼠标滑动背景变色*/
function background_color_change(className, color, color_before) {
    $("." + className).hover(
        function() {
            $(this).css("background-color", color);
        },
        function() {
            $(this).css("background-color", color_before);
        });
}
//处理图片找不到的
//$(document).ready(function() {
//    $("img").each(function() {
//        $('img').error(function() {
//            if (window.location.host.indexOf('xdgz') >= 0) {
//                $(this).attr('src', "/assets/portal/wxxdgjzx/images/newban_default.jpg");
//            } else {
//                $(this).attr('src', "/assets/portal/global/images/newban_default.png");
//            }
//        })
//    });
//    turn_to_grayscale(1);
//});
//document.write("<script src='/assets/js/core/crypto-js.js'></script>");
var aseKey = "132456abcde"
let secret = ['password'];
const storage = {
    setItem: function(name, value) {
        localStorage.setItem(name, secret.indexOf(name) > -1 ? CryptoJS.AES.encrypt(value, aseKey).toString() : value)
    },
    getItem: function(name) {
        return secret.indexOf(name) > -1 && localStorage.getItem(name) !== null ? CryptoJS.AES.decrypt(localStorage.getItem(name), aseKey).toString(CryptoJS.enc.Utf8) || localStorage.getItem(name) : localStorage.getItem(name)
    },
    removeItem: function(name) {
        localStorage.removeItem(name)
    },
    clear: function() {
        localStorage.clear();
    }
};
// $(document).ready(function(){
// 	setTimeout(function(){
//     var Text=$('#weather').children().text();
// 	var Cts = "undefined";
// 	if(Cts.indexOf("Text") >= -1 )
// 	{
// 	   $('#weather').text("暂无天气数据");
// 	}
//    },1000);

// });
/**
 * @description 给网页添加灰度
 * @param {Object} value - 灰度
 */
function turn_to_grayscale(value) {
    var domain = ['yanshi', 'wxtjxx', 'wxgxxx', 'wxzqxx', 'wxhqsy', 'stwxx', 'wxsxyey', 'thsscyey', 'wzqedu']; // 域名
    var date = ['2020/04/04']; // 日期
    domain.forEach(function(item) {
        if (window.location.host.indexOf(item) >= 0 && 'Sat Apr 04 2020'.indexOf(new Date().toDateString()) >= 0) {
            document.querySelector('body').style.filter = 'grayscale(' + value + ')'
        }
    })
}