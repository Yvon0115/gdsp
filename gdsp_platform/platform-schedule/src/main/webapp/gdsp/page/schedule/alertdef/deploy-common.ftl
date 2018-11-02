<#--预警类型部署基本信息页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Box style="border-bottom:none;border-left:none;border-right:none;box-shadow:none;">
    <@c.BoxBody  style="width:100%;max-height:560px;min-height:330px; ">    	
        <@c.FormGroup id="common">
        <@c.FormInput name="name" label="预警名称" validation={"alphanumerSpec":true,"required":true,"minlength":1,"maxlength":128} helper="1-128位字符,仅支持汉字、字母、数字和部分特殊字符（.- _()[]（）【】）组合" events="{blur :function(){$Utils.validInputSpeChar(this)}}"/>
        <@c.Hidden name="group" value="${jobdef?if_exists.group?if_exists}"/>
        <@c.Hidden name="jobGroup" value="${jobdef?if_exists.group?if_exists}"/>
        <@c.FormText name="description" label="预警描述" validation={"maxlength":250}></@c.FormText>
        <@c.FormInput name="jobName" label="预警类型" value="${jobdef?if_exists.name?if_exists}" readonly=true/>
        <@c.FormItem id="triggerState" label="状态"   helper="部署预警是否生效" >
        	<div style="margin-top:5px;">
				<input type="radio"  value="1"  id="triggerState"  name="triggerState" checked>激活 &nbsp;
				<input type="radio"  value="2"  id="triggerState"  name="triggerState">休眠 &nbsp;
			</div>
        </@c.FormItem>
        </@c.FormGroup>
   	</@c.BoxBody>
</@c.Box>