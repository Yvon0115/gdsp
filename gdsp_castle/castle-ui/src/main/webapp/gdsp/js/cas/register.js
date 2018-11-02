define(["jquery","cas/core","cas/options"],function($,$F,$options){
	function initComponent(inits,$box){
		$.each(inits,function(i,init){
			/*m:method,o:jquery object,p:parameter*/
			var m = init.m, o = init.o,	p = init.p;
			if(o===true){
				if(p === true)
					m();
				else
					m(p);
			}else{
				if(p === true)
					o[m]();
				else
					o[m](p);
			}
		});
		$box.triggerHandler($F.eventType.pageLoad);
	}
	$F.regInitMethod(function($box){
		var filter = $options.register.filter
			,inits=[]
			,requires=[];
		$.each($options.components,function(name,desc){
			var $comps = $(desc[0],$box);
			if(filter)
				$comps = $comps.filter(filter);
			if($comps.length>0){
				if(desc.length>2){
					/*m:method,o:jquery object,p:parameter*/
					var m=desc[2]
						,o=$.isFunction(m)||$comps
						,p= desc.length <= 3||desc[3];
					inits.push({m:m,o:o,p:p});
				}
				if(desc.length>1){
					var rs = desc[1];
					if($.isArray(rs)){
						requires = requires.concat(rs);
					}else{
						requires.push(rs);
					}
				}
			}
		});

		/*统一初始化*/
		if(requires.length>0){
			require(requires,function(){
				initComponent(inits,$box)
			});
		}else{
			initComponent(inits,$box)
		}
	});
});