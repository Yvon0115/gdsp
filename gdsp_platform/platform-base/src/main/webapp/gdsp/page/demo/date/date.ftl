<#import "/gdsp/tags/castle.ftl" as c>
<#-- 步骤1.引入js -->
<@c.Script id="compJscript"  src="${__jsPath}/plugins/comp.js" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
         	<@c.BoxHeader class="border ">
         		<button id="btnId" >更改例子2options</button>
         		<button id="btnId3" >更改例子2options</button>
         		<button id="btnId2" >获取列子2选中值</button>
         	</@c.BoxHeader>
            <@c.BoxHeader class="border ">
                                        例子1：日期控件：组合方式(label)，公式的使用
            <@c.QueryAction target="#queryCondition2" class="pull-right" >查询</@c.QueryAction>
            <#-- 注意Condition组件的 labelsize 与ctrlsize 属性  -->
            <#-- formula与value的关系：如果设置了value则formula不起作用 -->
			<@c.Condition id="queryCondition2" target="#noiticeVos2" action="${ContextPath}/plugins/date.d"  button=false class="pull-right" style="width:620px" labelsize=1 ctrlsize=3  parameter="parameter">
				<@c.QueryComponentDateRange disabled=true
						formatmap={"year":"yyyy","season":"yyyymm","month":"yyyymm","week":"yyyymmdd","day":"yyyymmdd"}
						id={"type":"typeId","begin":"beginId","end":"endId"}
						name={"type":"dateType","begin":"startDate","end":"endDate"}
						value={"type":"day","begin":"20160304","end":"20160308"}
						<#-- 日期公式  -->
						<#-- formula={"begin":"setDateFormula('cur_day',5)","end":"setDateFormula('cur_day',8)"} -->
						options={"":"","year":"年","season":"季","month":"月","week":"周","day":"日"}
						<#-- 0周日  1周一 ...   daysOfWeekEnabled="1"-->
						
						/>
			</@c.Condition>
           	</@c.BoxHeader>
           	<@c.BoxHeader class="border ">
                                         例子2：日期控件：组合方式(placeholder)
            <@c.QueryAction target="#queryCondition3" class="pull-right" >查询</@c.QueryAction>
            <#-- 注意Condition组件的 labelsize 与ctrlsize 属性  -->
			<@c.Condition id="queryCondition3" target="#noiticeVos3" button=false class="pull-right" style="width:620px" labelsize=0 ctrlsize=4  parameter="parameter">
				<@c.QueryComponentDateRange 
						id={"type":"typeId2","begin":"beginId2","end":"endId2"}
						name={"type":"dateType2","begin":"startDate2","end":"endDate2"}
						placeholder={"type":"频度","begin":"开始","end":"结束"} 
						formula={"begin":"_year:setDateFormula('cur_year',1),
									_season:setDateFormula('cur_season',-2),
									_month:setDateFormula('cur_month',3),
									_week:setDateFormula('cur_week',4),
									_day:setDateFormula('cur_week',5)"
								 ,
								 "end":"setDateFormula('cur_season',3)"}
						options={"":"","year":"年","season":"季","month":"月","week":"周","day":"日"}
						<#-- 0周日  1周一 ... -->
						daysOfWeekEnabled="5"
						/>
			</@c.Condition>
           	</@c.BoxHeader>
           	<@c.BoxHeader class="border ">
            	<#-- 例子1：集成QueryComponent  调用顺序1   -->
            	<#-- 	控件嵌套顺序1：QueryComponent->FormComboBox->FormItem->ComboBox-->
            	<#-- 	控件嵌套顺序1：QueryComponent->FormDate->FormItem->DateEditor-->
              	例子3： 日期控件：非组合方式 <@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
               <@c.Condition id="queryCondition" target="#noiticeVos" button=false class="pull-right" button=false style="width:600px"  parameter="parameter">
     	            <#-- 步骤2. 注意option的value值(_year:年,_month:月,_season:季,_week:周,_day:日)-->
     	            <#-- 步骤2. extpro属性为  日期组件INPUT域的ID逗号间隔  -->
     	            <@c.QueryComponent name="dateType" placeholder="频度" type="combobox" value="" extpro="start_date,end_date" events="{change:function(){changeDateView(this);}}" keymap={"year":"_year","season":"_season","month":"_month","week":"_week","day":"_day"}>
     	            	<@c.Option value=""></@c.Option>
     	            	<@c.Option value="year">年</@c.Option>
		                <@c.Option value="season">季</@c.Option>
			            <@c.Option value="month">月</@c.Option>
			            <@c.Option value="week">周</@c.Option>
			            <@c.Option value="day">日</@c.Option>
     	            </@c.QueryComponent>
		            <@c.QueryComponent name="start_date" id="start_date" placeholder="开始日期" type="date" exttype="_week"  value="2015-01-01" op="" />
		            <@c.QueryComponent name="end_date" id="end_date" placeholder="结束日期" type="date"   exttype="_week" format="yyyymmdd" daysOfWeekEnabled="5" value="" op="" />
                </@c.Condition>
            </@c.BoxHeader>
            <@c.BoxBody >
                 <@c.TableLoader id="noiticeVos2" url="${ContextPath}/plugins/date.d">
	                    
	             </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
		     </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" />  
      <@c.Tab  id="addUserPanel" />   
       <@c.Tab  id="addOrgPanel" />
       <@c.Tab  id="addUserGroupPanel" />
         
</@c.Tabs>
<script>
	$(function(){
		$("#btnId").click(function(){ 
			var options = {"year":"年1","season":"季1","month":"月1","week":"周1","day":"日1"};
			changeDateOptions("typeId2",options);
		});
		$("#btnId2").click(function(){ 
			alert($("[name='dateType2']").val());
		});
		$("#btnId3").click(function(){ 
			var options = {"month":"月3","week":"周3","day":"日3"};
			changeDateOptions("typeId2",options);
		});
	});
</script>