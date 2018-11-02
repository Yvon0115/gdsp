<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["<th width='200px'>访问名称</th>","<th width='120px'>类型</th>","<th width='60px'>统计</th>"] keys=['name','type','cnt'] data=top10Vos />
