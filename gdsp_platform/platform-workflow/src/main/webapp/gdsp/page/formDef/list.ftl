<#--单据注册列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/wf_formDef" />
<@c.Hidden name="pk_categoryid" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
		<@c.Box class="col-md-3 no-padding">
			<@c.BoxHeader class="border header-bg">
				<@c.Button type="primary" icon="glyphicon glyphicon" action=[c.opentab("#categoryPnl","${ContextPath}/workflow/category/list.d")]>分类管理</@c.Button>
			</@c.BoxHeader>
			<@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
				<#--<@c.TableLoader id="formDefTreeContent" url="${ContextPath}/workflow/formdef/listTreeData.d">-->
					<#include  "formDefTree.ftl">
				<#--</@c.TableLoader>-->
			</@c.BoxBody>
		</@c.Box>
        <@c.Box class="col-md-9 no-padding">
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" click="addCategory()" >新增</@c.Button>
                <@c.Button icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/workflow/formdef/delete.d",{"dataloader":"#formDefContent"},{"checker":["id","#formDefContent"],"confirm":"确认删除选中项？"})]>删除</@c.Button>
                <@c.Search class="pull-right" target="#formDefContent" placeholder="模糊查询" conditions="formCode,formName,formURL"/>
            </@c.BoxHeader>
            
            <@c.BoxBody>
                <@c.TableLoader id="formDefContent" url="${ContextPath}/workflow/formdef/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
			 	<@c.Pagination class="pull-right" target="#formDefContent" page=formDefs?default("")/>
			 </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
	<@c.Tab  id="categoryPnl" >
    </@c.Tab>
</@c.Tabs>