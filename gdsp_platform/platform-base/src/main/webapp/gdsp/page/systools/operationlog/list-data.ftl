<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>!<#noparse>
<#--<@c.Link title="详细信息" icon="" action=[c.opentab("#detailPanel","${ContextPath}/systools/operationlog/toDetailInfo.d?id=${row.id}")]>详细信息</@c.Link>-->
<@c.Link title="详细信息" icon=""  action=[c.opendlg("#operationDlg","${ContextPath}/systools/operationlog/toDetailInfo.d?id=${row.id}","400","600",true)]>详细信息</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["\t","类型","表","表名","\t","操作人","时间","操作"] keys=["\t","type","table_name","table_desc","\t","username","createTime",op] ellipsis={"type":"80px","table_name":"150px","table_desc":"150px","username":"150px","createTime":"150px"} data=OperationLogPages?default({"content":[]}).content />
<@c.PageData page=OperationLogPages?default("") />
<@c.Hidden name="id" value=id/>


