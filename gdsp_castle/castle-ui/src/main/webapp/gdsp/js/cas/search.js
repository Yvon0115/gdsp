/**
 * @class
 * @name jQuery.fn.search
 * @description <jquery methods class> 搜索扩展
 */
define(["cas/core","cas/options","cas/dataloader"],function ($F,$options) {
	
	"use strict"; // jshint ;_;

	/* SEARCH AJAX CLASS DEFINITION
	 * ==================== */
	var Search = function (element, options) {
		var o = this
		,$el = o.$element = $(element)
		,target = $el.attr("loader-target");
		o.$target = $(target);
		if(o.$target.length > 0){
			o.$target.dataloader("addPlugin",{
				name:"searcher",
				getParameters:function(){
					return o.getSearchInfo();
				}
			});
		}
		o.options = $.extend({}, $options.search, options);
		o.$button = $el.find('span').on('click', $.proxy(o.buttonclicked, o));

		o.$input = $el.find('input');
		o.$input.on('keydown', $.proxy(o.keypress, o)).on('keyup', $.proxy(o.keypressed, o));

		o.$icon = $el.find('i');
		$el.find(".form-control-feedback").css("pointer-events","auto");
		o.activeSearch = '';
	};
	/**
	 * 原型定义
	 */
	Search.prototype = {

		constructor: Search,
		/**
		 * 查询检索方法
		 */
		search: function (searchText) {
			var o = this;
			//o.$icon.attr('class', 'glyphicon glyphicon-remove');
			o.activeSearch = searchText;
			if(o.$target.length > 0){
				o.$target.dataloader("loadReset");
				o.$target.dataloader("load");
			}
			o.$element.trigger({type:'searched',searchText:searchText}, searchText);
		},
		/**
		 * 取得检索信息
		 */
		getSearchInfo:function(){
			var o = this
			,val = o.$input.val()
			,cons = o.$input.attr("cons");
			return {_freeConVal:val,_freeCon:cons};
		},
		/**
		 * 清空检索信息
		 */
		clear: function () {
			var o = this;
			o.$icon.attr('class', 'glyphicon glyphicon-search');
			o.activeSearch = '';
			o.$input.val('');
			if(o.$target.length > 0){
				o.$target.dataloader("loadReset");
				o.$target.dataloader("load");
			}
			o.$element.trigger('cleared');
		},

		/**
		 * 操作事件处理
		 */
		action: function () {
			var o = this
			,val = o.$input.val()
			//,inputUnchangeEmpty = val === ''||val === o.activeSearch;
			//if (o.activeSearch && inputUnchangeEmpty) {
			//	o.clear();
			//} else if (val) {
				o.search(val);
			//}
		},
		/**
		 * 按钮事件处理
		 */
		buttonclicked: function (e) {
			e.preventDefault();
			if ($(e.currentTarget).is('.disabled, :disabled')) return;
			this.action();
		},
		/**
		 * 键盘keydown事件处理屏蔽键盘事件
		 */
		keypress: function (e) {
			if (e.which === $F.keyCode.ENTER) {
				e.preventDefault();
			}
		},
		/**
		 * 键盘keyup事件处理
		 */
		keypressed: function (e) {
			//var val, inputPresentAndUnchanged;

			if (e.which === $F.keyCode.ENTER) {
				e.preventDefault();
				this.action();
			}
			// else {
			//	val = this.$input.val();
			//	inputPresentAndUnchanged = val && (val === this.activeSearch);
			//	this.$icon.attr('class', inputPresentAndUnchanged ? 'glyphicon glyphicon-remove' : 'glyphicon glyphicon-search');
			//}
		},

		/**
		 * 禁用检索功能
		 */
		disable: function () {
			this.$input.attr('disabled', 'disabled');
			this.$button.addClass('disabled');
		},
		/**
		 * 启用检索功能
		 */
		enable: function () {
			this.$input.removeAttr('disabled');
			this.$button.removeClass('disabled');
		}

	};


	// SEARCH PLUGIN DEFINITION

	$.fn.searchs = function (option) {
		return this.each(function () {
			var data = $(this).search(option);
			if (typeof option === 'string') data[option]();
		});
	};
	
	$.fn.search = function (option,value) {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('search');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('search', (data = new Search(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.search.Constructor = Search;


	// SEARCH DATA-API

	$('body').on('mousedown.cas-search.data-api', '.search', function () {
		var $this = $(this);
		if ($this.data('search')) return;
		$this.search($this.data());
	});
	return $;
});