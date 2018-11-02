<#import "/gdsp/tags/castle.ftl" as c>
<@c.BoxBody>
    <@c.Form id="leaveForm" class="validate" cols=1 action="${ContextPath}/workflow/leave/save.d" method="post" after={"switchtab":"#mainPanel"} help=false>
        <@c.FormIdVersion id="${leave?if_exists.id?if_exists}" version="${leave?if_exists.version?default(0)}"/>
        <@c.FormDate disabled=true type="datetime" name="startTime" label="开始时间" value="${leave?if_exists.startTime?if_exists}" />
        <@c.FormDate disabled=true type="datetime" name="endTime" label="结束时间" value="${leave?if_exists.endTime?if_exists}" />
        <@c.FormInput readonly=true name="phone" label="联系电话" value="${leave?if_exists.phone?if_exists}" messages={"required":"联系电话"}/>
        <@c.FormInput readonly=true name="leaveDay" label="请假天数" value="${leave?if_exists.leaveDay?if_exists}" messages={"required":"请假天数"}/>       
        <@c.FormInput readonly=true type="hidden" name="deploymentid" value="${deploymentid?if_exists}" />
        <@c.FormComboBox disabled=true  name="leaveType" label="请假类型" value="${leave?if_exists.leaveType?if_exists}">
            <@c.Option value="1">事假</@c.Option>
            <@c.Option value="2">病假</@c.Option>
            <@c.Option value="3">带薪休假</@c.Option>
        </@c.FormComboBox>
        <@c.FormText  readonly=true name="reason" label="请假事由" colspan=2>${leave?if_exists.reason?if_exists}</@c.FormText>
    </@c.Form>
</@c.BoxBody>