<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=false titles=["维度名称","维度字段名称","维度说明"] keys=["dimname","dimfieldname","dimmemo"]  data=noDimList?default({"content":[]}).content checkbox=true/>
<@c.PageData page=noDimList?default("")/>