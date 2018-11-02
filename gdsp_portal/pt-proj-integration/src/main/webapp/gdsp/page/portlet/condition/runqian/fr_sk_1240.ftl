<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Condition id="queryCondition" button=false button=false parameter="parameter" 
				size=3 cols=9 labelsize=1 ctrlsize=2
				style="width:1000px;margin-left:-30px;" class="pull-left"  >
	<@c.QueryComponent name="query_date" id="query_date" label="查询日期" type="date" exttype="_day" formula="setDateFormula('cur_day',-1)" format="yyyymmdd"/>
	<@c.FormComboBox name="exch" label="交易所" >
      	<@c.Option  value="-1" selected=true>沪深合计</@c.Option>
        <@c.Option  value="2" >沪深明细</@c.Option>
	</@c.FormComboBox>
	<@c.FormComboBox name="board" label="板块" >
      	<@c.Option  value="0" selected=true>各板块明细</@c.Option>
        <@c.Option  value="-1" >各板块合计</@c.Option>
	</@c.FormComboBox>
</@c.Condition>