<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="script/indexanddim/dim"/>-->
<@c.Script src="script/indexanddim/dim" />
<#assign op>!<#noparse>
	<@c.Link title="点击选择该表" icon="glyphicon glyphicon-ok-sign"  click="saveDimTable('${row.id}','${row.doc_name}')" >选择</@c.Link>
</#noparse>
</#assign>
<@c.SimpleTable checkbox=false striped=false titles=["类型名称","类型编码","类型描述","操作"] keys=["doc_name","doc_code","doc_desc",op] data=defDocVO.content />
<@c.PageData page=defDocVO />

