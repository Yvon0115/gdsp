<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>图片维护</modal-title>
   <@c.Form id="inportForm" class="form-horizontal validate" action="${ContextPath}/portal/functionDec/doUpload.d" method="post"  isAjax=false enctype="multipart/form-data" after={"pagereload()":""}>
<div class="modal-body autoscroll">
      <@c.TableLoader id="functionContent1" url="${ContextPath}/portal/functionDec/listtagrid.d?id=${menuid?if_exists}">
            <#include "list-tagrid.ftl">     
      </@c.TableLoader> 
        <@c.Hidden name="menuid" value="${menuid?if_exists}" />
        <@c.FormText name="memo" label="图片描述" validation={"minlength":1,"maxlength":100}>${memo?if_exists}</@c.FormText> 
        <@c.FormInput name="fileName" style="padding-top:0px;padding-bottom:0px;" type="file" label="选择文件" helper="(支持格式:jpg,png,gif,jpeg)" validation={"required":true}/>  
</div>
<div class="modal-footer">
	<button class="btn btn-primary" type="submit"><i class="glyphicon glyphicon-saved"></i>确定</button>
	<@c.Button type="canel" icon="glyphicon glyphicon-off" action=[c.dismiss()]>取消</@c.Button>
</div>
</@c.Form>
