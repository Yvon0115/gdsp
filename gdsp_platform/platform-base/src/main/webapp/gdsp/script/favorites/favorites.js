define(["cas/simpletree"]);

function changeFavoritesFileNode(id){	
	$("#favorites_pid").val(id);
}

$(function(){ 
	
	var res = '';
	
$.validator.addMethod("repetitiveNameCheck",function(value,element){
	var pid='';
	var fileType = $("#fileType").val();
	var name="";
	if(fileType==2){
		 pid=$("#favorites_pid").val();

		 name = $("#favoritesName").val();
	}else if(fileType==1){ 
		 pid=$("#file_pid").val();

		 name = $("#favoritesFileName").val();
	}
    $.ajax({
        type:"POST",
        async:false, 
    	url:__contextPath + "/favorites/checkUnique.d?",
		data : {
			name : name,
			fileType : fileType,
			pid : pid
		},
        success:function(response){
            if(response=='true'){
                res = true;
            }else{
                res = false;
                $("#favorites_pid").val('');
                $("#file_pid").val('');
            }
        }
    });
    return res;
},"名称不能重复，请确认！");

})



function favoritespagereload() {
	$.closeDialog();
}

function changeNode(id){	
	$("#file_pid").val(id);
}
//原addFile.ftl中关于收藏夹的内容移至本文件
$(function(){  
    $(document).bind("click",function(e){  
        var target = $(e.target);
        if((target.closest(".selected").length == 0 && target.closest("#input_form").length == 0)){  
            $("#manageTree > li > div").removeClass("selected"); 
            $("#file_pid").val("");
        }  
    });  
}) 


function cancel(){
	$.closeDialog();
}

//原add.ftl中关于收藏夹的内容移至本文件
$(function(){  
    $(document).bind("click",function(e){  
        var target = $(e.target);  
        if((target.closest(".selected").length == 0 && target.closest("#input_form").length == 0)){  
            $("#manageTree > li > div").removeClass("selected");
            $("#favorites_pid").val("");
        }  
    });  
})  


function addFavoritesFile(){
	var menuId = $("#menuId").val();
	var name = $("#hide_name").val();
	$.closeDialog();
	$.openModalDialog({
		"href" : encodeURI(__contextPath +"/favorites/toAddFavoritesFile.d"),
		"params":{
			name :name,
			menuId : menuId
		},
		"height" : "550",
		"width" : "400",
		"title" : "新增收藏目录"
	});
}

function cancel(){
	$.closeDialog();
}


// 原header_csdc中关于收藏夹的内容移至本文件
$(function(){

	$(".menu_csdc li").click(function(){//.end 节点元素返回上一步操作
	//$(this).addClass('open').children('.sub_menu').slideDown().end().siblings().removeClass('open').children('.sub_menu').slideUp();
	//$(this).children('.arrow').addClass('on')
		//console.log($(this).children('a'));
		$(this).children('a').addClass('bgimg');
		$(this).children('div').addClass('menu_bg');
		//console.log($(this).children('div'));
		});
	$(document).bind("click",function(e){  
	     var target = $(e.target);  
	     if((target.closest("#favorites_div").length == 0)){  
	         $("#favorites_div").hide();
	     }  
	}); 

	$("#favorites_drop").click(function(){
		//如果位于密码修改页面，则点击收藏夹图标不给出响应
		if(typeof(this.baseURI)!="undefined"){
		if(this.baseURI.includes("toEditPassword.d")){
			return;
		}
		}
		//tab标签切换到收藏夹
		$("#favoritesPanel").parent().attr("class","liheight active");
		$("#favoritesPanel").attr("aria-expanded","true");
		$("#favoritesPanel-tabpane").attr("class","tab-pane active");
		$("#managePanel").parent().attr("class","liheight");
		$("#managePanel").attr("aria-expanded","false");
		$("#managePanel-tabpane").attr("class","tab-pane");
		
		var show = $("#favorites_div").css("display");
		if(show == "none"){
			$("#favorites").load(__contextPath + "/favorites/showFavorites.d"+"?timestamp=" + new Date().getTime(),null,function(data){
				var tree = $("#favoritesTree");
				$.fn.customtree(tree);
			
				var favoritesUrl;
				var url=JtabsUrl();
				if(typeof(url)!="undefined")
				if(url.indexOf("homepage.d") > 0){
					document.getElementById("addFavorites").disabled = true;
				}else{
					//获取选中菜单的URL
					var secondUrl=JtabsSecondUrl();
					if(secondUrl == undefined){
						secondUrl = "index.d"
					}
					if(secondUrl.indexOf("index.d") > 0){
						document.getElementById("addFavorites").disabled = true;
					}else{
						document.getElementById("addFavorites").disabled = false;
						var hideUrl = $("#hideUrl").val();
						if(hideUrl != ""){
							$("#favoritesUrl").val(hideUrl);
							$("#hideMenuId").val("");
						}else{
							var contextpath = $("#contextPath").val();
							//对菜单url进行截取
							var favoritesUrl = url.substring(url.indexOf(contextpath) + contextpath.length);
							//对如果包含&参数的收藏菜单进行处理
							if(favoritesUrl.indexOf("&")>0){
								var favoritesUrlsub=favoritesUrl.substring(0,favoritesUrl.indexOf("&"));
								$("#favoritesUrl").val(favoritesUrlsub);
							}else{
								$("#favoritesUrl").val(favoritesUrl);
							}
							var menuId = "";	
							var n_index=url.indexOf("?");
							//如果有？携带参数
							if(n_index>=0){
							  //表示从n_index这个位置一直截取到最后   
							  var url_sub=url.substr(n_index+1);
							  //如果包含&
							  if(url_sub.indexOf("&")>=0){
							  //对截取到的字符串进行分割分组
							  var sp_arr=url_sub.split("&");
//								  对切割后的地址进行查找
							  for(var i = 0;i<sp_arr.length;i++){
							  	//如果包含__mn=
							  	if(sp_arr[i].indexOf("__mn=")>=0){
							  	//对包含的数组中的值进行截取
							  		 menuId=sp_arr[i].substr(sp_arr[i].indexOf("=")+1);
							  	   }
							  	}
							  }else{
							  	menuId = url_sub.substring(url_sub.indexOf("__mn=") + 5);
							  }

							}
							
							$("#hideMenuName").val("");
							$("#hideMenuId").val(menuId);
						}
					}
				}
			});
			$("#favoritesManage").load(__contextPath + "/favorites/showManageFavorites.d"+"?timestamp=" + new Date().getTime(),null,function(data){
				var tree = $("#manageTree");
				$.fn.customtree(tree);
			});
			$("#favorites_div").show();
		}

	});

	$("#favorites_csdc").click(function(){
	  $("#favorites_div").hide();
	});

});

