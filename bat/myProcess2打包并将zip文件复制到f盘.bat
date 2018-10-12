D:
cd D:\javaSourceCode\elasticsearch_demo

md target\elasticsearch
echo ".\target\elasticsearch "

copy /y target\elasticsearch_demo-0.0.1-SNAPSHOT.jar  target\elasticsearch
copy /y src\main\resources\plugin-descriptor.properties target\elasticsearch
echo "elasticsearch-demo-0.0.1-SNAPSHOT.jar & plugin-descriptor.properties -> .\target\elasticsearch "

winrar a -ep1 -o+ -inul -r  -iback target\demo-5.4.0.1.zip target\elasticsearch
echo ".\target\elasticsearch -> demo-5.4.0.1.zip "

del F:\demo-5.4.0.1.zip
copy /y target\demo-5.4.0.1.zip  F:\
echo "demo-5.4.0.1.zip -> F: "
