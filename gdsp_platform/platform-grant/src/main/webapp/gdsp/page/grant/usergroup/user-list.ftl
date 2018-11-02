<#import "/gdsp/tags/castle.ftl" as c>
<@c.Tabs ulclass="header-bg">
    <@c.Tab  id="userListPanel" active=true title="用户组关联用户">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus-sign" action=[c.opentab("#addUserPanel","${ContextPath}/grant/usergroup/addGroupUser.d?groupId=${groupId?if_exists?html}")]>添加关联用户</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/grant/usergroup/deleteGroupUser.d",{"dataloader":"#userListContent"},{"checker":["id","#userListContent"],"confirm":"确认删除选中关联用户？"})]>删除</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-arrow-left" action=[c.opentab("#mainPanel")]>返回</@c.Button>
                <@c.Search class="pull-right"  target="#userListContent" conditions="username,account" placeholder="账号/姓名" />
            </@c.BoxHeader>
            <@c.BoxBody>
				<@c.TableLoader id="userListContent" url="${ContextPath}/grant/usergroup/userListData.d?groupId=${groupId?if_exists?html}">
                    <#include "userlist-data.ftl">
				</@c.TableLoader>
            </@c.BoxBody>
            <@c.BoxFooter>
		        <@c.Pagination class="pull-right"  target="#userListContent" page=userList?default("")/>
	        </@c.BoxFooter>   
        </@c.Box>
	</@c.Tab>
	<@c.Tab  id="addUserPanel" >
	</@c.Tab>
</@c.Tabs>
