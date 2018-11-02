<#import "/gdsp/tags/castle.ftl" as c>
<#--<input type="hidden" name="jsRequire" value="${__scriptPath}/wf_delegate.js"/>-->
<@c.Script src="script/wf_delegate" />
<#assign op>!<#noparse>
	<@c.Link title="点击选择该用户" icon="glyphicon glyphicon-ok-sign" click="saveAccept('${row.id}','${row.username}')">选择</@c.Link>
</#noparse>
</#assign>
<#-- 第一种 方法： 修改Comp.ftl中SimpleTable定义，增加了新的属性，然后用checkbox的click事件来控制使checkbox单选
				这种办法，使复选按钮实现了单选功能，但是用户体验不好，可以使用第二种方式。
 -->
<#--<@c.SimpleTable checkAllShow=false checkBoxClick="checkBoxClick" checkboxName="accept" striped=true titles=["账号","姓名"] keys=["account","username"] data=accepts.content checkbox=true/> -->
<#-- 第二种方法： SimpleTable禁用checkbox功能，在显示的数据后面加上一个操作连接<@c.Link>，当点击的时候，关闭窗体(或者页签)，返回值。
				这种办法比上一种的好，直观，明了。
 -->
<@c.SimpleTable checkbox=false striped=true titles=["账号","姓名","操作"] keys=["account","username",op] data=accepts.content />
<@c.PageData page=accepts />