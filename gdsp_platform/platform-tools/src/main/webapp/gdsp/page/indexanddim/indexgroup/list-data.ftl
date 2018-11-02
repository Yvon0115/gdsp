<#import "/gdsp/tags/castle.ftl" as c>

<#assign op>!<#noparse>


	<@c.Link title="删除指标" icon="glyphicon glyphicon-remove"action=[c.rpc("${ContextPath}/indexanddim/indexgroup/deleteRelation.d?id=${row.id}",{"dataloader":"#indsContent"},{"confirm":"确认删除该指标？"})] >删除</@c.Link>

</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["指标编码","指标名称","所属指标组","操作"] keys=["indexInfoVO.indexCode","indexInfoVO.indexName","indTreeVO.groupName",op] ellipsis={"indexInfoVO.indexCode":"80px","indexInfoVO.indexName":"250px","indTreeVO.groupName":"100px"} data=inds?default({"content":[]}).content checkbox=true/>
<@c.PageData page=inds?default("")/>
 <@c.Hidden name="groupId" value=groupId/>