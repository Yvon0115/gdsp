require(
	 [
	 	'ckeditor/ckeditor'
	 ],	
	 
	 function(){	
		
	}
	)

	function saveform() {
	    for ( instance in CKEDITOR.instances )
	        CKEDITOR.instances[instance].updateElement();
		
		
		var peicision = $("#peicision").val();
			if(peicision == null || peicision == "") {
				peicision = 2;
		}
		$("#peicision").val(peicision);
		$("#indexInfoForm").submit();	
	
	}
if("undefined" != typeof CKEDITOR)
	CKEDITOR.replace('businessbore');

