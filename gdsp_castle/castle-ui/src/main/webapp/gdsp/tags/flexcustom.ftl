<#--表单项目定义,不定义具体控件,只定义标签及控件位置-->
<#macro CustomFormItem id="" editorId="" label="" helper="" class="" lclass="" cclass="" mode="super" colspan=1 required=false addon=false style="" help=false>
    <#if mode=="super" && __formOptions?? && __formOptions?size gt 0>
        <#local labelSize = __formOptions["labelSize"]>
        <#local controlSize = __formOptions["controlSize"]>
        <#if colspan gt __formOptions["cols"]>
            <#local colspan = __formOptions["cols"]>
        </#if>
        <#local lsize=" col-sm-"+labelSize>
        <#local tempsize=controlSize+(colspan-1)*__formOptions["colCells"]>
        <#if labelSize+tempsize gt 12>
            <#local tempsize=12-labelSize>
        </#if>
        <#local csize=" col-sm-"+tempsize>
        <#if label=="">
            <#local csize=" col-xs-offset-"+labelSize +csize>
        </#if>
        <#local leavecell = 12-_formcellcount>
        <#if _formcellcount==0 && _formitemcount gt 0>
            </div>
            <div class="row">
        </#if>
        <#if leavecell gt labelSize+tempsize>
            <#assign  _formcellcount= _formcellcount+ labelSize+tempsize>
        <#elseif leavecell == labelSize+tempsize>
            <#assign  _formcellcount= 0>
        <#else>
            </div>
            <div class="row">
            <#assign  _formcellcount= labelSize+tempsize>
        </#if>
        <#assign _formitemcount=_formitemcount+1>
        <#local help=__formOptions["help"]>
    <#else>
        <#local lsize="">
        <#local csize="">
    </#if>
    <div style="height:30px;">
    	<#if label!=""><label class="control-label${lsize} ${lclass}" for="${editorId}" style="padding-top:5px;min-width:50%;man-width:80%">${label}</label></#if>
		<#nested/>
    </div>
	</div>
</#macro>

<#--表单项目复选框-->
<#macro CustomFormCheckBox name id="" itemId="" value="" checkValue="" class="" label="" helper=""  disabled=false events="" style="" itemClass="" itemStyle="" lclass="" mode="super"  colspan=1 ext="">
    <#if id==""><#local id=name></#if><#t>
    <#if value!="" && value?string==checkValue><#local checked="checked"><#else><#local checked=""></#if><#t>
    <@CustomFormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass mode=mode colspan=colspan>
    	<input type="checkbox" id="${id}" name="${name}" class="form-box ${class}" ${attrs({"events":events,"style":style})} <#if disabled>disabled</#if> ${checked} value="${checkValue}" ${ext}>
    </@CustomFormItem>
</#macro>

<#--简单表格-->
<#macro CustomSimpleTable id=""  class="" checkbox=false checkboxfield="id" checkedfield="" checkboxName="id" striped=true hover=true border=false highlight=false titles=[] keys=[] data=[]>
<table id="${id}" class="table${hover?string(" table-hover","")}${striped?string(" table-striped","")}${border?string(" table-bordered","")}${highlight
?string(" table-highlight","")} ${class}">
    <tbody><#local cols=titles?size>
    <#if cols gt 0>
    <tr>
        <#if checkbox><#local cols=cols+1><th class="checkbox-col"><@CheckAll selector="input:checkbox[name=${checkboxName}]" parents="table:first"/></th></#if>
        <#list titles as t>
            <#if t?starts_with("<")>
            	${t}
            <#else>
             	<th>${t}</th>
            </#if>
        </#list>
    </tr>
    </#if>
    <#if data?size gt 0>
    <#local templ = "">
    <#list keys as key>
        <#if key?starts_with("#")>
            <#local templ = templ+key?substring(1)>
        <#elseif key?starts_with("!")>
            <#local templ = templ+"<td>"+key?substring(1)+"</td>">
        <#else>
            <#if key?index_of(".") gt -1>
                <#local ks = key?split(".")>
                <#local kk = "['"+ks?join("']?if_exists['")+"']">
                <#local templ = templ+r"<td>${row"+kk+"?if_exists}</td>">
            <#else>
                <#local templ = templ+r"<td>${row['"+key+"']?if_exists}</td>">
            </#if>
        </#if>
    </#list>
        <#assign inlineTemplate = templ?interpret>
        <#list data as row>
        <tr>
        <#if checkbox><td><input type="checkbox" name="${checkboxName}" <#if checkedfield!="" && booleanValue(row[checkedfield]?default(false))> checked</#if>></td></#if>
            <@inlineTemplate />
        </tr>
    </#list>
    <#else>
    <tr><td colspan="${cols}" align="center">暂无数据</td></tr>
    </#if>
    </tbody>
