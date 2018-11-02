<#--预警类型部署周期设置页面-->
<#import "/gdsp/tags/castle.ftl" as c>

<@c.Box style="border-bottom:none;border-left:none;border-right:none;box-shadow:none;">
    <@c.BoxBody style="width:100%;min-height:330px; ">
        <@c.FormGroup id="execInf" label="周期设置:">
        <@c.FormItem id="execPolicy">
        	<div style="margin-top:5px;">
			<input type="radio"  value="1"  id="execPolicy"  name="execPolicy" checked>即时执行&nbsp;&nbsp;
			<input type="radio"  value="2"  id="execPolicy"  name="execPolicy">定时执行&nbsp;&nbsp;
			<input type="radio"  value="3"  id="execPolicy"  name="execPolicy">高级设置&nbsp;&nbsp;
			</div>
        </@c.FormItem>

        <@c.FormItem id="period" label="执行周期" helper=""> 
        	<div style="margin-top:5px;">
       		<input type="radio"  value="1"  id="period"  name="period">每天&nbsp;&nbsp;
			<input type="radio"  value="2"  id="period"  name="period">每周&nbsp;&nbsp;
			<input type="radio"  value="3"  id="period"  name="period">每月&nbsp;&nbsp;
			</div>
        </@c.FormItem>

        <@c.FormItem id="daysOfWeek" >
        	  <div style="margin-top:5px;">
	             <#list trigger.daysOfWeekLabels as week>
	           		<div style="display:inline-block;width:46px;"><input type="checkbox"  value="${week_index+1}"  id="daysOfWeek"  name="daysOfWeek">${week}</div>
	      		 </#list>
      		 </div>			
        </@c.FormItem>        
        <@c.FormItem id="daysOfMonth" >
        	  <div style="margin-top:5px;">
	             <#list trigger.daysOfMonthLabels as month>
	           		<div style="display:inline-block;width:36px;"><input type="checkbox"  value="${month_index+1}"  id="daysOfMonth"  name="daysOfMonth">${month}&nbsp;</div>
	      		 </#list>
      		  </div>				
        </@c.FormItem>

        <@c.FormItem id="frequency" label="执行频率" helper="一天内执行次数">
        	<div style="margin-top:5px;">
				<input type="radio"  value="1"  id="execTime"  name="execTime">单次&nbsp;&nbsp;
				<input type="radio"  value="2"  id="execTime"  name="execTime">多次&nbsp;&nbsp;
			</div>	 
        </@c.FormItem>
        </@c.FormGroup> 
        
      <@c.FormGroup id="oneTime" label="单次设置:">
        <@c.FormDate type="time" name="onceTime" label="执行时间" value="" format="hh:ii:ss" validation={"onceTimeRequired":true} messages={"onceTimeRequired":"一次执行需指定开始时间!"} formatViewType="time"/>
        </@c.FormGroup> 
        
        <@c.FormGroup id="moreTime" label="多次设置:">
        <@c.FormItem id="bTimeSet" label="运行开始时间点" helper="几点开始执行">
        	<input type="number" class="form-control " value="" min="0" max="23" id="bTime"  name="bTime" >	 
        </@c.FormItem>
        
        <@c.FormItem id="eTimeSet" label="运行结束时间点" helper="几点结束执行">
        	<input type="number" class="form-control " value="" min="0" max="23" id="eTime"  name="eTime" validation={"eTimeCompare":true} valid-msgs={"eTimeCompare":"多次执行结束点应该晚于开始点！"}> 
        </@c.FormItem>
        
        <@c.FormComboBox  name="gaptype" label="间隔单位" helper="间隔时间单位"  value="1" events="{change: function(){typeChange(this)}}">
			<@c.Option value="1">小时</@c.Option>
            <@c.Option value="2">分钟</@c.Option>
            <@c.Option value="3">秒</@c.Option>
        </@c.FormComboBox>
        
        <@c.FormItem id="gapTimeSet" label="间隔时长" helper="间隔多长时间执行一次">
        	<input type="number" class="form-control " value="" min="0" max="23" id="gapTime"  name="gapTime" validation={"gapTimeRequired":true} valid-msgs={"gapTimeRequired":"多次执行需指定间隔时长！"}> 
        </@c.FormItem>     
       
        </@c.FormGroup> 
	    <#-- Cron表达式 -->
	     <@c.FormGroup id="expressionSet">
       		<@c.FormInput name="expression" label="Corn表达式" validation={"cronCheck":true,"remote":"${ContextPath}/schedule/jobdef/cronExpressionCheck.d"} messages={"cronCheck":"不能为空！","remote":"Cron表达式错误！"} />
        </@c.FormGroup> 
	     
	    <@c.FormGroup id="period" label="有效期:">
        <@c.FormDate type="datetime" name="startTime" label="开始时间" value="" validation={"required":true}/>
        <@c.FormDate type="datetime" name="endTime" label="终止时间" value=""  validation={"compareTime":true}/>
       	</@c.FormGroup> 
   	</@c.BoxBody>
</@c.Box>