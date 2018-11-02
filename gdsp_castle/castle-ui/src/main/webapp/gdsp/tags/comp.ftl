<#-- 主要页面控件 -->
<#global EMPTY={"empty":true}/>
<#global EMPTYA=[-9999]/>
<#setting url_escaping_charset='utf-8'>
<#--js引入标签-->
<#macro Script id="" src="" onload="" >
<input type="hidden" name="jsRequire" ${attrs({"id":id,"value":src,"onload":onload})}/>
</#macro>

<#--搜索框-->
<#macro Search target conditions id="" class="" iclass="" placeholder="搜索" events="">
<div id="${id}" class="has-feedback search ${class}" ${attrs({"id":id,"loader-target":target,"events":events})}>
    <input type="text" class="form-control ${iclass}" placeholder="${placeholder}" cons="${conditions}"/>
    <span class="form-control-feedback pointer"><i class="glyphicon glyphicon-search"></i></span>
</div>
</#macro>

<#--按钮组-->
<#macro ButtonGroup class="">
<div class="btn-group ${class}">
    <#nested >
</div>
</#macro>

<#--按钮-->
<#macro Button id="" type="default" submit=false size="default" class="" icon="" click="" disabled=false style="" title="" action=[] mouseovervalidate=false>
    <#if size=="default"><#local size=""><#else><#local size="btn-"+size></#if>
    <#assign onmouseoverStr = "">
    <#if mouseovervalidate> 
    	<#assign onmouseoverStr = "onmouseover=\"$('input').blur();\"">
    </#if>
    <button <#if id!="">id=${id}</#if> type="${submit?string("submit","button")}" ${onmouseoverStr} class="btn btn-${type} ${size} ${class}" ${attrs({"style":style,"title":title,"click":click})} <#if disabled>disabled</#if>  ${action?join("")}><#if icon!=""><i class="${icon}"></i></#if><#nested ></button>
</#macro>

<#--链接-->
<#macro Link  class="" icon="" click=""  style="" title="" action=[]>
    <#local astr=action?join("")>
    <#if astr?index_of("href=") gte 0>
        <#local t="">
    <#else>
        <#local t=" href=\"javascript:void(0)\"">
    </#if>
<a class="link ${class}" ${attrs({"style":style,"title":title,"click":click})} ${astr}${t}><#if icon!=""><i class="${icon}"></i></#if><#nested></a>
</#macro>

<#--全选复选框-->
<#macro CheckAll selector box="" parents="" checked=false>
<input type="checkbox" class="cas-checker" checker-finder="${selector}" checker-box="${box}" checker-parents="${parents}" <#if checked>checked</#if>/>
</#macro>

<#--表格加载容器-->
<#macro TableLoader id="" class="" url="" style="" initload=false >
<div id="${id}" class="dataloader ${class}" url="${url}" style="${style}" ${attr("initialLoad",initload)}>
    <#nested >
</div>
</#macro>
<#--普通表格-->
<#macro Table id="" class="" triped=true hover=true>
    <table id="${id}" class="table<#if hover> table-hover</#if><#if triped> table-striped</#if> ${class}">
        <#nested >
    </table>
</#macro>
<#--简单表格-->
<#macro SimpleTable id=""  class="" checkbox=false checkboxfield="id" checkedfield="" checkboxName="id" striped=true hover=true border=false highlight=false titles=[] keys=[] data=[] ellipsis={}>
<table id="${id}" class="table simpletable${hover?string(" table-hover","")}${striped?string(" table-striped","")}${border?string(" table-bordered","")}${highlight?string(" table-highlight","")} ${class}">
    <tbody><#local cols=titles?size>
    <#if cols gt 0>
    <tr id="0">
        <#if checkbox><#local cols=cols+1><th class="checkbox-col"><@CheckAll selector="input:checkbox[name=${checkboxName}]" parents="table:first"/></th></#if>
        <#list titles as t>
            <#if t?starts_with("<")>
            	${t}
            <#else>
             	<th>${t}</th>
            </#if>
        </#list>
    </tr>
    </#if>
    <#if data?size gt 0>
    <#local templ = "">
    <#list keys as key>
        <#if key?starts_with("#")>
            <#local templ = templ+key?substring(1)>
        <#elseif key?starts_with("!")>
            <#local tk=key?substring(1)>
            <#if ellipsis[key]?exists>
            <#local templ = templ+"<td title=\"${tk?html}\"><div class=\"ellipsis\" style=\"max-width:"+ellipsis[key]+";\">"+tk+"</div></td>">
            <#else>
            <#local templ = templ+"<td>"+tk+"</td>">
            </#if>
        <#else>
            <#if ellipsis[key]?exists>
                <#if key?index_of(".") gt -1>
                    <#local ks = key?split(".")>
                    <#local kk = "['"+ks?join("']?if_exists['")+"']">
                    <#local templ = templ+r"<td title='${row"+kk+"?if_exists?html}'><div class='ellipsis' style='max-width:"+ellipsis[key]+r";'>${row"+kk+"?if_exists?html}</div></td>">
                <#else>
                    <#local templ = templ+r"<td title='${row['"+key+"']?if_exists?html}'><div class='ellipsis' style='max-width:"+ellipsis[key]+r";'>${row['"+key+"']?if_exists?html}</div></td>">
                </#if>
            <#else>
                <#if key?index_of(".") gt -1>
                    <#local ks = key?split(".")>
                    <#local kk = "['"+ks?join("']?if_exists['")+"']">
                    <#local templ = templ+r"<td>${row"+kk+"?if_exists?html}</td>">
                <#else>
                    <#local templ = templ+r"<td>${row['"+key+"']?if_exists?html}</td>">
                </#if>
            </#if>
        </#if>
    </#list>
        <#assign inlineTemplate = templ?interpret>
        <#list data as row>
        <tr id=${row_index+1}>
        <#if checkbox><td><input id=${row_index+1} type="checkbox" name="${checkboxName}" value="${row[checkboxfield]}"<#if checkedfield!="" && booleanValue(row[checkedfield]?default(false))> checked</#if>></td></#if>
            <@inlineTemplate />
        </tr>
    </#list>
    <#else>
    <tr><td colspan="${cols}" align="center">暂无数据</td></tr>
    </#if>
    </tbody>
</table>
</#macro>
<#--简单树表-->
<#--拓展simpleTable，target焦点为tableloader的id-->
<#macro SimpleTreeTable id="" url="" target="" class="" isAsync=false controlCol=0 pIdField="parentid" rootName="chinasofti" checkbox=false checkboxfield="id" checkedfield="" checkboxName="id" treeTable=true striped=true hover=true border=false highlight=false titles=[] keys=[] data=[] ellipsis={}>
<#local keyString = "">
<#list keys as k>
    <#if keys[k_index+1]??>
        <#local keyString = keyString+k+r",">
    <#else>
        <#local keyString = keyString+k>    
    </#if>
</#list>
<#if checkbox>
	<#local column = controlCol+1>
<#else>
	<#local column = controlCol>
