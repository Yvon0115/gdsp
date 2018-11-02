<#import "/gdsp/tags/castle.ftl" as c>

<div  id="sub_page">
<@c.Box>
	<@c.BoxHeader class="header-bg with-border">
	<div class="btn-toolbar " role="toolbar" style="margin-left: 0px;">
	
		<div class="btn-group-vertical pull-left" role="group" >
		
			<!-- 新建自定义布局按钮-->
			<div class="btn-group" role="group" >
			<@c.Button type="primary" class="pull-left"  icon="fa fa-plus-square" click="newLayout('${page.id?if_exists}')">添加布局</@c.Button>
			</div>
		
			<div class="btn-group" role="group" style="margin-top: 20px;">
			<@c.Button type="primary" class="pull-left"  icon="fa fa-plus-square" click="pageCustom.showCompReference(e)"> 添加组件</@c.Button>
			</div>
		
		</div>
		
		<div class="pull-left page_custom" style="width:720px;height: 87px;">
			<@c.Form id="pageForm" cols=3 class="validate" action="${ContextPath}/appcfg/pagecfg/savePageConfig.d" method="post" before={"pageCustom.beforeSaveCfg(op)":""} after={"pageCustom.afterSaveCfg()":""} >

				<div class="btn-group pull-left" role="group">
			
					<div class="btn-group" role="group">
						<@c.FormComboBox name="layout_id"  label="布局" value="${page.layout_id}" events="{change:function(){pageCustom.changeLayout()}}" lclass="customlabel" cclass="customcontrol">
							<#list layout as lo>
								<@c.Option value="${lo.id}">${lo.doc_name}</@c.Option>
							</#list>
						</@c.FormComboBox>
					</div>
				<!-- 删除自定义布局 -->
				<@c.Button icon="glyphicon glyphicon-remove-sign" click="delLayout()" action=[c.opentab("#detailPanel","${ContextPath}/appcfg/pagecfg/pageConfig.d?page_id=${page.id}")] >删除布局</@c.Button>
 				</div>
 		
 				<div class="btn-group pull-right" role="group" style="margin-right: -261px;">
		    		<div class="btn-group" role="group">
						<@c.Button class="pull-right" action=[c.opentab("#detailPanel","${ContextPath}/appcfg/pagecfg/pageConfig.d?page_id=${page.id}")]  icon="fa fa-refresh" >重置</@c.Button>
						<@c.Button class="pull-right" icon="fa fa-save" action=[c.saveform("#pageForm")]>保存</@c.Button>
	        		</div>
					<@c.Button click="pagereload()"  icon="glyphicon glyphicon-arrow-left"  >返回</@c.Button>
	        		<button data-toggle="dropdown" class="btn btn-default  dropdown-toggle" type="button">
	           			<span class="caret"></span>
	            		<span class="sr-only">Toggle Dropdown</span>
	       			</button>
		        	<ul role="menu" class="dropdown-menu">
		            	<li><a href="#" click="toPublishFun('${page.id}',2)"><i class="fa fa-circle-o"></i>发布功能</a></li>
		            	<li><a href="#" click="toPublishFun('${page.id}',1)"><i class="fa fa-file-o"></i>发布页面</a></li>
		        	</ul>
        		</div>
 			
	 		<div class="input-group pull-left" style="margin-top: 15px;">
				<div class="input-group">
					<@c.FormInput name="page_name" label="名称" value="${page.page_name}" lclass="customlabel" cclass="customcontrol" events="{blur :function(){$Utils.validInputSpeChar(this)}}"  validation={"required":true,"minlength":1,"maxlength":60}/>
				</div>
			
				<div class="input-group pull-right">
					<@c.FormInput name="page_desc"  label="描述" value="${page.page_desc?if_exists}" lclass="customlabel" cclass="customcontrol" events="{blur :function(){$Utils.validInputSpeChar(this)}}" validation={"alphanumerSpec":true,"maxlength":120}></@c.FormInput>
				</div>
			</div>
				
			<@c.Hidden name="id" id="page_id" value="${page.id}" />
			<@c.Hidden name="del_comp" id="del_comp" value="" />
			<@c.Hidden name="col1" id="col1" value="" />
			<@c.Hidden name="col2" id="col2" value="" />
			<@c.Hidden name="col3" id="col3" value="" />
			
			</@c.Form>
        
		</div>
	</div>

	</@c.BoxHeader>
	<@c.BoxBody>
        <!-- Content Wrapper. Contains page content -->
		<#assign box_icon="<i class=\"fa fa-th\"></i> ">
        <div class="content-wrapper sub-content-wrapper">
			<@c.PageContent>
				<@c.GRow style="margin-left:0px;">
					<#assign col_index = 0>
					<#list layoutcol as lc >
						<#assign compList = coldata[lc.column_id]>
						<#assign col_index = lc_index+1>
						<#assign colspan=lc.colspan?default(4)>
						<#if colspan<13 >
                        <section class="col-md-${colspan?default('4')} connectedSortable" colspan="${colspan?default('4')}">
                        <#else>
                        <!-- 如果是自定义布局，colspan除15获取宽度百分比，15为自定义系数，用来区别boostrap的12栅栏，设置排序样式-->
                       	<section class="col-md-${colspan?default('4')} connectedSortable" colspan="${colspan?default('4')}" style="float: left; width:${colspan/15}%;">
                        </#if>
							<#if compList??>
								<#list compList as comp>
                                    <div class="box box-portlet col_${col_index}" id="${comp.id}" widget-id="${comp.widget_id}">
										<#assign box_title=box_icon+comp.title?if_exists>
										<@c.BoxHeader title=box_title class="header-bg border">
											<@c.BoxTools right=true>
												<@c.Button type="none" class="btn-box-tool" icon="fa fa-gear" action=[c.opendlg("#settingDlg","${ContextPath}/appcfg/pagecfg/setComp.d?widget_id="+comp.id+"&widgettype="+comp.widget_type?if_exists)]></@c.Button>
												<@c.Button type="none" class="btn-box-tool" icon="fa fa-minus" action=[c.attrs({"data-widget":"collapse"})]></@c.Button>
												<@c.Button type="none" class="btn-box-tool" icon="fa fa-times" action=[c.attrs({"data-widget":"remove"})]></@c.Button>
											</@c.BoxTools>
										</@c.BoxHeader>
										<@c.BoxBody>
											<#if comp.widget_type??&&comp.widget_type=="chart">
                                                <div class="chart" id="line-chart" style="height: 250px; text-align: center;">
                                                    <img src="${__imagePath}/1.png" style="height: 180px;margin-top: 20px;" />
                                                </div>
											<#else>
                                                <div class="chart" id="line-chart" style="height: 250px; text-align: center;">
                                                    <img src="${__imagePath}/2.jpg" style="margin: 0 auto;" />
                                                </div>
											</#if>
										</@c.BoxBody>
                                    </div>
								</#list>
							</#if>
                        </section>
					</#list>
				</@c.GRow>
			</@c.PageContent>
            <!-- /.content -->
			<#if compmap??>
				<#list compmap?keys as compid>
					<#assign comp=compmap[compid]>
					<@c.Hidden name="hidden_${compid}" id="hidden_${compid}" value="${comp}" />
				</#list>
			</#if>
        </div>
    </div>
	</@c.BoxBody>
</@c.Box>
	

<!-- ./wrapper -->
<@c.Script id="pageCustom" src="script/appcfg/pagecfg/page_customizat" onload="pageCustom.initSortBox()"/>
<@c.Script src="script/appcfg/pagecfg/pagecfg" />