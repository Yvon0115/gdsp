<#import "/gdsp/tags/castle.ftl" as c>

<@c.SimpleTable striped=false titles=["指标编码","指标名称","业务口径"] 
keys=["indexCode","indexName","businessbore"]  
ellipsis={"indexCode":"80px","indexName":"80px","businessbore":"200px"}
data=inds?default({"content":[]}).content checkbox=true/>
<@c.PageData page=inds?default("")/>
