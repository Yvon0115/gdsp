<#import "/gdsp/tags/castle.ftl" as c>
<input type="hidden"   name="jsRequire" value="${__scriptPath}/indexanddim/idxdim.js"/>
<@c.Tabs class="clearfix">
	<@c.Tab  id="mainPanel" active=true>
		<@c.Box  class="box-right">
	         <@c.BoxBody class="scrollbar">
	         	<#if errorList?size gt 0 >
		    		共失败${errorList?size}条，详情如下：<br />
		    		<table border=1>
			    	<#list errorList as errorStr>
			    		<tr>
			    			<td width="50px">
			    				${errorStr_index?if_exists+1}
			    			</td>
			    			<td>
			    				${errorStr?if_exists}
			    			</td>
			    		</tr>
			    	</#list>
			    	</table>
		    	</#if>
		    	<hr />
		    	共成功${excelList?size - errorList?size}条
			 </@c.BoxBody>
			 <@c.BoxFooter class="text-center">
		        <@c.Button type="canel" icon="glyphicon glyphicon-arrow-left" click="redirectMain()">返回</@c.Button>
		    </@c.BoxFooter>
		</@c.Box>
	</@c.Tab>
</@c.Tabs>