</table>
</#macro>

<#--表单项目日期框-->
<#macro CustomFormDate name type="date" id="" itemId="" label="" helper="" value=""  class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super"  colspan=1 placeholder="" format="" validation={} messages={} ext="">
    <#if id==""><#local id=name></#if>
    <#local cclass="col-"+type+" "+cclass>
    <@HFormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?exists>
    	<div style="float:left;width:47%;"><input id="${id}" aria-invalid="false" name="${name}_start" type="date" class="form-control " value="${value}"/></div>
    	<div style="float:left;width:6%;" align="center" valign="middle">-</div>
    	<div style="float:left;width:47%;"><input id="${id}" aria-invalid="false" name="${name}_end" type="date" class="form-control " value="${value}"/></div>
    </@HFormItem>
</#macro>

<#--表单项目日期框-->
<#macro CustomFormDates name readonly=false type="date" id="" itemId="" label="" helper="" value=""  class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super"  colspan=1 placeholder="" format="" validation={} messages={} ext="">
    <#if id==""><#local id=name></#if>
    <#local cclass="col-"+type+" "+cclass>
    <@HFormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?exists>
    	<div style="float:left;width:47%;"><@DateEditor id=id name=name+"_start" readonly=readonly type=type value=value class="form-control " +class style=style disabled=disabled events=events placeholder=placeholder format=format validation=validation messages=messages ext=ext/></div>
    	<div style="float:left;width:6%;" align="center" valign="middle">-</div>
    	<div style="float:left;width:47%;"><@DateEditor id=id name=name+"_end" readonly=readonly type=type value=value class="form-control " +class style=style disabled=disabled events=events placeholder=placeholder format=format validation=validation messages=messages ext=ext/></div>
    </@HFormItem>
</#macro>

<#--表单项目下拉框-->
<#macro CustomFormComboBox name ref id="" itemId="" dictId="" value=""  label="" helper="" class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} ext="">
    <#if id==""><#local id=name></#if>
    <#assign items=Application["loaderDict"]>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?exists>
        <@ComboBox name=name id=id value=value class="form-control "+class style=style disabled=disabled events=events placeholder=placeholder validation=validation messages=messages ext=ext>
		   	<@Option value=""></@Option>
		   	<#local i=0>
		   	<#list items as item>
		   		<#if ref == item.dimn>
		    		<@Option value=item.code_val>${item.code_val}</@Option>
		   		</#if>
		    </#list>
        </@ComboBox>
    </@FormItem>
</#macro>

<#--表单项目复选框-->
<#macro CustomsFormCheckBox name ref cclass="" id="" itemId="" value="" checkValue="" class="" label="" helper=""  disabled=false events="" style="" itemClass="" itemStyle="" lclass="" mode="super"  colspan=1 ext="">
    <#if id==""><#local id=name></#if><#t>
    <#assign items=Application["loaderDict"]>
    <#if value!="" && value?string==checkValue><#local checked="checked"><#else><#local checked=""></#if><#t>
    <@FormItem id=itemId editorId=id cclass=cclass label=label helper=helper class=itemClass style=itemStyle lclass=lclass mode=mode colspan=colspan>
    		<#list items as item>
		   		<#if ref == item.dimn>
		   			<div class="col-md-3">
		    			<label class="control-label col-sm-10" style="padding-right:15px;">${item.code_val}</label><input type="checkbox" id="${name}" name="${name}" class="form-box ${class}" ${attrs({"events":events,"style":style})} <#if disabled>disabled</#if> ${checked} value="${item.code_val}" ${ext}>
		   			</div>
		   		</#if>
		    </#list>
    </@FormItem>
</#macro>

<#--表单项目定义,不定义具体控件,只定义标签及控件位置-->
<#macro HFormItem id="" editorId="" label="" helper="" class="" lclass="" cclass="" mode="super" colspan=1 required=false addon=false style="" help=false>
    <#if mode=="super" && __formOptions?? && __formOptions?size gt 0>
        <#local labelSize = __formOptions["labelSize"]>
        <#local controlSize = __formOptions["controlSize"]>
        <#if colspan gt __formOptions["cols"]>
            <#local colspan = __formOptions["cols"]>
        </#if>
        <#local lsize=" col-sm-"+labelSize>
        <#local tempsize=controlSize+(colspan-1)*__formOptions["colCells"]>
        <#local tempsize=12-labelSize>
        <#local csize=" col-sm-"+tempsize>
        <#if label=="">
            <#local csize=" col-xs-offset-"+labelSize +csize>
        </#if>
        <#local leavecell = 12-_formcellcount>
        <#if _formcellcount==0 && _formitemcount gt 0>
            </div>
            <div class="row">
        </#if>
        <#if leavecell gt labelSize+tempsize>
            <#assign  _formcellcount= _formcellcount+ labelSize+tempsize>
        <#elseif leavecell == labelSize+tempsize>
            <#assign  _formcellcount= 0>
        <#else>
            </div>
            <div class="row">
            <#assign  _formcellcount= labelSize+tempsize>
        </#if>
        <#assign _formitemcount=_formitemcount+1>
        <#local help=__formOptions["help"]>
    <#else>
        <#local lsize="">
        <#local csize="">
    </#if>
