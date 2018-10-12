echo "将源码版es中plugin目录下的demo文件夹删除，复制刚才在软件版es的plugin文件夹下安装得到的demo文件夹"
rd /s /Q E:\elasticsearch-5.4.1(1)\elasticsearch-5.4.1\core\src\main\plugins\demo
md E:\elasticsearch-5.4.1(1)\elasticsearch-5.4.1\core\src\main\plugins\demo

xcopy E:\elasticsearch-5.4.1\plugins\demo E:\elasticsearch-5.4.1(1)\elasticsearch-5.4.1\core\src\main\plugins\demo\ /e

echo 脚本执行完成