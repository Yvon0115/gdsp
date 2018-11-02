/**
 * 表单验证
 */
define(["jquery","cas/core","cas/utils",localeFile,"plugins/jquery/jquery.validate","plugins/jquery/jquery.metadata"],function($,$F,$utils,$lang){
	$.validator.addMethod("alphanumeric", function(value, element) {
		return this.optional(element) || /^\w+$/i.test(value);
	}, "Letters, numbers or underscores only please");
	
	$.validator.addMethod("alphanumer", function(value, element) {
		return this.optional(element) || /^[A-Za-z0-9\u4e00-\u9fa5]+$/i.test(value);
	}, "仅支持数字、字母和汉字组合");
	
	$.validator.addMethod("alphanumerSpec", function(value, element) {
		return this.optional(element) || /^[A-Za-z0-9\u4e00-\u9fa5._()\[\]【】（）\-\:\：]+$/i.test(value);
	}, "仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合");
	
	
	$.validator.addMethod("lettersonly", function(value, element) {
		return this.optional(element) || /^[a-z]+$/i.test(value);
	}, "Letters only please"); 
	
	// 座机电话号码校验
	$.validator.addMethod("phone", function(v, element) {
	    v = v.replace(/\s+/g, ""); 
//		var a = this.optional(element) || v.match(/^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$/);
	    var a = this.optional(element) || v.match(/^((\+\d{1,3}\-\d{3,4}\-\d{7,8})|(\(\d{1,3}\-\d{3,4}\)\d{7,8})|(\d{3,4}\-\d{7,8})|(\d{7,8}))$/);
		return a;
	}, "Please specify a valid phone number");
	// 移动电话号码校验
	$.validator.addMethod("mobile", function(v, element) {
	    v = v.replace(/\s+/g, ""); 
	    
		return this.optional(element) || v.match( /^1[3|5|8|4|7]\d{9}$/);
	}, "Please specify a valid mobile number");
	
	// 邮政编码校验
	$.validator.addMethod("postcode", function(v, element) {
	    v = v.replace(/\s+/g, ""); 
		return this.optional(element) || v.match(/^[0-9 A-Za-z]{5,20}$/);
	}, "Please specify a valid postcode");
	
	$.validator.addMethod("date", function(v, element) {
	    v = v.replace(/\s+/g, "");
		return this.optional(element) || v.match(/^\d{4}[\/-]\d{1,2}[\/-]\d{1,2}$/);
	});
	$.validator.addMethod("greaterTo",function( value, element, param ) {
		// bind to the blur event of the target in order to revalidate whenever the target field is updated
		// TODO find a way to bind the event just once, avoiding the unbind-rebind overhead
		var target = $( param );
		if ( this.settings.onfocusout ) {
			var ename="blur";
			if(target.hasClass("wrapped"))	{
				ename="change";
			}
			target.unbind(".validate-greaterTo").bind(ename+".validate-greaterTo", function () {
				$(element).valid();
			});
		}
		var tvalue = target.val();
		if(!value || !tvalue)
			return true;
		return value > tvalue;
	},$utils.formatValue( "Please enter a value greater than {0}." ));

	$.validator.addMethod("greaterEqual",function( value, element, param ) {
		// bind to the blur event of the target in order to revalidate whenever the target field is updated
		// TODO find a way to bind the event just once, avoiding the unbind-rebind overhead
		var target = $( param );
		if ( this.settings.onfocusout ) {
			var ename="blur";
			if(target.hasClass("wrapped"))	{
				ename="change";
			}
			target.unbind(".validate-greaterEqual").bind(ename+".validate-greaterEqual", function () {
				$(element).valid();
			});
		}
		var tvalue = target.val();
		if(!value || !tvalue)
			return true;
		return value >= tvalue;
	},$utils.formatValue( "Please enter a value greater than or equal to {0}." ));

	$.validator.addMethod("lessTo",function( value, element, param ) {
			// bind to the blur event of the target in order to revalidate whenever the target field is updated
			// TODO find a way to bind the event just once, avoiding the unbind-rebind overhead
			var target = $( param );
			if ( this.settings.onfocusout ) {
				var ename="blur";
				if(target.hasClass("wrapped"))	{
					ename="change";
				}
				target.unbind(".validate-lessTo").bind(ename+".validate-lessTo", function () {
					$(element).valid();
				});
			}
		var tvalue = target.val();
		if(!value || !tvalue)
			return true;
		return value < tvalue;
	},$utils.formatValue( "Please enter a value less than {0}." ));

	$.validator.addMethod("lessEqual",function( value, element, param ) {
		// bind to the blur event of the target in order to revalidate whenever the target field is updated
		// TODO find a way to bind the event just once, avoiding the unbind-rebind overhead
		var target = $( param );
		if ( this.settings.onfocusout ) {
			var ename="blur";
			if(target.hasClass("wrapped"))	{
				ename="change";
			}
			target.unbind(".validate-lessTo").bind(ename+".validate-lessTo", function () {
				$(element).valid();
			});
		}
		var tvalue = target.val();
		if(!value || !tvalue)
			return true;
		return value <= tvalue;
	},$utils.formatValue( "Please enter a value greater than or equal to {0}." ));

	// 管理校验规则
	$.validator.addClassRules({
		date: {date: false},
		alphanumerSpec:{ alphanumerSpec: true},
		alphanumeric: { alphanumeric: true },
		lettersonly: { lettersonly: true },
		phone: { phone: true },
		postcode: {postcode: true}
	});
	$.validator.setDefaults({
		highlight:function(element,errorClass,validClass){
			var $grp = $(element).parents('.form-group:first');
			$grp.find(".valid-flag>i").attr("class",this.settings.errorIcon);
			$grp.addClass(errorClass).removeClass(validClass);
		},
		unhighlight:function(element,errorClass,validClass){
			var $grp = $(element).parents('.form-group:first');
			$grp.removeClass(errorClass).removeClass(validClass);
			var icon=$grp.find(".valid-flag>i");
			icon.attr("class",icon.attr("normal"))
			this.showHelps(element);
		},
		success:function(label,element){
			var $grp = $(element).parents('.form-group:first');
			$grp.removeClass(this.settings.errorClass);
			$grp.addClass(this.settings.validClass);
			//this.hideHelps(element);
			$grp.find(".valid-flag>i").attr("class",this.settings.validIcon);
		},
		/**
		 * 扩展出值改变事件
		 */
		onchange:function(element, event){
			this.element(element);
		},
		errorClass:"has-error",
		validClass:"has-success",
		errorIcon:"fa fa-times-circle",
		validIcon:"fa fa-check-circle",
		onkeyup:$.emptyFunction,
		focusInvalid: false,
		focusCleanup: true,
		onsubmit:true,
		//加入对bootstrap-select下拉框组件中隐藏域的效验，因为下拉框是吧值存到隐藏域中。
		ignore:".ignore :hidden:not(.selectpicker)",
		invalidHandler: function(e, validator) {
			var errors = validator.numberOfInvalids();
			var $form = $(validator.currentForm);
			if (errors) {
				var key = $form.attr("validateFormError");
				var message = key?$lang[key]["validateFormError"]||key:$lang.form["validateFormError"];
				message = $utils.format(message,[errors]);
				$F.messager.error(message);
			}
			$form.trigger("formsubmit",false);
		},
		submitHandler:function(form,e){
			var $form =$(form);
			$(form).trigger("formsubmit",true);
			var m = $form.attr("submitMethod")||"formSubmitHandle";
			if($form[m])
				return $form[m]();
			return true;
		}
	});
	$.extend(true,$.validator,{
		messages:$lang.validator,
		autoCreateRanges:true,
		dataRules:function( element ) {
			if (!$.metadata) {
				return {};
			}
			var opt = {type:"attr",name:"validation"};
			var meta = $.data(element.form, 'validator').settings.meta;
			var rules = meta ?$(element).metadata(opt)[meta] :$(element).metadata(opt);
			return rules;
		}
	})

	$.extend($.validator.prototype,{
		_superInit:$.validator.prototype.init,
		init: function() {
			this.labelContainer = $( this.settings.errorLabelContainer );
			this.errorContext = this.labelContainer.length && this.labelContainer || $( this.currentForm );
			this.containers = $( this.settings.errorContainer ).add( this.settings.errorLabelContainer );
			this.submitted = {};
			this.valueCache = {};
			this.pendingRequest = 0;
			this.pending = {};
			this.invalid = {};
			this.reset();

			var groups = ( this.groups = {} ),
				rules;
			$.each( this.settings.groups, function( key, value ) {
				if ( typeof value === "string" ) {
					value = value.split( /\s/ );
				}
				$.each( value, function( index, name ) {
					groups[ name ] = key;
				});
			});
			rules = this.settings.rules;
			$.each( rules, function( key, value ) {
				rules[ key ] = $.validator.normalizeRule( value );
			});

			function delegate( event ) {
				var validator = $.data( this[ 0 ].form, "validator" ),
					eventType = "on" + event.type.replace( /^validate/, "" ),
					settings = validator.settings;
				if ( settings[ eventType ] && !this.is( settings.ignore ) ) {
					settings[ eventType ].call( validator, this[ 0 ], event );
				}
			}
			$( this.currentForm )
				.validateDelegate( ":text, [type='password'], [type='file'], select, textarea, " +
				"[type='number'], [type='search'] ,[type='tel'], [type='url'], " +
				"[type='email'], [type='datetime'], [type='date'], [type='month'], " +
				"[type='week'], [type='time'], [type='datetime-local'], " +
				"[type='range'], [type='color'], [type='radio'], [type='checkbox']",
				"focusin focusout keyup", delegate)
				// Support: Chrome, oldIE
				// "select" is provided as event.target when clicking a option
				.validateDelegate("select, option, [type='radio'], [type='checkbox']", "click", delegate)
				.validateDelegate(".wrapped", "change", delegate);

			if ( this.settings.invalidHandler ) {
				$( this.currentForm ).bind( "invalid-form.validate", this.settings.invalidHandler );
			}

			// Add aria-required to any Static/Data/Class required fields before first validation
			// Screen readers require this attribute to be present before the initial submission http://www.w3.org/TR/WCAG-TECHS/ARIA2.html
			$( this.currentForm ).find( "[required], [data-rule-required], .required" ).attr( "aria-required", "true" );
		},
		getHelpElement:function(element){
			return $(element).parents('.form-group:first').find('.help-inline:not([generated]),.help-block:not([generated])');
		},
		getTipElement:function(element){
			return $(element).parents(".control-container:first");
		},
		destoryTips:function(element){
			var $tip = this.getTipElement(element);
			if($tip.length > 0){
				$tip.tooltip("destroy");
				$tip.attr("data-original-title","");
			}
		},
		showHelps:function(element){
			var $help = this.getHelpElement(element);
			if($help.length > 0){
				$help.show();
			}else{
				var $tip = this.getTipElement(element);
				if($tip.length > 0){
					var helpMsg = $tip.attr("title");
					if(helpMsg){
						$tip.attr("data-original-title",helpMsg);
					}else{
						$tip.tooltip("destroy");
					}
				}
			}
		},
		hideHelps:function(element){
			var $help = this.getHelpElement(element);
			if($help.length > 0){
				$help.hide();
			}
		},
		showLabel:function(element, message) {
			var label = this.errorsFor( element );
			var $help = this.getHelpElement(element);
			if($help.length == 0){
				var $tip = this.getTipElement(element);
				if($tip.length > 0  && message){
					//tip方式验证失败
					$tip.attr("data-original-title",message);
					$tip.tooltip({container: document.body});
					return;
				}
				if ($tip.length > 0  && !message && this.settings.success ) {
					label.text("");
					if ( typeof this.settings.success === "string" ) {
						label.addClass( this.settings.success );
					} else {
						this.settings.success.call(this,label, element );
					}
					this.toShow = this.toShow.add(label);
					this.destoryTips(element);
					return;
				}
			}
			if(message){
				message = "<span class=\"ar\"></span><i class=\"fa fa-times-circle\"></i>"+message
			}

			if ( label.length ) {
				// check if we have a generated label, replace the message then
				if ( label.attr("generated") ) {
					label.html(message);
				}
			} else {
				var $grp = $(element).parents('.form-group:first');
				var cls =$grp.attr('helpType')=='inline'?'help-inline':'help-block';
				// create label
				label = $("<" + this.settings.errorElement + "/>")
					.attr({"for":  this.idOrName(element), generated: true})
					.addClass(cls)
					.html(message || "");
				if ( this.settings.wrapper ) {
					// make sure the element is visible, even in IE
					// actually showing the wrapped element is handled elsewhere
					label = label.hide().show().wrap("<" + this.settings.wrapper + "/>").parent();
				}
				if ( !this.labelContainer.append(label).length ) {
					if ( this.settings.errorPlacement ) {
						this.settings.errorPlacement(label, $(element) );
					} else {
						$grp.find(">.control-container").append(label);
					}
				}
			}
			if ( !message && this.settings.success ) {
				label.text("");
				if ( typeof this.settings.success === "string" ) {
					label.addClass( this.settings.success );
				} else {
					this.settings.success.call(this,label, element );
				}
			}
			this.toShow = this.toShow.add(label);
			this.hideHelps(element);
		},
		hideErrors: function() {
			this.showHelps(this.toHide);
			this.addWrapper( this.toHide ).hide();
		},
		errors: function() {
			return $( this.settings.errorElement + ".help-inline[generated],"+this.settings.errorElement+".help-block[generated]", this.errorContext );
		},
		validElements: function() {
			return this.currentElements.not(this.invalidElements()).not(this.successList);
		},
		// return the custom message for the given element and validation method
		// specified in the element's "messages" metadata
		customDataMessage: function(element, method) {
			if (!$.metadata) {
				return;
			}
			var opt = {type:"attr",single:"validmsg",name:"valid-msgs"}
				,meta = this.settings.meta ? $(element).metadata(opt)[this.settings.meta] : $(element).metadata(opt)
				,msg = meta && meta[method]
				,theregex = /\$?\{(\d+)\}/g;
			if ( theregex.test( msg ) ) {
				return $utils.formatValue(msg);
			}
			return msg;
		},
		elements: function() {
			var validator = this,
				rulesCache = {};

			// select all valid inputs inside the form (no submit or reset buttons)
			return $( this.currentForm )
				.find( "input, select, textarea" )
				.not( ":submit, :reset, :image, [disabled]" )
				.not( this.settings.ignore )
				.filter( function() {
					if ( !this.name && validator.settings.debug && window.console ) {
						console.error( "%o has no name assigned", this );
					}

					// select only the first element for each name, and only those with rules specified
					if ( this.name in rulesCache || !validator.objectLength( $( this ).rules() ) ) {
						return false;
					}

					rulesCache[ this.name ] = true;
					return true;
				});
		}

	});
	/**
	 * 设置验证属性名
	 */
	$.metadata.setType("attr", "validation");

	return $F;
});
