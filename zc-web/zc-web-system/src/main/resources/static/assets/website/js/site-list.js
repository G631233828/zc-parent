$(function() {
    var $script = $('script[src*="site-list.js"]');
    var siteId = $script.data('siteId') || undefined;
    var categoryId = $script.data('categoryId') || undefined;
    var pageSize = 20;

    window.loadnews = (function(pageNum) {
        $.post('/api/po/nologin/news/list/datas', {
            pageNum: pageNum,
            pageSize: pageSize,
            siteId: siteId,
            categoryId: categoryId
        }, function(res) {
            if (res.status != 200) {
                return false;
            }
            var $news = $('.site-list-news', '.site-list-data').empty();
            $.each(res.data.list || [], function(i, item) {
                var _links = item.links || [],
                    _cate = item.categories[0],
                    _link = '/api/po/sites/' + _cate.siteId + '/' + _cate.categoryId + '/' + item.id + '/detail.html';
                if (_links && _links.length) {
                    _link = _links[0].url;
                }
                $news.append('<li><a href="' + _link + '" target="_blank"><span style="' + (item.nameColor ? ('color: ' + item.nameColor + ';') : '') + '">' + item.name + '</span><p>' + item.createdTime + '</p></a></li>');
            });
            if (!res.data.total) {
                $news.append('<li style="text-align: center;padding-top: 300px;border: none;"><a href="javascript:void(0);" style="display: inline-block;"><span>网站正在建设中...</span><p></p></a></li>');
                return false;
            }
            $('.site-list-page', '.site-list-data').html('\
                                <dl>\
                                    <dd class="site-list-page-total"><a href="javascript:void(0);"></a></dd>\
                                    <dd class="site-list-page-prepage"><a href="javascript:void(0);">上一页</a></dd>\
                                    <dd class="site-list-page-nextpage"><a href="javascript:void(0);">下一页</a></dd>\
                                </dl>')
                .find('.site-list-page-total a').text('共' + res.data.total + '记录').end()
                .find('.site-list-page-prepage a').attr('href', 'javascript:loadnews(' + (res.data.prePage || 1) + ');').end()
                .find('.site-list-page-nextpage a').attr('href', 'javascript:loadnews(' + (res.data.nextPage || res.data.pages) + ');').end()
                .find('.site-list-page-nextpage').each(function() {
                    var $this = $(this);
                    $.each(res.data.navigatepageNums || [], function(i, item) {
                        var _page = item || 1;
                        $this.before('<dd class="site-list-page-nums' + (_page == res.data.pageNum ? ' curr' : '') + '"><a href="javascript:loadnews(' + _page + ');">' + _page + '</a></dd>');
                    });
                });

        }, 'json');
        return arguments.callee;
    }(1))
});