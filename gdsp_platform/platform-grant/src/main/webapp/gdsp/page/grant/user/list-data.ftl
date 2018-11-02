<#--用户列表界面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
    <li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/grant/user/edit.d?id=${row.id}")]>修改</@c.Link></li>
    <#if row.isdisabled??&& row.isdisabled=="Y">
    <li><@c.Link title="启用" icon="glyphicon glyphicon-open" click="enable('${row.id}')" >启用</@c.Link></li>
    <#else>
    <li><@c.Link title="停用" icon="glyphicon glyphicon-lock" click="disable('${row.id}')" >停用</@c.Link></li>
    </#if>
    <#if row.islocked??&& row.islocked=="Y">
    <li><@c.Link title="解锁" icon="glyphicon glyphicon-open" click="unlockUser('${row.id}')" >解锁</@c.Link></li>
	<#else>
	</#if>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/user/delete.d?id=${row.id}",{"dataloader":"#usersContent"},{"confirm":"确认删除用户‘${row.username?if_exists}’？"})]>删除</@c.Link></li>
	<li><@c.Link title="授权" icon="glyphicon glyphicon-share" action=[c.opentab("#detailPanel","${ContextPath}/grant/user/loadRoles.d?userId=${row.id?if_exists}")]>授权</@c.Link></li>
	<li><@c.Link title="机构变更" icon="glyphicon glyphicon-transfer" action=[c.opendlg("#transDlg","${ContextPath}/grant/user/transOrg.d?userId=${row.id}",true)]>机构变更</@c.Link></li>
	<li><@c.Link title="密码重置" icon="glyphicon glyphicon-adjust" action=[c.opendlg("#passwordDlg","${ContextPath}/grant/user/resetPassword.d?userId=${row.id}","","800px",true)]>密码重置</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<#assign isDisabled>!<#noparse>
<#if row.isdisabled??&& row.isdisabled=="Y">停用<#else>启用</#if>
</#noparse>
</#assign>
<#assign isLocked>!<#noparse>
<#if row.islocked??&&row.islocked=="Y">是<#else>否</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["账号","姓名","手机","邮箱","机构名称","锁定","状态",""] keys=["account","username","mobile","email","orgname",isLocked,isDisabled,op] ellipsis={"account":"70px","username":"70px","email":"70px","orgname":"50px"} data=(userPage.content)?default([]) checkbox=true/>
<@c.Hidden name="orgID" value=orgID/>
<@c.PageData page=userPage />