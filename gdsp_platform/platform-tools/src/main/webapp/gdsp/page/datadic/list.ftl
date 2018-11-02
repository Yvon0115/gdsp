<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/conf/datadic/datadic.js"/>-->
<@c.Script src="script/conf/datadic/datadic" />
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
		<@c.Box>
			<@c.BoxHeader class="border header-bg">
				<@c.Button type="primary" size="sm" icon="glyphicon glyphicon-plus" action=[c.opentab("#detail","${ContextPath}/conf/datadic/addDim.d")]>添加数据</@c.Button>
				<@c.Button  size="sm" icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/conf/datadic/deleteDim.d",{"dataloader":"#diclistContent"},{"checker":["id","#diclistContent"],"confirm":"确认删除选中数据？"})]>删除</@c.Button>
				<@c.Search class="pull-right"  target="#diclistContent" conditions="dic_name" placeholder="类型名称"/>
			</@c.BoxHeader>
			<@c.BoxBody>
				<@c.TableLoader id="diclistContent" url="${ContextPath}/conf/datadic/reloadList.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
			</@c.BoxBody>
			<@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#diclistContent" page=dataDicVO?default(null)/><!-- 分页 -->
            </@c.BoxFooter>
		</@c.Box>
	</@c.Tab>
	<@c.Tab id="detail">

	</@c.Tab>
</@c.Tabs>