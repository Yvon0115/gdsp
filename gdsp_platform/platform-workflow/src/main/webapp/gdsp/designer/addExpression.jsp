<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/gdsp/common/global.jsp"%>

<script type="text/javascript">
	// 全局变量
	var num = 0;
	var str = "";
	
	// 获取固定标签对象
	//var $textArea = jq("#condition");
	//这里不能这样用，在后边$textArea.val(condi)会没有，设置不了该标签的值
	var $trDefault = jq("#trDefault");
	var $table = jq("#table1");
	//var $btnMatch = jq("#btnMatch");
	
	// 点击添加按钮时，克隆默认行，设置id值自增
	function addNewCondition() {
		var $trClone = $trDefault.clone(true);
		num = num + 1;// 让全局变量自增
		$trClone.attr("id", num);// 将num值当做新增行的id值
		$trClone.appendTo($table);// 新增行添加到table里
	}
	
	/*
	 * 点击单元格“删除”触发的click事件
	 * 如果tr为是默认的trDefault则提示不能删除
	 * 	否则就删除该tr行
	 */
	function deleteCondition(obj) {
		var $tr = jq(obj).parent().parent();
		if ($tr.attr("id") == "trDefault") {
			jq.messager.alert("操作提示", "该行不能删除！","warning");
		} else {
			$tr.remove();
		}
	}
	
	function doMatchCondition(){
		var condi = "";
		jq("#condition").val();
		$table.find("tr").each(function() {
			if (jq(this).attr("id") != "title") {
				jq(this).find("td").each(function() {
					if (jq(this).attr("id") != "tdDel") {
						condi = condi + jq(this).children().val() + " ";
					}
				})
				condi = condi + "\r\t";
			}
		})
		jq("#condition").val(condi);
		test_win.window("close");
	}
</script>

<div align="left"><span style="color: #666; font-weight: bold; font-size: 15px;"><b>条件设置</b></span><br/></div>
<div align="center"><a id="btnAdd" href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="addNewCondition()">新增</a></div>
<div id="div1">
	<table id="table1" border="1" >
		<thead>
			<tr id="title" style="text-align:center;vertical-align:middle;font-size: 15px;">
				<th><b>逻辑</b></th>
				<th><b>左括号</b></th>
				<th><b>变量</b></th>
				<th><b>关系运算符</b></th>
				<!-- <th><b>值类型</b></th> -->
				<th><b>值</b></th>
				<th><b>右括号</b></th>
				<th><b>操作</b></th>
			</tr>
		</thead>
		<tbody>
			<tr id="trDefault">
				<td><select style="width: 60px;" >
						<option></option>
						<option>and</option>
						<option>or</option>
				</select></td>
				<td>
					<select style="width: 60px">
							<option></option>
							<option>(</option>
					</select>
				</td>
				<td><select style="width: 60px">
						<option>var1</option>
						<option>var2</option>
						<option>var3</option>
				</select></td>
				<td><select style="width: 80px">
						<option>=</option>
						<option>></option>
						<option><</option>
						<option>>=</option>
						<option><=</option>
						<option>!=</option>
						<option>-</option>
				</select></td>
				<!-- <td>
				<select style="width: 100%">
					<option>字符串</option>
					<option>数字</option>
					<option>日期</option>
					<option>布尔</option>
				</select>
				</td> -->
				<td><input style="width: 70px" type="text"></td>
				<td><select style="width: 60px">
						<option></option>
						<option>)</option>
				</select></td>
				<td id="tdDel"style="cursor: pointer;width:80px;">
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="deleteCondition(this)">删除</a></td>
			</tr>
		</tbody>
	</table>
	<br/>
	<!-- <input type="button" value="拼接" id="btnMatch" onclick="doMatchCondition()"/> -->
	<div align="left"><a id="btnMatch" href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="doMatchCondition()">拼接</a></div>
