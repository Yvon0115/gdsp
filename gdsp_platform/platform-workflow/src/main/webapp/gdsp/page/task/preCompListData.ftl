<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=false titles=["节点名称","办理人","办理结果","附加意见"] keys=["actName","userId","result","options"] data=hdetail?if_exists.content?default([])/>
<@c.PageData page=hdetail?default("") />