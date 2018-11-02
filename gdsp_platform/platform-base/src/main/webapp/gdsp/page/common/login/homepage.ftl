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
<body >
		<!--页面中部-->
	<div class="middle" id="scrollable">
		<div class="items">
			<ul class="item_list in">
			<!--第一个循环-->
				<li>
					<a class="box00" href="http://${SMSPath}">
						<span class="box01_icon"><img src="../images/sys.png"></span>
						<span class="box01_text">统计监测</span>
					</a>	
					<a class="box01" href="http://${RMSPath}">
						<span class="box01_icon"><img src="../images/sys.png"></span>
						<span class="box01_text">风险监测</span>
					</a>
					<a class="box02">
					<span class="box01_icon"><img src="../images/sys.png"></span>
						<span class="box01_text"></span>
					</a>
					<div class="box06">
						<div id="title6">待办公文<span class="badge">4</span></div>
						<div class="content_db">
						<p><a href="#">[市场部]统计周报</a>2015-04-30</p>
							<p><a href="#">[中国证监会]关于杨正太、杨志</a>2015-04-30</p>
							<p><a href="#">[信息中心]证券期货简报</a>2015-04-30</p>
							<p><a href="#">[稽查局]首席稽查办公室</a>2015-04-30</p>
							<p><a href="#">[市场部]统计周报</a>2015-04-30</p>
							<p><a href="#">证监会通报对四...</a>2015-04-30</p>
						</div>
					</div>
					
					<div class="box07">
						<div id="title7">待阅公文<span class="badge">5</span></div>
						<div class="content_db">
						<p><a href="#">[市场部]统计周报</a>2015-04-30</p>
								<p><a href="#">[中国证监会]关于杨正太、杨志</a>2015-04-30</p>
								<p><a href="#">[信息中心]证券期货简报</a>2015-04-30</p>
								<p><a href="#">[稽查局]首席稽查办公室</a>2015-04-30</p>
								<p><a href="#">[市场部]统计周报</a>2015-04-30</p>
								<p><a href="#">证监会通报对四...</a>2015-04-30</p>
						</div>
					</div>
					<a class="box033">
					<span class="box01_icon"><img src="../images/sys.png"></span>
						<span class="box01_text"></span>
					</a>
					<a>
					<div class="box05">
					<img src="../images/rili.png" width="210" height="210">
						
					</div>
					</a>
					<!--中间图片-->
						<div class="box03" >
						
						<!--通知公告-->
						<div class="box04"  >
							<div class="box_title_in">通知公告</div>
							<div class="box4_content">
								<marquee direction="up"  loop="-1" scrollamount="2" scrolldelay="10" onMouseOut="this.start()" onMouseOver="this.stop()">
								<div class="notice"><p class="notice_title">关于就《证券公司及基金管理公司子公司资产证券化业务管...</p><p class="notice_date">2014-12-24</p></div>
								<div class="notice"><p class="notice_title">关于就《公开募集证券投资基金运作指引第1号——商品期货...<p class="notice_date">2014-12-24</p></div>
								<div class="notice"><p class="notice_title">关于就《募集证券投资</p><p class="notice_date">2014-12-24</p></div>
							</marquee>
							</div>
						<div>
						</div>
				</li>
			<ul>
		</div>
	</div>
</body>
</html>