	$(function(){
		$("#timeLimit").on("input propertychange",function(){
			var va = $("#timeLimit").val();
			var regex= /^(0|\+?[1-9][0-9]*)$/;
			var lengthMatch = va.match(regex);
			if (lengthMatch == null) {
				$("#timeLimit").val("");
			}
		});
	
		$("#pwdLength").on("input propertychange",function(){
			var va = $("#pwdLength").val();
			var regex= /^(0|\+?[1-9][0-9]*)$/;
			var lengthMatch = va.match(regex);
			if (lengthMatch == null) {
				$("#pwdLength").val("");
			}
		});	
	})