</#if>
<table id="${id}" url="${url}" treetable-target="${target}" <#if isAsync>isAsync</#if> rootName="${rootName}" column="${column}" controlCol="${controlCol}" checkboxName="${checkboxName}" checkboxfield="${checkboxfield}" keys="${keyString}" ${attr("ellipsis",toJsonString(ellipsis))} ${checkbox?string("checkbox","")} class="table${treeTable?string(" table-treeTable","")}${hover?string(" table-hover","")}${striped?string(" table-striped","")}${border?string(" table-bordered","")}${highlight?string(" table-highlight","")}  ${class}">
    <thead><#local cols=titles?size>
    <#if cols gt 0>
    <tr>
        <#if checkbox><#local cols=cols+1><th class="checkbox-col" style="width:50px;"><@CheckAll selector="input:checkbox[name=${checkboxName}]" parents="table:first"/></th></#if>
        <#list titles as t>
            <#if t?starts_with("<")>
                ${t}
            <#else>
                <th>${t}</th>
            </#if>
        </#list>
    </tr>
    </#if>
	</thead>
	<tbody>
    <#if data?size gt 0>
    <#local templ = "">
    <#list keys as key>
        <#if ellipsis[key]?exists>
            <#if key?index_of(".") gt -1>
                <#local ks = key?split(".")>
                <#local kk = "['"+ks?join("']?if_exists['")+"']">
                <#if key_index == controlCol>
                	<#local templ = templ+r"<td title='${row"+kk+"?if_exists?html}'><div class='ellipsis' style='max-width:"+ellipsis[key]+r";'><span controller='true'></span>${row"+kk+"?if_exists?html}</div></td>">
                <#else>
                	<#local templ = templ+r"<td title='${row"+kk+"?if_exists?html}'><div class='ellipsis' style='max-width:"+ellipsis[key]+r";'>${row"+kk+"?if_exists?html}</div></td>">
                </#if>
            <#else>
            	<#if key_index == controlCol>
                	<#local templ = templ+r"<td title='${row['"+key+"']?if_exists?html}'><div class='ellipsis' style='max-width:"+ellipsis[key]+r";'><span controller='true'></span>${row['"+key+"']?if_exists?html}</div></td>">
                <#else>
                	<#local templ = templ+r"<td title='${row['"+key+"']?if_exists?html}'><div class='ellipsis' style='max-width:"+ellipsis[key]+r";'>${row['"+key+"']?if_exists?html}</div></td>">
                </#if>
            </#if>
        <#else>
            <#if key?index_of(".") gt -1>
                <#local ks = key?split(".")>
                <#local kk = "['"+ks?join("']?if_exists['")+"']">
                <#if key_index == controlCol>
                	<#local templ = templ+r"<td><span controller='true'></span>${row"+kk+"?if_exists?html}</td>">
                <#else>
                	<#local templ = templ+r"<td>${row"+kk+"?if_exists?html}</td>">
                </#if>
            <#else>
            	<#if key_index == controlCol>
                	<#local templ = templ+r"<td><span controller='true'></span>${row['"+key+"']?if_exists?html}</td>">
                <#else>
                	<#local templ = templ+r"<td>${row['"+key+"']?if_exists?html}</td>">
                </#if>
            </#if>
        </#if>
    </#list>
        <#assign inlineTemplate = templ?interpret>
        <#list data as row>
            <tr id="${row[checkboxfield]}" pId="${row[pIdField]!""}">
            <#if checkbox><td><input type="checkbox" name="${checkboxName}" value="${row[checkboxfield]}"<#if checkedfield!="" && booleanValue(row[checkedfield]?default(false))> checked</#if>></td></#if>
                <@inlineTemplate />
            </tr>
            <#nested>
        </#list>
    <#else>
    <tr><td colspan="${cols}" align="center">暂无数据</td></tr>
    </#if>
    </tbody>
</table>
</#macro>
<#-- 简单网格simpleGrid，原生pqGrid网格集成 -->
<#-- target属性焦点为tableloader的id(非dataloader加载赋值为空)，data为后台model中装填的json数据 -->
<#macro SimpleGrid id target data rpcParam="checkedIds" class="" gtitle="" width="" height="" minWidth="" style="" freezeCols="" freezeRows="" hoverMode=""
 showcollap=false collapsible=true numCellShow=false draggable=false sortable=true localPaging=false editable=false disable=false autoFitable=true columnBorders=true resizable=true flexWidth=true flexHeight=true hwrap=false wrap=false virtualX=true virtually=true showHeader=true showBottom=false showTitle=false showTop=true showToolbar=true checkable=false>
 	<#-- 网格隐藏域，存储选中id，可用于远程操作页面参数获取 -->
 	<@Hidden name="${rpcParam}" value="" />
	<div id="${id}" class="simpleGrid ${class}" cas-simpleGrid="${target}" ${attr("grid-data",data)} rpcParam="${rpcParam}" gtitle="${gtitle}" width="${width}" height="${height}" minWidth="${minWidth}" style="${style}" freezeCols="${freezeCols}" freezeRows="${freezeRows}" hoverMode="${hoverMode}"
	 <#if showcollap> showcollap</#if> <#if collapsible> collapsible</#if> <#if !numCellShow> numCellShow</#if> <#if draggable> draggable</#if> <#if sortable> sortable</#if> <#if localPaging> pageable</#if> <#if !editable> editable</#if> <#if autoFitable> autoFitable</#if> <#if disable> disable</#if> <#if columnBorders> columnBorders</#if> <#if resizable> resizable</#if>
	  <#if flexWidth> flexWidth</#if> <#if flexHeight> flexHeight</#if> <#if !hwrap> hwrap</#if> <#if !wrap> wrap</#if> <#if virtualX> virtualX</#if> <#if virtually> virtually</#if> <#if showHeader> showHeader</#if> <#if showBottom> showBottom</#if> <#if !showTitle> showTitle</#if> <#if showTop> showTop</#if> <#if showToolbar> showToolbar</#if> <#if checkable> checkable</#if>>
		<#nested>
	</div>
</#macro>
<#--分页数据-->
<#macro PageData page>
<#if page==""><#local page={"number":0,"size":10,"totalElements":0}></#if>
<div pageno="${page.number}" pagesize="${page.size}" totalcount="${page.totalElements}" style="display:none;"></div>
</#macro>
<#--分页控件-->
<#macro Pagination target page label="" sizeOption="" jumpAction="" class="" box="" style="">
<#if page==""><#local page={"number":0,"size":10,"totalElements":0}></#if>
<div class="cas-page clearfix ${class}" loader-target="${target}" pageno="${page.number?string}" style="${style}" totalcount="${page.totalElements}" pagesize="${page.size}" pagelabel="${label}" pagesizeoption="${sizeOption}" pageJump="${jumpAction}" page-box="${box}">
</div>
<#if jumpAction != "">
<div id="${jumpAction}" class="cas-page clearfix pageplugin-position ${class}"></div>
</#if>
<#if label != "">
<div class="cas-page clearfix page-label ${class}"><p id="${label}"></p></div>
</#if>
<#if sizeOption != "">
<div id="${sizeOption}" class="cas-page clearfix pageplugin-position ${class}"></div>
</#if>
</#macro>

<#--tab页签相关-->
<#--tab页签集-->
<#macro Tabs id="" class="" ulclass="" header=true style="">
    <#if _tabpanes??>
        <#local _tempheads=_tabheads>
        <#local _temppanes=_tabpanes>
        <#local _tempshowcounts=_tabheadershowcount>
    </#if>
    <#assign _tabheads=[]/>
    <#assign _tabpanes=[]/>
    <#assign _tabheadershowcount=0/>
    <#nested>

    <div class="tabs ${class}" ${attr("style",style)}>
        <ul class="nav nav-tabs oneline-tabs ${ulclass}" <#if _tabheadershowcount ==0 || !header>style="display:none"</#if>>
            <#list _tabheads as h>
            ${h}
            </#list>
        </ul>
    <#if _tabpanes?size gt 0>
        <div class="tab-content">
            <#list _tabpanes as p>
            ${p}
            </#list>
        </div>
    </#if>
    </div>
    <#if _temppanes??>
        <#assign _tabheads=_tempheads>
        <#assign _tabpanes=_temppanes>
        <#assign _tabheadershowcount=_tempshowcounts>
    </#if>
</#macro>
<#--tab页签对象-->
<#macro Tab  id class="" title="" tabclass="" active=false url="#" reload=false events="" style="">

<#local head>
    <li class="${tabclass} <#if active>active</#if>" <#if title == "" && style == "">style="display:none"</#if><#if style != "">style="${style}"</#if>><a id="${id}" <#if style != "">style="${style}"</#if> data-toggle="tab"  ${attrs({"href":url,"data-target":"#"+id+"-tabpane","reload":reload,"events":events})}><div class="ellipsis">${title}</div></a></li>
</#local>
<#if title != "">
<#assign _tabheadershowcount=_tabheadershowcount+1>
</#if>
<#assign _tabheads=_tabheads+[head]>

<#local pane>
    <div id="${id}-tabpane" class="tab-pane ${class}<#if active> active</#if>">
        <#nested >
    </div>
</#local>
<#assign _tabpanes=_tabpanes+[pane]>
</#macro>

<#--SimpleTree简单树-->
<#macro TreeNode class="" style="" url="" value="" icon="" label="" expandIcon="" nclass="" nstyle="" hasIcon=true checkbox=false checked=false  expand=1 ckname="" ckvalue=""><#t>
<li ${checked?string(" ckbox=\"true\"","")}${checkbox?string(" hasCheck=\"true\"","")}${hasIcon?string(" hasIcon=\"true\"","")}  ${attrs({"class":nclass,"icon":icon,"expandIcon":expandIcon,"ckname":ckname,"ckvalue":ckvalue,"style":style})}><a ${attrs({"class":nclass,"style":nstyle,"url":url,"value":value})}>${label}</a><ul><#nested ></ul></li>
</#macro>

<#macro TreeListBuilder nodes codeField="innercode" valueField="id" nameField="name" nexp="" linkexp="" labelexp="" titleShowWidth="180px"><#t>
    <#local level=1>
    <#local NodeTmp = nexp?interpret>
    <#local LinkTmp = linkexp?interpret>
    <#if labelexp=="">
    <#local labelexp=r"${node[nameField]}">
    </#if>
    <#local LabelTmp = labelexp?interpret>
    <#list nodes as node>
        <#local nclen= node[codeField]?length>
        <#local cur= nclen/4>
        <#local end= level-cur>
        <#local liAttr = "">
        <#if (end > 0) >
            <#list 1..end as i>
                </ul>
            </li>
            </#list>
        </#if>
        <#local level = cur>
        <#local nextIdx = node_index+1>
            <li <@NodeTmp/>><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a>
        <#if nextIdx lt nodes?size && nclen lt nodes[nextIdx][codeField]?length>
            <#local level = level + 1>
        <ul>
        </#if>
    </#list>
    <#list 1..level as i>
    </ul>
    </li>
    </#list>
