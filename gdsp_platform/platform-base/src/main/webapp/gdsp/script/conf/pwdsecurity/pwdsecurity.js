	$(function(){
		//密码修改页去除首页图标、下拉箭头、收藏夹、菜单栏
		$("#homePage").remove();
		$(".menu_csdc").remove();
		$("#favorites_drop").remove();
		$("#favorites_csdc").remove();
		//除退出登陆外,所有链接都不可用
		$("#favorites_drop").unbind();
		$("#message").css({display:'none'});
		$("#notice").css({display:'none'});
		$("a").not(".user-close-csdc").prop("href","#");
		var pwdLength = $("#pwd_length").val();
		var pwdNumberState = $("#pwd_number_state").val();
		var pwdCharacterState = $("#pwd_character_state").val();
		var pwdCaseState = $("#pwd_case_state").val();
		var pwdEnglishState = $("#pwd_english_state").val();
		var num = 0;
		var head = "提示:";
		if(pwdLength != 0){
			num += 1;
			var lengthTip = num + ". 密码长度不能小于" + pwdLength + "位";
			$("#length_tip").html(lengthTip);
		}
		if(pwdNumberState == "Y"){
			num += 1;
			var numberTip = num + ". 密码必须包含数字";
			$("#number_tip").html(numberTip);
		}
		if(pwdCharacterState == "Y"){
			num += 1;
			var characterTip = num + ". 密码必须包含@,#,$三种特殊字符之一";
			$("#character_tip").html(characterTip);
		}
		if(pwdEnglishState == "Y"){
			num += 1;
			var englishTip = num + ". 密码必须包含英文字母";
			$("#english_tip").html(englishTip);
		}
		if(pwdCaseState == "Y"){
			num += 1;
			var caseTip = num + ". 密码必须包含大写英文字母";
			$("#case_tip").html(caseTip);
		}
		$("#tip").html(head);
		if(num == 0){
			$("#no_tip").html("未设置密码规则");
		}
	});

	function savePassword(){
		var user_password = $("#user_password").val();
		var re_password = $("#re_password").val();
		if (user_password != re_password) {
		        $F.messager.warn("两次输入的密码不一致", {
		            "label": "确定"
		        });
		        return;
		    }
		    if ($.trim(user_password) == "") {
		        $F.messager.warn("密码不能为空", {
		            "label": "确定"
		        });
		        return;
		    }
		    if (user_password.indexOf(" ") != -1) {
		        $F.messager.warn("密码不允许包含空格！", {
		            "label": "确定"
		        });
		        return;
		    }
		$.ajax({
			url:__contextPath + "/security/pwd/checkPassword.d",
			cache:false, 
			async:false,
			data : {
				user_password : user_password
			},
			success:function(data){
				if(data === 'true'){
					$.ajax({
						url:__contextPath + "/security/pwd/editPassword.d",
						cache:false, 
						async:false,
						data : {
							id : $("#userId").val(),
							account : $("#account").val(),
							username : $("#username").val(),
							user_password : user_password,
							re_password : re_password,
						},
						success:function(data){
							window.location.href = __contextPath + "/navigation.d";
						},
						error:function(data){
							alert("修改失败！");
						}
					})
				}
			}
		})
	}
	
	function reset(){
		$("#user_password").val("");
		$("#re_password").val("");
	}