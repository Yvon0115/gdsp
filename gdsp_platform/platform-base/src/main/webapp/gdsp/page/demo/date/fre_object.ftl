<#import "/gdsp/tags/castle.ftl" as c>
 <@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Script src="script/plugins/fre_object" />
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/plugins/fre_object.js">-->
<@c.Condition id="queryCondition" target="#noiticeVos" action="${ContextPath}/plugins/date.d"  button=false class="pull-left" style="width:1000px;margin-left:-30px;" labelsize=1 ctrlsize=3  parameter="parameter">
	<@c.FormGroup cols=3 >
	    <@c.FormComboBox id="query_object" name="object" label="查询对象" events="{change:function(){invstrchange(this);}}">
	        <@c.Option  value="-1" selected=true>全部投资者</@c.Option>
	        <@c.Option  value="1">单一投资者</@c.Option>
	    </@c.FormComboBox>
				<@c.FormComboBoxDict itemId="invstr2" itemStyle="display:none" type="invst" label="投资者" id="invstr" name="invst"   value="">
				</@c.FormComboBoxDict>
	 	<@c.QueryComponentDateRange 
				formatmap={"year":"yyyy","season":"yyyymm","M":"yyyymm","week":"yyyymmdd","D":"yyyymmdd"}
				id={"type":"typeId","begin":"beginId","end":"endId"}
				name={"type":"freq","begin":"start_date","end":"end_date"}
				label={"type":"频度","begin":"开始日期","end":"结束日期"}
				value={"type":"D","begin":"","end":""}
				<#-- 日期公式  -->
				formula={"begin":"_year:setDateFormula('cur_year',1),
									_season:setDateFormula('cur_season',-2),
									_month:setDateFormula('cur_month',0),
									_week:setDateFormula('cur_week',4),
									_day:setDateFormula('cur_day',-1)"
								 ,
								 "end":"setDateFormula('cur_day',0),setDateFormula('cur_month',0)"}
								 
				options={"D":"日"}
				keymap={"Y":"_year","S":"_season","M":"_month","W":"_week","D":"_day"} 
		/>
	</@c.FormGroup>
</@c.Condition>
