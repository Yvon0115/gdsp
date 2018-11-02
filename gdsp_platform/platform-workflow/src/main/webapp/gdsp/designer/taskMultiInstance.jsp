<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<script type="text/javascript">
<!--
	jq(function() {
		loadTaskMultiInstance();
		setSeq();
	});
	function saveTaskMultiInstance() {
		if (jq('#sequentialYes').attr('checked')) {
			task.isSequential='true';
		}
		if (jq('#sequentialNo').attr('checked')) {
			task.isSequential='false';
		}
		task._multiInstanceRule=jq("input[name='multiInstanceRule']:checked").val();
		if(task._multiInstanceRule=='allAgree')
			task._multiInstanceVal=1;
		if(task._multiInstanceRule=='halfAgree')
			task._multiInstanceVal=2;
		if(task._multiInstanceRule=='oneAgree')
			task._multiInstanceVal=3;
		if(task._multiInstanceRule=='percentageAgree')
			task._multiInstanceVal=jq("#percentageValue").val();
		if(task._multiInstanceRule=='countAgree')
			task._multiInstanceVal=jq("#countValue").val();
		
		//task._loopCardinality = jq("#loopCardinality").val();
		//task._collection = jq("#collection").val();
		//task._elementVariable = "assignee";
		//task._completionCondition = jq("#completionCondition").val();
		//task._multiInstanceRule = jq("#multiInstanceRule").val();
	}
	function loadTaskMultiInstance() {			
		if (task.isSequential=='true') {			
			jq('#sequentialYes').attr('checked', true);
			jq('#multiCheckbox').attr('checked', true);
		} else if(task.isSequential=='false') {			
			jq('#multiCheckbox').attr('checked', true);
			jq('#sequentialNo').attr('checked', true);
		}

		//jq("#loopCardinality").val(task._loopCardinality);
		//jq("#collection").val(task._collection);
		///jq("#elementVariable").val(task._elementVariable);
		//jq("#completionCondition").val(task._completionCondition);
		//jq("#multiInstanceRule").val(task._multiInstanceRule);
		jq("input[name='multiInstanceRule']:checked").val(task._multiInstanceRule);
		if(task._multiInstanceRule=='allAgree')
			jq('#allAgree').attr('checked', true);
		if(task._multiInstanceRule=='halfAgree')
			jq('#halfAgree').attr('checked', true);
		if(task._multiInstanceRule=='oneAgree')
			jq('#oneAgree').attr('checked', true);
		if(task._multiInstanceRule=='percentageAgree'){
			jq('#percentageAgree').attr('checked', true);
			jq("#percentageValue").val(task._multiInstanceVal);
		}
		if(task._multiInstanceRule=='countAgree'){
			jq('#countAgree').attr('checked', true);
			jq("#countValue").val(task._multiInstanceVal);
		}
	}
	function setSeq(){
		if (jq('#multiCheckbox').attr('checked')) {
			task.multiCheckbox=true;
			//jq('#sequentialYes').attr('checked', true);
			//jq('#halfAgree').attr('checked', true);
			jq("input[name='multiInstanceRule']").removeAttr("disabled");
			jq("input[name='sequential']").removeAttr("disabled");
			jq("input[name='val']").removeAttr("disabled");
		}else{
			task.multiCheckbox=false;
			jq("input[name='multiInstanceRule']").attr("disabled", "disabled");
			jq("input[name='sequential']").attr("disabled", "disabled");
			jq("input[name='val']").attr('disabled', 'disabled');
			jq('#sequentialYes').attr('checked', false);
			jq('#sequentialNo').attr('checked', false);
			jq("input[name='multiInstanceRule']").attr('checked', false);
			task.isSequential = null;
			task._multiInstanceRule=null;
			task._multiInstanceVal=0;
		}
	}
