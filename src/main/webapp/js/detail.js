$(document).ready(function () {
    /* 手风琴效果(也可bootstrap实现,此处我是手写的) */
    $('.bky-panelMore').on('click', function () {
        $(this).next('div').slideToggle().parent().siblings().find('.bky-panel-content').slideUp();
    });
    /* 如果需求是点击子菜单添加背景颜色 则代码如下 */
    $('.bky-panel-body').on('click',function () {
        $(this).addClass('active').siblings('.bky-panel-body').removeClass('active').parents('.bky-panel-list').siblings().find('.bky-panel-body').removeClass('active');
    });
    /* 点击按钮 显示 模态窗效果 */
    $('#search').on('click',function () {
        $('#dialog').fadeIn().next('.dialog-content').fadeIn();
    });
    /* 取消模态框 */
    $('#cancel').on('click',function () {
        $('#dialog').fadeOut().next('.dialog-content').fadeOut();
    });
    /* 点击灰色部分 取消 模态框 */
    $('#dialog').on('click',function () {
        $('#dialog').fadeOut().next('.dialog-content').fadeOut();
    });

    $(".initMenu").addClass('active').siblings('.bky-panel-body').removeClass('active').parents('.bky-panel-list').siblings().find('.bky-panel-body').removeClass('active');
    
    // jq页面跳转
    $(function () {
        // 加载日志管理
        $('.bky-panel-body').click(function () {
            var url = $(this).data("url");
            $('#pagecontainer').load(url);
        });
    })
    /*$(function () {
        // 加载日志管理
        $('.bky-panel-title').click(function () {
            top.jzts();
            var url = $(this).data("url");
            $('#pagecontainer').load(url);
        });
    })*/
});
