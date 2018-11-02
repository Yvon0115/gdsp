<#import "/gdsp/tags/castle.ftl" as c>

<@c.SimpleTable striped=true titles=["节点名称","办理人","办理结果","附加意见","办理时间"] keys=["actName","userId","result","options","createTime"] data=phdetail.content/>
<@c.PageData page=phdetail />