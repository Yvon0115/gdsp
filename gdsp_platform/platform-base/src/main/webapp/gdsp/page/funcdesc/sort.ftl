<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>排序</modal-title>
<div class="modal-body" id="modalBodyId">
	<@c.GRow>
		<@c.GCell cols=9>
			<div class="panel panel-default">
				<div class=" scrollbar">
					<@c.Form id="sortForm" class="validate" action="${ContextPath}/portal/functionDec/doSort.d" method="post" after={"dataloader":"#funcFileContent"}>
						<table class="table scrollbar" id="sort_table">
							<tbody>
								<#if datas??>
									<#list datas as data>
										<tr>
											<td width="10px">
												<i class="fa fa-file-text-o"></i>
											</td>
											<td>${data.itemDesc}</td>
											<@c.Hidden name="id" id="id_${data_index}" value="${data.id}" />
										</tr>
									</#list>
								</#if>
							</tbody>
						</table>
					</@c.Form>
				</div>
			</div>
		</@c.GCell>
		<@c.GCell cols=3>
			<div style="margin-top: 110px;">
				<@c.Button type="primary" size="sm" class="btn-block" icon="fa fa-angle-double-up fa-lg" click="move_top()"></@c.Button>
				<@c.Button type="primary" size="sm" class="btn-block" icon="fa fa-angle-up fa-lg" click="move_up()"></@c.Button>
				<@c.Button type="primary" size="sm" class="btn-block" icon="fa fa-angle-down fa-lg" click="move_down()"></@c.Button>
				<@c.Button type="primary" size="sm" class="btn-block" icon="fa fa-angle-double-down fa-lg" click="move_bottom()"></@c.Button>
			</div>
		</@c.GCell>
	</@c.GRow>
</div>
<div class="modal-footer">
	<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#sortForm")]>保存</@c.Button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
</div>
		
