<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${systemName?if_exists}</title>
    <link type="image/x-icon" href="${__imagePath}/logo/3D_l/main/head/logo/3D_logo_48px.pngrtcut icon">
    <#include "/gdsp/include/resource.ftl">
</head>
<body class="skin-black sidebar-mini">
    <div class="wrapper">

    <#include "/gdsp/include/header.ftl">
    <#include "/gdsp/include/main-sidebar.ftl">
    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <#include "/gdsp/page/${__decorateContent}.ftl">
    </div><!-- /.content-wrapper -->
    <#include "/gdsp/include/foot.ftl">
    <#include "/gdsp/include/control-sidebar.ftl">
</div><!-- ./wrapper -->
<script data-main="${__jsPath}/frame.js" src="${__jsPath}/require.js" ></script>
<script>
    var node = document.getElementsByClassName("content-wrapper")[0];
    if(node){
        node.style.minHeight = (window.innerHeight - 66) + "px";
    }
</script>
</body>
</html>