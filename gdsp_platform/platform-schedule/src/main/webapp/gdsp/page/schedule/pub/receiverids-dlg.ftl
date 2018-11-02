<#--接受者对话框页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/schedule/jobdef_deploy.js"/>-->
<@c.Script src="script/schedule/jobdef_deploy" />
<modal-title>设置接收者</modal-title>
<div class="modal-body">
<@c.Tabs style="height:100%; autoscroll">
    <@c.Tab  id="userListPanel" active=true events="{'show.bs.tab':function(){onChangeShowReceverPanel(true);}}">
    	<@c.Box>
    	<@c.BoxHeader>
    		<@c.Button type="primary" class="mainHeader1" icon="glyphicon glyphicon-plus"  click="addReceiversUser()">添加用户</@c.Button>
			<@c.Button class="mainHeader2" icon="glyphicon glyphicon-trash" click="deleteUsersFromReceivers()">删除</@c.Button>
			<@c.QueryAction target="#queryCondition" class="pull-right" >查询</@c.QueryAction>
          		<@c.Condition id="queryCondition"  target="#receiverContent" class="pull-right" button=false style="width:300px;" cols=2 ctrlsize=6>
		                <@c.QueryComponent name="account" placeholder="账号" type="text" />
		                <@c.QueryComponent name="username" placeholder="姓名" type="text" />
	             </@c.Condition>
    	</@c.BoxHeader>
		<@c.TableLoader id="receiverContent" url="${ContextPath}/schedule/jobdef/receiverListData.d">
			<#include "userlist-data.ftl">
		</@c.TableLoader>
		<@c.Hidden name="receivers" id="receivers" value="${receivers?if_exists}"/>
		<@c.Hidden name="receiveridscode" id="receiveridscode" value="${receiveridscode?if_exists}"/>
		</@c.Box>
    </@c.Tab>
    <@c.Tab  id="addUserPanel" events="{'show.bs.tab':function(){onChangeShowReceverPanel(false);}}">
    </@c.Tab>
</@c.Tabs>
</div>
<div id="mainFooter" class="modal-footer">
	<Button type="button" class="btn btn-primary"  click="savaRecerversInf()" data-dismiss="modal"><i class="fa fa-save"></i>保存</Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
<div id="detailFooter" class="modal-footer" style="display: none;">
	<@c.Button type="primary" icon="fa fa-save" click="addUserToReceivers()">确定</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#userListPanel")]>取消</@c.Button>
</div>

