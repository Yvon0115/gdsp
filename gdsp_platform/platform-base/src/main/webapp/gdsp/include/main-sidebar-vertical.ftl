<#import "/gdsp/tags/nav.ftl" as nav>
<!-- Left side column. contains the logo and sidebar -->
<@c.Script src="script/search" />
<#if hasSideBar>
    <#assign _moduleId = Context.requestInfo.moduleId>
    <#assign _menuSide = Context.getLoaderValue("menuNavigator")?default([])>
    <#if _menuSide?? && _menuSide?size gt 0>
<aside class="main-sidebar" style="z-index:100;">
    <!-- sidebar: style can be found in sidebar.less
    <div class="header">
    	<div class="pull-left">功能导航</div>
    	<a href="#" class="sidebar-toggle pull-right" data-toggle="offcanvas" role="button">
    	<i class="fa fa-bars"></i></a>
    </div> -->
    <div style="margin: 2px 0px 0px 0px">
    	<input id="product_search" type="text" style="width:230px;height:25px;background-color:#eeeeee;border:1px solid #5a7a8e;color:#080808"
    	 class="form-control"  placeholder="功能搜索" data-provide="typeahead">
    	 <input type="hidden" id="load_state"/>
    </div>
    
    <div class="sidebar-content">
    <section class="sidebar">
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="sidebar-menu" id="jtabs-sidebar-menu-xg">

            <@nav.SideBarMenu menus=_menuSide urlField="funcUrl" nameField="funname"/>
            <li >&nbsp;</li>
        </ul>
    </section>
    </div>
    
    <!-- /.sidebar -->
</aside>
<div class="sidebar-accordion-open" onclick="sidebarAccordion('close');">
</div>
<div class="sidebar-accordion-close" onclick="sidebarAccordion('open');">
</div>
    </#if>
</#if>
<script>
	function sidebarAccordion(state){
		if(state=='close'){
			$(".sidebar-accordion-open").css("visibility","hidden");
			$(".sidebar-accordion-close").css("visibility","visible");
			$(".main-sidebar").css("display","none");
			$("#__castlePageContent").css("margin-left","0px");
		}else{
			$(".sidebar-accordion-open").css("visibility","visible");
			$(".sidebar-accordion-close").css("visibility","hidden");
			$(".main-sidebar").css("display","block");
			$("#__castlePageContent").css("margin-left","230px");
		}
	}
</script>