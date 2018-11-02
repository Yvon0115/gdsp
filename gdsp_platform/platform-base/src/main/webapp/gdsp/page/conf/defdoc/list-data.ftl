<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<@c.Link title="修改数据信息" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel1","${ContextPath}/conf/defdoc/toEdit.d?id=${row.id}&type_id=${type_id?if_exists?html}")]>修改</@c.Link>
<#--  <@c.Link title="删除数据" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/conf/defdoc/delete.d?id=${row.id}",{"dataloader":"#docContents"},{"confirm":"确认删除数据 ‘${row.doc_name?if_exists}’？"})]>删除</@c.Link>-->
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["<th width=150px>名称</th>","<th width=150px>编码</th>","<th width=200px>描述</th>","<th width=150px>上级名称","<th width=150px>上级编码</th>",""] keys=["doc_name","doc_code","doc_desc","parentName","parentCode",op] 
ellipsis={"doc_name":"150px","doc_code":"150px","doc_desc":"200px","parentName":"150px","parentCode":"150px"}
data=defDocVO.content checkbox=true/>
<@c.PageData page=defDocVO />