</#macro>
<#--为树的li(整行)增加事件lievents 在simpletree中将此属性移到复选框中，扩展复选框事件。-->
<#--属性isDis配合disableField属性，需要设置树的属性为不可选状态时设置此属性为true-->
<#--增加不可选中状态disableField,默认都是可选择的（鼠标悬停小手,点击有背景色）,传递VO对应的字段，该字段结果中N为不可选，Y为可选.-->
<#--titleShowWidth标题显示的长度，超过此长度用...代替，默认180px.示例：titleShowWidth="200px"-->
<#macro TreeMapBuilder map key="__null_key__" lievents="" isDis=false disableField="N" idField="id" valueField="id" nameField="name" nexp="" linkexp="" labelexp="" titleShowWidth="180px"><#t>
    <#if !(map[key]?exists)>
        <#return >
    </#if>
    <#local nodes=map[key]>
    <#if !nodes?exists || nodes?size==0>
        <#return >
    </#if>
    <#local NodeTmp = nexp?interpret>
    <#local LinkTmp = linkexp?interpret>
    <#local LiTmp = lievents?interpret>
    <#if labelexp=="">
        <#local labelexp=r"${node[nameField]}">
    </#if>
    <#local LabelTmp = labelexp?interpret>
    <#list nodes as node>
        <#local id=node[idField]><#t>
        <#local child=map[id]!""><#t>
        <#--增加判断，如果不是最后一个节点不增加lievents属性，配合checkOption="3"(只有末级才有复选框)的情况使用，如果有特殊需求可根据逻辑自行变更-->
    	<#if child?is_indexable && child?size gt 0>
	    	<li <@NodeTmp/> <#if isDis && "${node[disableField]}"=="N"> dis="${node[disableField]}" style="cursor:default;"<#elseif isDis><@LiTmp/></#if>><a value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a><#t>
    	<#elseif isDis && "${node[disableField]}"=="N">
	    	<li <@NodeTmp/> dis="${node[disableField]}" style="cursor:default;"><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a><#t>
    	<#else>
	    	<li  <@LiTmp/> <@NodeTmp/>><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a><#t>
        </#if>
        <#if child?is_indexable && child?size gt 0>
        	<ul>
                <@TreeMapBuilder map=map key=id isDis=isDis disableField=disableField lievents=lievents idField=idField valueField=valueField nameField=nameField nexp=nexp linkexp=linkexp labelexp=labelexp titleShowWidth=titleShowWidth/>
            </ul>
        </#if>
        </li>
    </#list>
</#macro>
<#--基于Bootstrap-treeview构建树-->
<#--需要配置的属性：showCheckbox : true,showTags: false,multiSelect:"",//是否可多选，默认false,levels:"",//设置默认展开到的层级,aloneEvents是否为节点单独设置调用事件-->
<#macro EasyTreeBuilder url id="" searchAble=false showCheckbox=false showTags=false multiSelect=false selectFirstNode=false levels="" events="" checkOption="" showBorder=false><#t>
	<#if searchAble>
	  	<input type="input" class="form-control" id="${id}input-search" placeholder="请输入搜索内容..." value="">
	  	<br/>
	</#if>
	<div id="${id}" class="easytree treeview" url="${url}" id="${id}" <#if showCheckbox> showCheckbox</#if> <#if showTags>showTags</#if> <#if multiSelect>multiSelect</#if> <#if selectFirstNode> selectFirstNode</#if> levels="${levels}" events="${events}" checkOption="${checkOption}" <#if showBorder> showBorder</#if>>
		<#nested >
	</div>
</#macro>
<#--基于ztree构建树-->
<#macro ZTreeBuilder url id="" iStyle="" levels="2" events={} nodes=[] menuCheckbox=false asyncParam={"id":"id","name":"name"} checkBoxEnable=true isAsync=true isCascade=true editEnable=true tabId="" searchAble=true enableCheck=true allDisable=false isFirstSelected=true chkStyle="checkbox" radioType="level" isSelectedExpand=true isSinglePath=false isCancleCheck=false>
	<#if searchAble && !menuCheckbox>
		<input type="input" class="form-control" style="${iStyle}" treeId="${id}" id="${id}-input-search" placeholder="请输入搜索内容..." value="">
	  	<br/>
	  	<ul id="${id}" class="ztree ${id}" url="${url}" tabId="${tabId}" ${attrs({"asyncParam":asyncParam})}  events="${events}" <#if isAsync>isAsync="true"</#if> <#if checkBoxEnable>checkBoxEnable="true"</#if> <#if isCascade>isCascade="true"</#if> <#if editEnable>editEnable="true"</#if> <#if searchAble>searchAble="true"</#if> <#if enableCheck>enableCheck="true"</#if> <#if allDisable>allDisable="true"</#if> <#if isFirstSelected>isFirstSelected="true"</#if> <#if isSelectedExpand>isSelectedExpand="true"</#if> <#if isSinglePath>isSinglePath="true"</#if> <#if isCancleCheck>isCancleCheck="true"</#if> levels="${levels}" nodes="${nodes}" chkStyle="${chkStyle}" radioType="${radioType}"></ul>
	<#elseif menuCheckbox && searchAble>
		<@FormRef name="ztreeInput${id}" class="ztreeInput ${id}" />
		<div id="ztreeMenu${id}" class="ztreeDiv ${id}" style="display:none; position:absolute;">
			<input type="input" class="form-control" style="${iStyle}" treeId="${id}" id="${id}input-search" placeholder="请输入搜索内容..." value="">
		  	<br/>
			<ul id="${id}" class="ztree ${id}" url="${url}" tabId="${tabId}" ${attrs({"asyncParam":asyncParam})}  events="${events}" <#if menuCheckbox>menuCheckbox="true"</#if> <#if isAsync>isAsync="true"</#if> <#if checkBoxEnable>checkBoxEnable="true"</#if> <#if isCascade>isCascade="true"</#if> <#if editEnable>editEnable="true"</#if> <#if searchAble>searchAble="true"</#if> <#if enableCheck>enableCheck="true"</#if> <#if allDisable>allDisable="true"</#if> <#if isFirstSelected>isFirstSelected="true"</#if> <#if isSelectedExpand>isSelectedExpand="true"</#if> <#if isSinglePath>isSinglePath="true"</#if> <#if isCancleCheck>isCancleCheck="true"</#if> levels="${levels}" nodes="${nodes}" chkStyle="${chkStyle}" radioType="${radioType}"></ul>
		</div>
	<#elseif !searchAble && !menuCheckbox>
		<ul id="${id}" class="ztree ${id}" url="${url}" tabId="${tabId}" ${attrs({"asyncParam":asyncParam})}  events="${events}" <#if isAsync>isAsync="true"</#if> <#if checkBoxEnable>checkBoxEnable="true"</#if> <#if isCascade>isCascade="true"</#if> <#if editEnable>editEnable="true"</#if> <#if searchAble>searchAble="true"</#if> <#if enableCheck>enableCheck="true"</#if> <#if allDisable>allDisable="true"</#if> <#if isFirstSelected>isFirstSelected="true"</#if> <#if isSelectedExpand>isSelectedExpand="true"</#if> <#if isSinglePath>isSinglePath="true"</#if> <#if isCancleCheck>isCancleCheck="true"</#if> levels="${levels}" nodes="${nodes}" chkStyle="${chkStyle}" radioType="${radioType}"></ul>
	</#if>
</#macro>

