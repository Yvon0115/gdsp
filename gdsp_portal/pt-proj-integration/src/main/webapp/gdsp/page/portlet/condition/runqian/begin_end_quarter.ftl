<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Condition id="queryCondition" button=false button=false parameter="parameter" 
				size=3 cols=6 labelsize=1 ctrlsize=2
				style="width:1000px;margin-left:-30px;" class="pull-left"  >
	<@c.QueryComponent label="开始日期" name="start_date" id="start_date" placeholder="开始日期" type="date" exttype="_season"  format="yyyymm"  formula="setDateFormula('cur_season',-12)" endDate="end_date"/>
	<@c.QueryComponent label="结束日期" name="end_date" id="end_date" placeholder="结束日期" type="date"   exttype="_season" format="yyyymm"  formula="setDateFormula('cur_season',-1)" startDate="start_date"/>
</@c.Condition>