//-->
</script>
<table>
	<tr>
		<td></td>
		<td><input type="checkbox" id="multiCheckbox" onchange="setSeq();" />启用会签</td>
	</tr>
	<tr>
		<td align="left">
			<p style="width: 70px; text-align: right; font-size: 14px; float: right;">会签类型:</p>
		</td>
		<td>
			<input onchange="saveTaskMultiInstance();" type="radio" id="sequentialYes" name="sequential" value="true" />顺序
			<input onchange="saveTaskMultiInstance();" type="radio" id="sequentialNo" name="sequential" value="false"/>并行 
			<!-- <input type="radio" name="sequential" value="" checked="checked" />不启动多实例 -->
			<!-- <select style="width:160px;" name="isSequential" id="isSequential">
		         <option value="">不启动多实例</option>
		         <option value="true">顺序</option>
		         <option value="false">并行</option>
		        </select> -->
		</td>
	</tr>
	<tr>
		<td align="left">
			<p style="width: 70px; text-align: right; font-size: 14px; margin-top:-50px;" >会签规则:</p>
		</td>
		<td >
			<input onchange="saveTaskMultiInstance();" value="allAgree" type="radio" id="allAgree" name="multiInstanceRule"/>全票通过<br />
			<input onchange="saveTaskMultiInstance();" value="halfAgree" type="radio" id="halfAgree" name="multiInstanceRule"/>半数通过(大于等于50%)<br />
			<input onchange="saveTaskMultiInstance();" value="oneAgree" type="radio" id="oneAgree" name="multiInstanceRule"/>一票通过<br />
			<input onchange="saveTaskMultiInstance();" value="percentageAgree" type="radio" id="percentageAgree" name="multiInstanceRule"/>百分比通过
			<input  onchange="saveTaskMultiInstance();" id="percentageValue" style="width: 30px;height: 14px;" type="text" name="val"/>%(大于等于)
			<br />
			<input onchange="saveTaskMultiInstance();" value="countAgree" type="radio" id="countAgree" name="multiInstanceRule"/>固定票数通过
			<input  onchange="saveTaskMultiInstance();" id="countValue" style="width: 30px;height: 14px;" type="text" name="val"/>票(大于等于)
			<br />
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
		<!-- 	<input onchange="saveTaskMultiInstance();" value="allAgree" type="radio" id="allAgree" name="multiInstanceRule"/>全票通过<br />
			<input onchange="saveTaskMultiInstance();" value="halfAgree" type="radio" id="halfAgree" name="multiInstanceRule"/>半数通过(大于等于50%)<br />
			<input onchange="saveTaskMultiInstance();" value="oneAgree" type="radio" id="oneAgree" name="multiInstanceRule"/>一票通过<br />
			<input onchange="saveTaskMultiInstance();" value="percentageAgree" type="radio" id="percentageAgree" name="multiInstanceRule"/>百分比通过
			<input  onchange="saveTaskMultiInstance();" id="percentageValue" style="width: 30px;height: 12px;" type="text" name="val"/>%(大于等于)
			<br />
			<input onchange="saveTaskMultiInstance();" value="countAgree" type="radio" id="countAgree" name="multiInstanceRule"/>固定票数通过
			<input  onchange="saveTaskMultiInstance();" id="countValue" style="width: 30px;height: 12px;" type="text" name="val"/>票(大于等于)
			<br /> -->
		</td>
	</tr>
	<%-- <fieldset style="line-height: 21px;">
		<legend>说明</legend>
		<div>1.${flowUtil.stringToList(assigneeUserIdList)}，将字符串转换成集合，暴露的SpringBean方法</div>
		<div>2.多实例任务Activiti默认会创建3个流程变量，nrOfInstances:实例总数，nrOfActiveInstances:当前活跃的，也就是当前还未完成的，对于顺序的多实例，此值总是1,nrOfCompletedInstances:已完成的实例个数。</div>
		<div>3.状态:不启动多实例,则只会创建一个任务，默认不启动，不启动多实例，一下配置都无效，true:顺序执行，fasle:并行,同时执行。</div>
		<div>4.循环数量:指定创建多任务的数量。可使用表达式从流程变量获取。</div>
		<div>5.循环集合:流程变量中一个集合类型变量的变量名称。根据集合元素多少创建任务数量。可使用表达式。例:流程变量：assigneeUserIdList=[user1,user2]，可用assigneeUserIdList。</div>
		<div>6.集合元素:集合中每个元素的变量名称，可在每个任务中获取,可使用表达式。例：集合为当定义集合元素名称为:assigneeUserId,可在任务直接指派人员用表达式${assigneeUserId}获取，用于动态会签。</div>
		<div>7.结束条件:多实例活动结束的条件，默认为完成多全部实例，当表达式返回true时结束多实例活动。例：${nrOfCompletedInstances/nrOfInstances&gt;=0.6} 说明当有60%的任务完成时，会完成此多实例，删除其他未完成的，继续下面的流程。</div>
	</fieldset> --%>
</table>