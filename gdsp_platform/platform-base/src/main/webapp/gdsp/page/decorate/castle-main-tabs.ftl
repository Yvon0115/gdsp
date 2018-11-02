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
<body class="skin-default fixed sidebar-none">
    <#include "castle-main-sub-tabs.ftl">
<div id="__preloader">
    <div id="__preloader-icon">
        <i class="fa fa-spinner"></i>
    </div>
</div>
<script data-main="${__jsPath}/frame.js" src="${__jsPath}/require.js" ></script>
</body>
</html>