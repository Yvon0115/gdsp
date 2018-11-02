<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>执行情况</modal-title>
<div class="modal-body autoscroll">
	<@c.Form id="passwordForm" class="validate" action="" method="post" after={"pageload":{}}>
	   <@c.FormIdVersion id="${logVO?if_exists.id?if_exists}" version="${logVO?if_exists.version?default(0)}"/>
	   ${logVO?if_exists.memo?if_exists}
  	</@c.Form>
</div>
<div class="modal-footer">
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>关闭</@c.Button>
</div>
