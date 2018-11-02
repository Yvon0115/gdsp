<div class="box">
    <div class="box-body" style="min-height: 400px;">
        <div class="error-page">
            <div class="error-content">
                <h3>500</h3>
                <p>系统出错,请联系系统管理员!</p>
                <hr style="margin-top:10px;border-color:#95CFF7">
                <div style="display: none">${exception?if_exists?html}
	            <#list exception.stackTrace as trace>
                    <h4>${trace?if_exists?html}</h4>
	            </#list></div>
	            <hr>
                <p><a style="width:35%" class="btn btn-primary" href="${ContextPath}/index.d" target="_top"><i class="fa fa-hand-o-up"></i>点击返回</a></p>
            </div>
        </div><!-- /.error-page -->
    </div>
</div>