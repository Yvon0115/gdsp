<#--公告列表页面-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="${__jsPath}/plugins/comp.js" />
<@c.Script src="script/sysnotice/simple-list" onload="initNoticePlaceholder()" />
<modal-title>公告</modal-title>
<#--<div class="modal-body" style="height:475px;">-->
<@c.Tabs >
    <@c.Tab  id="mainNoticeDlgPanel" active=true>
		<div style="">       
        <@c.Box style="min-height:500px;">        
            <@c.BoxHeader class="border header-bg" style="margin-top:3px;">
            	<#-- 根据相关bug，去除此搜索框 wqh 2017/2/9 -->
            	<#-- <@c.Search class="pull-left"  target="#NoticeContent" conditions="title" placeholder="公告标题"/> -->
	            <@c.QueryAction target="#queryNoticeContion" class="pull-right">查询</@c.QueryAction>
                 <@c.Condition id="queryNoticeContion" target="#NoticeContent" isvalid=true class="pull-right" button=false style="width:800px"  cols=4 ctrlsize=3 parameter="parameter">
     	                <@c.QueryComponent name="title" placeholder="公告标题" type="text" value="" op="like"  />
     	                <@c.QueryComponent name="typeNotice" id="timenotice"  type="combobox" value="" op="">
     	                  <@c.Option value=""></@c.Option>
			                <@c.Option value="0">今天</@c.Option>
		                    <@c.Option value="1">昨天</@c.Option>
			                <@c.Option value="2">近一周</@c.Option>
			                 <@c.Option value="3" selected=true>近一月</@c.Option>
			                  <@c.Option value="4">自由查询</@c.Option>
		                </@c.QueryComponent>
		                <@c.QueryComponent id="notice_start_date" name="p_start_date"   type="date" value="" op="" validation={"lessEqual":"#p_end_date"} messages={"lessEqual":"请输入不大于结束日期 {0} 的值"}/>
		                <@c.QueryComponent id="notice_end_date" name="p_end_date"  type="date" value="" op="" validation={"greaterEqual":"#p_start_date"} messages={"greaterEqual":"请输入不小于开始日期 {0} 的值"}/>
                </@c.Condition>
            </@c.BoxHeader>
              
            <@c.BoxBody class="scrollbar" style="height:455px;">
                <@c.TableLoader id="NoticeContent" url="${ContextPath}/public/notice/noticeDlg-list.d">  
					<#include "notice-dlg-list.ftl">
                </@c.TableLoader>
            </@c.BoxBody>  
             <@c.BoxFooter class="text-center">
			        <@c.Pagination class="pull-right" target="#NoticeContent" page=notices/>
		     </@c.BoxFooter> 
        </@c.Box>
        </div>
    </@c.Tab>
    <@c.Tab id="detailNoticeDlgPanel"></@c.Tab>
</@c.Tabs>
<#--</div>-->
<#--<div class="modal-footer">-->
	<#--<@c.Button type="canel" icon="glyphicon glyphicon-off"  action=[c.dismiss()]>关闭</@c.Button>-->
<#--</div>-->
