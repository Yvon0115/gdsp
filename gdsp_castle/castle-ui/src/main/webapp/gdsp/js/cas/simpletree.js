/**
 * @class
 * @name jQuery.fn.simpletree
 * @description <jquery methods class> 搜索扩展
 */
define(["cas/core","cas/options","bootstrap/bootstrap"],function ($F,$options){
	"use strict"; // jshint ;_;
	/**
	 * 构造方法
	 */
	var SimpleTree = function (element, options) {
		var o = this
		,$el = o.$element = $(element)
		,hasIcon = $el.hasClass("tree-icon")
		,hasCheck = $el.hasClass("tree-checked")
		,exLevel = $el.attr("expandLevel")
		,defaultSelect = $el.attr("defaultSelect")==="true"
		,checkOption = $el.attr("checkOption")||0
		,isCheckExpand = $el.attr("isCheckExpand");
		exLevel = exLevel?parseInt(exLevel,10):-1;
		
		/*三个选项0:默认规则,父节点包含子节点规则;1:父节点快捷选择规则,每个节点自己维护自己的规则,父节点默认快捷选中子节点;2:自己维护自己的规则 */
		//增加只有末级节点带复选框的设置;3:checkOption
		checkOption = checkOption*1;
		o.options = options = $.extend({onlyIconExpand:false},options,{
				hasIcon:hasIcon,
				hasCheck:hasCheck,
				exLevel:exLevel,
				checkOption:checkOption,
				isCheckExpand:isCheckExpand
			});
		
		var $levels = $(">div.tree-level", $el);
		$levels.each(function(){
			var $l = $(this)
			,id = $l.attr("id")
			,type = $l.attr("type")
			,url = $l.attr("url")
			,mfield =  $l.attr("masterField")
			,mvalue = mfield!=null?$l.attr(mfield):null
			,detail =  $l.attr("detailParameter")
			,data = {level:id};
			data[detail] = mvalue;
			$el.appendAJAX({async:false,url:url,data:data});
		})
		var $nds = $(">li", $el),ncount = $nds.length;
		var ncount = $nds.size();
		$nds.each(function(i){
			var $n = $(this)
			,first = i==0
			,last = i+1==ncount;
			buildNode($n,{
				hasIcon:options.hasIcon,
				hasCheck:options.hasCheck,
				options: options,
				folderIcon:options.folderIcon,
				expandIcon:options.expandIcon,
				leafIcon:options.leafIcon,
				level: 0,
				exp:(ncount>1?(first?options.firstExp:(last?options.lastExp:options.exp)):options.oneExp),
				coll:(ncount>1?(first?options.firstColl:(last?options.lastColl:options.coll)):options.oneColl),
				expand:exLevel >= 0,
				isLast:last
			},$el);
		});
		_initEventHandle($el,options);
		if(defaultSelect)
			$el.find(">li>div>a:first").trigger("click");
		$el.css("display","table");
	};
	/*私有方法定义*/
	/**
	 * 复选框事件初始化方法
	 * @param $el 树对象
	 * @param options 选项
	 */
	function _initCheckFnHandle($el,options){
		if(!options.hasCheck)
			return;
		var checkFn = eval($el.attr("oncheck"));
		if(!checkFn || !$.isFunction(checkFn))
			return;
		$el.on('click',"div.ckbox",function(){
			var $ck = $(this);
			var checked = $ck.hasClass("checked");
			var items = [];
			if(!checked)
				return;
			var $n = $ck.parent().parent();
			var $boxes = $("input", $n);
			if($boxes.size() > 1) {
				$boxes.each(function(){
					var $b = $(this);
					items[items.length] = {name:$b.attr("name"), value:$b.val(), text:$b.attr("text")};
				});
			} else {
				items = {name:$boxes.attr("name"), value:$boxes.val(), text:$boxes.attr("text")};
			}								
			checkFn({checked:checked, items:items});
		});
	}
	/**
	 * 初始化事件处理
	 * @param $el 树对应节点对象
	 * @param options 选项
	 */
	function _initEventHandle($el,options){
		_initCheckFnHandle($el,options);
		$el.on("click","a",function(e){
			e.preventDefault();
			var $a = $(this);
			$("div." + options.selected, $el).removeClass(options.selected);
			var $nitem = $a.parent();
			//增加不可选中状态并且不增加背景色
			if(!$nitem.children(".ckbox").hasClass("disabled")){
				$nitem = $a.parent().addClass(options.selected);
			}
			
			//$(".ckbox",$nitem).trigger("click");
			var clickNode = $.Event('clickNode', {
				node: $nitem.parent(),
				link:$a
			})
			$a.parents("ul.simpletree").trigger(clickNode);
		});
		$el.on("dblclick","a",function(e){
			e.preventDefault();
			var $a = $(this);
			$("div." + options.selected, $el).removeClass(options.selected);
			var $nitem = $a.parent();
			//增加不可选中状态并且不增加背景色
			if(!$nitem.children(".ckbox").hasClass("disabled")){
				$nitem = $a.parent().addClass(options.selected);
			}
			//$(".ckbox",$nitem).trigger("click");
			var clickNode = $.Event('clickNode', {
				node: $nitem,
				link:$a
			})
			$a.parents("ul.simpletree").trigger(clickNode);
		});

	}
	
	/**
	 * 添加节点前边的链接线
	 * @param level 节点层级
	 * @param $n 当前节点
	 */
	function _addSpace(level,$n){
		if (level <= 0)
			return;
		var $pn = $n.parent().parent();
		var space = !$pn.next()[0]?"indent":"line";
		var nline = "<div class='" + space + "'></div>";
		if (level > 1) {
			var next = $(">div>div:first",$pn);
			var prev = "";
			while(level > 1){
				prev += "<div class='" + next.attr("class") + "'></div>";
				next = next.next();
				level--;
			}
			nline = prev + nline;
		}
		$(">div", $n).prepend(nline);
	}
	/**
	 * 设置指定节点的复选框状态
	 * @param $n 节点
	 */
	function _check($n,op,$el) {
		var $ck = $(">div>.ckbox", $n);
		$ck.on('click.simpletree',function(){
			var checked = $ck.hasClass("checked")
				,addCss = checked?"unchecked":"checked"
				,removeCss = checked?"checked":"unchecked";
			
			$ck.removeClass(removeCss).removeClass("indeterminate").addClass(addCss);
			$n.attr("ckbox", !checked);
			if(op.options.checkOption !== 2) {
				$("ul>li", $n).each(function () {
					var box = $("div.ckbox", this);
					var c = box.hasClass(addCss);
					box.removeClass(removeCss).removeClass("indeterminate").addClass(addCss);
					$(this).attr("ckbox", !checked);
					if(c)
						$el.trigger("nodeCheckChange",$ck);
				});
			}
			$el.trigger("nodeCheckChange",$ck);
			if(op.options.checkOption === 0)
				_checkParent($n,$el,true);
			return false;
		});
		if(op.options.checkOption === 0){
			var subCheckedLen = $("li[ckbox='true']",$n).size();
			if ($n.attr("ckbox") == "true" || subCheckedLen>0) {
				var cls = subCheckedLen == $("li",$n).size()?"checked":"indeterminate";
				$ck.removeClass("unchecked").addClass(cls);
				_checkParent($n,$el);
			}
		}else{
			if ($n.attr("ckbox") == "true") {
				$ck.removeClass("unchecked").addClass("checked");
			}
		}
	};
	/**
	 * 设置上级节点的复选框状态
	 */
	function _checkParent($n,$el,isEvent){
		if($n.parent().hasClass("simpletree"))
			return;
		var $p = $n.parent().parent();
		var $ck = $(">div>.ckbox",$p);
		if($ck.length > 0) {
			var $st = $(">ul", $p);
			var boxcount = $st.find(">li>a").size() + $st.find("div.ckbox").size();
			var checkcount = $st.find("div.checked").size();
			var imcount = $st.find("div.indeterminate").size();
			var addCss = (boxcount == checkcount ? "checked" : (imcount+checkcount != 0 ? "indeterminate" : "unchecked"));
			if (addCss == "checked" || addCss == "indeterminate")
				$p.attr("ckbox", "true");
			else
				$p.removeAttr("ckbox");

			if ($ck.hasClass(addCss))
				return;
			$ck.removeClass("unchecked").removeClass("checked").removeClass("indeterminate").addClass(addCss);
			if (isEvent)
				$el.trigger("nodeCheckChange", $ck);
		}
		_checkParent($p,$el,isEvent);
	};
	/**
	 * 展开节点对象
	 * @param $et 事件源对象,文字链接，或十字叉
	 * @param $n 节点对象
	 * @param op 节点级选项
	 * @param expand 是否展开
	 */
	function toggleNode($et,$n,op,expand){
		var $ul = $(">ul",$n)
		,level = parseInt($n.attr('nlevel'),10)
		,hasChild = $n.attr("hasChild") === "true";
		if($ul.length == 0 && !hasChild)
			return;
		/*第一个子节点*/
		var $fn = $(">li:first",$ul);
		if($ul.length == 0 || $ul.attr("created")!== "true"){
			buildSubTree($n,op.options);
		}
		if($ul.is(":hidden")&&expand===false||!$ul.is(":hidden")&&expand===true)
			return;
		var $et = $(">div>div", $n).eq(level);
		$et.toggleClass(op.exp).toggleClass(op.coll);
		if (op.hasIcon) {
			var icon = $n.attr("icon")||op.folderIcon
			,expIcon = $n.attr("expandIcon")||op.expandIcon;
			$(">div>i:last", $n).toggleClass(expIcon).toggleClass(icon);
		}
		$ul.is(":hidden")?$ul.slideDown("fast"):$ul.slideUp("fast");
	};
	/**
	 * 节点li属性{
	 *  //配置属性
	 * 	hasCheck:是否显示复选框
	 * 	hasIcon:是否显示图标
	 * 	icon:收缩是图标样式
	 * 	expandIcon:展开是图标样式
	 * 	expand:当包含子节点时是否展开
	 *  //中间属性
	 * 	nlevel:所处层级
	 * }
	 *
	 * 选项属性{
	 * 	hasCheck:是否显示复选框
	 * 	hasIcon:是否显示图标
	 * 	isLast:是否为当前层次的末级节点
	 * 	folderIcon:文件夹图标
	 * 	expandIcon:扩展时文件夹图标
	 * 	leafIcon:叶子节点图标
	 * 	expand:当包含子节点时是否展开
	 * 	coll:链接线图标，可收缩图标，一般为-
	 * 	exp：链接线图标，可收缩图标，一般为+
	 * 	level：节点层级数
	 * }
	 * @param $n
	 * @param op
	 * @returns
	 */
	function buildNode($n,op,$el){
		//判断下级树是否有选中有就设置为展开
		var $item = $("li[ckbox='true']");
		var sel = $n.find($item);
		//增加属性isCheckExpand，是否展开到已选中的节点
		if(sel.first().children().first().attr("value")!=undefined&&op.options.isCheckExpand=="true"){
			op.expand=true;
			op.exLevel=8;//设置展开8级，如果有更深层次此处需要修改
		}else if(op.options.isCheckExpand=="true"){
			op.expand=false;
			op.exLevel=-1;
		}
		var $subtree = $(">ul", $n)
			,hasChild = $n.attr("hasChild")==="true"
			,$pnode = $n.parent().prev()
			,hasCheck = $n.attr('hasCheck')==="true"||op.hasCheck
			,hasIcon = $n.attr('hasIcon')==="true"||op.hasIcon
			,checked = hasCheck && $(">.checked",$n).size() > 0?'checked':'unchecked'
			,isLast = op.isLast
			,level = op.level
			,isDisabled=$n.attr('dis')=="N";
		$n.attr("nlevel",level);
		if($subtree.children().size()>0 || hasChild) {
			var icon = $n.attr("icon")||op.folderIcon
				,expIcon = $n.attr("expandIcon")||op.expandIcon
				,expand =op.expand||(!$n.hasClass("collapse") && $n.hasClass("expand"))
				,coll = op.coll
				,exp = op.exp;
				
			$n.children(":first").wrap("<div></div>");
			//增加只有末级节点带复选框的设置
			if($subtree.children().size()>0 && op.options.checkOption===3){
				var events = $n.attr('onclick');
				if(events!=undefined)
					$n.removeAttr("onclick");
				$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
			}else if(isDisabled==true){//如果设置了不可选中dis='N',则增加class	disabled
				$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+"<div class='ckbox disabled'></div>"+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
			}else{
				var events = $n.attr('onclick');
				if(events!=undefined){
					if(op.options.checkOption===2&&op.level!=0){
						$n.removeAttr("onclick");
						var tmp = events.replace("changeColumnByDim","changeDimVal");
						$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+(hasCheck ?"<div class='ckbox " + checked + "'onclick='"+tmp+"'></div>":"")+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
					}else{
						//将li上的onclick事件转移到复选框上
						$n.removeAttr("onclick");
						$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+(hasCheck ?"<div class='ckbox " + checked + "'onclick='"+events+"'></div>":"")+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
					}
				}else{
					$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+(hasCheck ?"<div class='ckbox " + checked + "'></div>":"")+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
				}
				
			}
			expand ? $subtree.show() : $subtree.hide();
			var expandSelector = ">div>div:first"+(op.onlyIconExpand?"":",>div>a");
			$(expandSelector, $n).on('click.simpletree',function(){
				var $t = $(this);
				toggleNode($t,$n,op);
			});
			_addSpace(level, $n);
			if(expand)
				buildSubTree($n,op.options);
		} else {
			$subtree.remove();
			var icon = $n.attr("icon")||op.leafIcon;
			$n.children().wrap("<div class='node'></div>");
			//增加只有末级节点带复选框的设置
			if($subtree.children().size()>0 && op.options.checkOption===3){
				var events = $n.attr('onclick');
				if(events!=undefined)
					$n.removeAttr("onclick");
				$(">div", $n).prepend("<div class='nline'></div>"+(hasIcon?"<i class='"+icon+"'></i>":""));
			}else if(isDisabled==true){
				$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+"<div class='ckbox disabled'></div>"+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
			}else{
				var events = $n.attr('onclick');
				if(events!=undefined){
					if(op.options.checkOption===2&&op.level!=0){
						$n.removeAttr("onclick");
						var tmp = events.replace("changeColumnByDim","changeDimVal");
						$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+(hasCheck ?"<div class='ckbox " + checked + "'onclick='"+tmp+"'></div>":"")+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
					}else{
						//将li上的onclick事件转移到复选框上
						$n.removeAttr("onclick");
						$(">div", $n).prepend("<div class='" + (expand ? coll : exp) + "'></div>"+(hasCheck ?"<div class='ckbox " + checked + "'onclick='"+events+"'></div>":"")+(hasIcon?"<i class='"+ (expand ? icon : expIcon) +"'></i>":""));
					}
				}else{
					$(">div", $n).prepend("<div class='nline'></div>"+(hasCheck?"<div class='ckbox "+checked+"'></div>":"")+(hasIcon?"<i class='"+icon+"'></i>":""));
				}
			}
			_addSpace(level, $n);
			if(isLast)$n.addClass("last");
		}
		var tnode = $(">div>a",$n);
		if(!tnode.attr("title")){
			//modify By wanglijiang
			var nodeText = tnode.text();
			tnode.attr("title",nodeText);
//			tnode.css("height","18.5px");
			if(tnode.css("max-width")=="")
				tnode.css("max-width","180px");
			tnode.css("overflow","hidden");
			tnode.css("vertical-align","bottom");
			tnode.css("text-overflow","ellipsis");
//			if(nodeText && nodeText.length > 10){
//				tnode.text(nodeText.substring(0,20)+" ...")
//			}
		}
		tnode.tooltip({container: document.body});
		if (hasCheck) _check($n,op,$el);
//			if($.browser.msie){
//				$(">div",$n).dblclick(function(){
//					$("a", this).trigger("dblclick");
//					return false;
//				});
//				$(">div",$n).click(function(){
//					$("a", this).trigger("click");
//					return false;
//				});
//			}
		//});
	};
	/**
	 * 创建子树
	 * @param $n 需要创建字数的上级节点
	 * @param options 选项
	 */
	function buildSubTree($n,options) {
		var $el = $n.parents("ul.simpletree")
		,exLevel = options.exLevel
		,$ul = $n.find(">ul:first");
		if(!$n.attr("nlevel") || $ul.attr("created")==="true")
			return;
		
		if($ul.length == 0){
			$n.append("<ul></ul>");
			$ul = $n.find(">ul:first");
		}
		$ul.attr("created","true");
		var $clevel = $(">div.tree-level#"+$n.attr("tlevel"), $el);
		if($clevel != null){
			if($clevel.attr("type") == "recuive"){
				var id = $clevel.attr("id")
					,type = $clevel.attr("type")
					,url = $clevel.attr("url")
					,recuiveField =  $clevel.attr("recuiveField")
					,recuiveValue = $n.attr(recuiveField)
					,recuive =  $clevel.attr("recuiveParameter")
					,data={level:id};
				data[recuive]=recuiveValue;
				$ul.appendAJAX({async:false,url:url,data:data});

			}
			$(">div.tree-level", $clevel).each(function(){
				var $level = $(this)
					,id = $level.attr("name")
					,type = $level.attr("type")
					,url = $level.attr("url")
					,masterField =  $level.attr("masterField")
					,masterValue = $n.attr(masterField)
					,detail =  $level.attr("detailParameter")
					,data = {levelName:name};
					data[detail] = masterValue;
				$ul.appendAJAX({async:false,url:url,data:data});
			});
		}
		var level = parseInt($n.attr("nlevel"),10)
			,expand = exLevel >= level
			,$nds = $(">li", $ul)
			,ncount = $nds.length
			,subLevel = level+1;
			
		$nds.each(function(i){
			var $newn=$(this)
				,first=i==0
				,last=i+1==ncount;
			buildNode($newn,{
				hasIcon:options.hasIcon,
				hasCheck:options.hasCheck,
				options: options,
				folderIcon:options.folderIcon,
				expandIcon:options.expandIcon,
				leafIcon:options.leafIcon,
				level: subLevel,
				exp:last?options.lastExp:options.exp,
				coll:last?options.lastColl:options.coll,
				expand:exLevel >= subLevel,
				isLast:last
			},$el);
		});
	};
	
	/**
	 * 原型定义
	 */
	SimpleTree.prototype = {
		constructor: SimpleTree,
		/**
		 * 取得选中的节点
		 */
		getSelectedNode:function(){
			var o = this
			,op = o.options;
			return $("div."+op.selected,o.$el).find("a:first");
		},
		/**
		 * 查找指定的节点
		 */
		lookupNode:function(content){
			this.positionTreeNode("a:contains(" + content + ")");
		},
		/**
		 * 根据选择器定位节点
		 */
		positionNode:function(selector){
			var o = this,$el = o.$el,options=o.options
			,$nodes = $(selector,$el);
			if($nodes.size() == 0)
				return;
			//取得目标节点并展开其父节点
			var $target = $($nodes[0])
			,$ans = $target.parents("ul.simpletree li").find("a:first");
			var count = 0;
			for(var i = $ans.size()-1; i >= 0; i--){
				var $an = $($ans[i]);
				var $ln = $an.parents("li:first");
				var isLast = $ln.next()[0]?false:true;
				var exp,coll;
				if(count == 0){
					var ncount = $ln.parent().children().size();
					var first = $ln.prev()[0]?false:true;
					var last = $ln.next()[0]?false:true;
					exp = ncount>1?(first?options.firstExp:(last?options.lastExp:options.exp)):options.oneExp;
					coll = ncount>1?(first?options.firstColl:(last?options.lastColl:options.coll)):options.oneColl;
				}else{
					exp = isLast?options.lastExp:options.exp;
					coll = isLast?options.lastColl:options.coll;
				}
				var op = {
					hasIcon:options.hasIcon,
					hasCheck:options.hasCheck,
					exp:exp,
					coll:coll,
					options:options,
					level:count,
					space:isLast?null:options.space,
					expand:true,
					isLast:isLast
				}
				count++;
				toggleNode($an,$ln,op);
			}
			$target.trigger("click");
		},
		/**
		 * 添加子节点
		 */
		addNode:function(content){
			if(content.substring(0,4) != "<li ")
				content="<li>" + content +"</li>";
			var o = this,$el = o.$el,options=o.options
			,$an = o.getSelectedNode()
			,$pnul
			,$container
			,level = 0
			,isLast = true;
			if($an.size() == 0){
				$pnul = $el;
				$pnul.append(content);
				var $newn = $pnul.children("li:last");
				$container=$newn;
			}else{
				var $pn = $an.parent().parent();
				level = parseInt($pn.attr("nlevel"),10)
				$pnul = $pn.children("ul");
				isLast = ($pn.next()[0]?false:true);
				if($pnul.size() == 0){
					$pn.removeClass("last");
					$pn.removeAttr("nlevel");
					$pn.append($an.outerHtml()+"<ul>"+content+"</ul>");
					$container = $pn;
				}else{
					toggleNode($an,$pn,{
						hasIcon:options.hasIcon,
						hasCheck:options.hasCheck,
						options: options,
						folderIcon:options.folderIcon,
						expandIcon:options.expandIcon,
						leafIcon:options.leafIcon,
						level: level,
						exp:isLast?options.lastExp:options.exp,
						coll:isLast?options.lastColl:options.coll,
						expand:true,
						isLast:isLast
					},true);
					var $lastn = $pnul.children("li:last");
					$lastn.removeClass("last");
					var $line = $(">div>div",$lastn).eq(level+1);
					if($line.hasClass(options.lastExp)){
						$line.removeClass(options.lastExp).addClass(options.exp);
					}else if($line.hasClass(options.lastColl)){
						$line.removeClass(options.lastColl).$line(options.coll);
					}
					$("li",$lastn).each(function(){
						$(">div>div",$(this)).eq(level+1).removeClass("indent").addClass("line");
					});
					$pnul.append(content);
					$container = $pnul.children("li:last");
					isLast = true;
					level++;
				}
			}
			buildNode($container,{
				hasIcon:options.hasIcon,
				hasCheck:options.hasCheck,
				options: options,
				folderIcon:options.folderIcon,
				expandIcon:options.expandIcon,
				leafIcon:options.leafIcon,
				level: level,
				exp:options.lastExp,
				coll:options.lastColl,
				expand:true,
				isLast:isLast
			},this.$element);
		}
	}
	/**
	 * jquery方法扩展simpletree
	 */
	$.fn.simpletree = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.simpletree,option):$options.simpletree;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('simpletree');
			if (!data) $this.data('simpletree', (data = new SimpleTree(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};
	
	$.fn.customtree = function (tree,option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.simpletree,option):$options.simpletree;
		var $set = tree.each(function () {
			var $this = tree;
			var data = $this.data('simpletree');
			if (!data) $this.data('simpletree', (data = new SimpleTree(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});
		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.simpletree.Constructor = SimpleTree;
	$.fn.customtree.Constructor = SimpleTree;
 	return $;
});