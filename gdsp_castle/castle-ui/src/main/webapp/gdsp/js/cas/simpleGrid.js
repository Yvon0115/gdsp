/**
 * 网格
 */
define(["cas/options","pqGrid","cas/dataloader","link!plugins/jquery/jquery-ui-min","link!plugins/pqGrid/pqgrid-min"],function($options) {
	"use strict";
	/**
	 * 构造方法
	 */
	var SimpleGrid = function(element,options){
		
		var o = this,$el = o.$element = $(element)
		,gtitle = $el.attr("gtitle")
		,width = $el.attr("width")||"100%"
		,height = $el.attr("height")||"450"
		,minWidth = $el.attr("minWidth")||"30"
		,hoverMode = $el.attr("hoverMode")||"row"
		,style = $el.attr("style")
		,showcollap = typeof($el.attr("showcollap"))=="undefined"?true:false
		,collapsible = typeof($el.attr("collapsible"))=="undefined"?true:false
		,numCellShow = typeof($el.attr("numCellShow"))=="undefined"?true:false
		,draggable = typeof($el.attr("draggable"))=="undefined"?false:true
		,sortable = typeof($el.attr("sortable"))=="undefined"?false:true
		,pageable = typeof($el.attr("pageable"))=="undefined"?false:true
		,editable = typeof($el.attr("editable"))=="undefined"?true:false
		,disable = typeof($el.attr("disable"))=="undefined"?false:true
		,autoFitable = typeof($el.attr("autoFitable"))=="undefined"?false:true		
		,columnBorders = typeof($el.attr("columnBorders"))=="undefined"?false:true
		,resizable = typeof($el.attr("resizable"))=="undefined"?false:true
		,flexWidth = typeof($el.attr("flexWidth"))=="undefined"?false:true
		,flexHeight = typeof($el.attr("flexHeight"))=="undefined"?false:true
		,hwrap = typeof($el.attr("hwrap"))=="undefined"?true:false
		,wrap = typeof($el.attr("wrap"))=="undefined"?true:false
		,virtualX = typeof($el.attr("virtualX"))=="undefined"?true:false
		,virtually = typeof($el.attr("virtually"))=="undefined"?true:false
		,showHeader = typeof($el.attr("showHeader"))=="undefined"?false:true
		,showBottom = typeof($el.attr("showBottom"))=="undefined"?false:true
		,showTitle = typeof($el.attr("showTitle"))=="undefined"?true:false
		,showTop = typeof($el.attr("showTop"))=="undefined"?false:true
		,showToolbar = typeof($el.attr("showToolbar"))=="undefined"?false:true
		,checkable = typeof($el.attr("checkable"))=="undefined"?false:true
		,freezeCols = $el.attr("freezeCols")||"0"
		,freezeRows = $el.attr("freezeRows")||"0"
		,id=$el.attr("id")
		,target = $el.attr("cas-simpleGrid");
		o.rpcParam = $el.attr("rpcParam");//页面选中id隐藏域
		o.$target = $(target);
		/* 初始加载表格数据，创建模型 */
		var json = o.initData()
		,colModel = json.colModel
		,dataModel = {
				data:json.dataModel,
	            location: "local",
	            sorting:"local",
	            sortDir:["up","down"],
	        }
		,scrollModel = {//滚动条设置
				horizontal:options.horizontal,//水平滚动条是否可视
				pace:options.pace,			  //滚动条滚动速度
				autoFit:autoFitable,		  //是否自适应表格宽度
				theme:true					  //滚动条是否启用主题
		};
		var pageModel,selectionModel,numberCell;
		if(checkable){
			var checkOp = {
				title: "", 
				dataIndx: "state", 
				width: options.cbWidth, 
				minWidth:options.cbMinWidth,
				maxWidth:options.cbMaxWidth, 
				align: "center", 
				type:'checkBoxSelection', 
				cls: options.cbCls, 
				resizable:options.cbResizable, 
				sortable:false };
			var len = colModel.unshift(checkOp);
			selectionModel = {
				type: 'none', 
				mode: 'range',
				All: false,
				cbHeader:true, 
				cbAll:false
			};
			//启用checkbox后调整列冻结的列数
			freezeCols = parseInt(freezeCols)+1;
			freezeCols = freezeCols>len?len:freezeCols;
			freezeCols = freezeCols==1?0:freezeCols;
		}else{//非checkbox时为行选中
			selectionModel = {
					type: "row", 
					mode: 'single',
					All: false,
					cbHeader:null, 
					cbAll:false
				};
		}
		if(numCellShow){
			numberCell = {
				title:"序号",
				width:40,
				minWidth:30,
				resizable:false,
				show:true
			}
		}else{
			numberCell = {
				show:false
			}
		}
		if(pageable){
			pageModel = { // 本地分页对象
					type: "local", 
					rPP: options.rPP,
					strRpp: options.strRpp,
					rPPOptions:options.rPPOptions,
					strDisplay: options.strDisplay, 
					strPage: options.strPage
			}
		}
		var colp= { on:false,collapsed : collapsible };
		o.obj = {
				title	:	gtitle,				// 表格标题
				width	:	width,				// 表格宽度
				height	:	height,				// 表格高度
			colModel	:	colModel,			// 列模型
			dataModel	:	dataModel,			// 数据模型
			pageModel	:	pageModel,			// 分页模型
			numberCell	:	numberCell,			// 序列数模型
			scrollModel	:	scrollModel,		// 滚动条模型
		selectionModel	: 	selectionModel,		// 选中模型
		filterModel		: 	{ mode: 'AND' },	// 检索模型,多条件查询时为and关系
			toolbar		: 	null,				// 工具栏
			collapsible	:	colp,				// 是否折叠
			draggable	:	draggable,			// 是否可拖动
			editable	:	editable,			// 是否可编辑
		columnBorders	:	columnBorders,		// 是否显示列边框
			virtualX	:	virtualX,			// x轴是否可滚动
			virtually	:	virtually,			// y轴是否可滚动
			freezeRows	:	freezeRows,			// 冻结行
			freezeCols	:	freezeCols,			// 冻结列
			resizable	:	resizable,			// 是否可调整网格头大小
			sortable	:	sortable,			// 是否可排序
			flexWidth	:	flexWidth,			// 是否自适应宽度
			flexHeight	:	flexHeight,			// 是否自适应高度
			showTitle	:	showTitle,			// 是否显示标题
			showTop		:	showTop,			// 是否显示顶部
			showToolbar	:	showToolbar,		// 是否显示工具栏
			showHeader	:	showHeader,			// 是否显示头
			showBottom	:	showBottom,			// 是否显示底部
			pageable	:	pageable,			// 是否可分页
			hwrap		:	hwrap,				// 标题是否随宽度变化
			wrap		:	wrap,				// 数据是否随宽度变化
			hoverMode	:	hoverMode			// 鼠标悬停网格
		};
		
		o.checkedIds=[];
		o.init();
	}
	/**
	 * 原型定义
	 */
	SimpleGrid.prototype = {
		constructor: SimpleGrid,
		/**
		 * 初始化方法
		 */
		init:function(){
			var o = this
			,$el = this.$element
			,id = $el.attr("id")
			,obj = this.obj
			,$simpleGrid = $("#"+id).pqGrid(obj);
			/*初始化查询对象*/
			if(o.$target.length > 0){
				o.$target.dataloader("addPlugin",{
					name:"simpleGrid",
					refresh: function(){
						o.refreshGridInfo($simpleGrid);
					}
				});
			}
			o.initEventHandle();
		},
		/**
		 * 初始化加载数据
		 */
		initData:function(){
			let o = this
			,$el = o.$element
			,json = $el.attr("grid-data")
			,val = $.parseJSON(json)
			,colM = val.colModel;
			/* 为列模型对象添加render触发，方便选中时获取rowData，对行数据进行操作 */
			for(let x in colM){
				colM[x].render = function( ui ){var rowData = ui.rowData;return rowData[x];};
			}
			return val;
		},
		/**
		 * 刷新表格
		 */
		refreshGridInfo:function(obj){
			var $initedGrid = obj
			,o = this
			,refreshData = o.initData();
			var colModel = refreshData.colModel;
			var dataModel = refreshData.dataModel;
			$initedGrid.pqGrid( { colModel: colModel } );
			$initedGrid.pqGrid( { dataModel: { data: dataModel } } );
			$initedGrid.pqGrid("refreshDataAndView");
		},
		/**
		 * 获取网格选中id
		 */
		getCheckedIds:function(){
			return this.checkedIds;
		},
		/**
		 * 手动重置选中id状态，方便前端控制
		 */
		resetCheckedIds:function(){
			this.checkedIds.splice(0,checkedIds.length);
		},
		/**
		 * 选中触发后为id隐藏域赋值
		 */
		assignHidden:function(checkedIds){
			var o = this
			,$el = this.$element
			,$hidden={};
			if(o.rpcParam!=""){
				$hidden = $("#"+o.rpcParam);
			};
			var paramString="";
			if(checkedIds.length == 0){
				paramString="";
			}else if(checkedIds.length == 1){
				paramString = checkedIds[0];
			}else{
				$.each(checkedIds,function(i){
					var item = this;
					i==checkedIds.length-1?paramString += item:paramString += item + ",";
				});
			}
			$hidden.val(paramString);
		},
		/**
		 * 初始化事件
		 */
		initEventHandle:function(){
			var o = this
			,$el = this.$element
			,checkedIds=o.checkedIds;
			//行选中触发
			$el.on( "pqgridrowselect", function( event, ui ) {
				var rows=ui.rows,$tr,rowData=ui.rowData;
				if(rowData){
					if($.inArray(rowData.id,checkedIds) == -1)
						checkedIds.push(rowData.id);
						o.assignHidden(checkedIds);
				}else if(rows){
					$.each(rows,function(i,item){
						if($.inArray(item.rowData.id,checkedIds) == -1)
							checkedIds.push(item.rowData.id);
							o.assignHidden(checkedIds);
					});
				}
			});
			//行取消选中触发
			$el.on( "pqgridrowunselect", function( event, ui ) {
				var rows=ui.rows,$tr,rowData=ui.rowData;
				if(rowData){
					var index=checkedIds.indexOf(rowData.id);
					if(index != -1)
						checkedIds.splice(index,1);
						o.assignHidden(checkedIds);
				}else if(rows){
					checkedIds.splice(0,checkedIds.length);
					o.assignHidden(checkedIds);
				}
			});
		}
		
	}
	
	/**
	 * jquery方法拓展simpleGrid
	 * 手动刷新，将checkedIds数组清空
	 */
//	$.fn.ResetCheckedIds = function () {
//		var $el=$(this),o=$el.data('simplegrid')
//		,checkedIds=o.checkedIds;
//		checkedIds.splice(0,checkedIds.length);
//	}
	
	/**
	 * jquery拓展simpleGrid
	 */
	$.fn.simplegrid = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.simplegrid,option):$options.simplegrid;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('simplegrid');
			if (!data) 
				$this.data('simplegrid', (data = new SimpleGrid(this, options)));
			if (typeof option === 'string') 
				methodReturn = data[option](value);
		});
		return methodReturn;
	};
	
	$.fn.simplegrid.Constructor = SimpleGrid;
	return $;
});