//多页签url获取方法
function JtabsUrl(){
	var url;
	if($F.Jtabs==undefined){//没有启用多页签
	
	url = document.location.href;
		
	}else{
		var url_id = $("#jtabs-titlesid-xg > ul > li.active > a").attr("href");
		if(typeof(url_id)!="undefined"){
		var	t_index=url_id.indexOf("#tab_");
		}
		if(t_index>=0){
		 
			url_id=url_id.substr(5);
			
			}
		
		url=$("a[jtab-id="+url_id+"]").attr("jtab-url");
	}
	return url;

}


function JtabsSecondUrl(){
	var secondUrl;
	if($F.Jtabs==undefined){//没有启用多页签
	
		secondUrl = $("#one_level .active a").prop("href");
		
	}else{
		var url_id = $("#jtabs-titlesid-xg > ul > li.active > a").attr("href");
		
		var	t_index=url_id.indexOf("#tab_");
		
		if(t_index>=0){
		 
			url_id=url_id.substr(5);
			
			}
		
		secondUrl=$("a[jtab-id="+url_id+"]").attr("jtab-url");
	}
	return secondUrl;

}




function retunToFavorites(){
	$("#favorites_div").hide();
	$.closeDialog();
	var url = $("#favoritesUrl").val();
	var name = $("#hideMenuName").val();
	var menuId = $("#hideMenuId").val();
	
		if(url.indexOf("__mn=")<0){
			url=url+"&"+"__mn="+menuId;
		}
	$.openModalDialog({
//		?url=" + url + "&name=" + name + "&menuId=" + menuId
		"href" :encodeURI(__contextPath +"/favorites/toAddFavorites.d"),
		"params":{
			name :name,
			url : url,
			menuId : menuId
		},
		"height" : "550",
		"width" : "400",
		"title" : "添加到收藏夹"
	});
}

	function addToFavorites(){
		$("#favorites_div").hide();
		var url = $("#favoritesUrl").val();
		var name = $("#hideMenuName").val();
		var menuId = $("#hideMenuId").val();
		if(url.indexOf("__mn=")<0){
			url=url+"&"+"__mn="+menuId;
		}
		if(menuId==""||url==""){
			$F.messager.warn("无可收藏的功能菜单!",{"label":"确定"});
			return;
		}
		$.openModalDialog({
//			?url=" + url + "&name=" + name + "&menuId=" + menuId
			"href" :encodeURI(__contextPath +"/favorites/toAddFavorites.d"),
			"params":{
				name :name,
				url : url,
				menuId : menuId
			},
			"height" : "550",
			"width" : "400",
			"title" : "添加到收藏夹"
		});
	}

	function selectFavoriteNode(fileType,url,name){
		if(fileType == "2"){
			if($F.Jtabs==undefined){//没有启用多页签
				if(url.indexOf("/") == 0){
					window.location.href = __contextPath + url;
				}else{
					window.location.href = __contextPath + "/"  + url;
				}
			}else{
				var tabId= url.substring(url.indexOf("_mn=")+4,url.length);
				var tabName = name;
				var tabUrl = "";
				if(url.indexOf("/") == 0){
					tabUrl = __contextPath + url;
				}else{
					tabUrl = __contextPath + "/"  + url;
				}
				$F.Jtabs.add({
					id: tabId,
					title: tabName,
					url: tabUrl,
					ajax: false
				});
			}
		}
	}

	function manageNode(id){	
		$("#manage_id").val(id);
	}

	function deleteFavorites(){
		var id = $("#manage_id").val();
		if(id == ""){
			$F.messager.warn("请先选中要删除的收藏或目录!",{"label":"确定"});
			return;
		}
		$F.messager.confirm("确认要删除该收藏或目录及目录以下的所有目录和收藏吗？",{"callback":function(flag){
			$.ajax({
				url:__contextPath + "/favorites/deleteFavorites.d",
				cache:false,
				async:false,
				data :　{
					id : id
				},
				success:function(data){
					$("#manage_id").val("");
					$("#favorites").load(__contextPath + "/favorites/showFavorites.d"+"?timestamp=" + new Date().getTime(),null,function(data){
						var tree = $("#favoritesTree");
						$.fn.customtree(tree);
					});
			
					$("#favoritesManage").load(__contextPath + "/favorites/showManageFavorites.d"+"?timestamp=" + new Date().getTime(),null,function(data){
						var tree = $("#manageTree");
						$.fn.customtree(tree);
					});
				}
			})
		}})
	}
