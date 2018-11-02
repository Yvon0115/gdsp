<#import "/gdsp/tags/castle.ftl" as c>
<modal-title><h3 class="box-title">${sysNoticeVo?if_exists.title?if_exists}</h3></modal-title>
<div class="modal-body " id="modalBodyId">
	<@c.Box>
	<@c.BoxHeader class="border">
       	 发布时间：
        ${sysNoticeVo?if_exists.publish_date?if_exists}
    </@c.BoxHeader>
    <@c.BoxBody>
    ${sysNoticeVo?if_exists.content?if_exists}
    </@c.BoxBody>
</@c.Box>
</div>
<div class="modal-footer">
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>关闭</@c.Button>
</div>
		

