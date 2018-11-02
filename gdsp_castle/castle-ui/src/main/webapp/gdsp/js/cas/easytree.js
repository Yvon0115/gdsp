/**
 * @class
 * @name jQuery.fn.easytree
 * @description	基于Bootstrap-treeview的树构建
 */
define(["cas/core","cas/options","bootstrap/bootstrap-treeview"],function ($F,$options){
	"use strict"; // jshint ;_
	/**
	 * 构造方法
	 */
	var EasyTree = function (element, options) {
		var o = this,$el = o.$element = $(element)
		,showCheckbox = typeof($el.attr("showCheckbox"))=="undefined"?false:true
		,showBorder = typeof($el.attr("showBorder"))=="undefined"?false:true
		,showTags = typeof($el.attr("showTags"))=="undefined"?false:true
		,selectFirstNode = typeof($el.attr("selectFirstNode"))=="undefined"?false:true
		,checkOption = $el.attr("checkOption")
		,multiSelect = typeof($el.attr("multiSelect"))=="undefined"?false:true
		,id=$el.attr("id")
		,url=$el.attr("url")
		,levels=$el.attr("levels")==null?1:$el.attr("levels");
		$F.isReadOnlyTree = true;
		o.options = options = $.extend(options,{
			checkOption:checkOption,
			id:id
		});
		var datas = _getTreeData(__contextPath+url);
		var json = eval('(' + datas + ')'); 
		var $checkableTree = $("#"+id).treeview(
		{
			color: options.color,//文本颜色
			data : json,
			//初始化整体树属性
			backColor:options.backColor,//所有节点的默认背景色,可被节点所覆盖
			showBorder:showBorder,//是否显示边框
			borderColor:options.borderColor,//边框颜色
			checkedIcon:options.checkedIcon,//复选框选中图标
			uncheckedIcon:options.uncheckedIcon,
			collapseIcon:options.collapseIcon,//展开图标
			expandIcon:options.expandIcon,//关闭图标
			highlightSearchResults:options.highlightSearchResults,//高亮搜索到的结果，默认true。
			highlightSelected:options.highlightSelected,//高亮选中的结果，默认true。
			levels:levels,//设置默认展开到的层级
			nodeIcon:options.nodeIcon,//设置所有节点的图标
			onhoverColor:options.onhoverColor,//鼠标悬浮的颜色
			preventUnselect:true,//一个节点是否可以没有另一个节点被选中。默认flase
			searchResultBackColor:options.searchResultBackColor,
			searchResultColor:options.searchResultColor,
			selectedBackColor:options.selectedBackColor,
			selectedColor:options.selectedColor,
			multiSelect:showTags,//是否可多选，默认false
			showIcon : true,
			showCheckbox : showCheckbox,
			showTags: showTags
		});
		$("#"+id).on('click','.list-group-item',function(event){
			$(event.target).find(".expand-icon").eq(0).click();
		})
//		$("#treeview-test2").treeview('disableAll');//停用树
//		$("#treeview-test2").treeview('enableAll');//启用树
		_initEventHandle($el,options);
		_initSearch($checkableTree,options);
		_initSelectFirstNode(selectFirstNode,$checkableTree,$el,levels);
		
	}
	/**
	 * 获取构建树的数据
	 * @param url
	 */
	function _getTreeData(url){
		var val = "";
		$.ajax({
			type: "GET",
			url:url,
			cache:false,
			async: false,
			error: function(request) {
			    alert("error");
			},
			success: function(data) {
				val = data;
			}
		});
		return val;
	}
	/**
	 * 初始化是否默认选中第一个节点
	 */
	function _initSelectFirstNode(selectFirstNode,$checkableTree,$el,levels){
		if(selectFirstNode){
			//获取第一个根节点
			var node = getFirstRootNode($checkableTree);
			if(node){
				//触发选中
				if(!node.state.selected){
					var $a = $("li[data-nodeid='"+node.nodeId+"']",$el);
					if($a&&$a.length!=0){
						$a.click();
					}
					}
				//展开逻辑
				if(levels>1&&!node.state.expanded){
					$checkableTree.treeview('expandNode',node.nodeId);
				}
			}
		}
	}
	/**
	 * 获取第一个根节点
	 */
	function getFirstRootNode($checkableTree){
		return $checkableTree.treeview('getNode',0);
	}
	/**
	 * 初始化事件处理
	 * @param $el 树对应节点对象
	 * @param options 选项
	 */
	function _initEventHandle($el,options){
		$el.on('nodeSelected',function(event, data){//节点选中
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeSelected = $.Event('onNodeSelected', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeSelected);
//			$("#treeview-test2").treeview('enableAll');//启用树
		});
		$el.on('nodeUnselected ', function(event, data){//节点取消选中
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeUnselected = $.Event('onNodeUnselected', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeUnselected);
		});
		$el.on('nodeChecked', function(event, data){//节点选中（checkbox）
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeChecked = $.Event('onNodeChecked', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeChecked);
			if(options.checkOption==1){
				var selectNodes = getNodeIdArr(data);//获取所有子节点
	            if(selectNodes){ //子节点不为空，则选中所有子节点
	                $('#'+$a.context.id).treeview('checkNode', [ selectNodes,{ silent: true }]);
	            }
	            /*var parentNodes = getParentNodeIdArr(data);
	            for(var x in parentNodes){
	            	var childNode = getNodeIdArr($('#'+options.id).treeview('getNode',parentNodes[x]));
	            	console.log($('#'+options.id).treeview('getChecked',parentNodes[x]));
	            	console.log(childNode);
	            	if(isContained(getNodeIdArr(childNode),childNode)){
	            		console.log(11111);
	            	}
	            }*/
//	            _checkParent(data,$el);
			}
		});
		$el.on('nodeUnchecked', function(event, data){//节点取消选中（checkbox）
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeUnchecked = $.Event('onNodeUnchecked', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeUnchecked);
			if(options.checkOption==1){
				var selectNodes = getNodeIdArr(data);//获取所有子节点
				if(selectNodes){ //子节点不为空，则取消选中所有子节点
					$('#'+$a.context.id).treeview('uncheckNode', [ selectNodes,{ silent: true }]);
				}
			}
		});
		$el.on('nodeCollapsed', function(event, data){//节点被折叠
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeCollapsed = $.Event('onNodeCollapsed', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeCollapsed);
		});
		$el.on('nodeExpanded', function(event, data){//节点被展开
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeExpanded = $.Event('onNodeExpanded', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeExpanded);
		});
		$el.on('searchComplete', function(event, results){//搜索完成之后触发
			var $a = $("li[data-nodeid='"+results.nodeId+"']",$(this));
			var searchComplete = $.Event('onSearchComplete', {
				node:results
			});
			$a.parents("div.treeview").trigger(searchComplete);
		});
		$el.on('searchCleared', function(event, results){//搜索结果被清除之后触发
			var $a = $("li[data-nodeid='"+results.nodeId+"']",$(this));
			var searchCleared = $.Event('onSearchCleared', {
				node:results
			});
			$a.parents("div.treeview").trigger(searchCleared);
		});
		$el.on('nodeDisabled', function(event, data){//节点被禁用
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeDisabled = $.Event('onNodeDisabled', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeDisabled);
		});
		$el.on('nodeEnabled', function(event, data){//节点被启用
			var $a = $("li[data-nodeid='"+data.nodeId+"']",$(this));
			var nodeEnabled = $.Event('onNodeEnabled', {
				node:data
			});
			$a.parents("div.treeview").trigger(nodeEnabled);
		});
		//判断数组a是否包含b
		function isContained(a, b){
		    if(!(a instanceof Array) || !(b instanceof Array)) return false;
		    if(a.length < b.length) return false;
		    var aStr = a.toString();
		    for(var i = 0, len = b.length; i < len; i++){
		       if(aStr.indexOf(b[i]) == -1) return false;
		    }
		    return true;
		}
		//递归获取所有的子结点id
		function getNodeIdArr( node ){
		        var ts = [];
		        if(node.nodes){
		            for(var x in node.nodes){
		                ts.push(node.nodes[x].nodeId)
		                if(node.nodes[x].nodes){
		                var getNodeDieDai = getNodeIdArr(node.nodes[x]);
		                    for(var j in getNodeDieDai){
		                        ts.push(getNodeDieDai[j]);
		                    }
		                }
		            }
		        }else{
		            ts.push(node.nodeId);
		       }
		   return ts;
		}
		//递归获取所有的直接父结点id
		function getParentNodeIdArr(node){
		        var ts = [];
		        if($('#'+options.id).treeview('getParent',node).nodeId){
	                var parentNode = $('#'+options.id).treeview('getParent',node);
	                ts.push(parentNode.nodeId);
	                var getNodeDieDai = getParentNodeIdArr(parentNode);
                    for(var j in getNodeDieDai){
                        ts.push(getNodeDieDai[j]);
                    }
		        }else{
		            ts.push($('#'+options.id).treeview('getParent',node).nodeId);
		       }
		   return ts;
		}
	}
	/**
	 * 设置上级节点的复选框状态
	 */
	function _checkParent($n,$el){
		if(!($el.treeview('getParent',$n).nodeId>=0))
			return;
		var $p = $el.treeview('getParent',$n);
		var $ck = $("[data-nodeid='"+$p.nodeId+"'] > .check-icon");
		var $node = $("[data-nodeid='"+$p.nodeId+"']");
		if($ck.length > 0) {
			console.log($p.nodes);
			var $st = $(">ul", $p);
			var boxcount = $p.nodes.length;
			var checkcount = 0;
			var imcount = 0;
			for(var x in $p.nodes){
				if($("[data-nodeid='"+$p.nodes[x].nodeId+"'] .fa fa-check-square")){
					imcount += 1;
				}
                if($p.nodes[x].state.checked){
                	checkcount += 1;
                }
            }
			var addCss = (boxcount == checkcount ? "glyphicon-check" : (imcount+checkcount != 0 ? "fa fa-check-square" : "glyphicon-unchecked"));
			if(addCss.indexOf("fa")>=0){
				$node.state.checked = "indeterminate";
			}else if(addCss.indexOf("glyphicon-unchecked")>=0){
				$node.state.checked = false;
			}else if(addCss.indexOf("glyphicon-check")>=0){
				$node.state.checked = true;
			}
			/*if (addCss == "checked" || addCss == "fa fa-check-square")
				$p.attr("ckbox", "true");
			else
				$p.removeAttr("ckbox");*/

			if ($ck.hasClass(addCss))
				return;
			if(addCss.indexOf("fa")>=0){
				$ck.removeClass("icon node-icon glyphicon glyphicon-unchecked")
				.removeClass("icon node-icon glyphicon glyphicon-check").addClass(addCss);
			}else
				$ck.removeClass("glyphicon-unchecked").removeClass("glyphicon-check").removeClass("fa fa-check-square").addClass(addCss);
		}
		_checkParent($p,$el);
	};
	
	/**
	 * 初始化搜索设置项
	 * @param	$checkableTree 树对象
	 */
	function _initSearch($checkableTree,opt){
		//搜索
		var search = function(e) {
			var pattern = $('#'+$checkableTree.attr("id")+'input-search').val();
			var options = {
			  ignoreCase: true,
			  exactMatch: false,
			  revealResults: true
			};
			$checkableTree.treeview('search', [ pattern, options ]);
		}
		//给搜索按钮绑定事件
		//$('#btn-search').on('click', search);
		//input框绑定键盘事件
		$('#'+opt.id+'input-search').on('keyup', search);
		//清除搜索结果
		/*$('#btn-clear-search').on('click', function (e) {
			$checkableTree.treeview('clearSearch');
			$('#input-search').val('');
		});*/
	}
	/**
	 * 原型定义
	 */
	EasyTree.prototype = {
		constructor: EasyTree
	}
	/**
	 * jquery方法扩展simpletree
	 */
	$.fn.easytree = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.easytree,option):$options.easytree;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('easytree');
			if (!data) 
				$this.data('easytree', (data = new EasyTree(this, options)));
			if (typeof option === 'string') 
				methodReturn = data[option](value);
		});
		return methodReturn;
	};
	$.fn.easytree.Constructor = EasyTree;
 	return $;
});