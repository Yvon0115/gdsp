define([ "cas/modal","cas/combobox"], function() {
	var funcs = {
		exportIdx: function(){
			var url = __contextPath +"/index/indexmanager/doExportIdx.d";
			window.location=url;
		},
		redirectMain: function() {
			window.location.href = __contextPath +"/index/indexmanager/list.d";
		}
	}
	return funcs;
})
