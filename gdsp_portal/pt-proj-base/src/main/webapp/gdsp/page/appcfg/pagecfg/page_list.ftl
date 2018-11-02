<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/appcfg/pagecfg/pagecfg" />
<@c.Hidden name="dir_id" id="dir_id" value=""/>
<@c.Tabs>
	<@c.Tab id="mainPanel" active=true>
		<div class="col-md-3 no-padding">
			<@c.Box>
				<@c.BoxHeader class="border header-bg">
					<@c.Button type="primary" icon="glyphicon glyphicon-plus" click="newDir('${dir_id?if_exists}')">添加</@c.Button>
					<@c.Button icon="glyphicon glyphicon-pencil" click="editDir('${dir_id?if_exists}')">修改</@c.Button>
					<@c.Button icon="glyphicon glyphicon-trash" click="delDir('${dir_id?if_exists}')">删除</@c.Button>
				</@c.BoxHeader>
				<@c.BoxBody class="no-padding scrollbar" style="height:450px;">
					<@c.TableLoader id="dirsContent" url="${ContextPath}/appcfg/pagecfg/listDirsData.d" >
						<#include "dirs_tree.ftl">	
					</@c.TableLoader>
				</@c.BoxBody>
			</@c.Box>
		</div>
		<div class="col-md-9 no-padding">
			<@c.Box class="box-right">
				<@c.BoxHeader class="border header-bg">
					<@c.Button type="primary" icon="glyphicon glyphicon-plus" click="newPage('${dir_id?if_exists}')">添加</@c.Button>
					<@c.Button icon="glyphicon glyphicon-sort" click="sort('${dir_id?if_exists}');">排序</@c.Button>
					<@c.Search class="pull-right" id="pageSearch" target="#pageContent" events="" conditions="page_name" placeholder="页面名称" />
				</@c.BoxHeader>
				<@c.BoxBody>
					<@c.TableLoader id="pageContent" url="${ContextPath}/appcfg/pagecfg/listPageData.d?dir_id=${dir_id?if_exists}">
						<#include "page_list_data.ftl">
					</@c.TableLoader>		
				</@c.BoxBody>
				<@c.BoxFooter>
					<@c.Pagination class="pull-right" target="#pageContent" page=pageVOs?default("") />
				</@c.BoxFooter>
			</@c.Box>
		</div>
	</@c.Tab>
	<@c.Tab id="detailPanel">
	</@c.Tab>
</@c.Tabs>