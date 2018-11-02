<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${systemName?if_exists}|登录</title>
    <link type="image/x-icon" href="${__imagePath}/main/head/logo/3D_logo_48px.png" rel="shortcut icon">
   <link href="gdsp/css/style_gdsp.css" rel="stylesheet" type="text/css">
<#include "/gdsp/include/resource.ftl">
</head>
<script type="text/javascript">
    function validateLogin(){
        var $msg = $("#message"),$usrname=$("#username"),$pwd=$("#password");
        var usrname = $usrname.val()||"";

        if(usrname.trim().length==0){
            $msg.html("用户名密码不能为空!");
            $usrname.focus();
            return false;
        }
        var pwd = $pwd.val()||"";
        if(pwd.trim().length==0){
            $msg.html("用户名密码不能为空!");
            $pwd.focus();
            return false;
        }
        return true;
    }
    
    function clearValidate(){
	  	$("#username").val('');
		$("#password").val('');
		$("#message").html('');
    }
    
    function changeCaptcha(){
		$.get("${ContextPath}/jcaptcha.jpg");
		var imgElement = document.getElementById("captchaId");
		imgElement.src="${ContextPath}/jcaptcha.jpg?t=" + Math.random();
	}
    
    if(window !=top){
    	top.location.href=location.href;
	}
	
</script>

<body>
<div class="login-box">
    <!-- /.login-logo -->
   
<div class="login" id="main_login" style="top:30px; right: 45px;">     
   
 <div class="input_text">   
 <div id="message"style="position:absolute;color:red;margin-top:-20px;margin-left:100px;"> ${error?if_exists}</div>
   <form  action="login.d" method="post" onsubmit="return validateLogin()"> 

		<table class="table-condensed tb_bg" width="100%" border="0" cellspacing="1" cellpadding="5">
			<tbody>
				<tr>
					<td></td>
				</tr>
				<tr>
     				<td width="27%" height="54" style="font-size: 16px;">用户名：</td>
					<td width="73%"><input id="username" name="username" value="${username?if_exists}" class="form-control l35" fv_type="NOTCN" type="text" maxlength="20" placeholder="请输入您的用户名" value=""  data-toggle="tooltip" data-placement="right"></td>
				</tr>   
        		<tr>
					<td style="font-size: 16px;">密　码：</td>
					<td><input id="password" name="password" value="${pwd?if_exists}" class="form-control highlight_green l35" fv_type="NOTCN" type="password" placeholder="请输入您的密码"  data-toggle="tooltip" data-placement="right"></td>
				</tr>
				<#if jcaptchaEbabled>  
					<tr>
						<td width="27%" height="54" style="font-size: 16px;">验证码：</td>
						<td>
							<table>
							<tr>
								<td><input id="jcaptchaCode" style="width:100px" name="jcaptchaCode" class="form-control" fv_type="NOTCN" type="text" maxlength="10" placeholder="验证码" value=""  data-toggle="tooltip" data-placement="right" /></td>
								<td>&nbsp;&nbsp;<img id="captchaId" style="height:30px" src="${ContextPath}/jcaptcha.jpg" onclick="changeCaptcha()" title="点击更换验证码"></td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td></td>
						<td id="catpchaTd">
						</td>
					</tr>
				<#else>	
					<tr>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
					</tr>
				</#if>
       			<tr>
					<td></td>
					<td colspan="2">
						&nbsp;&nbsp;
						<button type="submit" class="btn btn-primary"  >&nbsp;&nbsp;登　录&nbsp;&nbsp;</button>&nbsp;&nbsp;	
						<button type="reset" class="btn btn-warning" onclick="clearValidate()" > &nbsp;&nbsp;重  &nbsp;置&nbsp;&nbsp;&nbsp</button>
					</td>
					
			    </tr>
          </tbody>
        </table>
	</form>
</div>
<!--<div class="login_copyright">Copyright © 2014-2016 All rights Reserved 版权所有</div>-->
</div>
</div>
</body>
</html>       
        
        
        
        
   