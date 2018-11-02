<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Script src="script/integration/subject"  />
<@c.Condition id="queryCondition" target="#noiticeVos" action="${ContextPath}/plugins/date.d"  button=false class="pull-left" 
                                  style="width:850px;margin-left:-20px;" labelsize=1 ctrlsize=3  parameter="parameter">

	    <@c.FormComboBox id="query_object" name="object" label="查询对象" events="{change:function(){area_Change(this);}}">
	      	<@c.Option  value="-1" selected=true>全部辖区</@c.Option>
	        <@c.Option  value="0" >单一辖区</@c.Option>
	    </@c.FormComboBox>
		<@c.QueryComponent label="查询日期" name="query_date" itemId="query_date_div" itemStyle="display:block" type="date" exttype="_month"  format="yyyymm"  formula="_day:setDateFormula('cur_day',-1),_month:setDateFormula('cur_month',-1)"/>
		<@c.FormComboBoxDict  name="area" itemId="area_div" type="area" label="辖区"  itemStyle="display:none" height="300">
		</@c.FormComboBoxDict>
	 	<@c.QueryComponent label="开始日期" name="start_date" itemId="start_date_div" id="start_date" itemStyle="display:none;margin-top:10px;" type="date" exttype="_month"  format="yyyymm"  formula="_day:setDateFormula('cur_month',-12),_month:setDateFormula('cur_month',-12)" endDate="end_date"/>
	    <@c.QueryComponent label="结束日期" name="end_date"   itemId="end_date_div"   id="end_date"   itemStyle="display:none;margin-top:10px;" type="date" exttype="_month"  format="yyyymm"  formula="_day:setDateFormula('cur_month',-1),_month:setDateFormula('cur_month',-1)" startDate="start_date"/>

</@c.Condition>