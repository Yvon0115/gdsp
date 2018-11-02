<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script id="exportIdxJS" src="script/indexanddim/exportidx"/>
<@c.Script id="queryBoxJS" src="script/indexanddim/queryboxhistory"/>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/indexanddim/idxmanage.js"/>-->
<@c.Script src="script/indexanddim/idxmanage" />
<@c.Tabs>
    <@c.Tab  id="mainPanel" active=true title="">
        <@c.Box>
            <@c.BoxHeader class="border header-bg">
                <@c.Button type="primary" icon="glyphicon glyphicon-plus" action=[c.opentab("#detailPanel","${ContextPath}/index/indexmanager/add.d")]>添加</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-trash" action=[c.rpc("${ContextPath}/index/indexmanager/delete.d",{"dataloader":"#indexListContent"},{"checker":["id","#indexListContent"],"confirm":"确认删除选中指标？"})] >删除</@c.Button>
                <@c.Button icon="glyphicon glyphicon-import" action=[c.opendlg("#importDlg","${ContextPath}/index/indexmanager/importIdxInfo.d","300","600",true)]>导入指标</@c.Button>
                <@c.Button  icon="glyphicon glyphicon-export" click="exportIdxJS.exportIdx()">导出模板</@c.Button>
                <@c.Search class="pull-right" target="#indexListContent" conditions="indexCode,indexName" placeholder="指标编码/指标名称"/><!-- 指标搜索框 -->
                <#--<span id="condbtn"><@c.Button type="primary" size="sm" click="queryBoxJS.display()">多条件查询</@c.Button></span>-->
                <!-- 条件搜索框 -->
                <div id="cond" style="display:none;" class="formgroup form-flow" >
                <br>
                <@c.Condition id="queryCondition" target="#indexListContent" ctrlsize=2 button=false style="width:1480px;margin-left:-20px">
                		<@c.FormInput id="indexCode"   name="indexCode" label="" placeholder="指标编码" value=""/>
		        		<@c.FormInput id="indexName" name="indexName" label="" placeholder="指标名称"  value=""/>
		        		<@c.FormRef id="indexTableName" name="indexTableName" placeholder="指标表" label=""  showValue="${IndexInfoVO?if_exists.indexTableName?if_exists}" url="${ContextPath}/index/indexmanager/findIdxList.d?type_id="+type_id/>
		       	 		<@c.FormRef id="comedepart" name="comedepart" placeholder="归属部门" label="" value=""  showValue="${IndexInfoVO?if_exists.comedepart?if_exists}" url="${ContextPath}/index/indexmanager/orgList.d" />
	                    <@c.Button type="reset" size="md"  click="clearData()">重置</@c.Button>
		       	 		<span id="query" click="subConds()"><@c.Button type="primary" size="md"  click="">查询</@c.Button></span>
                </@c.Condition>
				</div>
            </@c.BoxHeader>
            
            <@c.BoxBody>
                <@c.TableLoader id="indexListContent" url="${ContextPath}/index/indexmanager/listData.d">
                    <#include "list-data.ftl">
                </@c.TableLoader>
            </@c.BoxBody>
            
            <@c.BoxFooter>
            	<@c.Pagination class="pull-right" target="#indexListContent" page=indexlist /><!-- 分页 -->
            </@c.BoxFooter>
        </@c.Box>
    </@c.Tab>
    
    <@c.Tab  id="detailPanel" >
    </@c.Tab>
    
</@c.Tabs>