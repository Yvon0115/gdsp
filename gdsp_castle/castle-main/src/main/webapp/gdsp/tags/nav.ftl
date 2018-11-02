<#macro SideBarMenu menus key="__null_key__" keyField="id" urlField="url" iconField="" openField="" nameField="name" level=0>
	<#local nodes=menus[key]?if_exists>
	<#if !nodes?exists || nodes?size==0>
		<#return >
	</#if>
	<#if !(__navPaths??)>
		<#if Context.requestInfo?? && Context.requestInfo.paths?? && Context.requestInfo.paths?size gt 0>
		    <#assign  __navPaths=Context.requestInfo.paths>
		<#else>
			<#assign  __navPaths=[]>
		</#if>
	</#if>
	<#local hasPath=__navPaths?size gt 0>
	<#local space><#if level gt 0><#list 1..level as l><div class="space"></div></#list></#if></#local>
	<#list nodes as node>
		<#local id=node[keyField]><#t>
		<#local activeFlag=(hasPath && __navPaths[level+1]?exists&&id==__navPaths[level+1])>
		<#local child=menus[id]?default("")><#t>
		<#local hasChild= child?is_indexable && child?size gt 0>
		<#if iconField!="" && node[iconField]?? && node[iconField]!="">
			<#local icon=node[iconField]/>
		<#elseif node["funtype"] == 0>
			<#local icon="fa fa-folder-o"/>
		<#else>
			<#local icon="fa fa-file-o"/>
		</#if>
		<#if openField!="" && node[openField]??>
			<#local openMode=node[openField]/>
		<#else>
			<#local openMode="default"/>
		</#if>
		<#if hasChild>
			<#--<li<#if level == 0> class="treeview${activeFlag?string(" active","")}"</#if>>-->
			<li class="treeview${activeFlag?string(" active","")}" >
                <a href="#" title="${node[nameField]}">${space}<#if level==0><#if iconField!="" && node[iconField]?? && node[iconField]!=""><img src="${icon}" style="margin-right:4%;" ><#else><i class="${icon}"></i></#if></#if><span>${node[nameField]}</span><i class="fa fa-angle-left pull-right"></i>
                </a>
                <ul class="treeview-menu${activeFlag?string(" menu-open","")}">
	                <@SideBarMenu menus=menus key=id keyField=keyField urlField=urlField iconField=iconField openField=openField nameField=nameField level=level+1/>
				</ul>
			</li>
		<#else>
			<#if node[urlField]??&&node[urlField]?length gt 0><#local url="${ContextPath}/${node[urlField]}"><#else><#local url="javascript:void(0)"></#if>
        <li${activeFlag?string(" class=\"active\"","")}>
        	<#if __modeRefreshMain=='no'><a href="${url}" openMode="${openMode}" title="${node[nameField]}">
        	<#else>
        		<a href="javascript:void(0)" jtabs-addtab jtab-id='${node[keyField]}' jtab-title='${node[nameField]}' jtab-url='${url}' title="${node[nameField]}">
        	</#if>${space}<#if level== 0><#if iconField!="" && node[iconField]?? && node[iconField]!=""><img src="${icon}" style="margin-right:4%;" ><#else><i class="${icon}"></i></#if></#if><span>${node[nameField]}</span></a>
        </li>
		</#if>
	</#list>
</#macro>