<div ${attrs({"id":id,"style":style})} class="form-group">

    <#if label!=""><label class="control-label${lsize} ${lclass}" for="${editorId}">${label}</label></#if>
    <div class="control-container ${cclass} ${csize}" <#if helper!="">title="${helper}"</#if>>
        <#if addon>
        <div class="input-group">
        </#if>
        <#nested/>
        <#if addon>
        </div>
        </#if>
        <#if help><div style="float:left;height:100%;"><label class="help-block" for="${editorId}">${helper}&nbsp</label></div><#else><label class="bottom-padding"></label></#if>
    </div>
</div>
</#macro>

<#--自定义全选复选框-->
<#macro CustomCheckAll selector id="" box="" parents="" checked=false ext="">
<input id="${id}" type="checkbox" class="cas-checker" checker-finder="${selector}" checker-box="${box}" checker-parents="${parents}" ${ext} <#if checked>checked</#if>/>
</#macro>

<#--自定义表格-->
<#macro CustomTable id="" checkallId="" class="" checkbox=false checkboxfield="id" checkedfield="" checkboxName="id" striped=true hover=true border=false highlight=false titles=[] keys=[] data=[] allext="" ext="">
<table id="${id}" class="table${hover?string(" table-hover","")}${striped?string(" table-striped","")}${border?string(" table-bordered","")}${highlight
?string(" table-highlight","")} ${class}">
    <thead>
    <#local cols=titles?size>
    <#if cols gt 0>
    <tr>
        <#if checkbox><#local cols=cols+1><th class="checkbox-col"><@CustomCheckAll selector="input:checkbox[name=${checkboxName}]" id="${checkallId}" parents="table:first" ext="${allext}"/></th></#if>
        <#list titles as t>
            <#if t?starts_with("<")>
            	${t}
            <#else>
             	<th>${t}</th>
            </#if>
        </#list>
    </tr>
    </#if>
    </thead>
    <tbody>
    <#if data?size gt 0>
    <#local templ = "">
    <#list keys as key>
        <#if key?starts_with("#")>
            <#local templ = templ+key?substring(1)>
        <#elseif key?starts_with("!")>
            <#local templ = templ+"<td>"+key?substring(1)+"</td>">
        <#else>
            <#if key?index_of(".") gt -1>
                <#local ks = key?split(".")>
                <#local kk = "['"+ks?join("']?if_exists['")+"']">
                <#local templ = templ+r"<td>${row"+kk+"?if_exists}</td>">
            <#else>
                <#local templ = templ+r"<td>${row['"+key+"']?if_exists}</td>">
            </#if>
        </#if>
    </#list>
        <#assign inlineTemplate = templ?interpret>
        <#list data as row>
        <tr>
        <#if checkbox><td><input type="checkbox" name="${checkboxName}" value="${row[checkboxfield]}" ${ext} <#if checkedfield!="" && booleanValue(row[checkedfield]?default(false))> checked</#if>></td></#if>
            <@inlineTemplate />
        </tr>
    </#list>
    <#else>
    </#if>
    </tbody>
</table>
</#macro>

