/**
 * @class
 * @name jQuery.cn.pagination
 * @description <jquery methods class> 分页控件扩展
 */
define(["cas/core","cas/options",localeFile,"cas/utils","cas/dataloader"],function ($F,$options,$lang,$utils) {
	
	"use strict"; // jshint ;_;


	/* PAGINATION AJAX CLASS DEFINITION
	 * ==================== */
	var Pagination = function (element,options) {
		var o = this
		,$el = o.$element = $(element)
		,pageNo = $el.attr("pageno")
		,totalCount = $el.attr("totalcount")
		,pageSize = $el.attr("pagesize")
		,target = $el.attr("loader-target")
		,pageLabel = $el.attr("pagelabel")?"#"+$el.attr("pagelabel"):""
		,pageJump = $el.attr("pageJump")?"#"+$el.attr("pageJump"):""
		,pageOption = $el.attr("pagesizeoption")?"#"+$el.attr("pagesizeoption"):""
		,options = $options.pagination;
		
		o.pageNo = pageNo&&pageNo.isInteger()?parseInt(pageNo,10):0;
		/**java分页对象起始页为0，传入参数为从0开始*/
		o.pageNo++;
		o.totalCount = totalCount&&totalCount.isInteger()?parseInt(totalCount,10):0;
		o.pageSize = pageSize&&pageSize.isInteger()?parseInt(pageSize,10):null;
		var box = $el.attr("page-box");
		var $box = $(box);
		if($box.length==0){	
			$box = $el.parents(".modal:first");
		}
		if($box.length==0){
			$box = $el.parents(".box:first");
		}
		if($box.length==0){
			$box = $(document);
		}
		o.$target = $(target,$box);
		o.$pageLabel = $(pageLabel,$box);
		o.$pageJump = $(pageJump,$box);
		o.$pageOption = $(pageOption,$box);
		o.computePageInfo();
		
		o.init();
	}
	/**
	 * 原型定义
	 */
	Pagination.prototype = {

		constructor: Pagination,
		/**
		 * 初始化方法
		 */
		init:function(){
			var o = this
			,$el = this.$element
			,lang = $lang.pagination
			,options = $options.pagination
			,$pageJump = o.$pageJump
			,$pageOption = o.$pageOption;
			if($el.children().size() == 0){
				$el.append(
					'<ul class="pagination pagination-sm no-margin">'+
						'<li class="disabled" flag="true"><a href="#" >&laquo;</a></li>'+
						'<li class="disabled" flag="true"><a href="#" >&lsaquo;</a></li>'+
						'<li class="disabled" flag="true"><a href="#" >&rsaquo;</a></li>'+
						'<li class="disabled" flag="true"><a href="#" >&raquo;</a></li>'+
					'</ul>'
				);
			}
			var $els = $('li', $el),$first=$($els[0]),$second=$($els[1]),$prevlast=$($els[$els.length-2]),$last=$($els[$els.length-1]);
			this.$first=$first;
			this.$second=$second;
			this.$prevlast=$prevlast;
			this.$last=$last;
			$first.on('click.bs.pagination.data-api', function(e){
				e.preventDefault();
				if($(this).hasClass("disabled")){
					e.stopPropagation();
					return;
				}
				o.jumpPage(1);
				});
			$second.on('click.bs.pagination.data-api', function(e){
				e.preventDefault();
				if($(this).parent().hasClass("disabled")){
					e.stopPropagation();
					return;
				}
				o.prevPage();
			});
			$prevlast.on('click.bs.pagination.data-api',function(e){
				e.preventDefault();
				if($(this).parent().hasClass("disabled")){
					e.stopPropagation();
					return;
				}
				o.nextPage();
				});
			$last.on('click.bs.pagination.data-api',function(e){
				e.preventDefault();
				if($(this).parent().hasClass("disabled")){
					e.stopPropagation();
					return;
				}
				o.jumpPage(o.pageCount);
			});
			$el.on('click.bs.pagination.data-api',"li:not([flag])", function (e) {
				e.preventDefault();
				o.jumpPage( (parseInt($('a', this).text(),10)) );
			} );
			/*增加分页选项*/
			if($pageOption.length > 0){
				var $opCombo = $pageOption.find(">.combobox");
				if($opCombo.length == 0){
					var items = "";
					$.each(options.pageSizeMenu,function(mi){
						items+='<li data-value="'+this+'"><a href="#">'+this+'</a></li>';
					});
					var combo = '<div  class="input-append dropdown combobox" value="'+o.pageSize+'">'+
									'<input style="width:30px;height:20px;" type="text">'+
									'<ul class="dropdown-menu" style="min-width:80px;">'+items+
									'</ul>'+
									'<span class="add-on pointer" data-toggle="dropdown"><i class="caret"></i></span></div>';
					$pageOption.html($utils.format($lang.pagination.pageSizeInfo,[combo]));
					$pageOption.initPageUI();
					$opCombo=$pageOption.find(".combobox");
				}
				$opCombo.on("change.pagination.data-api",function(){
					var newSize = parseInt($(this).combobox("getValue"),10);
					o.pageSize = newSize;
					if(o.totalCount > newSize * (o.pageNo-1)){
						o.$target.dataloader("load");
					}else{
						o.jumpPage(parseInt((o.totalCount+newSize-1)/newSize,10));
					}
				});
			}
			/*增加跳页选项*/
			if($pageJump.length > 0){
				var $jumpAction = $pageJump.find(".jumpPage");
				if($jumpAction.length == 0){
					var jump = '<div  class="input-append ">'+
									'<input class="jumpPageInput" style="width:30px;height:20px;" type="text" value="'+o.pageNo+'" >'+
								'</div>';
					var butn = '<button class="btn btn-xsm jumpPage">GO</button>';
					$pageJump.html($utils.format($lang.pagination.pageJumpInfo,[jump,butn]));
					$pageJump.initPageUI();
					$jumpAction=$pageJump.find(".jumpPage");
				}
				$pageJump.on("keydown",$.proxy(o.keypress,o)).on("keyup",$.proxy(o.keypressed,o));
				$jumpAction.on("click",function(e){
					o.action();
				});
			}
			/*初始化查询对象*/
			o.$target.dataloader("addPlugin",{
				name:"pagination",
				getParameters: function(){
					return o.getPageInfo();
				},
				loadReset: function(){
					o.reset();
				},
				refresh: function($rs){
					var $page =$("[pagesize][pageno][totalcount]",$rs);
					if($page.size() == 0)
						return;
					var pageInfo = {
						pageNo:parseInt($page.attr("pageno"),10),
						pageSize:parseInt($page.attr("pagesize"),10),
						totalCount:parseInt($page.attr("totalcount"),10)
					}
					/**java分页对象起始页为0，传入参数为从0开始*/
					pageInfo.pageNo++;
					o.refreshPageInfo(pageInfo);
				}
			});
			o.drawPage();
		},
		/**
		 * 下一页方法处理
		 */
		nextPage: function(){
			this.jumpPage(this.pageNo+1);
		},
		/**
		 * 上一页方法处理
		 */
		prevPage: function(){
			this.jumpPage(this.pageNo-1);
		},
		/**
		 * 页面跳转
		 */
		jumpPage: function(pageNo){
			var o = this;
			if(pageNo < 1)pageNo=1;
			if(pageNo > o.pageCount)pageNo = o.pageCount;
			if(o.pageNo == pageNo)
				return;
			o.pageNo = pageNo;
			o.$target.dataloader("load");
		},
		/**
		 * 计算分页信息
		 */
		computePageInfo: function(){
			var o = this,pageSize = o.pageSize,pageNo = o.pageNo,totalCount = o.totalCount;
			o.pageSize = pageSize||$options.pagination.pageSize;
			o.pageCount = parseInt((totalCount+pageSize-1)/pageSize,10);
			o.startRec = totalCount == 0?0:(pageNo-1)*pageSize+1;
			var endRec = pageNo * pageSize;
			o.endRec = endRec > totalCount?totalCount:endRec;
		},
		/**
		 * 刷新页面信息
		 * @param pageInfo{Object} 类似{pageSize:20,pageNo:1,totalCount:100}对象
		 */
		refreshPageInfo: function (pageInfo) {
			var o = this;
			o.pageNo = pageInfo.pageNo;
			o.pageSize = pageInfo.pageSize;
			o.totalCount = pageInfo.totalCount;
			o.drawPage();
		},
		/**
		 * 获取分页信息
		 * @return 类似{_pageSize:20,_pageNo:1}对象
		 */
		getPageInfo: function(){
			var pageSize = this.pageSize;
			var pageNo = this.pageNo;
			return {
				_pageSize:pageSize,
				_pageNo:pageNo
			}
		},
		reset: function(){
			this.pageNo = 1;
		},
		/**
		 * 重画分页内容
		 */
		drawPage: function(){
			var o = this;
			o.computePageInfo();
			
			var listLength = $options.pagination.listLength||5
			,start,end
			,half=Math.floor(listLength/2)
			,pageNo = o.pageNo
			,pageCount = o.pageCount
			,$el = o.$element;
			

			if (pageCount < listLength) {
				start = 1;
				end = pageCount;
			}
			else if (pageNo-1 <= half ) {
				start = 1;
				end = listLength;
			} else if (pageNo-1 >= (pageCount-half) ) {
				start = pageCount - listLength + 1;
				end = pageCount;
			} else {
				start = pageNo - half;
				end = start + listLength - 1;
			}
			$('li:not(li[flag])',$el).remove();
			// add the new list items and their event handlers
			var i,cls;
			for ( i = start; i <= end; i++ ) {
				cls = (i==pageNo) ? 'class="active"' : '';
				$('<li '+cls+'><a href="#">'+i+'</a></li>')
					.insertBefore(this.$prevlast);
			}

			// add / remove disabled classes from the static elements
			if (o.pageNo === 1 ) {
				this.$first.addClass('disabled');
				this.$second.addClass('disabled');
			} else {
				this.$first.removeClass('disabled');
				this.$second.removeClass('disabled');
			}
			if (pageNo === pageCount || pageCount === 0 ) {
				this.$last.addClass('disabled');
				this.$prevlast.addClass('disabled');
			} else {
				this.$last.removeClass('disabled');
				this.$prevlast.removeClass('disabled');
			}
			if(o.$pageLabel){
				var info = $lang.pagination.totalInfo;
				var v = [o.totalCount,o.pageSize,pageCount,pageNo,o.startRec,o.endRec];
				$(o.$pageLabel).html($utils.format(info,v));
			}
		},
		/**
		 * 键盘keydown屏蔽回车事件处理
		 */
		keypress:function(e){
			if (e.which === $F.keyCode.ENTER) {
				e.preventDefault();
			}
		},
		/**
		 * 键盘keyup事件处理
		 */
		keypressed:function(e){
			if (e.which === $F.keyCode.ENTER) {
				e.preventDefault();
				this.action();
			}
		},
		/**
		 * 操作事件处理
		 */
		action: function () {
			var o = this
			,val = parseInt($("input.jumpPageInput").val()?$("input.jumpPageInput").val():"1",10)
			o.jumpPage(val);
		},
	}
	/* PAGINATION AJAX PLUGIN DEFINITION
	 * ===================== */
	$.fn.pagination = function(option,value) {
		var methodReturn;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('pagination');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('pagination', (data = new Pagination(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
	}
	$.fn.pagination.Constructor = Pagination

});