<#macro CsdcSideBarMenu menus key="__null_key__" keyField="id" urlField="url" iconField="" openField="" nameField="name" level=0>
	<#local nodes=menus[key]?if_exists>
	<#if !nodes?exists || nodes?size==0>
		<#return >
	</#if>
	<#if !(__navPaths??)>
		<#if Context.requestInfo?? && Context.requestInfo.paths?? && Context.requestInfo.paths?size gt 0>
		    <#assign  __navPaths=Context.requestInfo.paths>
		<#else>
			<#assign  __navPaths=[]>
		</#if>
	</#if>
	<#local hasPath=__navPaths?size gt 0>
	<#local space><#if level gt 0><#list 1..level as l><div class="space"></div></#list></#if></#local>
	<#local i = 0>
	<#list nodes as node>
		<#local id=node[keyField]><#t>
		<#local activeFlag=(hasPath && __navPaths[level+1]?exists&&id==__navPaths[level+1])>
		<#local child=menus[id]?default("")><#t>
		<#local hasChild= child?is_indexable && child?size gt 0>
		<#if iconField!="" && node[iconField]??>
			<#local icon=node[iconField]/>
		<#elseif node["funtype"] == 0>
			<#local icon="fa fa-folder-o"/>
		<#else>
			<#local icon="fa fa-file-o"/>
		</#if>
		<#if openField!="" && node[openField]??>
			<#local openMode=node[openField]/>
		<#else>
			<#local openMode="default"/>
		</#if>
		<#if hasChild>
			<#--<li<#if level == 0> class="treeview${activeFlag?string(" active","")}"</#if>>-->
			<li class="<#if level == 0>defActive</#if> ${activeFlag?string(" active open","")}" >
                <a href="#" title="${node[nameField]}" class="<#if level==0>titleTd<#else>titleTwo</#if>" style="color: #eee;"><#if level==0><i class="icon_1"></i></#if><span class="title">${node[nameField]}</span>
                </a>
                <ul class="sub_menu" style="display: ${activeFlag?string("block","")}">
	                <@CsdcSideBarMenu menus=menus key=id keyField=keyField urlField=urlField iconField=iconField openField=openField nameField=nameField level=level+1/>
				</ul>
			</li>
		<#elseif level == 0>
			<#if node[urlField]??&&node[urlField]?length gt 0><#local url="${ContextPath}/${node[urlField]}"><#else><#local url="javascript:void(0)"></#if>
			<li class="defActive ${activeFlag?string(" active open","")}" <#if __modeRefreshMain=='yes'> id="li_${node[keyField]}" </#if>>
				<#if __modeRefreshMain=='no'>
					<a href="${url}" title="${node[nameField]}" class="<#if level==0>titleTd<#else>titleTwo</#if>" style="color: #eee;"><#if level==0><i class="icon_1"></i></#if><span class="title">${node[nameField]}</span>
                	</a>
	        	<#else>
	        		<a href="javascript:void(0)" jtabs-addtab jtab-id='${node[keyField]}' jtab-title='${node[nameField]}' jtab-url='${url}' title="${node[nameField]}" class="titleTd" style="color: #eee;"><#if level==0><i class="icon_1"></i></#if><span class="title">${node[nameField]}</span>
                	</a>
	        	</#if>
			</li>
		<#else>
			<#if node[urlField]??&&node[urlField]?length gt 0><#local url="${ContextPath}/${node[urlField]}"><#else><#local url="javascript:void(0)"></#if>
	        <li class="${activeFlag?string("open","")}" <#if __modeRefreshMain=='yes'> id="li_${node[keyField]}" </#if> >
	        	<#if __modeRefreshMain=='no'><a href="${url}" openMode="${openMode}" title="${node[nameField]}" class="<#if level == 1> titleTwo round <#else>titleThree round3</#if>">
	        	<#else>
	        		<a href="javascript:void(0)" jtabs-addtab jtab-id='${node[keyField]}' jtab-title='${node[nameField]}' jtab-url='${url}' title="${node[nameField]}" class="<#if level == 1> titleTwo round <#else>titleThree round3</#if>">
	        	</#if><span>${node[nameField]}</span></a>
	        </li>
		</#if>
		<#local i = i+1>
	</#list>
</#macro>

