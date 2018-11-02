<#import "/gdsp/tags/castle.ftl" as c>
<#-- 已弃用的页面 -->
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title">添加关联机构</h3>
    </@c.BoxHeader>
    <@c.TableLoader id="roleOrgContent">
	    <@c.BoxBody>
			<@c.SimpleTable striped=false titles=["机构编码","机构名称","机构简称","上级机构","描述"] keys=["orgcode","orgname","shortname","pk_fatherorg","memo"] data=roleOrgs.content checkbox=true/>
			<@c.PageData page=roleOrgs />
	    </@c.BoxBody>
    </@c.TableLoader>

    <@c.BoxFooter class="text-center">
        <@c.Button type="primary" icon="fa fa-save" action=[c.rpc("${ContextPath}/grant/role/addOrgToRole.d?roleId=${id}",{"switchtab":"#roleOrgListPanel","dataloader":"#roleListContent2"},{"checker":["id","#roleOrgContent"],"confirm":"确认添加关联机构？"}) ]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#roleOrgListPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>
