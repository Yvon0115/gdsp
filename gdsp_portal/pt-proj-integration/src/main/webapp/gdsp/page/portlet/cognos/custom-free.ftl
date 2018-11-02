<#import "/gdsp/tags/castle.ftl" as c>
<!--自定义参数-->

<#list reportMetaVO.params as para>

	<#assign validateflag =para?if_exists.mustput?default("N") == "Y"/>
	<!---->
	<!--文本输入-->
	<#if para.viewType=="text">
	 	<@c.FormInput colspan=para.colspan?if_exists name="${para.name?if_exists}_showItem" id="${para.name?if_exists}_showItem" 
	 	events="{change:function(){dochangeValuebyDate('${para.name?if_exists}','${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}');}}" 
	 	 label="${para.displayName?if_exists}" value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}" validation={"required":validateflag}/>
	 	
	 	<div style="display:none;">
			<input type="text"  name="${para.name?if_exists}" id="${para.name?if_exists}"   class="form-control " value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}"/>
			<@c.Hidden name="${para.name?if_exists}_cubeFormat" value="${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}" />
		</div>
		
	<!--日期输入-->
	<#elseif para.viewType="year">
		<@c.FormDate  colspan=para.colspan?if_exists label="${para.displayName?if_exists}" 
			events="{change:function(){dochangeValuebyDate('${para.name?if_exists}','${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}');}}" 
			name='${para.name?if_exists}_showItem'  id="${para.name?if_exists}_showItem" 
			value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}" 
			validation={"required":validateflag} format="yyyy"/>
		<div style="display:none;">
			<input type="text" name="${para.name?if_exists}" id="${para.name?if_exists}" class="form-control"   value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}"/>
			<@c.Hidden name="${para.name?if_exists}_cubeFormat" value="${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}" />
		</div>
	<#elseif para.viewType="month">
		<@c.FormDate  colspan=para.colspan?if_exists label="${para.displayName?if_exists}" 
			events="{change:function(){dochangeValuebyDate('${para.name?if_exists}','${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}');}}" 
			name='${para.name?if_exists}_showItem'  id="${para.name?if_exists}_showItem" 
			value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}" 
			validation={"required":validateflag} format="yyyy-mm"/>
		<div style="display:none;">
			<input type="text" name="${para.name?if_exists}" id="${para.name?if_exists}" class="form-control"   value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}"/>
			<@c.Hidden name="${para.name?if_exists}_cubeFormat" value="${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}" />
		</div>
	<#elseif para.viewType=="day">
		<@c.FormDate  colspan=para.colspan?if_exists label="${para.displayName?if_exists}" 
			events="{change:function(){dochangeValuebyDate('${para.name?if_exists}','${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}');}}" 
			name='${para.name?if_exists}_showItem'  id="${para.name?if_exists}_showItem" 
			value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}" 
			validation={"required":validateflag} format="yyyy-mm-dd"/>
		<div style="display:none;">
			<input type="text" name="${para.name?if_exists}" id="${para.name?if_exists}" class="form-control"   value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}"/>
			<@c.Hidden name="${para.name?if_exists}_cubeFormat" value="${reportMetaVO.cubeValueMap[para.name?if_exists]?if_exists}" />
		</div>
	<!--下拉单选select-->
	<#elseif para.viewType=="select">
       <@c.FormComboBox colspan=para.colspan?if_exists name="${para.name?if_exists}" label="${para.displayName?if_exists}"  value="${reportMetaVO.dvMap[para.name?if_exists]?if_exists}">
       			<#list reportMetaVO.ctMap[para.name]?if_exists as value>
      				<@c.Option value="${value.doc_code?if_exists}">${value.doc_name?if_exists}</@c.Option>
       			</#list>
        </@c.FormComboBox>
	<!--checkbox 多选-->
	<#elseif para.viewType=="checkbox">
		<@c.FormItem editorId="param_type" colspan=para.colspan?if_exists    label="${para.displayName?if_exists}" >
			<@c.Box  class="scrollbar" style="max-height:250px;min-height:100px">
			<table class="table table-hover no-padding">
			    <tbody class="no-padding">
			    	<tr class="no-padding">
			        <th class="checkbox-col no-padding"><input type="checkbox" checker-parents="table:first" checker-box="" checker-finder="input:checkbox[cgroup=ccc]" class="cas-checker">
					</th>
		             	<th class="no-padding">全选/全消</th>
			    	</tr>
			    	  <#list reportMetaVO.ctMap[para.name]?if_exists as doc>
					        <tr class="no-padding">
						        <td class="no-padding"><input type="checkbox" value="${doc.doc_code?if_exists}"  name="${para.code?if_exists}" cgroup="ccc" id="${para.code?if_exists}"></td>
								<td class="no-padding">${doc.doc_name?if_exists}</td>
					        </tr>
				        </#list>
			    </tbody>
			</table>
			</@c.Box>
		</@c.FormItem>
	<#else>
	</#if>
</#list>
