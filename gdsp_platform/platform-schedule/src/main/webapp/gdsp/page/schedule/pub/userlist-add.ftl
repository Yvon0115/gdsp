<#import "/gdsp/tags/castle.ftl" as c>
	<@c.Search class="pull-right"  target="#addUserContent" conditions="account,username" placeholder="账号/姓名" />
	<@c.TableLoader id="addUserContent" url="${ContextPath}/schedule/jobdef/selUserListData.d?receivers="+"${receivers?if_exists}">  
		<#include "userlist-add-data.ftl">
		<#-- 
		<@c.SimpleTable id="receiverAddUser" striped=false checkbox=true
			titles=["账号","姓名","机构","手机","邮箱"] 
			keys=["account","username","orgname","mobile","email"] 
			ellipsis={"account":"100px","username":"84px","email":"100px","orgname":"140px"}  
			data=users />
		-->
	</@c.TableLoader>

<@c.Hidden name="receivers" id="receivers" value="${receivers?if_exists}"/>