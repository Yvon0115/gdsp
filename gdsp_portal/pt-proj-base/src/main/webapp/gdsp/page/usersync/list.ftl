<#import "/gdsp/tags/castle.ftl" as c>
<script>
function test(){
	$.ajax({url:__contextPath+"/sync/app/getUsers.d",success:function(response){
		if($.jsonEval(response).statusCode == $F.statusCode.ok){
			$F.messager.success("同步临时表成功！");
		}else{
			$F.messager.error("同步临时表失败！");
		}
	}});
}
</script>
<@c.Box>
	<@c.BoxHeader>
		<@c.Button class="" icon="glyphicon glyphicon-wrench" type="primary" click="test();">用户同步接口测试</@c.Button>
	</@c.BoxHeader>
</@c.Box>
