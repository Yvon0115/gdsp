<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Condition id="queryCondition" button=false button=false parameter="parameter" 
				size=3 cols=9 labelsize=1 ctrlsize=2
				style="width:1000px;margin-left:-30px;" class="pull-left"  >
	<@c.FormComboBox name="exch" label="交易所" >
        <@c.Option  value="-1" selected=true>全市场</@c.Option>
        <@c.Option  value="0" >上海</@c.Option>
        <@c.Option  value="1" >深圳</@c.Option>
	</@c.FormComboBox>
	<@c.QueryComponentDateRange 
			formatmap={"year":"yyyy","season":"yyyymm","M":"yyyymm","week":"yyyymmdd","D":"yyyymmdd"}
			id={"type":"typeId","begin":"beginId","end":"endId"}
			name={"type":"freq","begin":"start_date","end":"end_date"}
			label={"type":"频度","begin":"开始日期","end":"结束日期"}
			value={"type":"D","begin":"","end":""}
			<#-- 日期公式  -->
			formula={"begin":"_year:setDateFormula('cur_year',1),
									_season:setDateFormula('cur_season',-2),
									_month:setDateFormula('cur_month',-12),
									_week:setDateFormula('cur_week',4),
									_day:setDateFormula('cur_day',-10)",
								 "end":"_day:setDateFormula('cur_day',-1),_month:setDateFormula('cur_month',-1)"}
			options={"D":"日","M":"月"}
			keymap={"year":"_year","season":"_season","M":"_month","week":"_week","D":"_day"} 
			setDate={"beginId_endDate":"endId","endId_startDate":"beginId"}
			/>
</@c.Condition>