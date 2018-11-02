<#import "/gdsp/tags/castle.ftl" as c>
<div style="margin-top:-5px;margin-left:-15px;margin-right:-15px;">
<@c.SimpleTable striped=true titles=["\t","字段","名称","旧值","新值"] keys=["\t","col_name","col_desc","old_data","new_data"] ellipsis={"old_data":"84px","new_data":"84px"} data=DetailOpLogPages />
<#--<@c.PageData page=DetailOpLogPages?default("") />-->
</div>


