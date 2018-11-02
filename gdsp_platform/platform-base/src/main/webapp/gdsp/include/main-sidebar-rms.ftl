<#import "/gdsp/tags/nav.ftl" as nav>
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
    <#assign _menuSide = Context.getLoaderValue("menuNavigator")?default([])>
    <#if _menuSide?? && _menuSide?size gt 0>
<aside class="main-sidebar">
    <!-- sidebar: style can be found in sidebar.less -->
    <div class="sidebar-content" >
    <section class="sidebar" id="jtabs-sidebar-menu-xg">
        <!-- /.search form -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <!-- sidebar menu: : style can be found in sidebar.less -->
        <ul class="page_sidebar"> <!-- sidebar-menu -->

            <@nav.RmsCsdcSideBarMenu menus=_menuSide urlField="funcUrl" nameField="funname"/>
        </ul>
    </section>
    </div>
    <!-- /.sidebar -->
</aside>
    </#if>
</#if>