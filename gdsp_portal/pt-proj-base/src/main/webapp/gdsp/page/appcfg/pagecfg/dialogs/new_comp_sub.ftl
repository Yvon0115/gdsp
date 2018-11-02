<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable titles=["名称","描述","默认风格","地址"] keys=["widget_name","widget_desc","default_style","widget_link"] data=pubcomp.content checkbox=true/>
<@c.PageData page=pubcomp />