<#--表单项目单行编辑框-->
<#macro FormNumber name type="text" id="" itemId="" label="" helper="" value="" addon="" mask=""  class="" style=""  readonly=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} ext="">
    <#if id==""><#local id=name></#if>
	<@HFormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?exists>
    	<div style="float:left;width:47%;"><input class="form-control ${class}" type="${type}" id="${id}" name="${name}_min"  ${attrs({"events":events,"mask":mask,"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})} value="${value}" <#if readonly>readOnly</#if> ${ext}></div>
    	<div style="float:left;width:6%;" align="center" valign="middle">-</div>
    	<div style="float:left;width:47%;"><input class="form-control ${class}" type="${type}" id="${id}" name="${name}_max"  ${attrs({"events":events,"mask":mask,"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})} value="${value}" <#if readonly>readOnly</#if> ${ext}></div>
    </@HFormItem>
</#macro>

<#macro FlexiGrid id="" titles=[] class="" gTitle="" url="" width="" height="" minwidth="" style="">
	<table class="flexigrid ${class}" id="${id}" gTitle="${gTitle}" url="${url}" width="100%" style="${style}" height="${height}" minwidth="50">
	 	<thead>
	 		<tr>
	 			<#if labels??>
		 		<#else>
		 			<#list titles as label>
		 				<th name="account" sortable="false">${label}</th>
		 			</#list>
	 			</#if>
	 		</tr>
	 	</thead>
	 </table>
</#macro>

<#macro FlexiGrids id="" titles=[] class="" gTitle="" url="" width="" height="" minwidth="" style="">
	<table class="flexigrids ${class}" id="${id}" gTitle="${gTitle}" url="${url}" width="100%" style="${style}" height="${height}" minwidth="50">
	 	<thead>
	 		<tr>
	 			<#if labels??>
		 		<#else>
		 			<#list titles as label>
		 				<th name="account" sortable="false">${label}</th>
		 			</#list>
	 			</#if>
	 		</tr>
	 	</thead>
	 </table>
</#macro>

<#macro TwoSimpleTable id=""  class="" checkbox=false checkboxfield="id" checkedfield="" checkboxName="id" striped=true hover=true border=false highlight=false titles=[] keys=[] data=[]>
<div style="min-height:40px;max-height:40px;max-width:3000px;">
<table id="${id}" class="table${hover?string(" table-hover","")}${striped?string(" table-striped","")}${border?string(" table-bordered","")}${highlight
?string(" table-highlight","")} ${class}">
    <tbody><#local cols=titles?size>
    <#if cols gt 0>
    <tr>
        <#if checkbox><#local cols=cols+1><th class="checkbox-col">
        <@CheckAll selector="input:checkbox[name=${checkboxName}]" parents="table:first"/></th></#if>
        <#list titles as t>
            <#if t?starts_with("<")>
            	${t}
            <#else>
             	<th>${t}</th>
            </#if>
        </#list>
    </tr>
    </#if>
    </tbody>
</table>
</div>
<div class="table-responsive" style="min-height:470px;max-height:470px;max-width:3000px;">
<table id="${id}" class="table${hover?string(" table-hover","")}${striped?string(" table-striped","")}${border?string(" table-bordered","")}${highlight
?string(" table-highlight","")} ${class}">
	<tbody>
    <#if data?size gt 0>
    <#local templ = "">
    <#list keys as key>
        <#if key?starts_with("#")>
            <#local templ = templ+key?substring(1)>
        <#elseif key?starts_with("!")>
            <#local templ = templ+"<td>"+key?substring(1)+"</td>">
        <#else>
            <#if key?index_of(".") gt -1>
                <#local ks = key?split(".")>
                <#local kk = "['"+ks?join("']?if_exists['")+"']">
                <#local templ = templ+r"<td>${row"+kk+"?if_exists}</td>">
            <#else>
                <#local templ = templ+r"<td>${row['"+key+"']?if_exists}</td>">
            </#if>
        </#if>
    </#list>
        <#assign inlineTemplate = templ?interpret>
        <#list data as row>
        <tr>
        <#if checkbox><td><input type="checkbox" name="${checkboxName}" value="${row[checkboxfield]}" <#if checkedfield!="" && booleanValue(row[checkedfield]?default(false))> checked</#if>></td></#if>
            <@inlineTemplate />
        </tr>
    </#list>
    <#else>
    <tr><td colspan="${cols}" align="center">暂无数据</td></tr>
    </#if>
    </tbody>
</table>
</div>
</#macro>

<#--表单项目日期框-->
<#macro Wdate name type="date" id="" itemId="" label="" helper="" value=""  class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super"  colspan=1 placeholder="" format="" validation={} messages={} ext="">
    <#if id==""><#local id=name></#if>
    <#local cclass="col-"+type+" "+cclass>
    <#local y=.now?date[0..3]>
    <#local m=.now?date[5..6]>
    <#if "01,02,03"?contains(m)>
    	<#local dt = y + "年01季度">
    	<#local t = "01">
    <#elseif "04,05,06"?contains(m)>
    	<#local dt = y + "年02季度">
    	<#local t = "02">
    <#elseif "07,08,09"?contains(m)>
    	<#local dt = y + "年03季度">
    	<#local t = "03">
    <#elseif "10,11,12"?contains(m)>
    	<#local t = "04">
    	<#local dt = y + "年04季度">
    </#if>
    <@HFormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?exists>
		<input class="form-control" type="text" id="${id}" name="${name}" onClick="WdatePicker({maxDate:'%y-${t}',dateFmt:'yyyy年MM季度',disabledDates:['....-0[5-9]-..','....-1[0-2]-..']})" value="${dt}">
    </@HFormItem>
</#macro>