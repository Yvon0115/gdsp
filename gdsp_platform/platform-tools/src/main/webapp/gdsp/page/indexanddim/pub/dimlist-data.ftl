<#import "/gdsp/tags/castle.ftl" as c>
	<@c.SimpleTable striped=false titles=["维度名称","维度字段名称","维度数据表","维度说明","描述","维度类型"] keys=["dimname","dimfieldname","dimtablename","dimmemo","memo","dimtype"] data=dims.content checkbox=true/>
	<@c.PageData page=dims />

