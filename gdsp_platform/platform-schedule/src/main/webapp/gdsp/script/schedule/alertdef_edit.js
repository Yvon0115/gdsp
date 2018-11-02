/**
 * 任务类型管理
 */

function deleteParaRow(obj){    
	$F.messager.confirm("确认删除选中参数？", {
		"callback" : function(flag) {
			if (flag) {
				$(obj).parent().parent().remove();
			}
		}
	});
}

function addParaRow(){
	var table=document.getElementById("paraTable");  
	var length=table.rows.length-1;
    var newRow='<tr><td><input value="" id="paraname" name="parameters['+length+'].paraname" type="text"> </td>'
    	+'<td><select aria-invalid="false" name="parameters['+length+'].required"> <option value="N">否</option> <option value="Y">是</option> </select> </td>'
    	+'<td><input value="" id="description" name="parameters['+length+'].description" type="text"> </td>'
    	+'<td><a data-original-title="删除参数" class="link " title="" onclick="deleteParaRow(this)" href="javascript:void(0)"><i class="glyphicon glyphicon-remove"></i>删除</a></td>'
    	+'</tr>';  
    $('#paraTable').append(newRow);  
}  
//<tr>
//<td><input value="" id="paraname" name="paraname" type="text"> </td>
//<td><select aria-invalid="false" name="parameters[0].required"> <option value="Y">是</option><option value="N">否</option> </select></td>
//<td><input value="" id="description" name="description" type="text"> </td>
//<td><a data-original-title="删除参数" class="link " title="" onclick="deleteParaRow(this)" href="javascript:void(0)"><i class="glyphicon glyphicon-remove"></i>删除</a></td>        
//</tr>