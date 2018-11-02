<#import "/gdsp/tags/castle.ftl" as c>

<#if __currentPortlet.getPreference( "height")??>
	<#assign autoHeight=false>
	<#assign height=__currentPortlet.getPreference( "height")>
<#else>
	<#assign autoHeight=true>
</#if>

<@c.Hidden name="widget_id" value="${__currentPortlet.id}" />
  <@c.Script src="script/integration/favorites-open"  />
    <@c.Script src="script/treeswith"  />