require(['jquery','plugins/typeahead/typeahead'],function($){
	$.fn.typeahead.Constructor.prototype.blur = function() {
		   var that = this;
		      setTimeout(function () { that.hide() }, 250);
	};
	var idMap = {};
	var urlMap = {};
	var names = [];
	var loadState = "N";
	$("#product_search").typeahead({
		source : function(query,process){
			if(loadState == "Y"){
				process(names);
			}else{
				$.ajax({
					url : __contextPath + "/func/menu/queryAllAvailableMenu.d",
					dataType : "json",
					async : false,
					success : function(result){
						var menus = result.params.menus;
						$.each(menus,function(index,item){
							if(item.url != null && item.url != ""){
								urlMap[item.funname] = item.url;
								idMap[item.funname] = item.id;
								names.push(item.funname);
							}
						});
						process(names);
						loadState = "Y";
					}
				});
			}
		},
		afterSelect: function (item) {
			if($F.Jtabs==undefined){//没有启用多页签
				if(urlMap[item].indexOf(".d?") != -1){
					if(urlMap[item].indexOf("/") == 0){
			            window.location.href = __contextPath + urlMap[item] + "&__mn=" + idMap[item];
					}else{
						window.location.href = __contextPath + "/" + urlMap[item] + "&__mn=" + idMap[item];
					}
				}else{
					if(urlMap[item].indexOf("/") == 0){
						window.location.href = __contextPath + urlMap[item] + "?__mn=" + idMap[item];			
					}else{
						window.location.href = __contextPath + "/" + urlMap[item] + "?__mn=" + idMap[item];	
					}
				}
			}else{
				var tabId= idMap[item];
				var tabName = item;
				var tabUrl = "";
				if(urlMap[item].indexOf(".d?") != -1){
					if(urlMap[item].indexOf("/") == 0){
						tabUrl = __contextPath + urlMap[item] + "&__mn=" + idMap[item];
					}else{
						tabUrl = __contextPath + "/" + urlMap[item] + "&__mn=" + idMap[item];
					}
				}else{
					if(urlMap[item].indexOf("/") == 0){
						tabUrl = __contextPath + urlMap[item] + "?__mn=" + idMap[item];			
					}else{
						tabUrl = __contextPath + "/" + urlMap[item] + "?__mn=" + idMap[item];	
					}
				}
				$F.Jtabs.add({
					id: tabId,
					title: tabName,
					url: tabUrl,
					ajax: false
				});
			}
        },
        fitToElement : true,
        items : 4,
        item: '<li><a class="dropdown-item" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;" href="#" role="option"></a></li>',
	});
});

