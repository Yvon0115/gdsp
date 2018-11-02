/**
 * @class
 * @name jQuery.fn.combobox
 * @description <jquery methods class> 下拉控件封装
 */
define(["jquery","cas/core","cas/options","bootstrap/bootstrap"],function ($,$F,$options) {
	"use strict"; // jshint ;_;

	// COMBOBOX CONSTRUCTOR AND PROTOTYPE
	var Combobox = function (element, options) {
		var o = this
		,$el = o.$element = $(element)
		,v = $el.attr("value");
		o.options = $.extend({}, $options.combobox, options);
		$el.on('click', '.dropdown-menu a', $.proxy(o.itemclicked, o));
		$el.on('change', 'input:text', $.proxy(o.inputchanged, o));
		o.$input = $el.find('input[type=text]');
		o.$button = $el.find('.input-group-addon');
		o.$value = $el.find('input[type=hidden]');
		if(v!=null)
			o.selectByValue(v);
		// set default selection
		o.setDefaultSelection();
	};

	Combobox.prototype = {

		constructor: Combobox,

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
				o.$input.val($item.text());
				if(o.$value.length > 0)
					o.$value.val($item.attr("data-value"));
			}else{
				o.$input.val("");
				if(o.$value.length > 0)
					o.$value.val("");
			}
		},
		/**
		 * 重新初始化下拉，使用时需要注意，包裹li的父元素还是原来的，也就是下拉控件并没有真正重新初始化（比如在父元素上添加的属性等）
		 */
		setItemsOptions: function (options) {
			var o = this,valSelect = null;
			var template = '<li data-value="#tempvalue#" #tempselected#><a href="#">#tempname#</a></li>',optionsHtml='';
			var idx=0;
			var selectedHtml = '';
			for(var p in options){
				if(idx==0){
					selectedHtml = 'data-selected="true"';
					valSelect = p;
				}
				idx++;
				optionsHtml+=template.replace('#tempvalue#',p).replace("#tempselected#",selectedHtml).replace("#tempname#",options[p]);
			}
			$("ul",o.$element).html(optionsHtml);
			o.selectByValue(valSelect);
			/*
			if(valSelect!=null){
				o.selectByValue(valSelect);
				var data = o.selectedItem();
				if (data.value) {
					// trigger changed event
					o.$element.trigger('changed', data);
					return;
				}
			}
			*/
		},
		/**
		 * 设置默认选中条目
		 */
		setDefaultSelection: function () {
			var o=this,selector = 'li[data-selected=true]:first'
			,$item = o.$selectedItem;
			if($item&&$item.length > 0)
				return;

			$item = o.$element.find(selector);
			//取消默认选中第一项功能
//			if ($item.length == 0) {
//
//
//				if($item&&$item.length == 0)
//				o.selectByIndex(0);
//			} else {
//				// select by data-attribute
//				
//			}
			o.selectBySelector(selector);
			$item.removeData('selected');
			$item.removeAttr('data-selected');
		},
		/**
		 * 启用控件
		 */
		enable: function () {
			this.$input.removeAttr('disabled');
			this.$button.removeClass('disabled');
		},
		/**
		 * 停用控件
		 */
		disable: function () {
			this.$input.attr('disabled', true);
			this.$button.addClass('disabled');
		},
		/**
		 * 条目点击事件处理
		 */
		itemclicked: function (e) {
			e.preventDefault();
			var o = this;
			o.$selectedItem = $(e.target).parent();
			o.setItemValues();
			// set input text and trigger input change event marked as synthetic
			o.$input.trigger('change', { synthetic: true });

			// pass object including text and any data-attributes
			// to onchange event
			var data = o.selectedItem();

			// trigger changed event
			o.$element.trigger('changed', data);

		},
		/**
		 * 输入框变化事件处理
		 */
		inputchanged: function (e, extra) {
			var o=this;
			// skip processing for internally-generated synthetic event
			// to avoid double processing
			if (extra && extra.synthetic) return;

			var txt = $(e.target).val();
			var old = o.$selectedItem;
			o.selectByText(txt);
			if(old == o.$selectedItem)
				return;
			// find match based on input
			// if no match, pass the input value
			var data = o.selectedItem();
			if (data.value) {
				// trigger changed event
				o.$element.trigger('changed', data);
				return;
			}

			o.selectByValue(txt);
			if(old == o.$selectedItem)
				return;
			// find match based on input
			// if no match, pass the input value
			data = o.selectedItem();
			if (!data.value) {
				o.$selectedItem = old;
				o.setItemValues();
				return;
			}
			// trigger changed event
			o.$element.trigger('changed', data);

		}

	};


	// COMBOBOX PLUGIN DEFINITION
	$.fn.combobox = function (option, value) {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('combobox');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('combobox', (data = new Combobox(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.combobox.Constructor = Combobox;


	// COMBOBOX DATA-API
	$('body').on('mousedown.bs.combobox', '.combobox', function () {
		var $this = $(this);
		if ($this.data('combobox')) return;
		$this.combobox($this.data());
	}).on('keydown.bs.combobox', '.combobox .dropdown-menu a', function (e) {
		if(e.keyCode != $F.keyCode.ENTER)
			return;
		e.preventDefault();
		$(this).trigger("click");
	});
	return $F;
});