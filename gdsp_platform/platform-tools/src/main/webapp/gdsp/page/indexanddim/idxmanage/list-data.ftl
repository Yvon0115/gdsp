<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/index/indexmanager/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/index/indexmanager/delete.d?id=${row.id}",{"dataloader":"#indexListContent"},{"confirm":"确认删除指标‘${row.indexName?if_exists}’？"})]>删除</@c.Link></li>
	<#--去除展示详情功能(只是暂时去除功能键，功能保留)-->
	<#--<li><@c.Link title="详情" icon="glyphicon glyphicon-share" action=[c.opentab("#detailPanel","${ContextPath}/index/indexmanager/findDetailIdx.d?id=${row.id}")]>详情</@c.Link></li>-->
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["指标编码","指标名称","业务口径",""] 
	keys=["indexCode","indexName","businessbore",op] 
	ellipsis={"indexCode":"80px","indexName":"80px","businessbore":"200px"} data=indexlist.content checkbox=true/>
<@c.PageData page=indexlist />