<#macro TreeMapLoader initParm key="__null_key__" lievents="" isDis=false disableField="N" idField="id" valueField="id" nameField="name" nexp="" linkexp="" labelexp="" titleShowWidth="180px"><#t>
    <#assign map=Context.getLoaderValue(initParm)><#t>
    <#if !(map[key]?exists)>
        <#return >
    </#if>
    <#local nodes=map[key]>
    <#if !nodes?exists || nodes?size==0>
        <#return >
    </#if>
    <#local NodeTmp = nexp?interpret>
    <#local LinkTmp = linkexp?interpret>
    <#local LiTmp = lievents?interpret>
    <#if labelexp=="">
        <#local labelexp=r"${node[nameField]}">
    </#if>
    <#local LabelTmp = labelexp?interpret>
    <#list nodes as node>
        <#local id=node[idField]><#t>
        <#local child=map[id]!""><#t>
        <#--增加判断，如果不是最后一个节点不增加lievents属性，配合checkOption="3"(只有末级才有复选框)的情况使用，如果有特殊需求可根据逻辑自行变更-->
    	<#if child?is_indexable && child?size gt 0>
	    	<li <@NodeTmp/> <#if isDis && "${node[disableField]}"=="N"> dis="${node[disableField]}" style="cursor:default;"<#elseif isDis><@LiTmp/></#if>><a value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a><#t>
    	<#elseif isDis && "${node[disableField]}"=="N">
	    	<li <@NodeTmp/> dis="${node[disableField]}" style="cursor:default;"><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a><#t>
    	<#else>
	    	<li  <@LiTmp/> <@NodeTmp/>><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a><#t>
        </#if>
        <#if child?is_indexable && child?size gt 0>
        	<ul>
                <@TreeMapBuilder map=map key=id isDis=isDis disableField=disableField lievents=lievents idField=idField valueField=valueField nameField=nameField nexp=nexp linkexp=linkexp labelexp=labelexp titleShowWidth=titleShowWidth/>
            </ul>
        </#if>
        </li>
    </#list>
</#macro>

<#macro TreeMapFilterBuilder map leafexp key="__null_key__" idField="id" valueField="id" nameField="name" nexp="" linkexp="" labelexp="" titleShowWidth="180px"><#t>
    <#if !map[key]??>
        <#return >
    </#if>
    <#local nodes=map[key]>
    <#if !nodes?? || nodes?size==0>
        <#return >
    </#if>
    <#local NodeTmp = nexp?interpret>
    <#local LinkTmp = linkexp?interpret>
    <#if labelexp=="">
        <#local labelexp=r"${node[nameField]}">
    </#if>
    <#local LabelTmp = labelexp?interpret>
    <#list nodes as node>
        <#local id=node[idField]>
        <#local leafFlag=leafexp?eval>
        <#if !leafFlag><#t>
            <#local child=map[id]!"">
            <#if child?is_indexable && child?size gt 0>
                <#local childstring><@TreeMapFilterBuilder map=map leafexp=leafexp key=id idField=idField valueField=valueField nameField=nameField nexp=nexp linkexp=linkexp labelexp=labelexp titleShowWidth=titleShowWidth/></#local>
            <#else>
                <#local childstring="">
            </#if>
        </#if>
        <#if leafFlag>
        <li <@NodeTmp/>><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a></li>
        <#elseif childstring?trim !="">
        <li <@NodeTmp/>><a style="max-width:${titleShowWidth}" value="${node[valueField]}" <@LinkTmp/>><@LabelTmp/></a>
            <ul>
        ${childstring}
            </ul>
        </li>
        </#if>
    </#list>
</#macro>
<#macro SimpleLevel id url masterField="" detialParameter=""><#t>
<div class="tree-level" type="simple" id="${id}" url="${url}" ${attrs({"masterField":masterField,"detialParameter":detialParameter})}>
    <#nested >
</div>
</#macro>
<#macro RecuiveLevel id url recuiveField recuiveParameter masterField="" detialParameter=""><#t>
<div class="tree-level" type="recuive" id="${id}" url="${url}" recuiveField="${recuiveField}" recuiveParameter="${recuiveParameter}"  ${attrs({"masterField":masterField,"detialParameter":detialParameter})}>
    <#nested >
</div>
</#macro>
<#--增加属性isCheckExpand，是否展开到已选中的节点，默认false，配合checkbox使用-->
<#macro SimpleTree id="" class="" events="" style="" icon=true checkbox=false expand=-1 defaultSelect="" checkOption="" navflag=false isCheckExpand=false><#t>
<ul id="${id}" class="${navflag?string("nav bs-sidenav ","")} simpletree${icon?string(" tree-icon","")}${checkbox?string(" tree-checked","")} ${class}" expandLevel="${expand}" ${attrs({"events":events,"style":style,"defaultSelect":defaultSelect,"checkOption":checkOption,"isCheckExpand":isCheckExpand})}>
    <#nested >
</ul>
</#macro>

<#--编辑器-->
<#--基于DBoolean的Checkbox编辑控件-->
<#macro BooleanEditor name id="" class="" value="" disabled=false events="" style="" ext=""><#t>
    <#if id==""><#local id=name></#if><#t>
    <#if value?is_string && value=="Y">
        <#local checked="checked">
    <#elseif value["booleanValue"]?is_method && value.booleanValue()>
        <#local checked="checked">
    <#else>
        <#local checked="">
    </#if>
<input type="checkbox" id="${id}" name="${name}" ${attrs({"class":class,"events":events,"style":style})} <#if disabled>disabled</#if> ${checked} value="Y" ${ext}/>
</#macro>

<#--日期编辑控件  formatViewType:设置日期选择页面第一行显示日期或者时间（datetime,time） -->
<#macro DateEditor name type="date" scale="" readonly=false id="" value=""  class="" style=""  disabled=false events="" placeholder="" format="" validation={} messages={} ext="" formula="" daysOfWeekEnabled="1" startDate="" endDate="" formatViewType="">
    <#if id==""><#local id=name></#if>
    <#if type=="datetime">
        <#if format=="">
        <#local format="yyyy-mm-dd hh:ii:ss">
        </#if>
        <#local icon="glyphicon glyphicon-calendar">
    <#elseif type=="time">
        <#if format=="">
        <#local format="hh:ii">
        </#if>
        <#local icon="fa fa-clock-o">
    <#else>
        <#if format=="" && type!="_year" && type!="_season" && type!="_month" && type!="_week" && type!="_day">
        <#local format="yyyy-mm-dd">
        </#if>
        <#local icon="fa fa-calendar">
    </#if>
    <div id="${id}_date_editor_div" class="input-group date datepicker${disabled?string(" disabled","")}" type="${type}" scale="${scale}" data-date-format="${format}" data-date="" data-formula="${formula}"  daysOfWeekEnabled="${daysOfWeekEnabled}" startDate="${startDate}" endDate="${endDate}" formatViewType="${formatViewType}">
        <input class="wrapped ${class}" autocomplete="off" <#if readonly>readonly</#if> type="text" id="${id}" name="${name}" value="${value}" ${attrs({"events":events,"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})} ${ext}>
        <span class="input-group-addon pointer">
            <i class="${icon}"></i>
        </span>
    </div>
</#macro>
<#--参照编辑控件-->
<#macro RefEditor  name id="" class="" icon="" value="" disabled=false  placeholder="" style="" events="" validation={} messages={} showValue="" url="" code="" lookup=false  readFields="" writeFields="" ext=""><#t>
    <#if id==""><#local id=name></#if>
<div class="input-group reference" id="${id}" ${lookup?string("lookup=\"true\"","")} ${attrs({"events":events,"url":url,"readFields":readFields,"writeFields":writeFields})}>
    <input class="wrapped ${class}" type="text" id="${id}_label" name="${name}_label" value="${showValue}"<#if disabled> disabled</#if> readonly="true" ${attrs({"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})}>
    <input  type="hidden" id="${id}_value" name="${name}" value="${value}" ${ext}>
    <span class="input-group-addon pointer<#if disabled> disabled</#if>">
        <i class=<#if icon=="">"fa fa-th"<#else>"${icon}"</#if>></i>
    </span>
</div>
</#macro>

<#--下拉编辑器-->
<#--下拉选项-->
<#macro Option value selected=false>
<li data-value="${value}"  <#if selected>data-selected="true"</#if>><a href="#"><#nested/></a></li>
</#macro>
<#--下拉控件
classDic:该属性只用于基于数据字典下拉框时使用，用来区数据字典下拉框控件与普通下拉框，
使用数据字典下拉框空间时该属性值为ComboDataDic
-->
<#macro ComboBox name id="" class="" classDic="" value="" height="" disabled=false readonly=true placeholder="" style="" events="" validation={} messages={} ext="" extpro="" scale="" daysOfWeekEnabled="1" keymap={} formatmap={}>
    <#if id==""><#local id=name></#if>
<div <#if id!=""> id="${id}"</#if> class="input-group dropdown combobox ${classDic}" ${attrs({"value":value,"events":events})} >
    <input  type="text"   id="${id}_label" name="${name}_label" class="wrapped ${class}" data-toggle="dropdown" readonly=readonly  <#if disabled> disabled</#if> ${attrs({"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})}>
    <input  type="hidden" id="${id}_value" name="${name}"  ${ext} extpro="${extpro}" scale="${scale}" daysOfWeekEnabled="${daysOfWeekEnabled}" ${attrs({"keymap":keymap,"formatmap":formatmap})}>
    <#assign heightStyle="" />
    <#if height != ''>
		<#assign heightStyle="height:"+height+"px;" />
	</#if>
    <ul class="dropdown-menu" role="menu" style="${heightStyle}overflow:auto;">
        <#nested>
    </ul>
    <span class="input-group-addon pointer<#if disabled> disabled</#if>" data-toggle="dropdown"><span class="icon-caret"><i class="caret"></i></span>
    </span>
