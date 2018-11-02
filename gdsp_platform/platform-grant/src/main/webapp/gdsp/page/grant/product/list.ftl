<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/grant/product"/>
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
    <div class="col-md-3 no-padding">
    		<@c.Box  class="box-left">
				<@c.BoxBody class="no-padding scrollbar" style="min-height:200px;">
					<#include  "sup-tree.ftl"> 
				</@c.BoxBody>
			</@c.Box>
			</div>
			<div class="col-md-9 no-padding">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" click="addProduct()">添加商品</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/grant/product/delete.d",{"dataloader":"#productPageContent"},{"checker":["id","#productPageContent"],"confirm":"确认删除选中用户组？"})]>删除</@c.Button>
                <@c.Search class="pull-right"  target="#productPageContent" conditions="name,price" placeholder="姓名/价格"/><!-- 用户组搜索框 -->
            </@c.BoxHeader>
            <@c.BoxBody>
                <@c.TableLoader id="productPageContent" url="${ContextPath}/grant/product/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
                    <@c.Pagination class="pull-right" target="#productPageContent" page=productPage/><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
        </div>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
    
</@c.Tabs>
