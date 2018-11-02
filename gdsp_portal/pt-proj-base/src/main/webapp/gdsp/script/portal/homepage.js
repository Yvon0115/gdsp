/** 用户首页设置 wqh 2017/03/13 */
define(["cas/ajax"]);

/** 设为首页 */
function setUserDefaultHomePage(e) {
	// 取得页面菜单ID和页面ID
	var menu_id = e.prev().children("input").get(0).value;
	var page_id = e.prev().children("input").get(1).value;
//	$.get(__contextPath + "/portal/page/setDefaultPage.d", {
//		menuID : menu_id,
//		pageID : page_id
//	}, function(data) {
//		// $.get(__contextPath + "/index.d");
//		window.location.reload();// 刷新当前页面.
//	});
	$.ajax({
		url:__contextPath + "/portal/page/setDefaultPage.d",
		async:false,
		cache:false,
		data : {
		menuID : menu_id,
		pageID : page_id
		},
		success:function(data){
			window.location.reload();// 刷新当前页面.
		}
	});
}



$(document).ready(function() {
	/** 导航栏 页面菜单 鼠标悬浮事件 */
	$('li[role="pagemenu"]').mouseover(function() {
		$(this).addClass('open');
		// $(this).children("a").attr("aria-expanded","true");
	}).mouseout(function() {
		$(this).removeClass('open');
		// $(this).children("a").attr("aria-expanded","false");
	});
	/*
	 * $('li[role="pagemenu"]').mouseover(function() {
	 * $(this).children("ul").toggle(); }).mouseout(function() {
	 * $(this).children("ul").toggle(); });
	 */
});

// 测试
function liClick(){
	window.location = __contextPath + "/index.d";
}