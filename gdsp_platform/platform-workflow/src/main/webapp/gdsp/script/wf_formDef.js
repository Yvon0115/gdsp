// get categoryId
function selectNode(e){
//	var pk_category = e.link.attr("value");	
	var pk_category = e.node.id;
	$("#pk_categoryid").val(pk_category);
	$("#formDefContent").attr("url",__contextPath+"/workflow/formdef/listData.d?categoryCode="+pk_category);
	$("#formDefContent").dataloader("load");
}

function addCategory(){
	var pk_categoryid = $("#pk_categoryid").val();
	if(!pk_categoryid){
		$F.messager.warn("请先选择类型！",{"label":"确定"});
		return;
	}else{
		$("#detailPanel").attr("href",__contextPath+"/workflow/formdef/add.d?pk_categoryid="+pk_categoryid);
		$("#detailPanel").tab("show");
	}
}

function deleteParaRow(obj){    
	$(obj).parent().parent().remove();
}

function addParaRow(){
	//表格中最后一行中input标签的name属性值
	var $inputName=$("input[id='variableName']:last").attr("name");
	
	//处理如果删除了第一个的情况
	if($inputName != null)
		var index = parseInt($inputName.substring(11,12))+1;
	else 
		var index = 0;
	//新增的行，每个input输入框的name中的集合下标根据上一行的加1
    var newRow='<tr>'
    		  +'<td><input value="" id="variableName" name="parameters['+index+'].variableName" type="text"> </td>'
    		  +'<td><input value="" id="displayName" name="parameters['+index+'].displayName" type="text"> </td>'
    		  +'<td><input value="" id="memo" name="parameters['+index+'].memo" type="text"> </td>'
    		  +'<td><a data-original-title="删除变量" class="link " title="" onclick="deleteParaRow(this)" href="javascript:void(0)"><i class="glyphicon glyphicon-remove"></i>删除</a></td>'
    		  +'</tr>';  
    $('#paraTable').append(newRow); 
}  
/**
 * 
 */
function checkChar(th){
	

}
//<tr>
//<td><input value="" id="paraname" name="paraname" type="text"> </td>
//<td><select aria-invalid="false" name="parameters[0].required"> <option value="Y">是</option><option value="N">否</option> </select></td>
//<td><input value="" id="description" name="description" type="text"> </td>
//<td><a data-original-title="删除参数" class="link " title="" onclick="deleteParaRow(this)" href="javascript:void(0)"><i class="glyphicon glyphicon-remove"></i>删除</a></td>        
//</tr>