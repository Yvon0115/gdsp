<#import "/gdsp/tags/castle.ftl" as c>
<@c.SimpleTable striped=true titles=["\t","用户名","账号","所属机构","数据维度","数据维度值"] keys=["\t","username","account","orgname","dic_name","dimvl_name"]  data=UserDataLimitPages?default({"content":[]}).content />
<@c.PageData page=UserDataLimitPages?default("") />


