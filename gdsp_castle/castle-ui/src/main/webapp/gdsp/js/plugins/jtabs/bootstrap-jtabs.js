/**
 * Website: http://git.oschina.net/hbbcs/bootStrap-addTabs
 *
 * Version : 0.6
 * 
 * Created by joe on 2016-2-4 
 * Update by xiangguo
 * 1.重构代码去除全局变量，使之支持多个jtabs对象
 * 2.增加最大页签数量
 * 3.插件化
 * 4.增加分离title及content
 * 5.修改Bug
 * 6.修改页签激活逻辑
 */
!function ($) {
	var Jtabs = function (element,options) {
		this.obj = $(element);
		this.curTabSize = 0;
		this.options = $.extend({
	        content: '', //直接指定所有页面TABS内容
	        monitor: '', //监视的区域
	        close: true, //是否可以关闭
	        iframeUse: true, //使用iframe还是ajax
	        iframeHeight: $(window).height()-100, //固定TAB中IFRAME高度,根据需要自己修改
	        maxTabSize: 5,
	        titlesid:"",
	        contentsid:"",
	        callback: function () { //关闭后回调函数
	        }
	    }, options || {});
		this.splitTitleContent = false;
		this.titlesObj = null;
		this.contentsObj = null;
		//设置是否分离title与content
		if(this.options.titlesid!=null && this.options.titlesid!=""
        	&&this.options.contentsid!=null && this.options.contentsid!=""){
			this.splitTitleContent = true;
			var navtabs = $('<ul>', {
                'role': 'tablist',
                'class': "nav nav-tabs tab_bottom",
                'style':"height:auto"
            });
			$("#"+this.options.titlesid).append(navtabs);
			this.titlesObj = navtabs;
			this.contentsObj = $("#"+this.options.contentsid);
		}else{
			this.titlesObj = this.obj.children('.nav-tabs');
			this.contentsObj = this.obj.children(".tab-content");
		}
		
		var that = this;
		$F.Jtabs = that;
		//增加页签事件绑定
		if(this.options.monitor!=""){
			$("#"+this.options.monitor).on('click', '[jtabs-addtab]', function () {
				var tabId= $(this).attr('jtab-id')||"";
				var tabName = $(this).attr('jtab-title')||"";
				var tabUrl = $(this).attr('jtab-url')||"";
		        that.add({
		            id: tabId,
		            title: tabName,
		            url: tabUrl,
		            ajax: false
		        });
		    });
		}
		this.obj.on('click', '.close-tab', function () {
	        var id = $(this).prev("a").attr("aria-controls");
	        that.close(id);
	    });
		
		this.obj.on('click', 'a[data-toggle="tab"][role="tab"]', function () {
			var id = $(this).attr("aria-controls");
			that.updateMenu(id);
	    });
		/*
		this.obj.on('mouseover', '.close-tab', function () {
	        $(this).removeClass('glyphicon-remove-sign').addClass('glyphicon-remove-circle');
	    })
	
	    this.obj.on('mouseout', '.close-tab', function () {
	        $(this).removeClass('glyphicon-remove-circle').addClass('glyphicon-remove-sign');
	    })
	    */
	    $(window).resize(function () {
	    	that.contentsObj.find('iframe').css('height', $(window).height()-150);
	        that.drop();
	    });
	};
	
	Jtabs.prototype = {
		constructor: Jtabs,
		add: function (opts) {
	        var id = 'tab_' + opts.id;
	        //如果TAB不存在，创建一个新的TAB
	        if (!$("#" + id)[0]) {
	        	if(this.curTabSize >= this.options.maxTabSize){
	        		alert("最多打开8个页面或报表标签！");
					return;
				}
	        	this.curTabSize++;
	        	this.titlesObj.find('.active').removeClass('active');
	  	        this.contentsObj.find('.active').removeClass('active');
				
	            //创建新TAB的title
	        	var titlelength = this.options.titlelength || 6;
	        	var titleSubString = opts.title&&opts.title.length>titlelength?opts.title.substring(0,titlelength)+"…":opts.title;
	            var title = $('<li>', {
	                'role': 'presentation',
	                'id': 'tab_' + id,
	                'ref-aria-controls':id,
	                'style':"background-color: #ffffff;border: 1px solid #d2d2d2;color: #333333;margin-right:3px;"
	            }).append(
	                $('<a>', {
	                    'href': '#' + id,
	                    'aria-controls': id,
	                    'role': 'tab',
	                    'data-toggle': 'tab',
	                    'class':'tab-a',
	                    'style':'padding-top: 6px;height:29px;padding-bottom:8px;font-weight:normal;',
	                    'title':opts.title
	                }).html(titleSubString+"&nbsp;&nbsp;")
	            );
	
	            //是否允许关闭
	            if (this.options.close) {
	                title.append(
	                    $('<i>',{class:'close-tab glyphicon glyphicon-remove'})
	                	//$('<i>',{class:'close-tab glyphicon glyphicon-remove-circle'})
	                	
	                );
	            }
	            //创建新TAB的内容
	            var content = $('<div>', {
	                'class': 'tab-pane',
	                'id': id,
	                'role': 'tabpanel',
	                
	            });
	           
	
	            //是否指定TAB内容
	            if (opts.content) {
	                content.append(opts.content);
	            } else if (this.options.iframeUse && !opts.ajax) {//没有内容，使用IFRAME打开链接
	                content.append(
	                    $('<iframe>', {
	                        'class': 'iframeClass',
//	                        'height': $(window).height()+10,
	                        'style': 'min-height:550px;',
	                        'frameborder': "no",
	                        'border': "0",
	                        'src': opts.url,
	                        'zindexIframe':'yes'
	                    })
	                );
	            } else {
	                $.get(opts.url, function (data) {
	                    content.append(data);
	                });
	            }
	            //加入TABS
	            this.titlesObj.append(title);
	            this.contentsObj.append(content);
	        }
	        this.titlesObj.find('.active').removeClass('active');
  	        this.contentsObj.find('.active').removeClass('active');
	        //激活TAB
	        $("#tab_" + id).addClass('active');
	        $("#" + id).addClass("active");
	        $(".iframeClass").on("load", function(event){//判断 iframe是否加载完成  这一步很重要
	        	$(".skin-default",this.contentDocument).click(function(){//添加点击事件
	        	　　　$("#favorites_div").hide();//通过id选择器获取收藏夹对象，使其隐藏
	        	　　});
	        	});
	        this.updateMenu(id);
	        this.drop();
	    },
	    close: function (id) {
	    	this.curTabSize--;
	        //激活相邻优先前一个
	    	this.activeTitleObj = null;
	    	this.activeContentObj = null;
	    	var that = this;
	    	this.titlesObj.find("li").each(function(index,element){
	    		if($(element).attr('id') == ("tab_" + id)){
	    			if( $(element).attr("class")=='active' && index==0){
	    				that.activeTitleObj = $("#tab_" + id).next();
	    				that.activeContentObj =  $("#" + id).next();
		    		}else if( $(element).attr("class")=='active' && index!=0){
		    			that.activeTitleObj = $("#tab_" + id).prev();
		    			that.activeContentObj = $("#" + id).prev();
		    		}
	    		}
	    	});
	    	if(this.activeTitleObj!=null && this.activeContentObj!=null){
		    	this.activeTitleObj.addClass('active');
		    	this.activeContentObj.addClass('active');
		    	this.updateMenu($(this.activeTitleObj).attr("ref-aria-controls"));
	    	}
	        //关闭TAB
	        $("#tab_" + id).remove();
	        $("#" + id).remove();
	        this.drop();
	        this.options.callback();
	    },
	    updateMenu:function(tabid){
	    	if(tabid&&this.options.monitor){
	    		var monitor = $("#"+this.options.monitor);
	    		if(monitor.length>0){
		    		tabid = "li_"+tabid.substring(4);
		    		//删除之前所有的激活样式
		    		monitor.find("ul[jtabs-ul='yes']").find("li").removeClass("active").removeClass("open");
		    		monitor.find("ul[jtabs-ul='yes']").find("ul[class='sub_menu']").hide();
		    		//激活当前选择
		    		var curLi = $("#"+tabid);
		    		curLi.addClass("active").addClass("open");
		    		var parentEle = curLi.parent();
		    		var level = 0;
		    		//设置最大层级防止因找不到jtabs-ul引起的死循环
		    		while(level<20 && parentEle!=null && !($(parentEle).attr("jtabs-ul")&&$(parentEle).attr("jtabs-ul")=='yes')){
		    			if($(parentEle).is('ul')){
		    				$(parentEle).show();
		    			}else if($(parentEle).is("li")){
		    				$(parentEle).addClass("active").addClass("open");
		    			}
		    			parentEle = $(parentEle).parent();
		    			level++;
		    		}
	    		}
	    	}
	    },
	    drop:function(){},
	    drop2: function () {
	        var element = this.titlesObj;
	        //创建下拉标签
	        var dropdown = $('<li>', {
	            'class': 'dropdown pull-right hide tabdrop'
	        }).append(
	            $('<a>', {
	                'class': 'dropdown-toggle',
	                'data-toggle': 'dropdown',
	                'href': '#'
	            }).append(
	                $('<i>', {'class': "glyphicon glyphicon-align-justify"})
	            ).append(
	                $('<b>', {'class': 'caret'})
	            )
	        ).append(
	            $('<ul>', {'class': "dropdown-menu"})
	        )
	
	        //检测是否已增加
	        if (!$('.tabdrop').html()) {
	            dropdown.prependTo(element);
	        } else {
	            dropdown = element.find('.tabdrop');
	        }
	        //检测是否有下拉样式
	        if (element.parent().is('.tabs-below')) {
	            dropdown.addClass('dropup');
	        }
	        var collection = 0;
	
	        //检查超过一行的标签页
	        element.append(dropdown.find('li'))
	            .find('>li')
	            .not('.tabdrop')
	            .each(function () {
	                if (this.offsetTop > 0 || element.width() - $(this).position().left - $(this).width() < 53) {
	                    dropdown.find('ul').append($(this));
	                    collection++;
	                }
	            });
	
	        //如果有超出的，显示下拉标签
	        if (collection > 0) {
	            dropdown.removeClass('hide');
	            if (dropdown.find('.active').length == 1) {
	                dropdown.addClass('active');
	            } else {
	                dropdown.removeClass('active');
	            }
	        } else {
	            dropdown.addClass('hide');
	        }
	    }
	};
	$.fn.jtabs = function (option, value) {
		var args = Array.apply(null, arguments);
		args.shift();
		var internal_return;
		var initFirstTab=false;
		var jtabInstance = null;
		this.each(function () {
			var $this = $(this),
				data = $this.data('jtabs'),
				options = typeof option === 'object' && option;
			
			if (!data){
				$this.data('jtabs', (data = new Jtabs(this,options)));
				jtabInstance = data;
				initFirstTab = true;
			}
			if (typeof option == 'string' && typeof data[option] == 'function') {
				internal_return = data[option].apply(data, args);
				if (internal_return !== undefined) {
					return false;
				}
			}
			
		});
		if(initFirstTab){
			jtabInstance.add({id:option.initTabId,title:option.initTabTitle,url:option.initTabUrl,ajax:false});
		}
		if (internal_return !== undefined)
			return internal_return;
		else
			return this;
	}
	$.fn.jtabs.Constructor = Jtabs;
}(window.jQuery);