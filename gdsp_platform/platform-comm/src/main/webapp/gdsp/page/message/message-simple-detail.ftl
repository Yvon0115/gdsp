<#-- 简版消息详情 -->
<#import "/gdsp/tags/castle.ftl" as c>
<modal-title><h3 class="box-title">${messageVO?if_exists.subject?if_exists}</h3></modal-title>
<div class="modal-body " id="modalBodyId">
	<@c.Box>
	<@c.BoxHeader class="border">
       	 发布时间：
        ${messageVO?if_exists.transtime?if_exists}
    </@c.BoxHeader>
    <@c.BoxBody>
    ${messageVO?if_exists.content?if_exists}
    </@c.BoxBody>
</@c.Box>
</div>
<div class="modal-footer">
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.attrs({"data-dismiss":"modal"})]>关闭</@c.Button>
</div>
		

