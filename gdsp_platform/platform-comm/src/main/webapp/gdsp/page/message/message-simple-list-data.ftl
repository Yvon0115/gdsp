<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/message/message_tools" />


<#assign subject>!<#noparse>

	<@c.Link title=row.subject click="showDetaimessage(e,'${row.id}')">${row.subject}</@c.Link>

</#noparse>
</#assign>

<@c.SimpleTable striped=false   keys=[subject,"transtime"] data=messagePages.content/>
<@c.PageData page=messagePages?default("")/>