</div>
</#macro>
<#-- 日期类型选择控件  -->
<#macro QueryComponentDateRange options name scale="" isvalid=false readonly=false formula={} daysOfWeekEnabled="1" id={} value={} class={} disabled=false placeholder={} label={"type":"频度","begin":"开始","end":"结束"} style={} lclass="" cclass="" events={} 
	validation={"begin":{"required":true,"lessEqual":"#"+(id.end)!(name.end)!''},"end":{"required":true,"greaterEqual":"#"+(id.begin)!(name.begin)!''}} messages={"begin":{"lessEqual":"开始日期应小于等于结束日期"},"end":{"greaterEqual":"结束日期应大于等于开始日期"}} 
	ext="" formula={} keymap={"year":"_year","season":"_season","month":"_month","week":"_week","day":"_day"} 
	formatmap={"year":"yyyy","season":"yyyy-mm","month":"yyyy-mm","week":"yyyy-mm-dd","day":"yyyy-mm-dd"} beforeMethod="" afterMethod="" setDate={}>
	<#assign labelshow=label />
	<#assign placeholderType=(placeholder.type)!''/>
	<#if placeholderType != ''>
		<#assign labelshow={} />
	</#if>
	<#assign _validation=validation />
	<#if !isvalid>
		<#assign _validation={} />
	</#if>
	<@c.QueryComponent id="${(id.type)!''}" lclass=lclass name="${(name.type)!''}" placeholder="${(placeholder.type)!''}"  label="${(labelshow.type)!''}"  type="combobox" value="${(value.type)!''}" validation=(validation.type)!{} messages=(messages.type)!{} 
		events="{change:function(){ var beforeMethodStr ='${beforeMethod}'; var afterMethodStr ='${afterMethod}';  if(beforeMethodStr!='')eval(beforeMethodStr+'(this)');changeDateView(this); if(afterMethodStr!='')eval(afterMethodStr+'(this)');}}" 
		extpro='${(id.begin)!""},${(id.end)!""}' daysOfWeekEnabled=daysOfWeekEnabled  keymap=keymap formatmap=formatmap>
		    <#if options?keys?size gte 0><#t>
            <#list options?keys as key><#t>
            	<#assign selected=(key == (((value.type)!'')+""))/><#t>
                <@Option value=key selected=selected>${options[key]}</@Option>
            </#list><#t>
        </#if><#t>
    </@c.QueryComponent>
    <#assign initType=(value.type)!'_day' />
    <#assign dateFormat='yyyy-mm-dd'/>
   	<#if formatmap?keys?size gte 0><#t>
        <#list formatmap?keys as key><#t>
        	<#if key == initType><#t>
        	<#assign dateFormat=formatmap[key] /><#t>
        	</#if><#t>
        </#list><#t>
    </#if><#t>
    <#assign s_start_index="${(id.begin)!(name.begin)!''}"+"_startDate"/>
    <#assign s_end_index="${(id.begin)!(name.begin)!''}"+"_endDate"/>
    <#assign e_start_index="${(id.end)!(name.end)!''}"+"_startDate"/>
    <#assign e_end_index="${(id.end)!(name.end)!''}"+"_endDate"/>
    <#assign s_startDate=setDate[s_start_index]!""/>
    <#assign s_endDate=setDate[s_end_index]!""/>
    <#assign e_startDate=setDate[e_start_index]!""/>
    <#assign e_endDate=setDate[e_end_index]!""/>
	<@QueryComponent formula="${(formula.begin)!''}"  daysOfWeekEnabled=daysOfWeekEnabled  id="${(id.begin)!(name.begin)!''}" name="${(name.begin)!''}" op="${(op.begin)!''}" type="date" scale="${scale}" exttype="${(value.type)!dateFormat}" label="${(labelshow.begin)!''}" value="${(value.begin)!''}" class="${(class.begin)!''}" style="${(style.begin)!''}" placeholder="${(placeholder.begin)!''}" helper="${(helper.begin)!''}" colspan=1 readonly=readonly disabled=false rows=3 checkValue=""  format=dateFormat showValue="" url="" code="" readFields="" writeFields="" events=(helper.begin)!{} itemId="" itemClass="" itemStyle="" lclass=lclass cclass=cclass validation=(_validation.begin)!{} messages=(messages.begin)!{} advance=false  extpro="" keymap=keymap startDate="${s_startDate}" endDate="${s_endDate}"/>
	<@QueryComponent formula="${(formula.end)!''}"  daysOfWeekEnabled=daysOfWeekEnabled id="${(id.end)!(name.end)!''}" name="${(name.end)!''}" op="${(op.end)!''}" type="date" scale="${scale}" exttype="${(value.type)!dateFormat}" label="${(labelshow.end)!''}" value="${(value.end)!''}" class="${(class.end)!''}" style="${(style.end)!''}" placeholder="${(placeholder.end)!''}" helper="${(helper.end)!''}" colspan=1 readonly=readonly disabled=false rows=3 checkValue=""  format=dateFormat showValue="" url="" code="" readFields="" writeFields="" events=(helper.end)!{} itemId="" itemClass="" itemStyle="" lclass=lclass cclass=cclass validation=(_validation.end)!{} messages=(messages.end)!{} advance=false  extpro=""  keymap=keymap startDate="${e_startDate}"  endDate="${e_endDate}"/>
</#macro>
 
<#-- 双日期类型去频度选择控件  -->
<#macro QueryComponentDateRangeValid name scale="" readonly=false formula={} daysOfWeekEnabled="1" id={} class={} disabled=false placeholder={} label={} style={} lclass="" cclass="" events={} 
	ext="" formatmap={"year":"yyyy","season":"yyyy-mm","month":"yyyy-mm","week":"yyyy-mm-dd","day":"yyyy-mm-dd"} beforeMethod="" afterMethod="" setDate={}>
	<#assign labelshow=label />
	<#assign placeholderType=(placeholder.type)!''/>
	<#if placeholderType != ''>
		<#assign labelshow={} />
	</#if>
	<#assign initType=(value.type)!'_day' />
    <#assign dateFormat='yyyy-mm-dd'/>
   	<#if formatmap?keys?size gte 0><#t>
        <#list formatmap?keys as key><#t>
        	<#if key == initType><#t>
        	<#assign dateFormat=formatmap[key] /><#t>
        	</#if><#t>
        </#list><#t>
    </#if><#t>
	<@QueryComponent formula="${(formula.begin)!''}"  daysOfWeekEnabled=daysOfWeekEnabled  id="${(id.begin)!(name.begin)!''}" name="${(name.begin)!''}" op="${(op.begin)!''}" type="date" scale="${scale}" exttype="${(value.type)!dateFormat}" label="${(labelshow.begin)!''}" value="${(value.begin)!''}" class="${(class.begin)!''}" style="${(style.begin)!''}" placeholder="${(placeholder.begin)!''}" helper="${(helper.begin)!''}" colspan=0.5readonly=readonly disabled=false rows=3 checkValue=""  format=dateFormat showValue="" url="" code="" readFields="" writeFields="" events=(helper.begin)!{} itemId="" itemClass="" itemStyle="" lclass=lclass cclass=cclass validation={} messages={} advance=false  extpro="" keymap={} endDate="${(id.end)!(name.end)!''}"/>
	<@QueryComponent formula="${(formula.end)!''}"  daysOfWeekEnabled=daysOfWeekEnabled id="${(id.end)!(name.end)!''}" name="${(name.end)!''}" op="${(op.end)!''}" type="date" scale="${scale}" exttype="${(value.type)!dateFormat}" label="${(labelshow.end)!''}" value="${(value.end)!''}" class="${(class.end)!''}" style="${(style.end)!''}" placeholder="${(placeholder.end)!''}" helper="${(helper.end)!''}" colspan=0.5 readonly=readonly disabled=false rows=3 checkValue=""  format=dateFormat showValue="" url="" code="" readFields="" writeFields="" events=(helper.end)!{} itemId="" itemClass="" itemStyle="" lclass=lclass cclass=cclass validation={} messages={} advance=false  extpro=""  keymap={} startDate="${(id.begin)!(name.begin)!''}" />
</#macro>

<#--基于系统码表的下拉控件-->
<#macro ComboDict type name id="" class="" value="" disabled=false  placeholder="" style="" events="" validation="" messages="" empty=false  emptyName="" ext="" height="">
    <@ComboBox name=name id=id class=class value=value disabled=disabled placeholder=placeholder style=style events=events validation=validation messages=messages ext="" height=height>
        <#if empty><#assign selected=(value.equals(""))/><#t>
            <@Option value="" selected=selected>${emptyName}</@Option>
        </#if><#t>
        <#assign items=Context.getLoaderValue("loaderDict",type)><#t>
        <#if items?exists><#t>
            <#list items as item><#t>  
                <#assign selected=(item.doc_code == (value+""))><#t>         
                <@Option selected=selected value="${item.doc_code}">${item.doc_name}</@Option>
            </#list><#t>
        </#if><#t>
    </@ComboBox>
