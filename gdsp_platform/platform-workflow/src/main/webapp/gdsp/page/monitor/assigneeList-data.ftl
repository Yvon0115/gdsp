<#import "/gdsp/tags/castle.ftl" as c>
	<@c.SimpleTable striped=true titles=["\t","账号","姓名"] keys=["\t","account","username"] data=users.content />
<@c.PageData page=users />