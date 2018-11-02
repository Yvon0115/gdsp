<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/user/distribute" id="" onload="" />
<@c.Box>
	<@c.BoxHeader>
		<@c.Button class="" icon="glyphicon glyphicon-wrench" type="primary" click="multiDistribute();">批量分配</@c.Button>
	</@c.BoxHeader>
	<@c.BoxBody>
		<@c.TableLoader id="userContent" initload=false url="${ContextPath}/user/distributeOrg/listData.d">
			<#include "list-data.ftl">
		</@c.TableLoader>
	</@c.BoxBody>
	<@c.BoxFooter>
		<@c.Pagination class="pull-right" target="userContent" page=userPageData?default("") />
	</@c.BoxFooter>
</@c.Box>
<@c.Hidden name="allocateTreeId" value=""/>
