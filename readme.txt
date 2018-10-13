简介：
	这是一个es-plugin的demo，可以通过 ：http://127.0.0.1:9200/_dQuery/para?indexName=iris-2&from=0&size=20&query= 
	获取es中的数据。其中query可以不填，也可以参考文末方式填写。

打包方法：
	1、mvn clean install compile -Dmaven.test.skip=true
	2、将target\elasticsearch-demo-0.0.1-SNAPSHOT.jar 和 src\main\resources\plugin-descriptor.properties文件复制到新建的elasticsearch文件夹（位置随意）
	3、添加elasticsearch文件夹到压缩文件，文件名为demo-5.4.0.1.zip
	4、进入elasticsearch-5.4.1\bin文件夹下
	4.1、如果之前安装过同名plugin，需要卸载，执行：elasticsearch-plugin remove demo
	4.2、安装新插件，执行：elasticsearch-plugin install file:{path}/demo-5.4.0.1.zip （其中{path}是该压缩包的绝对路径）
	
	PS：第四步结束后，会得到一个elasticsearch-5.4.1\plugins\demo文件夹，如果需要在源码中启动插件，只需将该文件夹复制到源码版elasticsearch-5.4.1\core\src\main\plugins下即可

二次开发（重要！）：
	如果想利用此demo进行二次开发，需要注意以下几点：（假如新plugin的名称为“myplugin”）
	1、需要新建一个maven项目，  groupId为com.myplugin，  artifactId为elasticsearch_myplugin，然后复制pom.xml中的所有<dependency>至新项目对应位置。
	2、按照该插件目录，新建 action包，common包，plugin包，rest包，ding.util包（这个包是es数据解析的包，注意路径不要弄错），然后把对应包下的.java文件粘贴进去，然后代码会报错，只需要修改import中的路径为你项目中路径。
	3、需要在com.demo.elasticsearch_demo.rest.TasteEventRestAction类的TasteEventRestAction中，将url注册成不同于"_dQuery"的url，
	如"myplugin"等任意不与之前url重复的名称，因为再次使用"_dQuery"会造成重复注册的错误（但是如果卸载了之前的插件就没事）。不同的url对应不同的查询
	4、需要修改插件名称，有下面几个地方需要修改：
		1）com.demo.elasticsearch_demo.plugin.TastePlugin的name()方法，返回值需要修改，如"myplugin"
		2）com.demo.elasticsearch_demo.TasteEventAction中的NAME需要修改，如"mypluginevent"
		2）将本项目中的src/main下的resources/plugin-descriptor.properties文件复制过去到对应位置，然后修改其中的34行、40行、58行，demo修改为myplugin，58行需要保证和你项目中路径一致
		3）上述两步不保证完全修改完了，需要你按照 “打包方法” ，安装到es中去启动，如果出现 “started”，然后在浏览器中输入url能得到正确的数据，说明插件完全正确

			
	querys[0]="(cateid:8 OR cateid:10) AND (local:450 OR local:453 OR local:459 OR local:458 OR local:460 OR local:462)AND state:11 AND postdate:[20180401 TO 20180912]";
	querys[1]="(title:优惠 AND title:出租)AND postdate:[20180401 TO 20180912]";
	querys[2]="(title:开业 OR title:沈阳  OR title:广场)AND(local:3396 OR local:147 OR local:459 OR local:458 OR local:460 OR local:462)AND postdate:[20180401 TO 20180912]";
	querys[3]="(title:计算机 AND title:暑期)AND postdate:[20180401 TO 20180912]";
	querys[4]="(cateid:1008 OR cateid:1009) AND (local:1 OR local:1142 OR local:4 OR local:1813 OR local:460 OR local:462)AND postdate:[20180401 TO 20180912]";
	querys[5]="(dtitle9:163 OR cateid:10) AND (local:304 ) AND source:6";
	querys[6]="(cateid:[1 TO 1000]) AND (title:二手房 OR title:医疗 OR title:价格 OR title:中学 OR title:一卫 OR title:一厅)AND postdate:[20180322 TO 20180801]";
	querys[7]="(dcateid4:[500 TO 1500]) AND (title:三居 OR title:交通 OR title:中心广场 OR title:中学 )";
	querys[8]="(cateid:8 OR cateid:1 OR cateid:12) AND (title:医院 OR title:医疗 OR title:商品房 OR title:地铁 OR title:巴士)AND postdate:[20180401 TO 20180912]";
	querys[9]="(title:精装 AND title:通风 AND title:采光)AND postdate:[20180401 TO 20180912]";
	querys[10]="(cateid:[1000 TO 2000]) AND (title:隔断 OR title:装修 OR title:一卫 OR title:一厅)";
	querys[11]="(cateid:[1 TO 500]) AND (title:二手房 OR title:医疗 OR title:价格 OR title:中学 OR title:租房 )AND postdate:[20180322 TO 20180801]";
	querys[12]="postdate:[20170501 TO 20170601]";
	querys[13]="(cateid:8 OR cateid:1 OR cateid:12) AND (title:一居 OR title:万达 ) AND (title:出售 OR title:二手房 )AND (title:一厅 OR title:空调 )";
	querys[14]="(cateid:8 OR cateid:1 OR cateid:12) AND (title:五道口 OR title:位置 ) AND (title:公交 OR title:地铁  OR title:飞机场)AND (title:三卫  OR title:空调  OR title:电视)";
	querys[15]="(cateid:[1 TO 1000]) AND postdate:[20180401 TO 20180912] AND dcateid0:[500 TO 1500]";
	querys[16]="(cateid:[1 TO 500]) AND (title:付三 AND title:一卫) AND(title:二手 OR title:出售  OR title:租房 )AND postdate:[20180322 TO 20180801]";
	querys[17]="(cateid:[1 TO 500]) AND (local:450 OR local:453 OR local:459 OR local:458 OR local:460 OR local:462)AND state:11 AND postdate:[20180401 TO 20180912]";
	querys[18]="(cateid:8 OR cateid:10) AND (dcateid4:[500 TO 1500]) AND state:[0 TO 19]";
	querys[19]="(title:办公房 AND title:上海)";
            