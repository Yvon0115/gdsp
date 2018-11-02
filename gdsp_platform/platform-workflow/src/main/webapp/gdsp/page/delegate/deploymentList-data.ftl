<#import "/gdsp/tags/castle.ftl" as c>

<@c.SimpleTable checkedfield="ischecked" data=deploymentAltcategory.content striped=false titles=["流程编号","流程名称","流程类型"] keys=["deploymentCode","deploymentName","categoryName"] checkbox=true/>
<@c.PageData page=deploymentAltcategory />