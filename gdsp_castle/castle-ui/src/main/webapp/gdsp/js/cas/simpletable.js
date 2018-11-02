/**
 * @calss
 * @description  SimpleTable方法扩展
 */
define(["jquery","cas/core","cas/options"],function($,$F,$options) {
	"user strict";
	
	var SimpleTable = function (element, options) {
		var o = this,$el = o.$element = $(element);
		o.options = $.extend({}, $options.simpletable, options);
		o.$trs = $("tr");
		o.$checkboxs = $el.find('input[type=checkbox]');
	};
	
	SimpleTable.prototype = {
			constructor: SimpleTable,

			/**
			 * 获取当前table内容
			 */
			getTable: function () {
				var table = this.$trs.toArray();
				//删除标题行
				table.splice(0,1);
				return table;
			},
			
			/**
			 * 获取当前选中行数据
			 */
			getChecked:function(){
				var o = this
				,checkboxs = o.$checkboxs
				,rowData=[]
				,n = 0;
				$.each(checkboxs,function(i){
					if(checkboxs[i].checked){
						rowData[n]=$("tr").get(i);
						n++;
					}
				})
				return rowData;
			},
			/**
			 * 获取当前选中的总行数
			 */
			getRows:function(){
				return this.getChecked().length;
			},
	};
	
	/**
	 * jquery方法扩展simpleTable
	 */
	$.fn.simpletable = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.simpletable,option):$options.simpleTable;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('simpletable');
			if (!data) 
				$this.data('simpletable', (data = new SimpleTable(this, options)));
			if (typeof option === 'string') 
				methodReturn = data[option](value);
		});
		return methodReturn;
	};
	
	$.fn.simpletable.Constructor = SimpleTable;

	return $;
});