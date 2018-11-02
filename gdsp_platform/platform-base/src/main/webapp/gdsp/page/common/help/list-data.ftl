<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableLinks>
<!--<li><@c.Link title="编辑附件" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/sysnotice/edit.d?id=${row.id}")]>编辑</@c.Link></li>-->
<#if usertype==0>
<@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/help/delete.d?id=${row.id}",{"dataloader":"#noiticeVos"},{"confirm":"确认删除‘${row.title?if_exists}’？"})]>删除</@c.Link>
</#if>
<@c.Link title="下载" icon="glyphicon glyphicon-download" click="downloadAttachment('${row.id}')">下载</@c.Link>
</@c.TableLinks>
</#noparse>
</#assign>
<@c.SimpleTable striped=false titles=["标题","操作"] keys=["title",op] checkbox=true data=noticeVoPages.content/>
<@c.PageData page=noticeVoPages />