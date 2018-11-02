<#--任务管理页面 - 查看 - 接受者对话框页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/schedule/jobdef_deploy.js"/>-->
<@c.Script src="script/schedule/jobdef_deploy" />
<modal-title>接收者</modal-title>
<div class="modal-body autoscroll">
	<@c.TableLoader id="receiverContent" url="${ContextPath}/schedule/jobdef/receiverListData.d">
		<@c.SimpleTable id="receiverUserTable" striped=false titles=["账号","姓名","机构","手机","邮箱"] keys=["account","username","orgname","mobile","email"] data=users checkbox=false/>
	</@c.TableLoader>
</div>
<div class="modal-footer">
<@c.Hidden name="receivers" id="receivers" value="${receivers?if_exists}"/>
<@c.Hidden name="receiveridscode" id="receiveridscode" value="${receiveridscode?if_exists}"/>
<@c.Button type="canel" icon="glyphicon glyphicon-arrow-left" action=[c.dismiss()]>返回</@c.Button>
</div>

