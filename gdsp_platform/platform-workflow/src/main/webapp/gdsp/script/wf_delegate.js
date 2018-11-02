function checkBoxClick(btn){
	//alert($("[name='accept']").size());
	//每次点击时候先把所有的checkbox选中状态清空
	$("[name='accept']").each(function(i){
		this.checked = false;
	})
	//然后选中当前点击的checkbox
	btn.checked = true;
}

/*function saveAccept(){
	var $checkBox = $('#acceptsContent input:checkbox:checked');
	alert($checkBox.val());
	var acceptString =$checkBox.val();
	
	var $name = $checkBox.parent().next().next();
	var nameString = $name.html();
	
	//全局方法，关闭顶层的对话框
	//并将数据缓存到对话框中
	$.closeReference({value:[acceptString],text:[nameString]});
}*/

function saveAccept(acceptString,nameString){
	$.closeReference({value:[acceptString],text:[nameString]});
}

function saveDeployment(){
	var $checkBox = $('#deploymentsContent input:checkbox:checked');
	var list=[];
	$checkBox.each(function(i){
		list.push($(this).val());
	});
	var deploymentIdsString =list.join(",")
	
	var nlist = [];
	$checkBox.each(function(i){
	    var $name = $(this).parent().next().next();
	    nlist.push($name.html());
	});
	
	var deploymentNames =nlist.join(",");
	$.closeReference({value:[deploymentIdsString],text:[deploymentNames]});
}