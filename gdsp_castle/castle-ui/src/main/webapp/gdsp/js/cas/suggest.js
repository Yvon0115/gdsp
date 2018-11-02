/**
 * @class
 * @name jQuery.fn.suggest
 * @description <jquery methods class> 搜索扩展
 */
define(["cas/core","bootstrap/bootstrap"],function () {
	
	"use strict"; // jshint ;_;

	// SUGGEST CONSTRUCTOR AND PROTOTYPE

	var Sugguest = function (element, options) {
		var o = this
		,$el = o.$element = $(element)
		,action = $el.attr("sugguestAction")
		,$value = $($el.attr("valf"))
		,parentClass = $el.attr("wrapClass")||""
		,$linkFields = $($.attr("linkFields"));
		
		if($value.length == 0 && $el.parent().hasClass("input-append")){
			$value=$el.parent().find("input:hidden")
		}
		if(!$el.parent().hasClass("dropdown")){
			$el.wrap('<div class="dropdown'+parentClass+'"></div>')
		}
		o.$dropdown = $el.next("ul.dropdown-menu");
		if(o.$dropdown.length==0){
			o.$dropdown = $("<ul class='dropdown-menu'></ul>").insertAfter($el);
		}
		o.$value = $value;

		$el.on('suggest.suggest.data-api', '.suggest .dropdown-menu a', function (e) {
			if(e.keyCode != Fast.keyCode.ENTER)
				return;
			e.preventDefault();
			$(this).trigger("click");
		});

	};

	Sugguest.prototype = {

		constructor: Sugguest,

		/**
		 * 取得当前选中的数据
		 */
		selectedItem: function () {
			var o=this,
			$item = o.$selectedItem;
			var data = {};

			if ($item) {
				var txt = $item.text();
				var val = $item.attr("data-value");
				data = $.extend({ text: txt,value:val }, $item.data());
			}
			else {
				data = { text: o.$input.val(),value:o.$value.val()};
			}

			return data;
		},

		/**
		 * 取得当前选中值
		 */
		getValue:function(){
			return this.selectedItem().value;
		},
		/**
		 * 取得当前选中显示值
		 */
		getText:function(){
			return this.selectedItem().text;
		},
		/**
		 * 通过显示值选择条目
		 */
		selectByText: function (text) {
			var selector = 'li:innerTextExactMatch(' + text + ')';
			this.selectBySelector(selector);
		},
		/**
		 * 通过条目值选择条目
		 */
		selectByValue: function (value) {
			var selector = 'li[data-value="' + value + '"]';
			this.selectBySelector(selector);
		},
		/**
		 * 通过索引值选择条目
		 */
		selectByIndex: function (index) {
			var selector = 'li:eq(' + index + ')';
			this.selectBySelector(selector);
		},
		/**
		 * 通过选择器选择条目
		 */
		selectBySelector: function (selector) {
			var o = this;
			o.$selectedItem = o.$element.find(selector);
			o.setItemValues();
		},

		/**
		 * 设置输入框中的值
		 */
		setItemValues: function () {
			var o = this,$item=o.$selectedItem;
			if($item && $item.length>0){
				o.$element.val($item.text());
				if(o.$value.length > 0)
					o.$value.val($item.attr("data-value"));
			}else{
				o.$element.val("");
				if(o.$value.length > 0)
					o.$value.val("");
			}
		},
		/**
		 * 加载提示条目
		 */
		loadSuggest:function(){
			
		},
		/**
		 * 条目点击事件处理
		 */
		itemclicked: function (e) {
			var o = this;
			var $item = o.$selectedItem = $(e.target).parent();

			e.preventDefault();
			o.setItemValues();
			// set input text and trigger input change event marked as synthetic
			o.trigger('change', { synthetic: true });
		},
		/**
		 * 输入框变化事件处理
		 */
		inputchanged: function (e, extra) {
			var o=this;
			// skip processing for internally-generated synthetic event
			// to avoid double processing
			if (extra && extra.synthetic) return;
			//only txt input,don't valid item
			if(o.$value.length == 0)return;
			var txt = $(e.target).val();
			o.selectByText(txt);
		},
		/**
		 * 键盘keyup事件处理
		 */
		keypressed: function (e) {
			var o=this;

			if (e.keyCode === Fast.keyCode.ENTER) {
				e.preventDefault();
				this.$input.trigger("blur");
			} else {
				setTimeout(function(){o.loadSuggest()},200);
			}
		}

	};


	// SUGGEST PLUGIN DEFINITION
	$.fn.sugguests = function (option, value) {
		var methodReturn;
		var $set = this.each(function () {
			var data = $(this).combobox();
			if (typeof option === 'string') methodReturn = data[option](value);
		});

		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.sugguest = function () {
		var $this = $(this);
		var data = $this.data('sugguest');
		var options = typeof option === 'object' && option;

		if (!data) $this.data('sugguest', (data = new Combobox(this, options)));
		return data;
	};

	$.fn.sugguest.Constructor = Sugguest;


	// COMBOBOX DATA-API

	$(function () {
		$('body').on('keyup.sugguest.data-api', '.sugguest', function (e) {
			var $this = $(this);
			if ($this.data('sugguest')) return;
			$this.sugguest($this.data());
		});
	});

});