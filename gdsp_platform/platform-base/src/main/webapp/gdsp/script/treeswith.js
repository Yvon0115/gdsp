
/**
 * 编辑时隐藏左树
 * @param id
 * @param type
 */
function divCognosTreeSwith(id) {
	var flag = document.getElementById(id).style.display == 'none';
	document.getElementById(id).style.display = flag ? 'block' : 'none';
	if (!flag) {
		$("#bodyid").removeClass().addClass("col-md-12");
	} else {
		$("#bodyid").removeClass().addClass("col-md-9");
	}

}
