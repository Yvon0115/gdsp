<div class="box">
    <div class="box-body" style="min-height: 400px;">
        <div class="error-page">
            <div class="error-content">
                <h3>400</h3>
                <p>操作出错!</p>
                <hr style="margin-top:10px;border-color:#95CFF7">
                <h4>${exception.message?default("未知错误")?html}</h4>
                <hr>
                <p><a style="width:35%" class="btn btn-primary" href="${ContextPath}/index.d" target="_top"><i class="fa fa-hand-o-up"></i>点击返回</a></p>
            </div>
        </div><!-- /.error-page -->
    </div>
</div>