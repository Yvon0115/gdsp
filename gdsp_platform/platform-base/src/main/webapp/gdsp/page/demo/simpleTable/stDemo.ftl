<#--simpleTable-->
<#import "/gdsp/tags/castle.ftl" as c>
<@c.Script src="script/demo/stdemo"/>
<@c.Script id="" src="" onload="getSTable('#stable')"/>

<@c.Box style="height: 500px;width: 800px; margin: auto;" >
<h3>SimpleTable Demo页面</h3>
<br />
<@c.SimpleTable id="stable" striped=true titles=["账号","姓名","手机"] 
keys=["account","username","mobile"]
data=[{'id':'1','account':'1','username':'1','mobile':'1'},{'id':'2','account':'2','username':'2','mobile':'2'},
{'id':'3','account':'3','username':'3','mobile':'3'},{'id':'4','account':'4','username':'4','mobile':'4'},
{'id':'5','account':'5','username':'5','mobile':'5'},{'id':'6','account':'6','username':'6','mobile':'6'}]
ellipsis={"account":"70px","username":"70px","mobile":"70px"} checkbox=true />
<br />
<@c.Form id="Form" class="validate" action="">
<@c.Button click="getChecked()">点击</@c.Button>
<@c.FormInput name="tabledate" style="width:500px;" type="text" label="当前table内容:"  value=""/>
<@c.FormInput name="crow" style="width:500px;" type="text" label="当前选中的总行数:"  value=""/>
<@c.FormInput name="rowdata" style="width:500px;" type="text" label="当前选中行数据:"  value=""/>
</@c.Form>
</@c.Box>


