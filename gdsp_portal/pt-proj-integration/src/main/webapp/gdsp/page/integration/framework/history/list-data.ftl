<#import "/gdsp/tags/castle.ftl" as c>
<#if size??&& size!=0>
<#if  historyChangeVO.content ??>
   <#list historyChangeVO.content  as historyVO>
	  <@c.Box class="box box-success" style="border-top:none;">
		    <@c.BoxBody>
		      <@c.Hidden name="id" value="${historyVO?if_exists.id?if_exists}" />
	          <#if type?? && type=='1'>
	           <#if historyVO.state?? && historyVO.state=='Y'>
	           <@c.TableLinks>
	               <@c.Button title="删除" class="pull-right"  icon="glyphicon glyphicon-trash"  action=[c.rpc("${ContextPath}/framework/history/delete.d?id=${historyVO?if_exists.id?if_exists}",{"dataloader":"#histoyChange"},{"confirm":"确定删除报表说明?"})]> 删除</@c.Button>     
	              <@c.Button title="修改"  class="pull-right"  icon="glyphicon glyphicon-pencil" action=[c.opentab("#detailCHPanel","${ContextPath}/framework/history/edit.d?id=${historyVO?if_exists.id?if_exists}&link_id=${link_id?if_exists}")]>修改</@c.Button>
	            </@c.TableLinks>
	          </#if>
	          </#if>
	      	  <span class="direct-chat-timestamp pull-left">变更日期：${historyVO?if_exists.changeTime?if_exists}</span>
	      	  </br>
	          <span class="pull-center">变更申请人：${historyVO?if_exists.changeName?if_exists}</span>
	          </br>
	          <span class="pull-center">操作员：${historyVO?if_exists.username?if_exists}</span>
	          </br>
	          <span class="pull-center">操作时间：${historyVO?if_exists.operationTime?if_exists}</span>
	          </br>
	          <span class="pull-left" >变更类型：${historyVO?if_exists.opType?if_exists}</span>
	          </br>
	          <span class="direct-chat-timestamp pull-center">修改时间：${historyVO?if_exists.lastModifyTime?if_exists}</span>
	          </br>
	  		  <span class="box-title">变更标题： ${historyVO?if_exists.title?if_exists}</span>
	  		  </br>
	                                               变更描述：${historyVO?if_exists.comments?if_exists}
		    </@c.BoxBody>
		</@c.Box>
	</#list>
</#if>
<#else>
	<div style="margin-left:500px;font-size:14px">
		暂无数据
	</div>
</#if>
<@c.PageData page=historyChangeVO />
