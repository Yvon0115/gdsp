/**
 * 选择结构，赋值给隐藏域
 * @param e
 */
function selectParentNode(e){
	var id =e.node.id;
	var name = e.node.text;
	$("#refId").val(id);
	$("#refName").val(name);
}
/**
 * 点击确定时，将机构名称显示在input框中
 */
function saveParentNode(e){
	var code = $("#refId").val();
	var name = $("#refName").val();
	var v = {value:[code],text:[name]};
	$.closeReference(v);
	findRoles(code);
	reloadDataDic();
}
/**
 * 重新选择机构刷新右侧可授权数据
 */
function reloadDataDic(){
	$("#dicvalContent").attr("url", __contextPath +"/power/datalicense/queryDatadic.d");
	$("#dicvalContent").dataloader("load");
	//$("#dicvalContent").addCallbackDataloaderPlugin("load",pageLoad);
}
function renewLoad(){
	var roleId=$("#roleId").val();
	$("#dicvalContent").attr("url", __contextPath +"/power/datalicense/queryDatadic.d?roleId="+roleId);
	$("#dicvalContent").dataloader("load");
//	$("#dicvalContent").addCallbackDataloaderPlugin("load",pageLoad);
}
/**
 * 通过选择的机构，查询对应的角色
 * @param code
 */
function findRoles(code){
	if(code!=null){
		$.ajax({
			type: "post",
			dataType:"json",
		    url: __contextPath + "/power/datalicense/queryRoles.d",
		    data: {"orgId":code},
		    success: function(result){
		    	if(result!=null){
		    		$("a[name='roleArea']").remove();
		    		$("br").remove();
		    		var roleList=result.roleList;
		    		if(roleList.length>0){
		    			for(var i=0;i<roleList.length;i++){
		    				var a='<a class="list_A" name="roleArea" id="'+ 
		    				roleList[i].id +'" value="'+ roleList[i].id +'">'+roleList[i].rolename+'</a>';
		    				 $("#changeRoleList").append(a);
		    			}
		    		}
		    	}else{
		    		$F.messager.warn("查询角色失败。",{"label":"确定"});
		    	}	
		    },
		    error:function(){
		    	$F.messager.warn("查询角色失败。",{"label":"确定"});	
		    }
		})
	}else{
		$F.messager.warn("未选中任何机构。",{"label":"确定"});
	}
}

$("body").delegate("a[name=roleArea]","click",function(){
	$(".list_A").css({"background-color":"#ffffff","color":"#383737"});
	$(this).css({"background-color":"#e3e3e3","color":"#383737"});
	var roleId=this.id;
	$("#roleId").val(roleId);
	$("#dicvalContent").attr("url", __contextPath+"/power/datalicense/queryDatadic.d?roleId="+roleId);
	$("#dicvalContent").addCallbackDataloaderPlugin("load",pageLoad,"addCallbackPlugin");
	$("#dicvalContent").dataloader("load");
	
//	$("#dicvalContent").on("load",function(){
//		pageLoad();
//	})
	/*setTimeout(function(){
		pageLoad();
	},200)*/

});



function savePowerDic(){
	var dicId =[];
	var jsonObj = {};
	var $treenDiv=$('div[class="easytree treeview"]');
	$.each($treenDiv,function(i){
	dicId.push($(this).attr("id").substring(0,32));
	});
	for(var i=0;i<dicId.length;i++){
		var $treeNode=$("#"+dicId[i]+"Tree").treeview('getChecked');
		var dicValue=[];
		$.each($treeNode,function(i){
			dicValue.push($(this).attr("id"));
		});
		jsonObj[dicId[i]] = dicValue;
	}
	var jsonStr= JSON.stringify(jsonObj);
	var roleid=$("#roleId").val();
		$.ajax({
			type: "POST",
		    url: __contextPath + "/power/datalicense/saveDicValToRole.d",
		    data:  {
				"dataValue":jsonStr,
				"roleId":roleid
			},
		    success: function(ajaxResult){
		    	var ajaxResultObj = $.parseJSON(ajaxResult);
		    	if(ajaxResultObj.statusCode==300){
		    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
		    		return;
		    	}
		    	if(ajaxResultObj.statusCode==200){
		    		$F.messager.success("保存成功!", {"label" : "确定"})
		    		return;
		    	}
		    },
		    error: function(){
		    	$F.messager.warn("保存失败，请刷新后重试",{"label":"确定"});
		    }
		});
//	}
}

function pageLoad(){
	//修改IE9下 切换tab时，active=false的placeholder属性位置乱
//	setTimeout(function(){
//		$("label[class='placeholder']").css({"left":"10px","top":"59px","width":"790px"});
//	},300);
	var $treeIds=$("div[class='easytree treeview']");
		$.each($treeIds,function(i){
		$("#"+$(this).attr("id")).treeview('disableAll');
		});
		$("div[class='box-footer text-center']>button[class='btn btn-primary  ']").css({'display':'block','margin-left':'300px'});
		$("div[class='box-footer text-center']>button[class='btn btn-canel  ']").css({'display':'block','margin-left':'370px','margin-top':'-26px'});
		$("button[class='btn btn-primary  pull-right']").css({'display':'block'});
	
}
function treeDisplay(){
	var $treeIds=$("div[class='easytree treeview']");
	$.each($treeIds,function(i){
	$("#"+$(this).attr("id")).treeview('enableAll');
	});
}
