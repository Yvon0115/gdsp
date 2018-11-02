<#import "/gdsp/tags/castle.ftl" as c>
<#-- 系统工具 - 公告管理主页面 -->
<@c.Script src="${__jsPath}/plugins/comp.js" />
<@c.Script src="script/sysnotice/simple-list" onload="initSysNoticePlaceholder()" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                    <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/sysnotice/add.d")]>添加公告</@c.Button>
                    <@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/sysnotice/delete.d",{"dataloader":"#noiticeVos"},{"checker":["id","#noiticeVos"],"confirm":"确认删除选中公告？"})]>删除公告</@c.Button>
                    <@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
                 <@c.Condition id="queryCondition" target="#noiticeVos" isvalid=true button=false class="pull-right" button=false style="width:50%"  parameter="parameter">
     	                <@c.QueryComponent id="sysTime" name="typeSys"  type="combobox" value="" op="">
     	                   <@c.Option value=""></@c.Option>
			                <@c.Option value="0">今天</@c.Option>
		                    <@c.Option value="1">昨天</@c.Option>
			                <@c.Option value="2">近一周</@c.Option>
			                 <@c.Option value="3" selected=true>近一月</@c.Option>
			                  <@c.Option value="4">自由查询</@c.Option>
		                </@c.QueryComponent>
		                <@c.QueryComponent id="sys_start_date" name="p_start_date"  type="date" value="" op="" validation={"lessEqual":"#sys_end_date"} messages={"lessEqual":"请输入不大于结束日期 {0} 的值"}/>
		                <@c.QueryComponent id="sys_end_date" name="p_end_date"  type="date" value="" op="" validation={"greaterEqual":"#sys_start_date"} messages={"greaterEqual":"请输入不小于开始日期 {0} 的值"}/>
                </@c.Condition>
                
            </@c.BoxHeader>
            <@c.BoxBody >
                <@c.TableLoader id="noiticeVos" url="${ContextPath}/sysnotice/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
			        <@c.Pagination class="pull-right" target="#noiticeVos" page=noticeVoPages?default("")/>
		     </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" />  
      <@c.Tab  id="addUserPanel" />   
       <@c.Tab  id="addOrgPanel" />
       <@c.Tab  id="addUserGroupPanel" />
         
</@c.Tabs>
