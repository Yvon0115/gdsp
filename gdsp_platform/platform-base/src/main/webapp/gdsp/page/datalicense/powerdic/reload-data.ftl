<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=false titles=["类型名称","类型编码","类型描述"] keys=["dic_name","dic_code","dic_desc"] data=dataDicVO?default({"content":[]}).content checkbox=true/>
<@c.PageData page=dataDicVO?default("") />