</#macro>

<#--基于数据字典的下拉控件
classDic:该属性只用于基于数据字典下拉框时使用，用来区数据字典下拉框控件与普通下拉框，
使用数据字典下拉框空间时该属性值为ComboDataDic
-->
<#macro ComboDataDic type name id=""  classDic="ComboDataDic" class=""  value="" disabled=false  placeholder="" style="" events="" validation="" messages="" empty=false  emptyName="" ext="" height="">
    <@ComboBox name=name id=id class=class classDic=classDic value=value disabled=disabled placeholder=placeholder style=style events=events validation=validation messages=messages ext="" height=height>
        <#if empty><#assign selected=(value.equals(""))/><#t>
            <@Option value="" selected=selected>${emptyName}</@Option>
        </#if><#t>
        <#assign items=Context.getLoaderValue("loaderDataDic",type)><#t>
        <#if items?exists><#t>
            <#list items as item><#t>  
                <#assign selected=(item.dimvl_code == (value+""))><#t>         
                <@Option selected=selected value="${item.dimvl_code}">${item.dimvl_name}</@Option>
            </#list><#t>
        </#if><#t>
    </@ComboBox>
</#macro>

<#--基于枚举的下拉控件-->
<#macro ComboEnum type name id="" class="" value="" disabled=false  placeholder="" style="" events="" validation="" messages="" empty=false  emptyName="" ext="" height="">
<@ComboBox name=name id=id class=class value=value disabled=disabled placeholder=placeholder style=style events=events validation=validation messages=messages ext=ext height=height>
    <#if empty><#assign selected=(value.equals(""))/><#t>
        <@Option value="" selected=selected>${emptyName}</@Option>
    </#if><#t>
    <#assign items=Context.getLoaderValue("loaderEmums",type)><#t>
    <#if items?exists><#t>
        <#list items as item><#t>
            <#assign selected=(item.code.equals(value+""))/><#t>
            <@Option value="${item.code}">${item.name}</@Option>
        </#list><#t>
    </#if><#t>
</@ComboBox>
</#macro>

<#--隐藏框-->
<#macro Hidden name id=""  value="" ext=""><#t>
    <#if id==""><#local id=name></#if><#t>
<input type="hidden" id="${id}" name="${name}"  value="${value}" ext=""/>
</#macro>

<#--表单相关宏定义-->
<#--表单定义-->
<#macro Form action id="" class="" target="" method="post" after={} before={} events="" cols=1 labelsize=-1 ctrlsize=-1 enctype="" mode="normal" isAjax=true help=true viewOnly=false>
    <#if before?keys?size gt 0>
        <#local b> ajax-before="${before?keys?join(";")}" <#list before?keys as key><#local md="cmd_"+key><#if !md?trim?ends_with(")") && md?eval?is_macro><#if before[key]?is_string&&before[key]==""><#local e=(md+"()")?eval><#else><#local e=(md+"(before[key])")?eval></#if> ${e}</#if></#list></#local>
    <#else>
        <#local b="">
    </#if>
    <#if after?keys?size gt 0>
        <#local a> ajax-after="${after?keys?join(";")}" <#list after?keys as key><#local md="cmd_"+key><#if !md?trim?ends_with(")") && md?eval?is_macro><#if after[key]?is_string&&after[key]==""><#local e=(md+"()")?eval><#else><#local e=(md+"(after[key])")?eval></#if> ${e}</#if></#list></#local>
    <#else>
        <#local a="">
    </#if>
    <form id="${id}" class="${class}" action="${action}" ${b} ${a} ${attrs({"enctype":enctype,"method":method,"target":target,"events":events})} <#if isAjax>isAjax="true"</#if>>
        <#if mode=="normal">
            <@FormGroup id="${id}_formgroup" cols=cols labelsize=labelsize ctrlsize=ctrlsize help=help collapse="none" viewOnly=viewOnly>
                <#nested >
            </@FormGroup>
        <#else>
            <#nested >
        </#if>
    </form>
</#macro>
<#macro FormGroup id="" label="" class="" style="" labelStyle="" cols=1 labelsize=-1 ctrlsize=-1 collapse="none" icon="" help=true viewOnly=false>
    <#assign _formcellcount=0>
    <#assign _formitemcount=0>
    <#local colCells = 12/cols>
    <#if cols=1>
        <#if labelsize=-1>
            <#local labelsize=2>
        </#if>
        <#if ctrlsize=-1>
            <#local ctrlsize=12-labelsize-labelsize>
        </#if>
    <#else>
        <#if labelsize=-1>
            <#local labelsize=1>
        </#if>
        <#if ctrlsize=-1>
            <#local ctrlsize=colCells-1>
        </#if>
    </#if>
    <#assign __formOptions={"colCells":colCells,"cols":cols,"help":help,"labelSize":labelsize,"controlSize":ctrlsize}>
	<#--区分查看页面/可编辑页面文本框样式-->    
    <#if viewOnly==false>
    <div id="${id}" class="formgroup form-flow ${class}" ${attr("style",style)}>
    <#else>
    <div id="${id}" class="formgroup form-flow-view${class}" ${attr("style",style)}>
    </#if>
        <#local contentClass="">
        <#local expand="">
        <#if label!="">
            <#if __scrollspyItems??&& __scrollspyItems!=EMPTYA>
                <#assign __scrollspyItems=__scrollspyItems+[{"id":id,"name":label}]/>
            </#if>
            <#if collapse="collapse">
                <#local colicon="fa fa-chevron-right">
                <#local contentClass=" collapse">
                <#local expand="expand=\"false\"">
            <#elseif collapse="expand">
                <#local colicon="fa fa-chevron-down">
                <#local contentClass=" collapse in">
                <#local expand="expand=\"true\"">
            <#else>
                <#local colicon="">
            </#if>
            <h5 ${attr("style",labelStyle)}><#if colicon!=""><a ${expand} data-toggle="collapse" href="#${id}_content"></#if><#if icon!=""><i class=""></i></#if><label>${label}</label><#if colicon!="">&nbsp;<i expand="fa fa-chevron-down" collapse="fa fa-chevron-right" collapse-icon="true" class="${colicon}"></i></a></#if></h5>
        </#if>
        <div class="${contentClass}" id="${id}_content">
            <div class="row" >
                <#nested >
            </div>
        </div>
    </div>
    <#local __formOptions=EMPTY>
</#macro>
<#--表单项目定义,不定义具体控件,只定义标签及控件位置-->
<#macro FormItem id="" editorId="" label="" helper="" class="" icon="" lclass="" cclass="" mode="super" colspan=1 required=false addon=false style="" help=false addfor=true>
    <#if mode=="super" && __formOptions?? && __formOptions?size gt 0>
        <#local labelSize = __formOptions["labelSize"]>
        <#local controlSize = __formOptions["controlSize"]>
        <#if colspan gt __formOptions["cols"]>
            <#local colspan = __formOptions["cols"]>
        </#if>
        <#local lsize=" col-sm-"+labelSize>
        <#local tempsize=controlSize+(colspan-1)*__formOptions["colCells"]>
        <#if labelSize+tempsize gt 12>
            <#local tempsize=12-labelSize>
        </#if>
        <#local csize=" col-sm-"+tempsize>
        <#if label=="">
            <#local csize=" col-xs-offset-"+labelSize +csize>
        </#if>
        <#local leavecell = 12-_formcellcount>
        <#if _formcellcount==0 && _formitemcount gt 0>
            </div>
            <div class="row">
        </#if>
        <#if leavecell gt labelSize+tempsize>
            <#assign  _formcellcount= _formcellcount+ labelSize+tempsize>
        <#elseif leavecell == labelSize+tempsize>
            <#assign  _formcellcount= 0>
        <#else>
            </div>
            <div class="row">
            <#assign  _formcellcount= labelSize+tempsize>
        </#if>
        <#assign _formitemcount=_formitemcount+1>
        <#local help=__formOptions["help"]||help>
    <#else>
        <#local lsize="">
        <#local csize="">
    </#if>
<div ${attrs({"id":id,"style":style})} class="form-group ${class}">
    <#if label!=""><label class="control-label${lsize} ${lclass}" <#if addfor> for="${editorId}"</#if>>${label}</label></#if>
    <div class="control-container ${csize} ${cclass}" <#if !help&&helper!="">title="${helper}"</#if>>
        <#if addon>
        <div class="input-group">
        </#if>
        <#nested/>
        <#if addon>
        </div>
        </#if>
        <span class="valid-flag"><i class="fa ${required?string("fa-asterisk","icon-blank")}" normal="fa ${required?string("fa-asterisk","icon-blank")}"></i></span>
        <#if help><label class="help-block<#if helper!=""> info</#if>" for="${editorId}"><#if helper!=""><span class="ar"></span><i class="fa fa-info-circle"></i></#if>${helper}&nbsp;</label><#else><label class="bottom-padding"></label></#if>
    </div>
