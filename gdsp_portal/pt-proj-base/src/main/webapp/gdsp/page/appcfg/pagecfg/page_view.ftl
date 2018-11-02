<#import "/gdsp/tags/castle.ftl" as c>
<style>
	.navbar-other {
		padding: 15px;
		color: #fff;
	}
	.navbar-other input,select {
		color: #000;
		height: 20px;
	}
	.box-header h3 {
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
		width:70%;
	}
</style>

<div class="skin-green wrapper" id="sub_page">
	<div class=" ">
		<!-- Header Navbar: style can be found in header.less -->
		<nav class=" " role="navigation" style="margin-left:0">
			<div class="navbar-custom-menu" style="float: none;">
					<ul class="nav navbar-nav">
						<!-- Tasks: style can be found in dropdown.less -->
						<li class="dropdown tasks-menu" style="height: 50px;">
							<div class="navbar-other">
								<span>页面名称：${page.page_name}</span>
							</div>
						</li>
						<li class="dropdown tasks-menu">
							<div class="navbar-other" style="height: 50px;">
								<span>描述：${page.page_desc}</span>
							</div>
						</li>
					</ul>
				<div style="height:50px;padding:8px 10px;float:right;">
					<@c.Button type="canel" icon="fa fa-close" action=[c.opentab("#detailPanel")]> 返回</@c.Button>
				</div>
			</div>
		</nav>
	</div>
	<!-- Content Wrapper. Contains page content -->
	<#assign box_icon="<i class=\"fa fa-th\"></i> ">
	<div class="content-wrapper sub-content-wrapper" style="margin-left:0">
		<@c.PageContent>
			<@c.GRow>
				<#assign col_index = 0> 
				<#list coldata?keys as colid>  
					<#assign compList = coldata[colid]> 
					<#assign col_index = col_index+1> 
					<#assign colspan=4> 
					<#list compList as comp>
						  <#if comp_index ==0>
						  	<#assign colspan = comp.colspan>
						  </#if>
					</#list> 
					<section class="col-lg-${colspan?default('4')} connectedSortable" colspan="${colspan?default('4')}">
	                    <#list compList as comp>  
	                    	<div class="box box-solid col_${col_index}">
	                    		<#assign box_title=box_icon+comp.title?if_exists>
								<@c.BoxHeader title=box_title class="bg-teal-gradient">
									<@c.BoxTools right=true>
										<@c.Button type="sm" class="bg-teal" icon="fa fa-minus" action=[c.attrs({"data-widget":"collapse"})]></@c.Button>
										<@c.Button type="sm" class="bg-teal" icon="fa fa-expand" action=[c.attrs({"data-widget":"expand"})]></@c.Button>
									</@c.BoxTools>
								</@c.BoxHeader>
								<@c.BoxBody class="border-radius-none">
									<#if comp.widget_type??&&comp.widget_type=="chart">
										<div class="chart" id="line-chart" style="height:${comp.height}px; text-align: center;">
											 <iframe width="100%" style="height:${comp.height}px" src="http://192.168.247.205:8081/ibmcognos/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=/content/folder[@name='CMOMS2-CSRC']/folder[@name='Rpt_Page']/folder[@name='4.日报']/report[@name='FR_DAY_0005']&run.prompt=false&cv.toolbar=false&cv.header=false&p_pdate=2015-05-14"></iframe>
										</div>
									<#else>
										<div class="chart" id="line-chart"  style="height:${comp.height}px; text-align: center;">
										 <iframe width="100%" style="height:${comp.height}px"  src="http://192.168.247.205:8081/ibmcognos/cgi-bin/cognos.cgi?b_action=cognosViewer&ui.action=run&ui.object=/content/folder[@name='CMOMS2-CSRC']/folder[@name='Rpt_Page']/folder[@name='4.日报']/report[@name='FR_DAY_0005']&run.prompt=false&cv.toolbar=false&cv.header=false&p_pdate=2015-05-14"></iframe>
										</div>
									</#if>
								</@c.BoxBody>
							</div>
	                    </#list>  
                    </section>
				</#list>
			</@c.GRow>
		</@c.PageContent>
	</div>
</div>

<!-- ./wrapper -->
<@c.Script src="script/appcfg/pagcfg/page_customization"/>