<#macro RmsCsdcSideBarMenu menus key="__null_key__" keyField="id" urlField="url" iconField="" openField="" nameField="name" level=0>
	<#local nodes=menus[key]?if_exists>
	<#if !nodes?exists || nodes?size==0>
		<#return >
	</#if>
	<#if !(__navPaths??)>
		<#if Context.requestInfo?? && Context.requestInfo.paths?? && Context.requestInfo.paths?size gt 0>
		    <#assign  __navPaths=Context.requestInfo.paths>
		<#else>
			<#assign  __navPaths=[]>
		</#if>
	</#if>
	<#local hasPath=__navPaths?size gt 0>
	<#local space><#if level gt 0><#list 1..level as l><div class="space"></div></#list></#if></#local>
	<#local i = 0>
	<#list nodes as node>
		<#local id=node[keyField]><#t>
		<#local activeFlag=(hasPath && __navPaths[level]?exists&&id==__navPaths[level])>
		<#local child=menus[id]?default("")><#t>
		<#local hasChild= child?is_indexable && child?size gt 0>
		<#if iconField!="" && node[iconField]??>
			<#local icon=node[iconField]/>
		<#elseif node["funtype"] == 0>
			<#local icon="fa fa-folder-o"/>
		<#else>
			<#local icon="fa fa-file-o"/>
		</#if>
		<#if openField!="" && node[openField]??>
			<#local openMode=node[openField]/>
		<#else>
			<#local openMode="default"/>
		</#if>
		<#if hasChild>
			<#--<li<#if level == 0> class="treeview${activeFlag?string(" active","")}"</#if>>-->
			<li class="<#if level == 0>defActive</#if> ${activeFlag?string(" active open","")}" >
                <a href="#" title="${node[nameField]}" class="<#if level==0>titleTd<#else>titleTwo</#if>" style="color: #eee;"><#if level==0><i class="icon_1"></i></#if><span class="title">${node[nameField]}</span>
                </a>
                <ul class="sub_menu" style="display: ${activeFlag?string("block","")}">
	                <@RmsCsdcSideBarMenu menus=menus key=id keyField=keyField urlField=urlField iconField=iconField openField=openField nameField=nameField level=level+1/>
				</ul>
			</li>
		<#elseif level == 0>
			<#if node[urlField]??&&node[urlField]?length gt 0><#local url="${ContextPath}/${node[urlField]}"><#else><#local url="javascript:void(0)"></#if>
			<li class="defActive ${activeFlag?string(" active open","")}" >
				<#if __modeRefreshMain=='no'>
					<a href="${url}" title="${node[nameField]}" class="<#if level==0>titleTd<#else>titleTwo</#if>" style="color: #eee;"><#if level==0><i class="icon_1"></i></#if><span class="title">${node[nameField]}</span>
                	</a>
	        	<#else>
	        		<a href="javascript:void(0)" jtabs-addtab jtab-id='${node[keyField]}' jtab-title='${node[nameField]}' jtab-url='${url}' title="${node[nameField]}" class="titleTd" style="color: #eee;"><#if level==0><i class="icon_1"></i></#if><span class="title">${node[nameField]}</span>
                	</a>
	        	</#if>
			</li>
		<#else>
			<#if node[urlField]??&&node[urlField]?length gt 0><#local url="${ContextPath}/${node[urlField]}"><#else><#local url="javascript:void(0)"></#if>
	        <li class="${activeFlag?string("open","")}">
	        	<#if __modeRefreshMain=='no'><a href="${url}" openMode="${openMode}" title="${node[nameField]}" class="<#if level == 1> titleTwo round <#else>titleThree round3</#if>">
	        	<#else>
	        		<a href="javascript:void(0)" jtabs-addtab jtab-id='${node[keyField]}' jtab-title='${node[nameField]}' jtab-url='${url}' title="${node[nameField]}" class="<#if level == 1> titleTwo round <#else>titleThree round3</#if>">
	        	</#if><span>${node[nameField]}</span></a>
	        </li>
		</#if>
		<#local i = i+1>
	</#list>
</#macro>
