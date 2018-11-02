
/**
 * 收藏夹
 * @param event
 * @returns
 */
function toFavorites(event, widget_id) {
	$.openModalDialog({
		"href" : __contextPath + "/portal/favorites/toFavorites.d?widget_id=" + widget_id,
		"data-target" : "#favoritesdlg",
		"showCallback" : function() {
		}
	});
	event.preventDefault();
	event.stopPropagation();
}

function selecfavoritiestNode(e){
	var id =e.link.attr("value");
	$("#dirId").val(id);
}

function afterSaveFavorites()
{
	$("#favoritesdlg").modal('hide');
}