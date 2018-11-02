/**
 * 系统选项设置
 */
define($.getExtendOptions(["cas/core"]),function($F){

	$F.options= {
		components: {
			/*名称			组件选择器			  	  					组件脚本文件	  							组件初始化方法名	初始化参数*/
			"datatable":	[".datatable[init!=true]",					"cas/datatable",						"datatable"],
			"dialog":		["[modal-toggle='modal']",					"cas/modal"],
			"form":			["[cas-form],form",							"cas/form"],
			"validate":		["form.validate",							"cas/validate",							"validate"],
			"ajax":			["[ajax-toggle='ajax']", 					"cas/ajax"],
			"date": 		[".datepicker", 							"cas/date", 							"datePicker"],
			"combobox": 	[".combobox", 								"cas/combobox", 						"combobox"],
			"reference": 	[".reference", 								"cas/reference"],
			"dataloader":	[".dataloader", 							"cas/dataloader", 						"dataloader"],
			"pagination": 	[".cas-page[loader-target]", 				"cas/pagination", 						"pagination"],
			"search": 		[".search", 								"cas/search", 							"search"],
			"sort": 		[".sort", 									"cas/sort", 							"sorter"],
			"tabs":			[".tabs[init!=true]",						"cas/tabs",								"tabs"],
			"tab": 			["[data-toggle='tab'][data-target]",		"cas/tab"],
			"tooltip": 		["[title]", 								"bootstrap/bootstrap", 					"tooltip", 		{container: document.body}],
			"event": 		["[events]", 								"cas/event", 							"initEvents"],
			"simpletree": 	[".simpletree", 							"cas/simpletree", 						"simpletree"],
			"easytree": 	[".easytree", 								"cas/easytree", 						"easytree"],
			"click": 		["[click]", 								"cas/event", 							"initClicks"],
			"checkAll": 	[".cas-checker", 							"cas/checker", 							],
			"scollbar": 	[".scrollbar", 								"cas/scrollbar", 						"scrolls"],
			"xquery": 		[".xquery", 								"cas/xquery", 			],
			"table": 		[".table-highlight", 						"cas/table", 			],
			"simplegrid":	[".simpleGrid",								"cas/simpleGrid",						"simplegrid"],
			"placehoder": 	["[placeholder]", 							"jquery", 								"inputPlaceHolder"],
			"ckeditor":		[".ckeditor",								"cas/CKeditor",							"ckeditor"],
			"flexigrid": 	[".flexigrid", 								"cas/flexigrid", 						"initFlexiGrid"],
			"jtabs": 		["[jtabs-init]", 							"cas/jtabs", 						    "initJtabs"],
			"portletAction":["[data-widget]",			        		"portal/portlet-action"],
			"selectpicker": [".selectpicker", 							"cas/selectpicker", 					"bselectpicker"],
			"ztree":		[".ztree",									"cas/zTree",							"ztree"],
			"treetable":	[".table-treeTable",						"cas/treetabination",					"treetable"],
			"simpleTable":  [".simpletable",                            "cas/simpletable",                      "simpletable" ]
		},
		includes:[],
		register:{

		},
		pages:{
			login:"/login.d",

		},
		config:{
			preloader:"#__preloader"
		},
		dataTable:{
			sDom: "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span12'i><'span12 center'p>>",
			iDisplayLength: 20,
		    aLengthMenu: [[20, 50, 100], [20, 50, 100]],
			sPaginationType: "bootstrap-fast",
			bServerSide: true,
			bProcessing: false,
			bDeferRender:false,
			sAjaxDataProp:"data",
			pageListLength:10
		},
		pagination:{
			pageSize:20,
			listLength:5,
			pageSizeMenu: [10, 20, 50, 100]
		},
		flexigrid:{
			rpOptions:[20, 50, 100]
		},
		simpletree:{
			selected:"selected",
			exp:"center_expandable", 
			coll:"center_collapsable", 
			firstExp:"first_expandable", 
			firstColl:"first_collapsable", 
			lastExp:"last_expandable",
			lastColl:"last_collapsable",
			expandIcon:"fa fa-folder-o",
			folderIcon:"fa fa-folder-open-o",
			oneExp:"one_expandable",
			oneColl:"one_collapsable",
			leafIcon:"fa fa-file-o",
			ck:"checked", 
			unck:"unchecked"
		},
		easytree:{
			color: "#383737",//#428bca
			backColor:"#FFFFFF",//所有节点的默认背景色,可被节点所覆盖
			showBorder:true,//是否显示边框
			borderColor:"#dddddd",//边框颜色
			checkedIcon:"glyphicon glyphicon-check",//复选框选中图标
			uncheckedIcon:"glyphicon glyphicon-unchecked",//取消选中图标
			emptyIcon:"glyphicon",//设置列表树中没有子节点的节点的图标
			collapseIcon:"glyphicon glyphicon-menu-down",//收缩节点图标
			expandIcon:"glyphicon glyphicon-menu-right",//展开节点图标
			highlightSearchResults:true,//高亮搜索到的结果，默认true。
			highlightSelected:true,//高亮选中的结果，默认true。
			nodeIcon:"glyphicon glyphicon-folder-open",//设置所有节点的图标
			onhoverColor:"#f8f8f8",//鼠标悬浮的颜色  #E3E3E3 更改为 #f8f8f8	 
			searchResultBackColor:"",//搜索结果背景色
			searchResultColor:"#D9534F",//搜索结果字体颜色
			selectedBackColor:"#f8f8f8",//选中背景色  #25abf2 更改为 #f8f8f8
			selectedColor:"#028ad2",//选中字体颜色  #FFFFFF 更改为 #028ad2
			showIcon : true,//是否显示节点图标
		},
		simplegrid:{
			rPPOptions	:	[10,20,50],					//分页下拉框选项
			rPP			:	"10",						//默认当前页数据显示设置
			/*每页记录: {0}||可自定义 为空时隐藏显示*/
			strRpp		:	"",
			/*显示第 {0} 到 {1} 条，总共 {2} 条数据||可自定义为空时隐藏显示提示*/
			strDisplay	:	"",
			/*第 {0} 页（共 {1} 页）||可自定义 为空时隐藏显示*/
			strPage		:	"第 {0} 页（共 {1} 页）",
			cbWidth		:	30,							//复选框列宽度
			cbMinWidth	:	30,							//复选框最小宽度
			cbMaxWidth	:	30,							//复选框最大宽度
			cbCls		:	'ui-state-default',			//复选框样式
			cbResizable	:	false,						//复选框(数字序列)是否可调整
			ncShow		:	false,						//是否显示数字序列
			horizontal	:	true,						//水平滚动条可视度
			pace		:	"fast",	//滚动条滚动速度，可选项（一致 consistent，最佳optimum，快fast）一致和最佳用于委托滚动，快用于实时滚动
		}
	};
	if(arguments.length >1){
		var arg = [true,$F.options];
		for(var i= 1; i < arguments.length; i++){
			arg.push(arguments[i]);
		}
		$.extend.apply($,arg);
	}
	return $F.options;
});