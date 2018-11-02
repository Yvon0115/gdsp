<#import "/gdsp/tags/castle.ftl" as c>
<style>
	#orgname {
		margin-left:10px;
		margin-top:15px;
	}
	#changeRoleList{
		margin-top:-10px;
	}
	.list_A {
		overflow:hidden;
		vertical-align:bottom;
		text-overflow:ellipsis;
		cursor:pointer;
		font-size:14px;
		color:#383737;
		font-weight:100;
		line-height:25px;
		font-family:'microsoft yaHei';
		margin-top:5px;
		background-color:#ffffff;
		display:block;
		width: 100%;
		padding-left: 20px;
	}
	.list_A:hover{
    color:#383737;
}
</style>
 <@c.FormRef  name="orgname" value="${orgVO?if_exists.id?if_exists}" showValue="${orgVO?if_exists.orgname?if_exists}" url="${ContextPath}/power/datalicense/toOrgTreePage.d" />
 <hr style="background-color:#e2e2e2;width:300px;height:1px;border:none;margin-left:-10px"> </hr>
<div><p style="margin-top:-15px;margin-left:10px;">角色名称</p></div>
 <div id="changeRoleList">
 <#list roleList as list> 
    <a class="list_A"  value="${list.id?if_exists}" id="${list.id?if_exists}" name="roleArea" >${list.rolename?if_exists}</a>
 </#list>
 </div>
