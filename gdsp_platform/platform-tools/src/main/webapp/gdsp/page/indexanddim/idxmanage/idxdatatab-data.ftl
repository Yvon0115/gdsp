<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="script/indexanddim/dim"/>-->
<@c.Script src="script/indexanddim/dim" />
<#assign op>!<#noparse>
	<@c.Link title="点击选择该表" icon="glyphicon glyphicon-ok-sign"  click="saveDimTable('${row.doc_name}','${row.doc_name}')" >选择</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable checkbox=false striped=true titles=["指标表名称","描述","操作"] keys=["doc_name","doc_desc",op] ellipsis={"doc_name":"200px","doc_desc":"200px"} data=defDocVO.content />
<@c.PageData page=defDocVO />