<#--查询条件定义b-->
<#--查询按钮包含标签，用于在查询面板里嵌入自定义按钮的标签-->
<#macro ConditionActions>
    <#assign _condition_actions>
        <#nested >
    </#assign>
</#macro>
<#--查询按钮-->
<#macro QueryAction target>
<button type="button" class="btn btn-primary" cas-query="${target}"><i class="fa fa-search"></i><#nested /></button>
</#macro>
<#--查询条件-->
<#macro Condition id="" action=""  class="" target="" method="post" before={} button="true" size=10 cols=2 labelsize=0 ctrlsize=0 mode="expression" layout="h">
	<#if before?keys?size gt 0>
		<#local b> ajax-before="${before?keys?join(";")}" <#list before?keys as key><#local md="cmd_"+key><#if !md?trim?ends_with(")") && md?eval?is_macro><#if before[key]?is_string&&before[key]==""><#local e=(md+"()")?eval><#else><#local e=(md+"(before[key])")?eval></#if> ${e}</#if></#list></#local>
	<#else>
		<#local b="">
	</#if>
<div class="row">
<#if !button || layout=="v">
    <div class="col-lg-12">
<#else>
    <div class="col-lg-${size}">
</#if>
<#if id="">
    <#local id=utils.UUID>
</#if>
<form id="${id}" class="xquery ${class}" action="${action}" ${b} ${attrs({"method":method,"target":target})} onsubmit="return $(this).xquery('submit')">
    <@FormGroup id="${id}_formgroup" cols=cols labelsize=labelsize ctrlsize=ctrlsize help=false collapse="none">
		<#nested >
	</@FormGroup>
</form>
    </div>
    <#if button>
    <#if layout="v">
    <div class="col-lg-12">
    </#if>
        <div class="pull-right">
    <#if _condition_actions?exists && _condition_actions=="">
        ${_condition_actions}
        <#assign _condition_actions="">
    <#else>
        <@QueryAction target="#"+id></@QueryAction>
    </#if>
        </div>
    <#if layout="v">
    </div>
    </#if>
    </#if>
</div>
</#macro>
<#--查询项目定义,不定义具体控件,只定义标签及控件位置-->
<#macro QueryItem id="" editorId="" label="" helper="" class="" lclass="" cclass="" colspan=1 required=false style="">
    <@FormItem id=id editorId=editorId label=label helper=helper class=class lclass=lclass cclass=cclass mode="super" colspan=colspan required=required style=style>
    <#nested />
    </@FormItem>
</#macro>

<#--查询组件-->
<#macro QueryComponent name op="=" type="text" id="" label="" value="" class="" style="" placeholder="" helper="" colspan=1 readonly=false disabled=false exttype="" rows=3 checkValue=""  format="" showValue="" url="" code="" readFields="" writeFields="" events="" itemId="" itemClass="" itemStyle="" lclass="" cclass="" validation={} messages={}>
    <#local ext="op=\""+op+"\"">
    <#if type="area">
        <@FormText name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan readonly=readonly rows=rows events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages >
        <#nested >
        </@FormText>
    <#elseif type=="checkbox">
        <@FormCheckBox name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled checkValue=checkValue events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass/>
    <#elseif type=="boolean">
        <@FormCheckBox name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass/>
    <#elseif type=="date">
        <#if exttype==""><#local exttype="date"></#if>
        <@FormDate name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled value=value type=exttype format=format events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass/>
    <#elseif type=="ref">
        <@FormRef name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled value=value showValue=showValue url=url code=code readFields=readFields writeFields=writeFields events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass/>
    <#elseif type=="combobox">
        <@FormComboBox name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled value=value events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass>
            <#nested >
        </@FormComboBox>
    <#else>
        <#if exttype==""><#local exttype="text"></#if>
        <@FormInput name=name id=id label=label ext=ext  class=class style=style placeholder=placeholder helper=helper colspan=colspan readonly=readonly value=value type=exttype events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages />
    </#if>
</#macro>
<#--查询条件定义e-->