/**
 * 消息控件
 */
define(["cas/core",localeFile,"bootstrap/bootstrap"],function($F,$lang) {
    var _animate       = true,
        _backdrop      = 'static',
        _icons         = {
    		HD_OK:"fa fa-check-circle",
    		HD_INFO:"fa fa-info-circle",
    		HD_WARN:"fa fa-warning",
    		HD_CONFIRM:"fa fa-question-circle",
    		HD_ERROR:"fa fa-times-circle",
    		HD_EXCLAIMATION:"icon-exclamation-sign"
		},
		defaults = {
			// show backdrop or not. Default to static so user has to interact with dialog
			backdrop: _backdrop,
			// animate the modal in/out
			animate: true,
			// additional class string applied to the top level dialog
			className: null,
			// whether or not to include a close button
			closeButton: true,
			// show the dialog immediately by default
			show: true,
			// dialog container
			container: "body"
		},
		templates = {
			dialog:
			"<div class='bootbox modal' tabindex='-1' role='dialog'>" +
			"<div class='modal-dialog'>" +
			"<div class='modal-content'>" +
			"<div class='modal-body'><div class='bootbox-body'></div></div>" +
			"</div>" +
			"</div>" +
			"</div>",
			header:
			"<div class='modal-header'>" +
			"<h5 class='modal-title'></h5>" +
			"</div>",
			footer:
				"<div class='modal-footer'></div>",
			closeButton:
				"<button type='button' class='bootbox-close-button close' data-dismiss='modal' aria-hidden='true'>&times;</button>"

		};
	function sanitize(options) {
		var buttons;
		var total;

		if (typeof options !== "object") {
			throw new Error("Please supply an object of options");
		}

		if (!options.message) {
			throw new Error("Please specify a message");
		}

		// make sure any supplied options take precedence over defaults
		options = $.extend({}, defaults, options);

		if (!options.buttons) {
			options.buttons = {};
		}

		buttons = options.buttons;

		total = getKeyLength(buttons);
		var index = 0;
		$.each(buttons, function(key, button) {

			if ($.isFunction(button)) {
				// short form, assume value is our callback. Since button
				// isn't an object it isn't a reference either so re-assign it
				button = buttons[key] = {
					callback: button
				};
			}

			// before any further checks make sure by now button is the correct type
			if ($.type(button) !== "object") {
				throw new Error("button with key " + key + " must be an object");
			}

			if (!button.label) {
				// the lack of an explicit label means we'll assume the key is good enough
				button.label = key;
			}

			if (!button.className) {
				if (total <= 2 && index === 0) {
					// always add a primary to the main option in a two-button dialog
					button.className = "btn-primary";
				} else {
					button.className = "btn-default";
				}
			}
			index++;
		});

		return options;
	}
	function processCallback(e, dialog, callback) {
		e.stopPropagation();
		e.preventDefault();

		// by default we assume a callback will get rid of the dialog,
		// although it is given the opportunity to override this

		// so, if the callback can be invoked and it *explicitly returns false*
		// then we'll set a flag to keep the dialog active...
		var preserveDialog = $.isFunction(callback) && callback.call(dialog, e) === false;

		// ... otherwise we'll bin it
		if (!preserveDialog) {
			dialog.modal("hide");
		}
	}
	/**
	 * 消息翻译
	 * @param str 常量穿
	 * @returns {*} 多语输出
	 * @private
	 */
    function _translate(str) {
        return $lang.messager[str];
    }

	/**
	 * 取得样式消息框选项
	 * @param options 停靠位置及透明度
	 * @returns {string} style串
	 */
	function getStyle(options){
		var pos = options["pos"]
			,style={}
		switch(pos){
			case "top":
				style={top:"2px","margin-top":"auto"};
				break;
			case "left":
				style={left:"2px","margin-left":"auto"};
				break;
			case "right":
				style={right:"2px","margin-left":"auto"};
				break;
			case "bottom":
				style={top:"auto",bottom:"2px","margin-top":"auto"};
				break;
			case "lt":
				style={top:"2px",left:"2px","margin-left":"auto","margin-top":"auto"};
				break;
			case "rt":
				style={top:"2px",right:"2px","margin-left":"auto","margin-top":"auto"};
				break;
			case "rb":
				style={top:"auto",right:"2px",bottom:"2px","margin-left":"auto"};
				break;
			case "rb":
				style={top:"auto",left:"2px",bottom:"2px","margin-left":"auto"};
				break;
		}
		var opacity = options["opacity"];
		if(opacity)
			style.opacity= opacity;
		return style;
	}
	function getTitle(options){
		var title =options.title||"";
		if(options.icon){
			var icon = _icons[options.icon]||options.icon;
			title= "<i class='"+icon+"'/>&nbsp;" + title;
		}
		if(title !="")
			return title;
	}
	function getKeyLength(obj) {
		// @TODO defer to Object.keys(x).length if available?
		var k, t = 0;
		for (k in obj) {
			t ++;
		}
		return t;
	}
    var that = {

	    alert:function(msg,options) {
	    	var label = options["label"]||_translate('OK'),
	            cb    = options.callback,
				buttons = {ok:{label:label,callback:cb}};
        	var op = $.extend({className: "bootbox-alert","onEscape": cb,buttons:buttons},options);
        	op.message = msg;
			op.title = getTitle(options);
        	if (this._closeTimer) {
        		clearTimeout(this._closeTimer);
    			that._closeTimer = null;
    		}

	        var dialog = that.dialog(op);

	        if (options["closeTimer"]){
	        	 var t = options["timeout"]||1500;
		        that._closeTimer = setTimeout(function(){
		        	dialog.modal("hide");
		        	clearTimeout(that._closeTimer);
		        	that._closeTimer=null
		        }, t);
	        }
	        return dialog;
	    },
	    showConfirm: function(msg,options) {
	        var labelCancel = options["labelCancel"]||_translate('CANCEL')
				,labelOk     = options["labelOk"]||_translate('CONFIRM')
				,cb    = options["callback"]
				,confirmCallback = function() {
					if (typeof cb === 'function') {
						cb(true);
					}
				}
				,cancelCb = function() {
					if (typeof cb === 'function') {
						cb(false);
					}
				}
				,buttons = {confirm:{label:labelOk,callback:confirmCallback},cancel:{label:labelCancel,callback:cancelCb}};
			var op = $.extend({className: "bootbox-confirm","onEscape": cancelCb,buttons:buttons},options);
			op.message = msg;
			op.title = getTitle(options);
	        return that.dialog(op);
	    },
	    dialog: function(op) {
	        var options    = sanitize($.extend(defaults,op));

			var dialog = $(templates.dialog);
			var innerDialog = dialog.find(".modal-dialog");
			var body = dialog.find(".modal-body");
			var buttons = options.buttons;
			var buttonStr = "";
			var callbacks = {
				onEscape: options.onEscape
			};

			if ($.fn.modal === undefined) {
				throw new Error(
					"$.fn.modal is not defined; please double check you have included " +
					"the Bootstrap JavaScript library. See http://getbootstrap.com/javascript/ " +
					"for more details."
				);
			}

			$.each(buttons, function(key, button) {

				// @TODO I don't like this string appending to itself; bit dirty. Needs reworking
				// can we just build up button elements instead? slower but neater. Then button
				// can just become a template too
				buttonStr += "<button data-bb-handler='" + key + "' type='button' class='btn " + button.className + "'>" + button.label + "</button>";
				callbacks[key] = button.callback;
			});
			/*增加样式处理，透明度和位置*/
			innerDialog.css(getStyle(options));

			body.find(".bootbox-body").html(options.message);

			if (options.animate === true) {
				dialog.addClass("fade");
			}

			if (options.className) {
				dialog.addClass(options.className);
			}

			if (options.size === "large") {
				innerDialog.addClass("modal-lg");
			} else if (options.size === "small") {
				innerDialog.addClass("modal-sm");
			}

			if (options.title) {
				body.before(templates.header);
			}

			if (options.closeButton) {
				var closeButton = $(templates.closeButton);

				if (options.title) {
					dialog.find(".modal-header").prepend(closeButton);
				} else {
					closeButton.css("margin-top", "-10px").prependTo(body);
				}
			}

			if (options.title) {
				dialog.find(".modal-title").html(options.title);
			}

			if (buttonStr.length) {
				body.after(templates.footer);
				dialog.find(".modal-footer").html(buttonStr);
			}


			/**
			 * Bootstrap event listeners; used handle extra
			 * setup & teardown required after the underlying
			 * modal has performed certain actions
			 */

			dialog.on("hidden.bs.modal", function(e) {
				// ensure we don't accidentally intercept hidden events triggered
				// by children of the current dialog. We shouldn't anymore now BS
				// namespaces its events; but still worth doing
				if (e.target === this) {
					dialog.remove();
				}
			});


			dialog.on("shown.bs.modal", function() {
				dialog.find(".btn-primary:first").focus();
				dialog.removeClass("fade");
			});


			if (options.backdrop !== "static") {
				// A boolean true/false according to the Bootstrap docs
				// should show a dialog the user can dismiss by clicking on
				// the background.
				// We always only ever pass static/false to the actual
				// $.modal function because with `true` we can't trap
				// this event (the .modal-backdrop swallows it)
				// However, we still want to sort of respect true
				// and invoke the escape mechanism instead
				dialog.on("click.dismiss.bs.modal", function(e) {
					// @NOTE: the target varies in >= 3.3.x releases since the modal backdrop
					// moved *inside* the outer dialog rather than *alongside* it
					if (dialog.children(".modal-backdrop").length) {
						e.currentTarget = dialog.children(".modal-backdrop").get(0);
					}

					if (e.target !== e.currentTarget) {
						return;
					}

					dialog.trigger("escape.close.bb");
				});
			}

			dialog.on("escape.close.bb", function(e) {
				if (callbacks.onEscape) {
					processCallback(e, dialog, callbacks.onEscape);
				}
			});

			/**
			 * Standard jQuery event listeners; used to handle user
			 * interaction with our dialog
			 */

			dialog.on("click", ".modal-footer button", function(e) {
				var callbackKey = $(this).data("bb-handler");
				processCallback(e, dialog, callbacks[callbackKey]);
			});

			dialog.on("click", ".bootbox-close-button", function(e) {
				// onEscape might be falsy but that's fine; the fact is
				// if the user has managed to click the close button we
				// have to close the dialog, callback or not
				processCallback(e, dialog, callbacks.onEscape);
			});

			dialog.on("keyup", function(e) {
				if (e.which === 27) {
					dialog.trigger("escape.close.bb");
				}
			});

			// the remainder of this method simply deals with adding our
			// dialogent to the DOM, augmenting it with Bootstrap's modal
			// functionality and then giving the resulting object back
			// to our caller

			$(options.container).append(dialog);

			dialog.modal({
				backdrop: options.backdrop ? "static": false,
				keyboard: false,
				show: false
			});

			if (options.show) {
				dialog.modal("show");
			}

			return dialog;
	    },

	    hideAll: function() {
	        $(".bootbox").modal("hide");
	    },
	    
	    animate: function(animate) {
	        _animate = animate;
	    },
	    
	    backdrop: function(backdrop) {
	        _backdrop = backdrop;
	    },

	    classes: function(classes) {
	        _classes = classes;
	    },
	    
		error: function(msg, options) {
			var op = $.extend({title:_translate("ERROR"),icon:"HD_ERROR"},options);
			that.alert(msg,op);
		},
		warn: function(msg, options) {
			var op = $.extend({title:_translate("WARN"),icon:"HD_WARN"},options);
			that.alert(msg,op);
		},
		info: function(msg, options) {
			var op = $.extend({title:_translate("INFO"),icon:"HD_INFO",closeTimer:true,backdrop:false,pos:"top",opacity:1},options);
			that.alert(msg,op);
		},
		success: function(msg, options) {
			var op = $.extend({title:_translate("SUCCESS"),icon:"HD_OK",closeTimer:true,backdrop:false,pos:"top",opacity:1},options);
			that.alert(msg,op);
		},
		confirm: function(msg, options) {
			var op = $.extend({title:_translate("CONFIRM"),icon:"HD_CONFIRM"},options);
			that.showConfirm(msg,op);
		}
    }
    $F.messager = that;
    return that;
});


