<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${systemName?if_exists}</title>
    <link type="image/x-icon" href="${__imagePath}/main/head/logo/3D_logo_48px.png" rel="shortcut icon">
    <#include "/gdsp/include/resource.ftl">
</head>
<#if Context.requestInfo?? && Context.requestInfo.menuType!=4 && Context.requestInfo.moduleId??&&Context.requestInfo.moduleId!="">
    <#assign hasSideBar=true>
<#else>
    <#assign hasSideBar=false>
</#if>
<body class="skin-default fixed ${hasSideBar?string("sidebar-mini","sidebar-none")}">
<div id="__preloader">
    <div id="__preloader-icon">
        <i class="fa fa-spinner"></i>
    </div>
</div>
<div class="wrapper">
    <#include "/gdsp/include/header_vertical.ftl">
    <#include "castle-main-sub-vertical.ftl">
</div><!-- ./wrapper <#include "/gdsp/include/foot.ftl">-->
<#include "/gdsp/include/foot_csdc.ftl">
<script data-main="${__jsPath}/frame.js" src="${__jsPath}/require.js" ></script>
</body>
</html>