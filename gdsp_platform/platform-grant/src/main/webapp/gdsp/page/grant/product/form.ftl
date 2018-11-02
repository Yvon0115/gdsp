<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/grant/product"/>
<@c.Box>
    <@c.BoxHeader class="border header-bg">
        <h3 class="box-title"><#if product??&& product.id??>修改商品<#else>添加商品</#if></h3>
    </@c.BoxHeader>
    <@c.BoxBody>
    <@c.Form  id="productForm" class="validate" action="${ContextPath}/grant/product/save.d" method="post" after={"switchtab":"#mainPanel","dataloader":"#productPageContent"}>
        <@c.FormIdVersion id="${product?if_exists.id?if_exists}" version="${product?if_exists.version?default(0)}"/>
        <@c.FormInput name="name" label="商品名称" value="${product?if_exists.name?if_exists}" helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" validation={"required":true,"alphanumerSpec":true,"minlength":1,"maxlength":60,"remote":"${ContextPath}/grant/product/uniqueCheck.d?id=${product?if_exists.id?if_exists}"}  messages={"remote":"商品名不能重复，请确认！"} />
        <@c.FormInput name="number" label="数量" value="${product?if_exists.number?if_exists}" />
        <@c.FormInput name="price" label="价格" value="${product?if_exists.price?if_exists}"  />
       	<@c.FormInput name="supname" label="供应商" value=supname readonly=true />
       	<@c.Hidden name="pk_sup" value="${product?if_exists.pk_sup?if_exists}"/>
	</@c.Form>
   	</@c.BoxBody>
    <@c.BoxFooter class="text-center">
        <@c.Button type="primary"  icon="fa fa-save" action=[c.saveform("#productForm")]>保存</@c.Button>
        <@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.opentab("#mainPanel")]>取消</@c.Button>
    </@c.BoxFooter>
</@c.Box>