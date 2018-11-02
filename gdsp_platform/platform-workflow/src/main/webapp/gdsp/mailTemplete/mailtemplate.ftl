<html>  
   <head>  
      <meta http-equiv="content-type" content="text/html;charset=utf8">  
   </head>  
   <body>
	<font color='red' size='30'>${username}:</font> <br/>
	您好，您有新的待办事项
	<table border="1">
		<tr>
			<td>类别</td>
			<td>流程名称</td>
			<td>发起人</td>
			<td>任务名称</td>
			<td>任务创建时间</td>
			<td>任务到期时间</td>
		</tr>
		<tr>
			<td>${categoryname}</td>
			<td>${deployname}</td>
			<td>${startUser}</td>
			<td>${taskname}</td>
			<td>${createtime}</td>
			<td>${duedate}</td>
		</tr>
	</table> 
       
   </body>  
</html> 