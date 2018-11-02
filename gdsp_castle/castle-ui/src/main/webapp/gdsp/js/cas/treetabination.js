/**
 * 树表组件
 * @author yuchenglong (于成龙)
 * @version v2.2
 * @lastUpdateDate 2017-09-06
 * --------------------------
 * @Previousversion 1.4.2
 * @author benzhan (詹潮江)
 * @mail zhanchaojiang@qq.com
 */
define(["cas/options","cas/dataloader"],function($options,$dataloader){
	"use strict";
	
	/**
	 * 构造方法
	 */
	var TreeTable = function(element,options){
		
		var o = this
		,$el = o.$element = $(element)
		,target = $el.attr("treetable-target")
		,url = $el.attr("url")
		,keys = $el.attr("keys").split(",")
		,ellipsis = $el.attr("ellipsis")!=""?$.parseJSON($el.attr("ellipsis")):{}
		,column = $el.attr("column")
		,controlCol = $el.attr("controlCol")
		,checkboxName = $el.attr("checkboxName")
		,checkboxfield = $el.attr("checkboxfield")
		,checkbox = typeof($el.attr("checkbox"))=="undefined"?false:true
		,rootName = $el.attr("rootName")
		,isAsync = typeof($el.attr("isAsync"))=="undefined"?false:true
		,options = $options.treetable;
		
		/* 可拓展options支持自定义事件onSelect和beforExpand
		 * theme：string {主题，有两个选项：default、vsStyle. 默认:default}
		 * expandLevel：int {树表的展开层次. 默认:1}
		 * column：int {可控制列的序号. 默认:0，即第一列}
		 * onSelect：function  {拥有controller自定义属性的元素的点击事件，return false则中止展开}
		 * beforeExpand：function {展开子节点前触发的事件} */
		o.options = $.extend({
            theme: 'default',
            expandLevel: 1,
            column: column,
            onSelect: function(o, id){
            	window.console && console.log('onSelect:' + id);
            },
            beforeExpand: function(o, id){
            	//判断id是否已经有了孩子节点，如果有了就不再加载，这样就可以起到缓存的作用
	            if ($('.' + id, $el).length) { return; }
	            
	            var $rows = o.getAppendData(id);
	            var len = $rows.length;
            	if(len>0){
            		var outerHtml = "";
            		$.each($rows,function(i){
            			var item = this,$item = $(this);
            			var tdhtml="";
            			var checkboxhtml = '<td>'+
            									'<input type="checkbox" name="'+checkboxName+'" value="'+item.id+'" >'+
            							   '</td>';
            			var curid = item.id?item.id:"";
            			for(var x in keys){
            				var key = keys[x];
            				if(x==controlCol){
            					if(ellipsis[key]){
	            					tdhtml += '<td title="'+item[key]+'">'+
	            									'<div class="ellipsis" style="max-width:'+ellipsis[key]+';">'+
	            										'<span controller="true"></span>'+
	            										item[key]+
	            									'</div>'+
  						 			 		  '</td>'
	            				}else{
	            					tdhtml += '<td title="'+item[key]+'">'+
	            									'<span controller="true"></span>'+
	            									item[key]+
						 			 		  '</td>'
	            				}
            				}else{
	            				if(ellipsis[key]){
	            					tdhtml += '<td title="'+item[key]+'">'+
	            									'<div class="ellipsis" style="max-width:'+ellipsis[key]+';">'+
	            										item[key]+
	            									'</div>'+
  						 			 		  '</td>'
	            				}else{
	            					tdhtml += '<td title="'+item[key]+'">'+
	            									item[key]+
						 			 		  '</td>'
	            				}
            				}
            			}
            			var trshtml = '<tr id="'+curid+'" pId="'+id+'">'+
            								checkboxhtml+
            								tdhtml+
            						  '</tr>';
            			outerHtml+=trshtml;
            		});
            		o.addChilds(outerHtml);
            	};
            }
        }, options);

        o.css = {
            'N' : o.options.theme + '_node',
            'AN' : o.options.theme + '_active_node',
            'O' : o.options.theme + '_open',
            'LO' : o.options.theme + '_last_open',
            'S' : o.options.theme + '_shut',
            'LS' : o.options.theme + '_last_shut',
            'HO' : o.options.theme + '_hover_open',
            'HS' : o.options.theme + '_hover_shut',
            'HLO' : o.options.theme + '_hover_last_open',
            'HLS' : o.options.theme + '_hover_last_shut',
            'L' : o.options.theme + '_leaf',
            'LL' : o.options.theme + '_last_leaf',
            'B' : o.options.theme + '_blank',
            'V' : o.options.theme + '_vertline'
        };
        o.isAsync = isAsync;
        o.ellipsis = ellipsis;
        o.rootName = rootName;
        o.$dataloader = $(target);
        o.pMap = {}, o.cMap = {};
        o.init();
	}
	
	/**
	 * 原型定义
	 */
	TreeTable.prototype = {
		constructor: TreeTable,
		/**
		 * 初始化组件
		 */
		init:function(){
			var o = this
			,$el = this.$element
			var $trs = $el.find('tbody>tr');
	        o.initRelation($trs,true);    
	        o.eventHandle();
		},
		/**
		 * 初始化节点关系
		 */
		initRelation:function ($trs,hideLevel) {
			var o = this
			,$el = this.$element
			,css = o.css
			,options = o.options;
            //构造父子关系
            $trs.each(function (i) {
            	if (!this.id) { return; }
                var pId = $(this).attr('pId') || 0;
                o.pMap[pId] || (o.pMap[pId] = []);
                o.pMap[pId].push(this.id);
                o.cMap[this.id] = pId;
                
                //给这个tr增加类为了提高选择器的效率
                $(this).addClass(pId);
            }).find('[controller]').css('cursor', 'pointer');

            //标识父节点是否有孩子、是否最后一个节点
            $trs.each(function (i) {
            	if (!this.id) { return; }
                var $tr = $(this);
                
                //如果是异步加载并且是根节点则在初始加载时添加hasChild属性
                o.isAsync?$tr.attr("hasChild",true):o.pMap[this.id] && $tr.attr('hasChild', true);
                var pArr = o.pMap[o.cMap[this.id]];
                if (pArr[0] == this.id) {
                    $tr.attr('isFirstOne', true);
                } else {
                    var prevId = 0;
                    for (var i = 0; i < pArr.length; i++) {
                        if (pArr[i] == this.id) { break; }
                        prevId = pArr[i];
                    }
                    $tr.attr('prevId', prevId);
                }

                pArr[pArr.length - 1] == this.id && $tr.attr('isLastOne', true);

                var depth = o.getDepth(this.id);
                $tr.attr('depth', depth);

                //格式化节点
				o.formatNode(this);

                //判断是否要隐藏限制的层次
                if (hideLevel) {
                    depth > options.expandLevel && $tr.hide();
                    //判断是否小于深度，如果小于深度则要换成展开的图标
                    if ($tr.attr('hasChild') && $tr.attr('depth') < options.expandLevel) {
                        var className = $tr.attr('isLastOne') ? css['LO'] : css['O'];
                        $tr.find('.' + css['AN']).attr('class', css['AN'] + ' ' + className);
                    }
                }               
            });
		},
		/**
		 * 递归获取深度
		 */
		getDepth:function(id){
			var o = this
            if (o.cMap[id] == o.rootName || o.cMap[id] == 0) { return 1; } 
            var $parentDepth = o.getDepth(o.cMap[id]);
            return $parentDepth + 1; 
		},
		/**
		 * 格式化节点
		 */
		formatNode:function(tr){
			var o =this,
			$el = o.$element
			,options = o.options
			,css = o.css;
            var $cur = $(tr);
            var id = $cur.attr("id");

            //-------------下面一大段都是获取$preSpan---------
            if (o.cMap[id] == 0 || o.cMap[id] == o.rootName) {
                //如果是顶级节点，则没有prev_span
                var $preSpan = $('<span class="prev_span"></span>');
            } else {
                //先判断是否有上一个兄弟节点
                if (!$cur.attr('isFirstOne') === "true") {
                	if($(o.ellipsis).length > 0){
                		var $preSpan = $('#' + $cur.attr('prevId')).children("td").eq(options.column).children("div").find('.prev_span').clone();
                	}else{
                		var $preSpan = $('#' + $cur.attr('prevId')).children("td").eq(options.column).find('.prev_span').clone();
                	}
                } else {
                    var $parent = $('#' + o.cMap[id]);
                    //没有上一个兄弟节点，则使用父节点的prev_span
                    if($(o.ellipsis).length > 0){
                    	var $preSpan = $parent.children("td").eq(options.column).children("div").find('.prev_span').clone();
                    }else{
                    	var $preSpan = $parent.children("td").eq(options.column).find('.prev_span').clone();
                    }

                    //如果父亲后面没有兄弟，则直接加空白，若有则加竖线
                    if ($parent.attr('isLastOne')) {
                        $preSpan.append('<span class="' + css['N'] + ' ' + css['B'] + '"></span>');
                    } else {
                        $preSpan.append('<span class="' + css['N'] + ' ' + css['V'] + '"></span>');
                    }
                }
            }
            //------------------------------------------------
            
            if ($cur.attr('hasChild') === "true") {
                //如果有下一个节点，并且下一个节点的父亲与当前节点的父亲相同，则说明该节点不是最后一个节点
                var className = $cur.attr('isLastOne') ? css['LS'] : css['S'];
                className = css['AN'] + ' ' + className;
            } else {
                var className = css['N'] + ' ' + ($cur.attr('isLastOne') ? css['LL'] : css['L']);
            }
            
            var $td = $(o.ellipsis).length > 0?$cur.children("td").eq(options.column).children("div"):$cur.children("td").eq(options.column);
            $td.prepend('<span arrow="true" class="' + className + '"></span>').prepend($preSpan);
        
		},
		/**
		 * 初始化事件处理
		 */
		eventHandle:function(){
			var o = this
			,$el = o.$element
			,css = o.css
			,opts = o.options;
			$el.on("click",function (event) {
	            var $target = $(event.target);

	            if ($target.next("span").attr('controller') === "true") {
	            	$target = $target.parents('tr[haschild]').find('[arrow]');
	                //判断是否是叶子节点
					if ($target.attr('class').indexOf(css['AN']) == -1 && $target.attr('class').indexOf(css['N']) == -1) { return; }
	                var id = $target.parents('tr[haschild]')[0].id;
	                if (opts.onSelect && opts.onSelect(o, id) === false) { return; }
	            }

	            if ($target.attr('arrow') === "true") {
	                var className = $target.attr('class');
	                if (className == css['AN'] + ' ' + css['HLO'] || className == css['AN'] + ' ' + css['HO']) {
	                    var id = $target.parents('tr[haschild]')[0].id;
	                    $target.attr('class', css['AN'] + " " + (className.indexOf(css['HO']) != -1 ?  css['HS'] : css['HLS']));

	                    //关闭所有孩子的tr
	                    o.shut(id);
						return;
	                } else if (className == css['AN'] + ' ' + css['HLS'] || className == css['AN'] + ' ' + css['HS']) {
	                    var id = $target.parents('tr')[0].id;
	                    $target.attr('class', css['AN'] + " " + (className.indexOf(css['HS']) != -1 ? css['HO'] : css['HLO']));
	                    
	                    opts.beforeExpand(o, id);
	                    //展开所有直属节点，根据图标展开子孙节点
	                    o.open(id);
						return;
	                }
	            }
	        });
			$el.mouseover(o.hoverActiveNode).mouseout(o.hoverActiveNode);
		},
		/**
		 * 递归关闭节点
		 */
		shut:function(id){
			var $treeTable = this
			,pmap = $treeTable.pMap;
			if (!pmap[id]) { return false; }
            for (var i = 0; i < pmap[id].length; i++) {
            	$treeTable.shut(pmap[id][i]);
            }
            $('tr.' + id).hide();
		},
		/**
		 * 递归展开节点
		 */
		open:function(id){
			var $treeTable = this
			,css=$treeTable.css
			,pmap = $treeTable.pMap;
			$('tr.' + id).show();
            if (!pmap[id]) { return false; }
            for (var i = 0; i < pmap[id].length; i++) {
                var cId = pmap[id][i];
                if (pmap[cId]) {
                    var className = $('#' + cId).find('.' + css['AN']).attr('class');
                    //如果子节点是展开图表的，则需要展开此节点
                    (className == css['AN'] + ' ' + css['O'] || className == css['AN'] + ' ' + css['LO']) && $treeTable.open(cId);
                }
            }
		},
		/**
		 * 鼠标移入移出样式调整
		 */
		hoverActiveNode:function(event){
			var $el=$(this),o=$el.data('treetable')
			,opts = o.options
			,css = o.css;
            var $target = $(event.target);

            if ($target.attr('controller') === "true") {
                $target = $target.parents('tr[haschild]').find('[arrow]');
            }

            if ($target.attr('arrow')) { 
                var className = $target.attr('class');
                if (className && !className.indexOf(css['AN'])) {
                    var len = opts.theme.length + 1;
                    className = className.split(' ')[1].substr(len);
                    if (className.indexOf('hover_') === 0) {
                        className = opts.theme + '_' + className.substr(6); 
                    } else {
                        className = opts.theme + '_hover_' + className;
                    }
                    
                    $target.attr('class', css['AN'] + ' ' + className);
                    return;
                }
            } 
		},
		/**
		 * 获取远程数据--用于动态加载节点数据
		 */
		getAppendData:function(id){
			var o = this
			,$el = o.$element
			,$dataloader = o.$dataloader
			,url = $dataloader.attr("url");
			var val;
			$.ajax({
				type:"get",
				url:url,
				data:{"id":id},
				dataType:"json",
				cache:false,
				async: false,
				error: function(request) {
				    alert("error");
				},
				success: function(data) {
					val = $.parseJSON(data);
				}
			});
			return val;
		},
		/**
		 * 添加孩子方法
		 */
		addChilds:function(trsHtml) {
			var o = this
			,pmap = o.pMap;
            var $trs = $(trsHtml);
            if (!$trs.length) { return false; }
            
            var pId = $($trs[0]).attr('pId');
            if (!pId) { return false; }
            
            //插入到最后一个孩子后面，或者直接插在父节点后面
            var insertId = pmap[pId] && pmap[pId][pmap[pId].length - 1] || pId;
            $('#' + insertId).after($trs);
            o.initRelation($trs);
        }
	}
	
	/**
	 * jquery拓展treetable
	 */
	$.fn.treetable = function (option, value) {
		var methodReturn
		,options = typeof option === 'object'? $.extend({},$options.treetable,option):$options.treetable;
		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('treetable');
			if (!data) 
				$this.data('treetable', (data = new TreeTable(this, options)));
			if (typeof option === 'string') 
				methodReturn = data[option](value);
		});
		return methodReturn;
	};
	
	$.fn.treetable.Constructor = TreeTable;
	return $;
});