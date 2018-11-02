<#import "/gdsp/tags/castle.ftl" as c>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>${systemName?if_exists}|首页</title>
	</head>
	<script>
		$("section[class*=content]").css({"padding": "0"});
	</script>
	<body>
	    <!--页面中部-->
		<div class="middle" id="scrollable">
			<div class="items">
				<ul class="item_list in">
				<!--第一个循环-->
					<li>
						<!--数据处理-->
						<a class="box00" href="" target="_blank">
							<span><img src="${ContextPath}/gdsp/images/home/01.jpg"></span>
						</a>
						<!--数据治理-->
						<a class="box01" href="" target="_blank">
							<span><img src="${ContextPath}/gdsp/images/home/02.jpg"></span>
						</a>
						<!--云计算大数据服务平台-->
						<a class="box02" href="" target="_blank">
							<span><img src="${ContextPath}/gdsp/images/home/03.jpg"></span>
						</a>
						<!--统一工作平台-->
						<a class="box033" href="${ContextPath}/index.d" target="_self">
							<span><img src="${ContextPath}/gdsp/images/home/04.jpg"></span>
						</a>
						<!--大数据场景开发平台-->
						<a class="box10" href="" target="_blank">
							<span><img src="${ContextPath}/gdsp/images/home/05.jpg"></span>
						</a>
						<!--云计算大数据管理平台-->
						<a class="box11" href="" target="_blank">	
							<span><img src="${ContextPath}/gdsp/images/home/06.jpg"></span>
						</a>
						<!--数据应用-->
						<a class="box12" href="" target="_blank">
							<span><img src="${ContextPath}/gdsp/images/home/07.jpg"></span>
						</a>
					</li>
				<ul>
			</div>
		</div>
	</body>
</html>