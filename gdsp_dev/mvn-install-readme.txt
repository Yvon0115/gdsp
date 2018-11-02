A.mvn install 相关
命令：mvn clean install -P{profile}


右键项目->Run As->Maven install 等价于 mvn install -P{默认profile} 按照B步骤配置则等价于 mvn install -Ptest
右键项目->Run As->Maven build 填写 参数Goal等于install,profiles等于test,test-dubbo,release,release-dubbo四者之一
profile列表：
1.test 
2.test-dubbo
3.release
4.release-dubbo

如果需要使用dubbo框架打测试包需要用 mvn clean install -Ptest-dubbo
如果需要使用dubbo框架打生产包需要用 mvn clean install -Prelease-dubbo
执行输出日志若出现[WARNING] The requested profile "bucunzai" could not be activated because it does not exist. 则需要排查问题，重新打包
B.profile配置
备注：profile配置两种方式任选一个（具体看eclipse配置的是哪个xml），若使用的是本地maven安装目录conf/setting.xml则按方式1，否则按照方式2
方式1.修改本地maven安装目录conf/setting.xml
方式2.修改setting-app.xml

修改内容如下： 在<profiles>节点内加入如下内容，若<profiles>节点内已有内容，则需要保证仅一个activeByDefault
 
 <profile>
			<id>test</id>
			<properties>
				<project.dev.version>0.0.1-SNAPSHOT</project.dev.version>
				<project.castle.version>0.0.1-SNAPSHOT</project.castle.version>
				<project.platform.version>0.0.1-SNAPSHOT</project.platform.version>
				<project.portal.version>0.0.1-SNAPSHOT</project.portal.version>
				<resource.folder>snapshot</resource.folder>
			</properties>
			<activation>
				<activeByDefault>true</activeByDefault>
			</activation>
		</profile>
		<profile>
			<id>release</id>
			<properties>
				<resource.folder>release</resource.folder>
			</properties>
		</profile>
		
		<profile>
			<id>test-dubbo</id>
			<properties>
				<project.dev.version>0.0.1-SNAPSHOT</project.dev.version>
				<project.castle.version>0.0.1-SNAPSHOT</project.castle.version>
				<project.platform.version>0.0.1-SNAPSHOT</project.platform.version>
				<project.portal.version>0.0.1-SNAPSHOT</project.portal.version>
				<resource.folder>snapshot-dubbo</resource.folder>
			</properties>
		</profile>
		<profile>
			<id>release-dubbo</id>
			<properties>
				<resource.folder>release-dubbo</resource.folder>
			</properties>
		</profile>

