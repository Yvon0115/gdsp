<#import "/gdsp/tags/castle.ftl" as c>
	<@c.SimpleTable striped=false titles=["维值","维值描述"] keys=["dimvalue","dimvaluememo"] data=dims.content checkbox=true/>
	<@c.PageData page=dims />