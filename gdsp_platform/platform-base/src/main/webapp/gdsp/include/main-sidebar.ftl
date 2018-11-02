<#import "/gdsp/tags/nav.ftl" as nav>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/search.js"/>-->
<@c.Script src="script/search" />

<script>
$(function(){

$(".page_sidebar li").click(function(){//.end 节点元素返回上一步操作
$(this).addClass('open').children('.sub_menu').slideDown().end().siblings().removeClass('open').children('.sub_menu').slideUp();
//$(this).children('.arrow').addClass('on')
});

})

</script>
<!-- Left side column. contains the logo and sidebar -->
<#if hasSideBar>
    <#assign _moduleId = Context.requestInfo.moduleId>
    <#assign _menuSide = Context.getLoaderValue("menuNavigator",_moduleId)>
    <#if _menuSide?? && _menuSide?size gt 0>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <div class="sidebar-content">
    <div style="margin: 10px 10px 5px 5px">
    	<input id="product_search" type="text" style="width:218px;height:32px;background-color:#1f465e;border:1px solid #5a7a8e;color:#d2d2d2"
    	 class="form-control"  placeholder="功能搜索" data-provide="typeahead">
    	 <input type="hidden" id="load_state"/>
    </div>
    <section class="sidebar" id="jtabs-sidebar-menu-xg">
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="page_sidebar" jtabs-ul="yes"> <!-- sidebar-menu -->

            <@nav.CsdcSideBarMenu menus=_menuSide key=_moduleId urlField="funcUrl" nameField="funname"/>
        </ul>
    </section>
    </div>
    <!-- /.sidebar -->
</aside>
    </#if>
</#if>