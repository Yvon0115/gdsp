<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Condition id="queryCondition" button=false button=false parameter="parameter" 
				size=3 cols=12 labelsize=1 ctrlsize=2
				style="width:1000px;margin-left:-30px;" class="pull-left"  >
	<@c.FormGroup cols=4>			
		<@c.FormComboBox id="query_object" name="object" label="查询对象" >
		        <@c.Option  value="0" selected=true>运行概况</@c.Option>
		        <@c.Option  value="1">每日运行情况</@c.Option>
		</@c.FormComboBox>
		<@c.QueryComponent label="开始日期" name="start_date" id="start_date" placeholder="开始日期" type="date" exttype="_day"  format="yyyymmdd"  formula="setDateFormula('cur_day',-10)" endDate="end_date"/>
		<@c.QueryComponent label="结束日期" name="end_date" id="end_date" placeholder="结束日期" type="date"   exttype="_day" format="yyyymmdd"  formula="setDateFormula('cur_day',-1)" startDate="start_date"/>
	</@c.FormGroup>
</@c.Condition>
<div class="scrollbar"  style="width:50%;max-height:200px;">
	<label class="col-md-2 col-sm-2"   style="margin-left: -15px;margin-top:20px;font-weight:normal;">指数</label>
    <div class="col-md-10 col-sm-10" style="margin-left: -30px;">
	<@c.SimpleTree id="indexTree${__currentPortlet.id}" expand="-1" checkbox=true >
		     <@c.TreeMapLoader nameField="idx_name" idField="idx_cde" valueField="idx_cde" initParm="loaderIndexDic" titleShowWidth="200px" nexp="ckbox='true' ">
		     </@c.TreeMapLoader>
	</@c.SimpleTree>
	</div>
</div>