</div>
</#macro>
<#--表单项目单行编辑框-->
<#macro FormInput name type="text" id=""  maxlength="" itemId="" label="" helper="" value="" addon="" mask=""  class="" style=""  readonly=false disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} help=false ext="">
    <#if id==""><#local id=name></#if>
<@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) addon=(addon !="") help=help>
    <input class="form-control ${class}" type="${type}" id="${id}" name="${name}" <#if maxlength!=""> maxlength="${maxlength}" </#if><#if type?lower_case=="password">autocomplete="off"</#if> ${attrs({"events":events,"mask":mask,"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})} value="${value}" <#if readonly>readOnly</#if> <#if disabled>disabled</#if> help=help ${ext}>
    <#if addon !="">
    <div class="input-group-addon">
        <i class="${addon}"></i>
    </div>
    </#if>
</@FormItem>
</#macro>
<#--表单项目多行编辑框-->
<#macro FormText name id="" itemId="" label="" helper="" class="" style=""  readonly=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super"  colspan=1 placeholder="" validation={} messages={} help=false rows=3 ext="">
    <#if id==""><#local id=name></#if>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help>
    <textarea class="form-control ${class}"  id="${id}" name="${name}" <#if rows gt 0>rows="${rows}"</#if>  ${attrs({"events":events,"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})} <#if readonly>readOnly</#if> ${ext}><#nested></textarea>
    </@FormItem>
</#macro>

<#--表单项目多行编辑框-->
<#macro FormCkeditorText name id="" itemId="" label="" helper="" class="" style=""  readonly=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super"  colspan=1 placeholder="" validation={} messages={} help=false rows=3 ext="">
    <#if id==""><#local id=name></#if>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help>
    <textarea class="form-control ckeditor ${class}"  id="${id}" name="${name}" <#if rows gt 0>rows="${rows}"</#if>  ${attrs({"events":events,"style":style,"placeholder":placeholder,"validation":validation,"valid-msgs":messages})} <#if readonly>disabled="disabled"</#if> ${ext}><#nested></textarea>
    </@FormItem>
</#macro>

<#--表单项目复选框-->
<#macro FormCheckBox name id="" itemId="" value="" checkValue="" class="" label="" helper=""  disabled=false events="" style="" itemClass="" itemStyle="" lclass="" mode="super"  colspan=1 help=false ext="">
    <#if id==""><#local id=name></#if><#t>
    <#if value!="" && value?string==checkValue><#local checked="checked"><#else><#local checked=""></#if><#t>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass mode=mode colspan=colspan help=help>
    <input type="checkbox" id="${id}" name="${name}" class="form-box ${class}" ${attrs({"events":events,"style":style})} <#if disabled>disabled</#if> ${checked} value="${checkValue}" ${ext}>
    </@FormItem>
</#macro>


<#--表单项目DBoolean复选框-->
<#macro FormBoolean name id="" itemId="" value="" class="" label="" helper=""  disabled=false events="" style="" itemClass="" itemStyle="" lclass="" mode="super"  colspan=1 help=false ext="">
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass mode=mode colspan=colspan help=help>
        <@BooleanEditor name=name id=id class="form-box ${class}" value=value disabled=disabled events=events style=style ext=ext/>
    </@FormItem>
</#macro>

<#--表单项目日期框-->
<#macro FormDate name type="date" scale="" readonly=false id="" itemId="" label="" helper="" value=""  class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super"  colspan=1 placeholder="" format="" validation={} messages={} help=false ext="" formula="" daysOfWeekEnabled="1" startDate="" endDate="" addfor=false formatViewType="">
    <#if id==""><#local id=name></#if>
    <#local cclass="col-"+type+" "+cclass>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help addfor=addfor>
        <@DateEditor id=id name=name type=type scale=scale readonly=readonly value=value class="form-control " +class style=style disabled=disabled events=events placeholder=placeholder format=format validation=validation messages=messages ext=ext formula=formula daysOfWeekEnabled=daysOfWeekEnabled startDate=startDate endDate=endDate formatViewType=formatViewType/>
    </@FormItem>
</#macro>

<#--表单项目参照框-->
<#macro FormRef name id="" itemId=""  value=""  label="" helper="" class="" icon="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} showValue="" url="" code="" readFields="" writeFields="" help=false ext="">
    <#if id==""><#local id=name></#if>
    <@FormItem id=itemId editorId=id label=label helper=helper icon=icon class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help>
        <@RefEditor id=id name=name value=value icon=icon class="form-control " +class style=style disabled=disabled events=events placeholder=placeholder validation=validation messages=messages showValue=showValue url=url code=code readFields=readFields writeFields=writeFields ext=ext/>
    </@FormItem>
</#macro>

<#--表单项目下拉框-->
<#macro FormComboBoxDict type name id="" itemId=""  value="" readonly=true label="" helper="" class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} help=false ext="" extpro="" daysOfWeekEnabled="1" keymap={} formatmap={} height="">
    <#if id==""><#local id=name></#if>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help>
        <@ComboDict type=type name=name id=id value=value class="form-control "+class style=style disabled=disabled events=events placeholder=placeholder validation=validation messages=messages ext=ext height=height>           
            <#nested >
        </@ComboDict>
    </@FormItem>
</#macro>

<#--表单项目下拉框-->
<#macro FormComboBox name id="" itemId=""  value="" readonly=true label="" helper="" class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} help=false ext="" extpro="" scale="" daysOfWeekEnabled="1" keymap={} formatmap={} height="">
    <#if id==""><#local id=name></#if>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help>
        <@ComboBox name=name id=id value=value readonly=readonly class="form-control "+class style=style disabled=disabled events=events placeholder=placeholder validation=validation messages=messages ext=ext extpro=extpro daysOfWeekEnabled=daysOfWeekEnabled scale=scale keymap=keymap formatmap=formatmap height=height>
            <#nested >
        </@ComboBox>
    </@FormItem>
</#macro>


<#--表单项目基于枚举的下拉框-->
<#macro FormComboEnum type name id="" itemId="" value=""  label="" helper="" class="" style=""  disabled=false events="" itemClass="" itemStyle="" lclass="" cclass="" mode="super" colspan=1 placeholder="" validation={} messages={} help=false ext="" height="">
    <#if id==""><#local id=name></#if>
    <@FormItem id=itemId editorId=id label=label helper=helper class=itemClass style=itemStyle lclass=lclass cclass=cclass mode=mode colspan=colspan required=validation["required"]?default(false) help=help>
        <@ComboEnum type=type name=name id=id value=value class="form-control "+class style=style disabled=disabled events=events placeholder=placeholder validation=validation messages=messages ext=ext height=height/>
    </@FormItem>
</#macro>


<#--新组建表单项目下拉框-->
<#macro FormBselectpicker
 					value=""
					name="" 
					id="" 
					itemId="" 
					label="" 
					helper="" 
					class="" 
					itemClass="" 
					itemStyle="" 
					lclass="" 
					cclass="" 
					mode="super" 
					colspan=1 
					help=false 
					validation={}
					messages={}
					<#-- 以下是新组建的属性    -->  
					multipleOption={} 
					events=""
					multiple=false
					disabled=false
					dataactionsbox=false 
					datacontainer=false
					datacontainerinclude="" 
					<#-- 可以自由添加class和style到option属性中    -->  
					classbls=""
					datawidth="fit"
					datastyle=""
					datalivesearch=false 
					datasize="5"
					datamaxoptions=false
					datamaxoptionssize="3"
					dataselectedtextformat=false
					dataselectedtextformatcount=5 >
	
    <@FormItem 
					id=itemId 
					editorId=id 
					label=label 
					helper=helper 
					class=itemClass 
					style=itemStyle 
					lclass=lclass 
					cclass=cclass 
					mode=mode 
					colspan=colspan 
					required=validation["required"]?default(false) 
					help=help >
        
		<@Bselectpicker
		messages=messages
		validation=validation
		value=value
		name=name		
		id=id 
		multipleOption=multipleOption 
		events=events
		multiple=multiple
		disabled=disabled
		dataactionsbox=dataactionsbox 
		datacontainer=datacontainer
		datacontainerinclude=datacontainerinclude
		classbls="form-control "+classbls
		datawidth=datawidth
		datastyle=datastyle
		datalivesearch=datalivesearch 
		datasize=datasize
		datamaxoptions=datamaxoptions
		datamaxoptionssize=datamaxoptionssize
		dataselectedtextformat=dataselectedtextformat
		dataselectedtextformatcount=dataselectedtextformatcount
		>
		<#nested>	
        </@Bselectpicker>
    </@FormItem>

