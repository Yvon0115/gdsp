<#import "/gdsp/tags/castle.ftl" as c>
<h4 class="box-title" style="margin-left:10px;">${sysNoticeVo?if_exists.title?if_exists}</h4>
<@c.Box style="min-height:500px;">
    <@c.BoxHeader class="border" style="line-height:33px;">
                  发布时间：${sysNoticeVo?if_exists.publish_date?if_exists}
    </@c.BoxHeader>
    <@c.BoxBody class="scrollbar" style="height:435px;">
    <div style="white-space: pre;" id="msg" style="margin-left:10px;">${sysNoticeVo?if_exists.content?if_exists}</div>
    </@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button class="pull-right" icon="glyphicon glyphicon-arrow-left"   action=[c.opentab("#mainNoticeDlgPanel"),c.dataloader("#NoticeContent")] >返回</@c.Button>
    </@c.BoxFooter>
</@c.Box>
<@c.Script src="script/sysnotice/simple-list" onload="openNewTab()" />
