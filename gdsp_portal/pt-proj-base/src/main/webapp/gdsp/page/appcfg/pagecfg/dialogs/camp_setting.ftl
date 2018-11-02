<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：spzxj8685@163.com
	时间：2015-06-24
	描述：组件属性设置
-->
<modal-title>组件设置</modal-title>
<div class="modal-body autoscroll padding" id="modalBodyId" >
	<@c.Form id="settingForm" class="validate" action="${ContextPath}/appcfg/pagecfg/saveSetting.d" after={"campSetting.save_setting(options,json)":""} method="post" labelsize=3 ctrlsize=7 >
		<@c.FormInput name="title" label="标题" value="${widget?if_exists.title?if_exists}"  helper="1-60位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":60}/>
		<@c.FormInput name="height" label="高度" value="${widget?if_exists.height?default('350')}" validation={"required":true,"digits":true,"maxlength":4}/>
		<!--<@c.FormBoolean name="auto_height" label="自适应高度" value=widget?if_exists.auto_height?default("")/>-->
		<@c.FormBoolean name="title_show" label="是否显示标题" helper="勾选为显示标题，默认显示" value=widget?if_exists.title_show?default("")/>
		<#if actions??&&actions?size gt 0>
	 		<@c.FormItem id="actions" label="按钮选择"   >
		        <#if widget??&&widget.action??>
		            <#assign selectActions=widget.action>
		        <#else>
			        <#assign selectActions=[]>
		        </#if>
	        	<#list actions as action>
	        		<div class="checkbox">
                        <label style="width:150px">
	                        <#if selectActions?seq_contains(action.code)>
                            <input type="checkbox" name="action" value="${action.code?if_exists}" id="${action.id?if_exists}" checked>
	                        <#else>
                          <input type="checkbox" name="action" value="${action.code?if_exists}" id="${action.id?if_exists}">
	                        </#if>
                          ${action.name?if_exists}
                        </label>
                      </div>
	        	</#list>
	        </@c.FormItem>
		</#if>
		<@c.Hidden name="id" value="${widget?if_exists.id?if_exists}" />
		<@c.Hidden name="width" value="${widget?if_exists.width?if_exists}" />
		<@c.Hidden name="column_id" value="${widget?if_exists.column_id?if_exists}" />
		<@c.Hidden name="widget_type" value="${widget?if_exists.widget_type?if_exists}" />
		<@c.Hidden name="widget_id" value="${widget?if_exists.widget_id?if_exists}" />
		<@c.Hidden name="widget_style" value="${widget?if_exists.widget_style?if_exists}" />
		<@c.Hidden name="page_id" value="${widget?if_exists.page_id?if_exists}" />
		<@c.Hidden name="sortnum" value="${widget?if_exists.sortnum?if_exists}" />
		<@c.Hidden name="version" value="${widget?if_exists.version?if_exists}" />
	</@c.Form>
</div>
<div class="modal-footer">
	<@c.Button type="primary" icon="fa fa-save" action=[c.saveform("#settingForm")]>保存</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off"   action=[c.attrs({"data-dismiss":"modal"})]>取消</@c.Button>
	<@c.Script id="campSetting" src="script/appcfg/pagecfg/page_customizat" onload="campSetting.comp_setting_init('${widget?if_exists.id?if_exists}')"/>
</div>
