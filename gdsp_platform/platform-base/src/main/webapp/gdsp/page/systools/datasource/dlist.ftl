<#import "/gdsp/tags/castle.ftl" as c>
<#-- 数据源管理初始化页面 -->
<@c.Script src="script/datasource/datasource" />
<@c.Tabs>
	<@c.Tab  id="mainPanel" active=true title="">
		<@c.Box class="col-md-3 box-left no-padding scrollbar" >
			<@c.BoxHeader class="border header-bg scrollbar" style="height:37px;">
				<h3 class="box-title">数据源类型</h3>
			</@c.BoxHeader>
			<#include "typeTree.ftl">
		</@c.Box>
		<@c.Box class="box-right col-md-9 no-padding">
        	<@c.BoxHeader class="border header-bg"><#-- action=[c.opentab("#detailPanel","${ContextPath}/systools/ds/toSava.d")] -->
        	    <@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus" click="loadForm()" >添加</@c.Button>
        	    <@c.Button size="sm" icon="glyphicon glyphicon-trash " click="deleteDs()" <#--action=[c.rpc("${ContextPath}/systools/ds/delete.d",{"dataloader":"#usersContent"},{"checker":["id","#usersContent"],"confirm":"确认删除选中数据？"})]-->>删除</@c.Button>
                <@c.Hidden name="pk_org" value=pk_org/>
                <@c.Search class="pull-right"  target="#datasourceContent" conditions="name" placeholder="数据源名称"/>
            </@c.BoxHeader>
            <@c.BoxBody >
                <@c.TableLoader id="datasourceContent" url="${ContextPath}/systools/ds/reloadList.d">
                    <#include "dlist-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
             <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#datasourceContent" page=dateSourceVO/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
</@c.Tabs>