</#macro>



<#--新下拉框-->
<#--未实现的：data-divider="true"分频器  data-header菜单标题  data-subtext注释  data-content自定义option内容  data-icon自定义option图标 show-tick单选标记图标 validation效验-->

<#macro Bselectpicker
		messages={}
		validation={}
 		value="" 
		name="" 
		id=""
		multipleOption={} 
		events=""
		multiple=false
		disabled=false
		dataactionsbox=false 
		datacontainer=false
		datacontainerinclude=""  
		classbls=""
		datawidth="fit"
		datastyle=""
		datalivesearch=false 
		datasize="5"
		datamaxoptions=false
		datamaxoptionssize="3"
		dataselectedtextformat=false
		dataselectedtextformatcount=5
	>
	<#-- 可以自由添加class和style到option属性中    -->   <#-- value的值代表需要被选中的-->
	<select id="${id}" name="${name}" value="${value}"   class="selectpicker ${classbls}" 
	${attrs({"events":events,"validation":validation,"valid-msgs":messages})}
	<#-- 是否多选-->
	<#if multiple>multiple</#if>
	<#-- 禁用下拉框-->
	<#if disabled>disabled</#if>
	<#-- 将下拉框的显示限制到某一个特定的布局中 datacontainerinclude：选择器名字 -->
	<#if datacontainer>
	<#if datacontainerinclude?exists>
	data-container="${datacontainerinclude}"</#if></#if>
	<#-- 是否激活全选或者全部选的按钮   只有在激活全选的状态下才能使用-->
	<#if dataactionsbox> data-actions-box="true" </#if>
	<#-- 下拉框boostrap原生样式，也可以在class属性中加入组件特有样式，或者自定义样式 -->
	<#if datastyle?exists>data-style="${datastyle}"</#if>
	<#-- 下拉框宽度  可以取fit(根据选择的option宽度自动调节),auto(自动设置为最宽的option长度),100px,75%-->
	<#if datawidth?exists>data-width="${datawidth}"</#if>
	<#-- 下拉框可以展示的数目 -->
	<#if datasize?exists>data-size="${datasize}"</#if>
	<#-- 是否激活过滤查询 -->
	<#if datalivesearch>data-live-search="true"</#if>
	<#-- 最多可以多选几个 -->
	<#if datamaxoptions>multiple data-max-options="${datamaxoptionssize}"</#if>
	<#-- 选择超过多少的时候显示计数信息 ,设置此项以后会自动将下拉框变为多选-->
	<#if dataselectedtextformat>multiple 
	<#if dataselectedtextformatcount?exists>
		data-selected-text-format="count > ${dataselectedtextformatcount}"</#if></#if>
	>
			<#if multipleOption?keys?size gte 0><#t>
				<#list multipleOption?keys as key>	
	   					<option  value="${key}">${multipleOption[key]}<#nested></option>
  				</#list>
		<#else>
			</#if><#t>
	</select>
	
</#macro>









<#--id和版本的隐藏框-->
<#macro FormIdVersion id version><#t>
<input name="id" name="id" type="hidden" value="${id?if_exists?html}">
<input name="version" name="version" type="hidden" value="${version?if_exists?html}">
</#macro>

<#--查询条件定义b-->
<#--查询按钮包含标签，用于在查询面板里嵌入自定义按钮的标签-->
<#macro ConditionActions>
    <#assign _condition_actions>
        <#nested >
    </#assign>
</#macro>
<#--查询按钮type="default" submit=false size="default" class="" icon=""-->
<#macro QueryAction target type="default" size="default" class="" style="">
    <#if size=="default"><#local size=""><#else><#local size="btn-"+size></#if>
    <button type="button" class="btn btn-${type} ${size} ${class}" cas-query="${target}" ${attr("style",style)}><i class="fa fa-search"></i><#nested /></button>
</#macro>
<#--查询条件-->
<#macro Condition id="" action=""  class="" isvalid=false style="" target="" method="post" before={} button=true size=11 cols=3 labelsize=0 ctrlsize=4 parameter="auto" layout="form" validateFormError="xquery">
    <#if before?keys?size gt 0>
        <#local b> ajax-before="${before?keys?join(";")}" <#list before?keys as key><#local md="cmd_"+key><#if !md?trim?ends_with(")") && md?eval?is_macro><#if before[key]?is_string&&before[key]==""><#local e=(md+"()")?eval><#else><#local e=(md+"(before[key])")?eval></#if> ${e}</#if></#list></#local>
    <#else>
        <#local b="">
    </#if>
<div class="${class}" style="${style}">
<#if button>
    <div class="col-lg-12">
</#if>
    <#if id="">
        <#local id=utils.UUID>
    </#if>
    <form id="${id}" class="xquery<#if isvalid> validate</#if>" action="${action}" ${b} submitMethod="xquerySubmitHandler" ${attrs({"method":method,"target":target,"parameter":parameter,"validateFormError":validateFormError})} >
        <@FormGroup id="${id}_formgroup" cols=cols labelsize=labelsize ctrlsize=ctrlsize help=false collapse="none">
		<#nested >
	    </@FormGroup>
    </form>
    <#if button>
    </div>
        <div class="col-lg-12">
        <div class="pull-right">
            <#if _condition_actions?exists && _condition_actions=="">
                ${_condition_actions}
                <#assign _condition_actions="">
            <#else>
                <@QueryAction target="#"+id  style="margin-top:7px;">查询</@QueryAction>
            </#if>
        </div>
        </div>
    </#if>
</div>
</#macro>
<#--查询项目定义,不定义具体控件,只定义标签及控件位置-->
<#macro QueryItem id="" editorId="" label="" helper="" class="" lclass="" cclass="" colspan=1 required=false style="">
    <@FormItem id=id editorId=editorId label=label helper=helper class=class lclass=lclass cclass=cclass mode="super" colspan=colspan required=required style=style>
        <#nested />
    </@FormItem>
</#macro>

<#--查询组件-->
<#macro QueryComponent name op="=" type="text" id="" label="" value="" class="" style="" placeholder="" helper="" colspan=1 readonly=false disabled=false exttype="" rows=3 checkValue=""  format="" showValue="" url="" code="" readFields="" writeFields="" events="" itemId="" itemClass="" itemStyle="" lclass="" cclass="" validation={} messages={} advance=false  extpro="" scale="" formula="" daysOfWeekEnabled="1" startDate="" endDate="" keymap={} formatmap={} height="">
    <#local ext="op=\""+op?html+"\" adv=\""+advance?string+"\"">
    <#if type="area">
        <@FormText name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan readonly=readonly rows=rows events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages >
            <#nested >
        </@FormText>
    <#elseif type=="checkbox">
        <@FormCheckBox name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled checkValue=checkValue events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages/>
    <#elseif type=="boolean">
        <@FormCheckBox name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages/>
    <#elseif type=="date">
        <#if exttype==""><#local exttype="date"></#if>
        <@FormDate name=name id=id label=label ext=ext class=class style=style placeholder=placeholder readonly=readonly helper=helper colspan=colspan disabled=disabled value=value type=exttype scale=scale format=format events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass formula=formula daysOfWeekEnabled=daysOfWeekEnabled startDate=startDate endDate=endDate validation=validation messages=messages />
    <#elseif type=="ref">
        <@FormRef name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled value=value showValue=showValue url=url code=code readFields=readFields writeFields=writeFields events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages/>
    <#elseif type=="combobox">
        <@FormComboBox name=name id=id label=label ext=ext class=class style=style placeholder=placeholder helper=helper colspan=colspan disabled=disabled value=value events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass extpro=extpro validation=validation messages=messages scale=scale keymap=keymap daysOfWeekEnabled=daysOfWeekEnabled formatmap=formatmap height=height>
            <#nested >
        </@FormComboBox>
    <#else>
        <#if exttype==""><#local exttype="text"></#if>
        <@FormInput name=name id=id label=label ext=ext  class=class style=style placeholder=placeholder helper=helper colspan=colspan readonly=readonly value=value type=exttype events=events itemId=itemId itemClass=itemClass itemStyle=itemStyle lclass=lclass cclass=cclass validation=validation messages=messages />
    </#if>
</#macro>
<#--上下折叠控件(手风琴)-->
<#macro accordion id>
		<h4 style="margin-top:0px;">
			<div data-toggle="collapse" data-parent="#accordion" href="#${id}" aria-expanded="true" class="accordion_expand" onclick="transExOrCollStatus(this);" border="0" />
		</h4>
		<div id="${id}" class="panel-collapse collapse in">
        	<#nested />
		</div>
</#macro>
