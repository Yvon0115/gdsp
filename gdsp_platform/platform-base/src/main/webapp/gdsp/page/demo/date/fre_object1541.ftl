<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/plugins/fre_object.js">-->
<@c.Script src="script/plugins/fre_object" />
<@c.Condition id="queryCondition" target="#noiticeVos" action="${ContextPath}/plugins/date.d"  button=false class="pull-left" style="width:1000px;margin-left:-30px;" labelsize=1 ctrlsize=3  parameter="parameter">
	<@c.FormGroup cols=3 >
	    <@c.FormComboBox id="query_object" name="object" label="查询对象" events="{change:function(){industrychange(this);}}">
	      	 <@c.Option  value="-1" selected=true>全部行业</@c.Option>
	        <@c.Option  value="1" >单一行业</@c.Option>
	    </@c.FormComboBox>
	     <div id="indu" style="display:none" >
				<@c.FormComboBoxDict  type="fq_001" label="行业" id="industry" name="industry"   value="">
				</@c.FormComboBoxDict>
	    </div>
	 	<@c.QueryComponentDateRange 
				formatmap={"year":"yyyy","season":"yyyymm","M":"yyyymm","week":"yyyymmdd","D":"yyyymmdd"}
				id={"type":"typeId","begin":"beginId","end":"endId"}
				name={"type":"freq","begin":"start_date","end":"end_date"}
				label={"type":"频度","begin":"开始日期","end":"结束日期"}
				value={"type":"D","begin":"","end":""}
				<#-- 日期公式  -->
				formula={"begin":"setDateFormula('cur_day',-10)","end":"setDateFormula('cur_day',0)"}
				options={"D":"日","M":"月"}
				keymap={"year":"_year","season":"_season","M":"_month","week":"_week","D":"_day"} 
		/>
	</@c.FormGroup>
</@c.Condition>
