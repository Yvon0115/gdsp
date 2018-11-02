<#import "/gdsp/tags/castle.ftl" as c>
<modal-title>选择安全级别</modal-title>
<#assign op>!<#noparse>
<input type="checkbox" name="safeLevelID" value="${row.doc_code},${row.doc_name}"/>
</#noparse>
</#assign>
<div class="autoscroll">
<@c.SimpleTable checkboxfield="doc_code"  titles=["","名称","描述"] keys=[op,"doc_name","doc_desc"] data=safeLevel/>
</div>
<div class="modal-footer">
    <button type="button" class="btn btn-primary" click="saveSafeLevelNode()" data-dismiss="modal">确定</button>
    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
</div>
 