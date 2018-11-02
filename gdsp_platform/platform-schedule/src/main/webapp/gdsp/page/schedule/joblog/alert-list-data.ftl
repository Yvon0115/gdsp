<#--预警日志列表数据页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<#assign result>!<#noparse>
<#if row.result??&& row.result==1>
<@c.Link icon="fa fa-check" action=[c.opendlg("#logInfoDlg","${ContextPath}/schedule/joblog/logInf.d?id=${row.id}",true)]></@c.Link>
<#else>
<@c.Link icon="fa fa-close" action=[c.opendlg("#logInfoDlg","${ContextPath}/schedule/joblog/logInf.d?id=${row.id}",true)]></@c.Link>
</#if>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["预警类型","预警","开始时间", "结束时间","耗时(秒)","结果"] keys=["job_name","trigger_name","begintime","endtime","elapsedtime",result] ellipsis={"job_name":"175px","trigger_name":"175px"} data=logPages.content checkbox=true/>
<@c.PageData page=logPages />
