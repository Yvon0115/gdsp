/**
 * @class
 * @name jQuery.fn.sort
 * @description <jquery methods class> 搜索扩展
 */
define(["cas/core","cas/dataloader"],function () {
	
	"use strict"; // jshint ;_;

	/* SEARCH AJAX CLASS DEFINITION
	 * ==================== */
	var Sorter = function (element,options) {
		var o = this
		,$el = o.$element = $(element)
		,target = $el.attr("loader-target");
		o.$target = $(target);
		o.$target.dataloader("addPlugin",{
			name:"sorter",
			getParameters:function(){
				return o.getSortInfo();
			}
		});
		o.$combobox = $el.on("change.sorter.data-api",$.proxy(o.sortChanged,o));
		o.$order = $el.find('a.add-on[order]').on("click.sorter.data-api",$.proxy(o.directChange,o));
	};
	/**
	 * 原型定义
	 */
	Sorter.prototype = {

		constructor: Sorter,

		/**
		 * 取得检索信息
		 */
		getSortInfo:function(){
			var o = this
			,val = o.$combobox.combobox("getValue");
			if(!val)
				return {};
			var order = o.$order.attr("order")||"asc";
			return {_orders:val+" " + order};
		},
		/**
		 * 排序方向变化
		 */
		directChange:function(e){
			e.preventDefault();
			var o = this,$e = $(e.target);
			if(!$e.isTag('a')){
				$e = $e.parents("a[order]:first");
			}
			var old = $e.attr("order")||"asc";
			if(old == "desc"){
				$e.attr("order","asc");
				$(">i",$e).removeClass("icon-arrow-down").addClass("icon-arrow-up");
			}else{
				$e.attr("order","desc");
				$(">i",$e).removeClass("icon-arrow-up").addClass("icon-arrow-down");
			}
			o.sortChanged(e);
		},
		/**
		 * 清空检索信息
		 */
		sortChanged: function (e) {
			e.preventDefault();
			this.$target.dataloader("load");
		},

		/**
		 * 禁用检索功能
		 */
		disable: function () {
			var o = this;
			o.$combobox.combobox('disable');
			o.$order.addClass('disabled');
		},
		/**
		 * 启用检索功能
		 */
		enable: function () {
			var o = this;
			o.$combobox.combobox('enable');
			o.$order.removeClass('disabled');
		}

	};

	$.fn.sorter = function (option) {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('sorter');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('sorter', (data = new Sorter(this, options)));
			if (typeof option === 'string') methodReturn = data[option]();
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.sorter.Constructor = Sorter;
	return $;
});