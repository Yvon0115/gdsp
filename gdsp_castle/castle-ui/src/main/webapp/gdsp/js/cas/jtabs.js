/**
 * jstabs本地初始化方法
 */
define(["jquery","cas/core","bootstrap/bootstrap","plugins/jtabs/bootstrap-jtabs","link!plugins/jtabs/bootstrap-jtabs"],function($,$F,$options){
	$.fn.initJtabs = function(){
		$(this).each(function(){
			var $o =$(this);
			var config = {};
			var maxSize = $o.attr("jtabs-size");
			var titlesid = $o.attr("jtabs-titlesid");
			var contentsid = $o.attr("jtabs-contentsid");
			var init = $o.attr("jtabs-init");
			var initTabId = $o.attr("jtabs-init-id");
			var initTabTitle = $o.attr("jtabs-init-title"); 
			var initTabUrl = $o.attr("jtabs-init-url");
			var monitor = $o.attr("jtabs-monitor"); 
			var titlelength = $o.attr("jtabs-titlelength"); 
			//设置最大tab数量
			if(maxSize!=null && maxSize!=""){
				config['maxTabSize'] = parseInt(maxSize);
			}
			config['titlesid']=titlesid!=null?titlesid:"";
			config['contentsid']=contentsid!=null?contentsid:"";
			config['initTabId'] = initTabId;
			config['initTabTitle'] = initTabTitle;
			config['initTabUrl'] = initTabUrl;
			config['monitor'] = monitor;
			config['titlelength'] = titlelength;
			
			$o.jtabs(config);
		});
	};
	return $F;
});
