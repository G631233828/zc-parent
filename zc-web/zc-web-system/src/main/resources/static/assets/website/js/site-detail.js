var $script = $('script[src*="site-detail.js"]');
var siteId = $script.data('siteId') || undefined;
var categoryId = $script.data('categoryId') || undefined;
var newsId = $script.data('newsId') || undefined;
var pageNum = 1,
    pageSize = 20,
    loadall = false;

$(function() {
    $('.site-detail-content').each(function() {
        new Viewer($(this).find('img[src*="/api/storage/"]').css('cursor', 'zoom-in').end().get(0));
    });
    $('.site-detail-comments').each(function() {
        var $box = $(this).find('.site-detail-comments-box').empty();
        $(this).find('.site-detail-comments-inputbtn').on('click.comments', function() {
            var $text = $(this).closest('.site-detail-comments-input').find('textarea'),
                _content = $.trim($text.val());
            if (!_content || !_content.length) {
                return false;
            }
            if (_content.length > 400) {
                return alert('长度不能超过400字符');
            }
            $.post('/api/po/nologin/comments', {
                newsId: newsId,
                content: _content
            }, function(res) {
                if (res.status >= 10000) {
                    let _msg = res.message || '发表评论失败';
                    if (res.fieldErrors && res.fieldErrors.length) {
                        let _msgs = [];
                        $.each(res.fieldErrors, function(i, field) {
                            field && field.message && _msgs.push(field.message);
                        });
                        _msgs.length > 0 && (_msg = _msgs.join(''));
                    }
                    alert(_msg);
                }
                if (res.status != 200) {
                    return false;
                }
                $text.val('');
                alert('发布评论成功，请耐心等待管理员审核。');
            }, 'json');
        }).end().find('.site-detail-comments-nums').on('click.comments', 'span', function() {
            var $nums = $(this).closest('.site-detail-comments-nums'),
                $list = $nums.siblings('.site-detail-comments-list'),
                $more = $list.find('.site-detail-comments-morebtn'),
                _ishidden = $list.is(':hidden');
            if (_ishidden && pageNum == 1) {
                pageNum = 1;
                loadall = false;
                $box.empty();
                $more.triggerHandler('click.comments');
            }
            $list[_ishidden ? 'slideDown' : 'slideUp']();
        }).end().find('.site-detail-comments-morebtn').on('click.comments', function() {
            if (loadall) {
                return false;
            }
            var $this = $(this),
                $nums = $this.closest('.site-detail-comments').find('.site-detail-comments-nums');
            $.post('/api/po/nologin/comments/list/detail', {
                pageNum: pageNum,
                pageSize: pageSize,
                newsId: newsId
            }, function(res) {
                if (res.status != 200) {
                    return false;
                }
                var _data = res.data,
                    _list = _data.list;
                $nums.html(_data.total + '条评论&nbsp;&nbsp;<span>点击查看</span>');
                $.each(_list, function(i, item) {
                    $box.append('' +
                        '<div class="site-detail-comments-comms">\n' +
                        '    <div class="site-detail-comments-user">\n' +
                        '        <span class="site-detail-comments-username">' + (item.createrName || '游客') + '</span>\n' +
                        '        <span class="site-detail-comments-time">' + item.createdTime + '</span>\n' +
                        '    </div>\n' +
                        '    <div class="site-detail-comments-content">' + $('<div>' + item.contents + '</div>').text() + '</div>\n' +
                        '</div>');
                });
                loadall = !_data.nextPage;
                $this.text(loadall ? '已加载全部评论' : '查看更多评论');
                pageNum = _data.nextPage;
            }, 'json');
        }).triggerHandler('click.comments');
    });
});