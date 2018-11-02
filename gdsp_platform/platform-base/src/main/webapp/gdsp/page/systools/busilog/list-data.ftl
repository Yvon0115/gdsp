<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["\t","编号","名称","类型","访问用户", "访问时间"] keys=["\t","funcode","funname","type","username","createTime"] data=ResAccessPages?if_exists.content![] />
<@c.PageData page=ResAccessPages!"" />
