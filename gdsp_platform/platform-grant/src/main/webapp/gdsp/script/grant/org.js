function selectNode(event){	
	var node = event.node;
	var pk_org = node.id;
	$("#pk_org").val(pk_org);
	$("#formPanel").loadUrl(__contextPath +"/grant/org/listData.d?pk_org="+pk_org);	
}

//function selectOrg(pk_org){	
//	$("#pk_org").val(pk_org);
//	$("#formPanel").loadUrl(__contextPath +"/grant/org/listData.d?pk_org="+pk_org);	
//}

function synchroShortName(container,obj)
{
	if($(container+" #shortname").val()!="")
		{
			return ;
		}
	var value=$(obj).val();
	$(container+" #shortname").val(value);
}

function deleteOrg(id){
	if(id=="" || id==null){
		$F.messager.warn("删除失败，请刷新页面后重新删除",{"label":"确定"});
		return;
	}
	$F.messager.confirm("确定删除机构吗？",{"callback":function(flag){
		if(flag){
			$.ajax({
				type: "POST",
			    url: __contextPath + "/grant/org/delete.d",
			    data: "id="+id,
			    success: function(ajaxResult){
			    	var ajaxResultObj = $.parseJSON(ajaxResult);
			    	if(ajaxResultObj.statusCode==300){
			    		$F.messager.warn(ajaxResultObj.message,{"label":"确定"});
			    		return;
			    	}
			    	if(ajaxResultObj.statusCode==200){
			    		window.location.reload();
			    		return;
			    	}
			    },
			    error: function(){
			    	$F.messager.warn("删除失败，请联系管理员",{"label":"确定"});
			    }
			});
		}
	}});
}
/**
 * 检测当前输入内容的合法性
 * @param th
 */
function checkChar(th){
	 if(/[/]/g.test(th.value)){
			$(th).val(th.value.replace(/[/]/g,""));  
	 }
	 if(/[^\u4E00-\u9FA5/a-zA-Z/1-9/]/g.test(th.value)){           
		$(th).val(th.value.replace(/[^\u4E00-\u9FA5/a-zA-Z/1-9]/g,""));  
	} 

}
/**
 * 回显父机构ID和名称
 */
function saveParentNode(){
	$("#orgAddForm #pk_fatherorg_label").val($("#refParentName").val());
	$("#orgAddForm #pk_fatherorg_value").val($("#refParentId").val());
}
/**
 * 选择父节点时，赋值给隐藏域
 * @param e
 */
function selectParentNode(e){
	var node = e.node;
	var pk_org = node.id;
	var name = e.node.text;
	$("#refParentId").val(pk_org);
	$("#refParentName").val(name);
}