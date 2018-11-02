<#--viewOnly属性demo页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box >
<div style="width:900px; heigth:600px; margin:auto;">  
	<div style="width:450px; height:600px; float:left;">
	<#--Form控件-->
	<h3>Form控件:viewOnly=false</h3>
    <@c.Form id="Form1" class="validate" action="" method="post" after={}>
        <@c.FormInput name="id1"  label="编号1" value="默认样式"/>
        <@c.FormInput name="id2" readonly=true label="编号2" value="readonly=true"/> 
        <@c.FormInput name="id3" disabled=true label="编号3" value="disabled=true"/> 
        <@c.FormInput name="id4" helper="提示信息" label="编号4" value="有提示信息"/>  
    </@c.Form>
	<h3>Form控件:viewOnly=true</h3>
    <@c.Form id="Form1" viewOnly=true class="validate" action="" method="post" after={}>
        <@c.FormInput name="id1"  label="编号1" value="默认样式"/>
        <@c.FormInput name="id2" readonly=true label="编号2" value="readonly=true"/> 
        <@c.FormInput name="id3" disabled=true label="编号3" value="disabled=true"/> 
        <@c.FormInput name="id4" helper="提示信息" label="编号4" value="有提示信息"/> 
    </@c.Form>	 
	</div>   
	<div style="width:450px; height:600px; float:left;">
	<#--FormGroup控件-->
    <h3>FormGroup控件:viewOnly=false</h3>
    <@c.FormGroup id="FormGroup1" >
         <@c.FormInput name="id1"  label="编号1" value="默认样式"/>
         <@c.FormInput name="id2" readonly=true label="编号2" value="readonly=true"/> 
         <@c.FormInput name="id3" disabled=true label="编号3" value="disabled=true"/> 
         <@c.FormInput name="id4" helper="提示信息" label="编号4" value="有提示信息"/> 
    </@c.FormGroup>
    <h3>FormGroup控件:viewOnly=true</h3>
    <@c.FormGroup id="FormGroup2" viewOnly=true>
         <@c.FormInput name="id1"  label="编号1" value="默认样式"/>
         <@c.FormInput name="id2" readonly=true label="编号2" value="readonly=true"/> 
         <@c.FormInput name="id3" disabled=true label="编号3" value="disabled=true"/> 
         <@c.FormInput name="id4" helper="提示信息" label="编号4" value="有提示信息"/>  
    </@c.FormGroup>    
	</div>
    <#--Form中嵌套FormGroup-->
    <h3>Form中嵌套FormGroup:viewOnly=true</h3>
    <@c.Form viewOnly=true id="Form3" class="validate" action="" method="post" after={}>
	     <@c.FormGroup id="FormGroup3">
	     <@c.FormInput name="id1"  label="编号1" value="默认样式"/>
         <@c.FormInput name="id2" readonly=true label="编号2" value="readonly=true"/> 
         <@c.FormInput name="id3" disabled=true label="编号3" value="disabled=true"/> 
         <@c.FormInput name="id4" helper="提示信息" label="编号4" value="有提示信息"/> 
         </@c.FormGroup>
    </@c.Form>
    </div>
</@c.Box >
