<#import "/gdsp/tags/castle.ftl" as c>
<#assign op>#<#noparse>
<@c.TableOperate>
	<li><@c.Link title="修改" icon="glyphicon glyphicon-edit" action=[c.opentab("#detailPanel","${ContextPath}/grant/product/edit.d?id=${row.id}")]>修改</@c.Link></li>
	<li><@c.Link title="删除" icon="glyphicon glyphicon-remove" action=[c.rpc("${ContextPath}/grant/product/delete.d?id=${row.id}",{"dataloader":"#productPageContent"},{"confirm":"确认删除用户组‘${row.name?if_exists}’？"})]>删除</@c.Link></li>
</@c.TableOperate>
</#noparse>
</#assign>
<@c.SimpleTable striped=true titles=["商品名称","数量","价格",""] keys=["name","number","price",op] ellipsis={"name":"140px","age":"140px","tel":"140px","email":"140px"} data=productPage.content checkbox=true/>
<@c.Hidden name="selSupId" value=selSupId/>
<@c.PageData page=productPage />