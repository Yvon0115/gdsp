define(["cas/core","cas/options","plugins/zTree/js/jquery.ztree.all","link!plugins/zTree/metroStyle"],function($F,$options){
	"user strict";
	/**
	 * 构造方法
	 */
	var ZTree = function(element,options){
		var o = this,$tree = o.$element = $(element);
		_contas={
			url:$tree.attr("url"),
			checkBoxEnable:typeof($tree.attr("checkBoxEnable"))=="undefined"?false:true,
			isCascade:typeof($tree.attr("isCascade"))=="undefined"?{"Y":"","N":""}:{"Y":"ps","N":"ps"},
			searchAble:typeof($tree.attr("searchAble"))=="undefined"?false:true,
			menuCheckbox:typeof($tree.attr("menuCheckbox"))=="undefined"?false:true,
			enableCheck:typeof($tree.attr("enableCheck"))=="undefined"?false:true,
			allDisable:typeof($tree.attr("allDisable"))=="undefined"?false:true,
			isAsync:typeof($tree.attr("isAsync"))=="undefined"?false:true,
			isFirstSelected:typeof($tree.attr("isFirstSelected"))=="undefined"?false:true,
			levels:$tree.attr("levels"),
			events:$tree.attr("events"),
			id:$tree.attr("id"),
			asyncParam:$tree.attr("asyncParam"),
			nodes:$tree.attr("nodes"),
			chkStyle:$tree.attr("chkStyle"),
			radioType:$tree.attr("radioType"),
			isSelectedExpand:$tree.attr("isSelectedExpand") == "true"?true:false,
			isSinglePath:$tree.attr("isSinglePath") == "true"?true:false,
			isCancleCheck:$tree.attr("isCancleCheck") == "true"?true:false
		};
		var setting={
			check:{
				enable:_contas.checkBoxEnable,
				chkDisabledInherit:false,
				nocheckInherit:true,
				chkboxType: _contas.isCascade,
				chkStyle:_contas.chkStyle,
				radioType:_contas.radioType,
				enableCheck:_contas.enableCheck
			},
			data:{
				key:{
					children:"nodes"
					,title:"title"
				}
			},
			async:{
				enable:_contas.isAsync,
				contentType:"application/x-www-form-urlencoded",
				dataType:"json",
				url:__contextPath+_contas.url,
				autoParam:["id="+(typeof (eval("("+_contas.asyncParam+")").id) === "undefined"?"id":eval("("+_contas.asyncParam+")").id),"name="+(typeof (eval("("+_contas.asyncParam+")").name) === "undefined"?"name":eval("("+_contas.asyncParam+")").name)],
				type:"POST",
				dataFilter:filter
			},
			view:{
				dblClickExpand:false,
				selectedMulti:false,
				showLine:true,
				showIcon:true,
				txtSelectedEnable:true,
				fontCss:fontCss,
				isFirstSelected:_contas.isFirstSelected,
				isSelectedExpand:_contas.isSelectedExpand,
				allDisable:_contas.allDisable,
				isSinglePath:_contas.isSinglePath,
				isCancleCheck:_contas.isCancleCheck,
				curExpandNode:null
			},
			edit:{
				enable:_contas.editEnable,
				editNameSelectAll:false,
				showRemoveBtn:false,
				showRenameBtn:false
				,drag:{ //拖拽功能(暂时已禁用)
					isCopy:false,
					prev: _contas.prev,
					next: _contas.next,
					inner:_contas.inner,
					minMoveSize: 5,
					borderMax: 10,
					borderMin: -5,
					maxShowNodeNum: 5,
					autoOpenTime: 0
				},
			},
			callback:{
				beforeClick:zTreeBeforeClick,
				beforeDrag:zTreeBeforeDrag,
				beforeExpand:zTreeBeforeExpand,
				
				onExpand:zTreeOnExpand,
				onClick:zTreeOnClick,
				onAsyncSuccess:zTreeOnAsyncSuccess,
				onAsyncError:zTreeOnAsyncError
			}
		};
		if(_contas.isAsync){
			$.fn.zTree.init($("#"+_contas.id),setting);//初始化zTree树
			var zTree = $.fn.zTree.getZTreeObj(_contas.id);
			_initEventsHandle(_contas.events,zTree.transformToArray(zTree.getNodes()));
		}else{
			var data = _getJsonData(__contextPath+_contas.url);
			if(data == "")
				return;
			var json = eval("("+data+")");
			$.fn.zTree.init($("#"+_contas.id),setting,json);//初始化zTree树
			var zTree = $.fn.zTree.getZTreeObj(_contas.id);
			jsons = zTree.transformToArray(zTree.getNodes());
			_loadIcon(jsons,_contas.id);
			_expandTree(null,_contas);
			_initDisable(jsons,_contas.id);
			filter(_contas.id,null,jsons);
			_initEventsHandle(_contas.events,jsons);
			_initChecked(jsons,_contas.id);
			//设置是否选中首节点
			var isFirst = zTree.setting.view.isFirstSelected;
			if(isFirst){
				if(jsons.length <= 0)
					return;
				var node = null;
				if(typeof jsons[0] != "object"){
					node = zTree.getNodeByParam("id",eval("("+jsons[0]+")")[0]["id"]);
				}else{
					node = zTree.getNodeByParam("id",jsons[0]["id"]);
				}
				if(node != null && node.parentTId == null){
					zTree.setting.view.curExpandNode = node;
					var func = $.Event("onNodeClick",{
						node:node
					});
					$("#"+node.tId+"_span").trigger(func);
					zTree.selectNode(node);
				}
			}
		}
		if(_contas.searchAble){
			searchInput = $("#"+_contas.id+"-input-search");
			searchInput.bind("keyup",getNodesByParFuzzy);
		}
		if(_contas.menuCheckbox){
			menuCheckbox = $("#ztreeInput"+_contas.id);
			menuCheckbox.bind("click",showMenu);
		}
	}
	function zTreeBeforeClick(treeId, treeNode, clickFlag){
		var nodeDisable = treeNode.nodeDisable;
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		if((typeof nodeDisable === "undefined"&&!zTree.setting.view.allDisable)|| nodeDisable === "false"){
			return true;
		}else if(nodeDisable === "true"||zTree.setting.view.allDisable){
			return false;
		}
	}
	function zTreeBeforeDrag(treeId, treeNodes){
		return false; //禁用拖拽功能
	}
	function zTreeBeforeExpand(treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var isSinglePath = zTree.setting.view.isSinglePath;
		if(!isSinglePath)
			return true;
		var curExpandNode = zTree.setting.view.curExpandNode;
		var pNode = curExpandNode ? curExpandNode.getParentNode():null;
		var treeNodeP = treeNode.parentTId ? treeNode.getParentNode():null;
		for(var i=0, l=!treeNodeP ? 0:treeNodeP.nodes.length; i<l; i++ ) {
			if (treeNode !== treeNodeP.nodes[i]) {
				zTree.expandNode(treeNodeP.nodes[i], false,true);
			}
		}
		while (pNode) {
			if (pNode === treeNode) {
				break;
			}
			pNode = pNode.getParentNode();
		}
		if (!pNode) {
			singlePath(treeNode,curExpandNode,zTree);
		}
	}
	function singlePath(newNode,curExpandNode,zTree) {
		if (newNode === curExpandNode) return;

        var rootNodes, tmpRoot, tmpTId, i, j, n;

        if (!curExpandNode) {
            tmpRoot = newNode;
            while (tmpRoot) {
                tmpTId = tmpRoot.tId;
                tmpRoot = tmpRoot.getParentNode();
            }
            rootNodes = zTree.getNodes();
            for (i=0, j=rootNodes.length; i<j; i++) {
                n = rootNodes[i];
                if (n.tId != tmpTId) {
                    zTree.expandNode(n, false,true);
                }
            }
        } else if (curExpandNode && curExpandNode.open) {
			if (newNode.parentTId === curExpandNode.parentTId) {
				zTree.expandNode(curExpandNode, false,true);
			} else {
				var newParents = [];
				while (newNode) {
					newNode = newNode.getParentNode();
					if (newNode === curExpandNode) {
						newParents = null;
						break;
					} else if (newNode) {
						newParents.push(newNode);
					}
				}
				if (newParents!=null) {
					var oldNode = curExpandNode;
					var oldParents = [];
					while (oldNode) {
						oldNode = oldNode.getParentNode();
						if (oldNode) {
							oldParents.push(oldNode);
						}
					}
					if (newParents.length>0) {
						zTree.expandNode(oldParents[Math.abs(oldParents.length-newParents.length)-1], false,true);
					} else {
						zTree.expandNode(oldParents[oldParents.length-1], false,true);
					}
				}
			}
		}
        if(zTree.setting.view.isCancleCheck){
        	zTree.checkAllNodes(false);
        }
        zTree.setting.view.curExpandNode = newNode;
	}
	function zTreeOnExpand(event, treeId, treeNode){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.setting.view.curExpandNode = treeNode;
	}
	function zTreeOnClick(event, treeId, treeNode, clickFlag){
		//点击节点时，展开节点
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		if(zTree.setting.view.isSelectedExpand){
			zTree.expandNode(treeNode,null,null,null,true);
		}
		return true;
	}
	function zTreeOnAsyncSuccess(event, treeId, treeNode, msg){
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		if(msg == "")
			return;
		msg = eval("("+msg+")");
		jsons = zTree.transformToArray(msg);
//		_loadIcon(jsons,treeId);
		_initDisable(jsons,treeId);
		_initChecked(jsons,treeId);
		console.log("异步加载成功！");
		//设置是否选中首节点
		var isFirst = zTree.setting.view.isFirstSelected;
		if(isFirst){
			if(jsons.length <= 0)
				return;
			var node = null;
			if(typeof jsons[0] != "object"){
				node = zTree.getNodeByParam("id",eval("("+jsons[0]+")")[0]["id"]);
			}else{
				node = zTree.getNodeByParam("id",jsons[0]["id"]);
			}
			var nodeDisable = node.nodeDisable;
			if(nodeDisable === "true" || zTree.setting.view.allDisable){
				return;
			}
			if(node != null && node.parentTId == null){
				zTree.setting.view.curExpandNode = node;
				var func = $.Event("onNodeClick",{
					node:node
				});
				$("#"+node.tId+"_span").trigger(func);
				zTree.selectNode(node);
			}
		}
	}
	function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){
		alert("树加载失败");
	}
	//个性化文字样式，只针对 zTree在节点上显示的<A>对象
	function fontCss(){
		return {};
	}
	//节点名字超长处理
	function filter(treeId, parentNode, childNodes){
		if (!childNodes) return null;
		if(typeof childNodes != "object")
			childNodes = eval("("+childNodes+")");
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		for (var i=0, l=childNodes.length; i<l; i++) {
			var treeNode = childNodes[i];
			var name = treeNode.name == null?"null":treeNode.name;
			treeNode.title = name;//悬浮字长不变
	        if (name.length > 17) {
	        	name = name.substring(0, 17) + "...";
	        }
	        treeNode.name = name;
	        zTree.updateNode(treeNode);
		}
		_loadIcon(childNodes,treeId);
		return childNodes;
	}
	/**
	 * 初始化节点事件的绑定
	 */
	function _initEventsHandle(jsons,msg){
		if(msg.length < 1 && jsons.length < 1)
			return;
		if(typeof msg !== "object")
			msg = eval("("+msg+")");
		events = eval("("+jsons+")");
		nodes = eval("("+_contas.nodes+")");
		var x = 0;
		for(var eventkey in events){
			var node = nodes[x];
			var namevlu = "";
			for(var key in node)
				namevlu = node[key];
			x++;
			if(namevlu.length == 0){ //给所有节点绑定事件
				switch(eventkey){
				case "onNodeClick":
					$("#"+_contas.id).on("ztree_click",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var nodeDisable = selnode.nodeDisable;
						if(nodeDisable === "true" || zTree.setting.view.allDisable){
							return;
						}
						var func = $.Event("onNodeClick",{
							node:selnode
						});
						$("#"+selnode.tId+"_span").trigger(func);
					});break;
				case "onNodeExpand":
					$("#"+_contas.id).on("ztree_expand",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeExpand",{
							node:selnode
						});
						$("#"+selnode.tId+"_switch").trigger(func);
						$("#"+selnode.tId+"_span").trigger(func);
					});break;
				case "onNodeCollapsed":
					$("#"+_contas.id).on("ztree_collapse",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeCollapsed",{
							node:selnode
						});
						$("#"+selnode.tId+"_switch").trigger(func);
						$("#"+selnode.tId+"_span").trigger(func);
					});break;
				case "onNodeSeclcted":
					$("#"+_contas.id).on("ztree_selected",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeSeclcted",{
							node:selnode
						});
						$("#"+selnode.tId+"_span").trigger(func);
					});break;
				case "onNodeUnselected":
					$("#"+_contas.id).on("ztree_unselected",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeUnselected",{
							node:selnode
						});
						$("#"+selnode.tId+"_span").trigger(func);
					});break;
				case "onNodeChecked":
					$("#"+_contas.id).on("ztree_check",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeChecked",{
							node:selnode
						});
						$("#"+selnode.tId+"_check").trigger(func);
					});break;
				case "onNodeAsyncSuccess":
					$("#"+_contas.id).on("ztree_async_success",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeAsyncSuccess",{
							node:selnode
						});
						$("#"+_contas.id).trigger(func);
					});break;
				case "onNodeAsyncError":
					$("#"+_contas.id).on("ztree_async_error",function(event){
						var id = this.id;
						var zTree =  $.fn.zTree.getZTreeObj(id);
						var selectedNodes = zTree.getSelectedNodes();
						var selnode = selectedNodes[0];
						var func = $.Event("onNodeAsyncError",{
							node:selnode
						});
						$("#"+_contas.id).trigger(func);
					});break;
				default:break;
				}
			}
			for(var i = 0;i < msg.length;i++){//给单个节点绑定事件（还未实现）
				jsonnode = msg[i];
				jsonname = jsonnode.name;
				if(namevlu.length > 0&& namevlu === jsonname){
					var treenode = zTree.getNodeByParam(key,namevlu);
					if((typeof treenode).toLocaleLowerCase()  === "object" && treenode !== null){
						switch(eventkey){
						case "onNodeClick":
							$("#"+_contas.id).on("ztree_click",[treenode,eventkey],function(event){
								var id = this.id;
								var zTree =  $.fn.zTree.getZTreeObj(id);
								var selectedNodes = zTree.getSelectedNodes();
								var selnode = selectedNodes[0];
								var func = $.Event(eventkey,{
									node:selnode
								});
								$("#"+selnode.tId+"_span").trigger(func);
							});break;
						case "onNodeExpand":
							$.fn.zTree.getZTreeObj(treenode.tId+"_a").bind("ztree_expand",function(event,treenode,eventkey){
								var id = this.id;
								var zTree =  $.fn.zTree.getZTreeObj(id);
								var selectedNodes = zTree.getSelectedNodes();
								var selnode = selectedNodes[0];
								var func = $.Event(eventkey,{
									node:selnode
								});
								$("#"+selnode.tId+"_switch").trigger(func);
								$("#"+selnode.tId+"_span").trigger(func);
							});break;
						case "onNodeCollapsed":
							$("#"+_contas.id).on("ztree_collapse",[treenode,eventkey],function(event){
								var id = this.id;
								var zTree =  $.fn.zTree.getZTreeObj(id);
								var selectedNodes = zTree.getSelectedNodes();
								var selnode = selectedNodes[0];
								var func = $.Event(eventkey,{
									node:selnode
								});
								$("#"+selnode.tId+"_switch").trigger(func);
								$("#"+selnode.tId+"_span").trigger(func);
							});break;
						case "onNodeSeclcted":
							$("#"+treenode.tId+"_a").on("mouseup ztree_selected",[treenode,eventkey],function(event){
								var id = this.id;
								var zTree =  $.fn.zTree.getZTreeObj(id);
								var selectedNodes = zTree.getSelectedNodes();
								var selnode = selectedNodes[0];
								var func = $.Event(eventkey,{
									node:selnode
								});
								$("#"+selnode.tId+"_span").trigger(func);
							});break;
						case "onNodeUnselected":
							$("#"+treenode.tId+"_a").on("mouseup ztree_unselected",[treenode,eventkey],function(event){
								var id = this.id;
								var zTree =  $.fn.zTree.getZTreeObj(id);
								var selectedNodes = zTree.getSelectedNodes();
								var selnode = selectedNodes[0];
								var func = $.Event(eventkey,{
									node:selnode
								});
								$("#"+selnode.tId+"_span").trigger(func);
							});break;
						case "onNodeChecked":
							$("#"+treenode.tId+"_a").on("mouseup ztree_check",[treenode,eventkey],function(event){
								var id = this.id;
								var zTree =  $.fn.zTree.getZTreeObj(id);
								var selectedNodes = zTree.getSelectedNodes();
								var selnode = selectedNodes[0];
								var func = $.Event(eventkey,{
									node:selnode
								});
								$("#"+selnode.tId+"_check").trigger(func);
							});break;
						default:break;
						}
					}
				}
			}
		}
	}
	/**
	 * 根据levels属性展开到相应级别的菜单（1：不展开；2：展开一层，以此类推）
	 * @param toLevel 如果不赋值（只要这个参数的typeof为undefined即可，即不传值）给这个参数，则默认根据对此节点的展开状态进行 toggle 切换，
	 */
	function _expandTree(toLevel,contas){
		var level = contas.levels-1;
		var zTree = $.fn.zTree.getZTreeObj(contas.id);
		var nodes = zTree.transformToArray(zTree.getNodes());
		for(var i =0;i<nodes.length;i++){
			var node = nodes[i];
			var leve = node.level+1;
			var isChecked = node.checked;
			if(leve <= level){
				zTree.expandNode(node,true);
			}
//			if(isChecked){
//				var parenttid = node.parentTId;
//				var parentNode = zTree.getNodeByTId(parenttid);
//				zTree.expandNode(parentNode,true);
//			}
			if(typeof toLevel !== "undefined" && toLevel != null){
				zTree.expandNode(node,true);
			}
		}
	}
	//带有下拉框的菜单树显示模式
	function showMenu(){
		var menuObj = $("#ztreeInput"+_contas.id);
		var menuOffset = $("#ztreeInput"+_contas.id).offset();
		$("#ztreeMenu"+_contas.id).css({left:0, top:30}).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	}
	function hideMenu() {
		$("#ztreeMenu"+_contas.id).fadeOut("fast");
		$("body").unbind("mousedown", onBodyDown);
	}
	function onBodyDown(event) {
		if (!(event.target.id == "menuBtn" || event.target.id == "ztreeInput"+_contas.id || event.target.id == "ztreeMenu"+_contas.id || $(event.target).parents("#ztreeMenu"+_contas.id).length>0)) {
			hideMenu();
		}
	} 
	/**
	 * 根据节点名称进行模糊查询
	 */
	function getNodesByParFuzzy(){
		var seaVlu = $(this).val();
		var treeId = $(this).attr("treeId");
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		var allNodes = zTree.transformToArray(zTree.getNodes());
		var nodes = [];
		if(seaVlu.length > 0)
			nodes = zTree.getNodesByParamFuzzy("name",seaVlu);
		for(var i = 0;i < nodes.length;i++){
			var node = nodes[i];
			var parentNodes = zTree.getNodesByParam("tId",node.parentTId);
			var parentnode = parentNodes[0];
			zTree.expandNode(parentnode,true);
			setFontCss(node.tId);
			var index = indexOf(allNodes,node);
			if(index > -1)
				allNodes.splice(index,1);
		}
		for(var j = 0;j < allNodes.length;j++){
			var nod = allNodes[j];
			cancleFontCss(nod.tId);
		}
	}
	//根据传入的数组以及数组内某个元素判断该元素在数组内的index
	function indexOf(vals,val){
		for(var i = 0;i < vals.length;i++){
			if(vals[i] == val)
				return i;
		}
		return -1;
	}
	//设置节点显示的样式
	function setFontCss(treeId){
		$("#"+treeId+"_a").css("color","red");
	}
	//去掉节点显示的样式
	function cancleFontCss(treeId){
		$("#"+treeId+"_a").css("color","");
	}
	/**
	 * 初始化自定义的图标
	 */
	function _loadIcon(nodes,id){
		var zTree = $.fn.zTree.getZTreeObj(id);
		for(var x in nodes){
			var node = nodes[x];
			if(typeof node != "object"){
				node = eval("("+node+")")[0];
			}
			var iconUrl = node["iconUrl"]
			,iconopenUrl = node["openUrl"]
			,iconcloseUrl = node["closeUrl"];
			if(typeof iconUrl !== "undefined"&& typeof iconopenUrl !== "undefined"&& typeof iconcloseUrl !== "undefined"){
					if(!node.isParent){
						node.icon = __contextPath+iconUrl;
					}
					node.iconOpen = __contextPath+iconopenUrl;
					node.iconClose = __contextPath+iconcloseUrl;
					zTree.updateNode(node,false);
			}else if(typeof iconUrl !== "undefined"&& typeof iconopenUrl !== "undefined"&& typeof iconcloseUrl === "undefined"){
					if(!node.isParent){
						node.icon = __contextPath+iconUrl;
					}
					node.iconOpen = __contextPath+iconopenUrl;
					node.iconClose = __contextPath+iconopenUrl;
					zTree.updateNode(node,false);
			}else if(typeof iconUrl !== "undefined"&& typeof iconopenUrl === "undefined"&& typeof iconcloseUrl !== "undefined"){
					if(!node.isParent){
						node.icon = __contextPath+iconUrl;
					}
					node.iconOpen = __contextPath+iconcloseUrl;
					node.iconClose = __contextPath+iconcloseUrl;
					zTree.updateNode(node,false);
			}else if(typeof iconUrl !== "undefined"&& typeof iconopenUrl === "undefined"&& typeof iconcloseUrl === "undefined"){
					node.icon = __contextPath+iconUrl;
					zTree.updateNode(node,false);
			}else if( typeof iconUrl === "undefined"&& typeof iconopenUrl !== "undefined"&& typeof iconcloseUrl !== "undefined"){
					node.iconOpen = __contextPath+iconopenUrl;
					node.iconClose = __contextPath+iconcloseUrl;
					zTree.updateNode(node,false);
			}else if( typeof iconUrl === "undefined"&& typeof iconopenUrl !== "undefined"&& typeof iconcloseUrl === "undefined"){
					node.iconOpen = __contextPath+iconopenUrl;
					node.iconClose = __contextPath+iconopenUrl;
					zTree.updateNode(node,false);
			}else if( typeof iconUrl === "undefined"&& typeof iconopenUrl === "undefined"&& typeof iconcloseUrl !== "undefined"){
					node.iconOpen = __contextPath+iconcloseUrl;
					node.iconClose = __contextPath+iconopenUrl;
					zTree.updateNode(node,false);
			}
		}
	}
	/**
	 * 初始化复选框是否禁用
	 */
	function _initChecked(nodes,id){
		var node = "";
		var zTree = $.fn.zTree.getZTreeObj(id);
		var setting = zTree.setting;
		for(var x in nodes){
			node = nodes[x];
			node = zTree.getNodeByParam("id",node["id"]);
			if(typeof node["chkEnabled"] === "undefined")
				zTree.setChkDisabled(node,!setting.check.enableCheck);
			if(typeof node["chkEnabled"] !== "undefined")
				zTree.setChkDisabled(node,!(node.chkEnabled==="true"));
			zTree.updateNode(node,false);
		}
	}
	/**
	 * 初始化节点是否禁用
	 */
	function _initDisable(allNodes,id){
		var zTree = $.fn.zTree.getZTreeObj(id);
		var setting = zTree.setting;
		for(var x in allNodes){
			var node = allNodes[x];
			if(typeof allNodes[x] != "object"){
				node = zTree.getNodeByParam("id",eval("("+node+")")[0]["id"]);
			}else{
				node = zTree.getNodeByParam("id",node["id"]);
			}
			if(node == null)
				continue;
			var nodeDisable = node.nodeDisable;
			if(nodeDisable === "true" || setting.view.allDisable){
				$("#"+node.tId+"_a").css({"cursor":"not-allowed"});
				zTree.updateNode(node,false);
			}
			if((typeof nodeDisable === "undefined"&&!setting.view.allDisable)||nodeDisable === "false"){
				$("#"+node.tId+"_a").css({"cursor":"pointer"});
				zTree.updateNode(node,false);
			}
		}
	}
	//非异步加载数据
	function _getJsonData(url){
		var vel="";
		$.ajax({
			type:"POST"
			,url:url
			,async:false
			,cache:false
			,dataType:"json"
			,error:function(request){
				alert("树加载失败");
			}
			,success:function(data){
				vel = data;
			}
		});
		return vel;
	}
	/**
	 * 原型定义
	 */
	ZTree.prototype = {
		constructor: ZTree
	}
	/**自定义外部可调用函数*/
	//刷新节点
	$.fn.refreshNode = function(e){
		if(e)
			e.preventDefault();
		var ztreeId = e.data.treeId;
		if(!ztreeId) ztreeId = _contas.id;
		var zTree = $.fn.zTree.getZTreeObj(ztreeId);
		if(zTree == null){
			console.log("ztree树对象初始化失败，请检查传入的树id是否正确!");
			return;
		}
		var type = e.data.type
		,slient = e.data.slient
		,nodes = zTree.getSelectedNodes()
		,isAsync = e.data.isAsync;
		//非异步加载下刷新树
		if(!isAsync){
			zTree.refresh();
			return;
		}
		//异步加载下刷新某个节点
		else{
			if(nodes.length == 0){
				return;
			}
			for(var i = 0; i < nodes.length;i++){
				zTree.reAsyncChildNodes(nodes[i],type,slient);
				if(!slient)zTree.selectNode(nodes[i]);
			}
		}
	}
	/**
	 * @param ztreeId 树的id
	 * @param isAsync 是否是异步重新加载树，true:异步，false:非异步
	 * @param type type="refresh"表示清空后重新加载，type !="refresh"表示追加子节点处理。
	 * @param isSlient 设定异步加载后是否自动展开父节点，isSilent = true 时，不展开父节点，其他值或缺省状态都自动展开。
	 * */
	$.fn.refreshNodes = function(ztreeId,isAsync,type,isSlient){
		if(!ztreeId) ztreeId = _contas.id;
		var zTree = $.fn.zTree.getZTreeObj(ztreeId);
		if(zTree == null){
			console.log("ztree树对象初始化失败，请检查传入的树id是否正确！");
			return;
		}
		if(isAsync == null || typeof isAsync == "undefined")
			isAsync = false;
		if(type == null || typeof type == "undefined")
			type="refresh";
		if(isSlient == null || typeof isSlient == "undefined")
			isSlient = false;
		//非异步加载下刷新树
		if(!isAsync){
			zTree.refresh();
			return;
		}
		//异步加载下刷新某个节点
		else{
			var nodes = zTree.getSelectedNodes();
			if(nodes.length == 0){
				$F.messager.warn("请选择一个节点进行异步刷新！");
				return;
			}
			if(nodes.length == 0){
				return;
			}
			for(var i = 0; i < nodes.length;i++){
				zTree.reAsyncChildNodes(nodes[i],type,isSlient);
				if(!slient)zTree.selectNode(nodes[i]);
			}
		}
	}
	
	$.fn.setFontCss = function(node,fontCss){
		if(!node) return;
		var tId = node.tId;
		$("#"+tId+"_a").css(fontCss);
		return;
	}
	/**
	 * jquery方法扩展ztree
	 */
	$.fn.ztree = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.ztree,option):$options.ztree;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('ztree');
			if (!data) 
				$this.data('ztree', (data = new ZTree(this, options)));
			if (typeof option === 'string') 
				methodReturn = data[option](value);
		});
		return methodReturn;
	};
	$.fn.ztree.Constructor = ZTree;
 	return $;
});