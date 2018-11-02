<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/datalicense/powerdic/deleteData.d?id=${row.id}",{"dataloader":"#powerDicContent"},{"confirm":"确认删除关联 ？"})]>删除</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=true  titles=["类型名称","类型编码","类型描述",""] keys=["dataDicVO.dic_name","dataDicVO.dic_code","dataDicVO.dic_desc",op] ellipsis={"dataDicVO.dic_name":"180px","dataDicVO.dic_code":"180px","dataDicVO.dic_desc":"220px"} data=dataSource?default({"content":[]}).content checkbox=true/>
<@c.PageData page=dataSource?default("") />
