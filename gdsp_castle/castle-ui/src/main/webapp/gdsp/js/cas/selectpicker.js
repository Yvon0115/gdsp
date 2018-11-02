/**
 * @class
 * @name jQuery.fn.multiselect
 * @description	基于Bootstrap-selectpicker的构建
 */
define(["cas/core","cas/options","plugins/selectpicker/bootstrap-select","link!plugins/selectpicker/bootstrap-select"],function ($F,$options){
 'use strict';	
	/**
	 * 构造方法 Bselectpicker
	 */
 
	var Bselectpicker = function (element, options) {
		//定义o和$el
		var o = this,
		$el = o.$element = $(element)
		,v = $el.attr("value")
		,id=$el.attr("id");
		o.options = $.extend({}, $options.selectpicker, options);
		//给组件绑定一个change事件，事件触发后执行一个代理方法，方法的参数是inputchanged，组件本身
		//$el.on('click', '.selectpicker', $.proxy(o.inputchanged, o));
		//获取组件选中的值	
		var $B_Selectpicker = $('.selectpicker').selectpicker();
		var v_array;
		if(typeof(v) != "undefined"&&v!=null&&v!=''){
			v_array=v.split(','); 
			$("#"+id).selectpicker('val', v_array);
		}
		
/*		selectpicker API 说明
		删除指定的一个option,删除以后需要刷新
		$('.remove-example').find('[value=Mustard]').remove();
		$('.remove-example').selectpicker('refresh');
		直接使所有的option处于selected状态
		$('.selectpicker').selectpicker('selectAll');
		设置指定option为selected状态
		$('.selectpicker').selectpicker('val', ['Mustard','Relish']);
		使所有的option处于取消选中的状态
		$('.selectpicker').selectpicker('deselectAll');
		编程方式更改了select下拉框后，使用强制刷新	
		$('.selectpicker').selectpicker('render');
		更改响应式布局
		$('.selectpicker').addClass('col-lg-12').selectpicker('setStyle');
		$('.selectpicker').selectpicker('mobile');
		使select下拉框处于打开状态
		$('.selectpicker').selectpicker('toggle');
		隐藏，显示，摧毁下拉框
		$('.selectpicker').selectpicker('hide');
		$('.selectpicker').selectpicker('show');
		$('.selectpicker').selectpicker('destroy');
*/
		//初始化事件		
		_BselectpickerinitEventHandle(id,$el,options);
	
	}
	
	
	function _BselectpickerinitEventHandle(id,$el,options){
		
		$el.on('change',function(option, checked, select){
		
			var nodeSelected = $.Event('SelectChange', {
				node:option
			});
			$("#"+id).trigger(nodeSelected);
		});
		
	//	$el.on('change', '.selectpicker', $.proxy($el));
		
	}
		
	
	
	Bselectpicker.prototype = {
		
	constructor: Bselectpicker

/*	inputchanged: function (e) {
			var data = {};
			o.$element.trigger('changed.bs.select', data);
		}
	*/
	};
	
//	原型设置
	$.fn.bselectpicker = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.bselectpicker,option):$options.bselectpicker;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('bselectpicker');
			if (!data) 
				$this.data('bselectpicker', (data = new Bselectpicker(this, options)));
			if (typeof option === 'string') 
				methodReturn = data[option](value);
		});
		return methodReturn;
	};
	
	$.fn.bselectpicker.Constructor = Bselectpicker;
	
	//初始化默认设置
	$.fn.selectpicker.defaults = {
		selectAllText: '全选',
    	deselectAllText: '撤销',
	    noneSelectedText: '请选择',
	    noneResultsText: '没有找到匹配项',
	    countSelectedText: '选中{1}中的{0}项',
	    maxOptionsText: ['超出限制 (最多选择{n}项)', '组选择超出限制(最多选择{n}组)'],
	    multipleSeparator: ', '
	  };
  
 	return $;

});

