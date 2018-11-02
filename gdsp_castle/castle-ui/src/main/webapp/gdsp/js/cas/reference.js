/**
 * 参照控件
 */
define(["jquery","cas/core","cas/ajax","cas/modal"],function ($,$F,$ajax) {
	
	"use strict"; // jshint ;_;

	/**
	 * 构造方法
	 */
	// REFERENCE CONSTRUCTOR AND PROTOTYPE
	var Reference = function (element, options) {
		var o = this
		,$el = o.$element = $(element)
		,id = $el.attr("id")||"ref"+$F.nextId()
		,v = $el.attr("ref-value")
		,l = $el.attr("ref-showvalue")
		,lookup = $el.attr("lookup")==="true"
		,wrapped = $el.attr("wrapped")!=="false"||true
		,readFields = $el.attr("readFields")
		,writeFields = $el.attr("writeFields");
		$el.on('change', 'input:text', $.proxy(o.textchanged, o));
		o.code = "ref_"+($el.attr("code")||$F.nextId());
		o.wrapped= wrapped;
		o.scope = $el.attr("scope")||$F.getContainer();
		if(readFields)
			o.readFields = readFields.split(',');
		if(writeFields)
			o.writeFields = writeFields.split(',');
		o.$input = $el.find('input[type=text]');
		o.$value = $el.find('input[type=hidden]');
		if(l)o.$input.val(l);
		if(v)o.$input.val(v);
		o.$button = $el.find('.input-group-addon');

	};
	/**
	 * 原型定义
	 */
	Reference.prototype = {

		constructor: Reference,

		/**
		 * 打开参照对话框
		 */
		openReference: function () {
			var o=this,url = o.$element.attr("url")||"#";
			if(url != "#") {
				url = url.evalTm(o);
			}
			var options = {
				src:o.$input,
				href:url,
				wrapped: o.wrapped,
				"data-target":"#"+o.code,
				hideCallback:$.proxy(o.returnReference, o),
				showCallback:$.proxy(o.afterShowReference,o)
			}
			$.openModalDialog(options);
		},

		/**
		 * 取得当前选中值
		 */
		returnReference:function(ref){
			var data = ref.data('reference_data');
			if(data == null){
				if(!ref.clear)
					return;
				delete ref.clear;
			}
			var o=this;
			o.setReference(data);
			ref.removeData('reference_data');
		},
		/**
		 * 显示参照后的处理
		 */
		afterShowReference:function(ref){
			ref.delegate('[data-dismiss="close"]', 'click.reference',function(){
				ref.removeData('reference_data');
				ref.modal('hide');
			});
			ref.delegate('[data-dismiss="clear"]', 'click.bs.reference',function(){
				ref.removeData('reference_data');
				ref.clear=true;
				ref.modal('hide');
			});
			ref.delegate('[data-dismiss="ok"]', 'click.bs.reference',function(){
					$.returnReference();
			});
			ref.removeData('reference_data');
		},
		
		/**
		 * 取得当前选中显示值
		 */
		getText:function(){
			return this.$input.val();
		},
		/**
		 * 取得当前选中显示值
		 */
		getValue:function(){
			return this.$value.val();
		},
		/**
		 * 取得当前选中数据
		 */
		getData:function(){
			var o=this;
			return o.$element.data("ref_data");
		},
		/**
		 * 通过显示值选择条目
		 */
		setReference: function (data) {
			if(data != null) {
				var vflag = data["value"] != null, tflag = data["text"] != null;
				$.each(data, function (key, value) {
					data[key] = value.join(",");
					if (!vflag) {
						data["value"] = data[key];
						vflag = true;
					} else if (!tflag) {
						data["text"] = data[key];
						tflag = true;
					}
				});
				if (vflag && !tflag) {
					data["text"] = data["value"]
				}
			}
			var o=this,$el=o.$element,$value=o.$value,$input=o.$input;
			var cur = $el.data("ref_data");
			if(cur==null && data == null )
				return;
			if(cur!=null && data != null && cur['value'] == data['value'])
				return;

			if(data == null) {
				$el.removeData("ref_data");
				data={};
			}else {
				$el.data("ref_data",data);
			}

			$value.val(data['value']);
			$input.val(data['text']).trigger('change', { synthetic: true });
			if(this.writeFields && this.readFields){
				var $box = this.parents("form")||$F.getContainer();
				var rfields = this.readFields;
				$.each(this.writeFields,function(i,field){
					var v = rfields.length > i ?data[rfields[i]]:null;
					$box.find("[name="+field+"]").val(v);
				});
			};
			$el.trigger("changed",{oldRef:cur,newRef:data});
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
		 * 重置
		 */
		reset: function () {
			this.setReference(null);
		},
		/**
		 * 输入框变化事件处理
		 */
		textchanged: function (e, extra) {
			var o=this;
			// skip processing for internally-generated synthetic event
			// to avoid double processing
			if (e.eventPhase == 3||extra && extra.synthetic) return;

			var txt = $(e.target).val();
			if(txt == null){
				o.setReference(null);
				return;
			}
			if(o.lookup)
				o.lookup(txt);

		},
		/**
		 * 根据指定值查找对应的参照数据
		 */
		lookup:function(keyValue){
			var o=this;
			var lookup = o.$element.attr("lookupUrl")||"#";
			if(url != "#") {
				url = url.evalTm(o);
			}else{
				return;
			}
			if(lookup.indexOf("?") > -1){
				lookup += '&';
			}else{
				lookup += '?';
			}
			lookup+="lookup=true&refValue="+keyValue;
			$.ajax({
				type: 'GET',
				url:lookup,
				dataType:"json",
				cache: false,
				success: $.proxy(o.setReference, o),
				error: $ajax.ajaxError
			});
		}

	};
	
	// REFERENCE PLUGIN DEFINITION
	$.fn.reference = function (option, value) {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('reference');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('reference', (data = new Reference(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.reference.Constructor = Reference;


	// REFERENCE DATA-API

	$("body").on("mousedown.bs.reference", ".reference", function (e) {
		var $this = $(this);
		if($(e.target,$this).is(".input-group-addon:not(.disabled) i,.input-group-addon:not(.disabled)")){
			$this.reference("openReference");
		}else{
			$this.reference();
		}
	});
	$("body").on("dblclick.bs.reference", ".reference>input:text", function () {
		$(this).parent().reference("reset");
	});
	/**
	 * 全局方法，关闭顶层的对话框
	 * 并将数据缓存到对话框中
	 * @praram data 数据
	 */
	$.returnReference=function(){
		var ref = $.getCurrentDialog()
			,$items = ref.find("[ref-checked]");
		if($items.length == 0) {
			return;
		}
		var data = {};
		$items.each(function(i,item){
			var nodes = $(this).children();
			$.each(nodes,function(i,n){
				var rs = data[n.tagName.toLowerCase()];
				if(!rs){
					rs = [];
					data[n.tagName.toLowerCase()]=rs;
				}
				rs.push($(n).text());
			})
		});
		$.closeReference(data);
	};

	/**
	 * 全局方法，关闭顶层的对话框
	 * 并将数据缓存到对话框中
	 * @praram data 数据
	 */
	$.closeReference=function(data){
		var ref = $.getCurrentDialog();
		ref.data('reference_data',data);
		ref.modal("hide");
	};
	return $;
});