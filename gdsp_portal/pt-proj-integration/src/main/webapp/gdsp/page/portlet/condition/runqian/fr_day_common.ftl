<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Condition id="queryCondition" button=false button=false parameter="parameter" 
				size=3 cols=6 labelsize=1 ctrlsize=2
				style="width:1000px;margin-left:-30px;" class="pull-left"  >
<@c.QueryComponent name="query_date" id="query_date" label="查询日期" type="date" exttype="_day" formula="setDateFormula('cur_day',-1)" format="yyyymmdd"/>
</@c.Condition>