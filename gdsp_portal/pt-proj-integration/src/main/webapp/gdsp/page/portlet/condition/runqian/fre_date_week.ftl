<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />

<@c.Condition id="queryCondition" button=false button=false parameter="parameter" 
				size=3 cols=6 labelsize=1 ctrlsize=2
				style="width:1000px;margin-left:-60px;" class="pull-left"  >
			<#-- 频度 -->
			 <@c.QueryComponent name="freq" label="频度" type="combobox" value="W" extpro="query_date,end_date" events="{change:function(){changeDateView(this);}}" keymap={"year":"_year","Q":"_season","M":"_month","W":"_week","day":"_day"} formatmap={"year":"yyyy","Q":"yyyymm","M":"yyyymm","W":"yyyymmdd","day":"yyyymmdd"}>
		       <@c.Option value="W" selected=true>周</@c.Option>
		       <@c.Option value="M">月</@c.Option>
		       <@c.Option value="Q">季</@c.Option>
		     </@c.QueryComponent>
     	    <#-- 开始日期 -->
		    <@c.QueryComponent name="query_date" id="query_date" label="查询日期" type="date" exttype="_week" formula="setDateFormula('cur_week',-1)" format="yyyymmdd" daysOfWeekEnabled="1"/>
</@c.Condition>
