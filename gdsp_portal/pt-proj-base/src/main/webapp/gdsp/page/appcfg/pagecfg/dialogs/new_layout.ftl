<#import "/gdsp/tags/castle.ftl" as c>
<!--
	作者：caianqi
	时间：2017-05-15
	描述：新增自定义布局
-->
<modal-title>新建自定义布局</modal-title>
<div class="modal-body" id="modalBodyId">
	
	<@c.Form id="layoutForm" class="validate" action="${ContextPath}/appcfg/pagecfg/saveLayout.d" method="post" after={"pageCustom.afterSaveLayout()":""}>
    
     <!--   <@c.FormIdVersion id="${defDocVO?if_exists.id?if_exists}" version="${defDocVO?if_exists.version?default(0)}"/>-->
	 <!--	<@c.Hidden name="doc_code" id="doc_code" value="${defDocVO?if_exists.doc_code?if_exists}" /> -->
        
		<@c.Hidden name="page_id" id="page_id" value="${page_id?if_exists}" />
       
        <@c.Hidden name="type_id" id="type_id" value="np_layout" />
		<@c.Hidden name="is_preset" id="is_preset" value="y" />
      	
		<@c.FormInput name="doc_name" label="布局名称" value="${defDocVO?if_exists.doc_name?if_exists}"  helper="1-10位字符,仅支持汉字、字母、数字组合" validation={"required":true,"alphanumer":true,"minlength":1,"maxlength":10}/>
    
    	<@c.FormInput name="doc_desc" label="布局备注" value="${defDocVO?if_exists.doc_desc?if_exists}" />
    	
		<@c.FormComboBox name="layout_column"  label="列数" value="${layout_column?if_exists}" events="{change:function(){pageCustom.changeHiddenWidth(this);}}" lclass="customlabel" cclass="customcontrol" helper="无论是2列还是3列，请保证各列的宽度相加为100">
				
				<@c.Option value="2" >两列</@c.Option>
				<@c.Option value="3" selected=true>三列</@c.Option>
			
		</@c.FormComboBox>

		  <@c.FormInput  id="width_one"   class="width_validation"   name="width_colspan_one"   label="第一列宽度" value="${width_colspan_one?if_exists}" helper="请输入1-99的任意整数"  validation={"required":true,"digits":true,"min":1,"max":99}  />
		  <@c.FormInput  id="width_two"   class="width_validation"  name="width_colspan_two"   label="第二列宽度" value="${width_colspan_two?if_exists}" helper="请输入1-99的任意整数"  validation={"required":true,"digits":true,"min":1,"max":99}  />
		  <@c.FormInput  id="width_three" class="width_validation"  name="width_colspan_three" label="第三列宽度" value="${width_colspan_three?if_exists}" helper="请输入1-99的任意整数"  validation={"required":true,"digits":true,"min":1,"max":99}  />
	
	</@c.Form>
</div>
<div class="modal-footer">
 	<@c.Button type="primary"  icon="fa fa-save"  action=[c.saveform("#layoutForm")]>保存</@c.Button>
	<@c.Button type="canel"  icon="glyphicon glyphicon-off"  action=[c.attrs({"data-dismiss":"modal"})]>退出</@c.Button>
</div>

<@c.Script id="pageCustom" src="script/appcfg/pagecfg/page_customizat" />
<@c.Script src="script/appcfg/pagecfg/pagecfg" />

