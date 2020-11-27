###maven快速构建flink模板
```
mvn archetype:generate  -DarchetypeGroupId=org.apache.flink	 -DarchetypeArtifactId=flink-quickstart-scala  -DarchetypeVersion=1.11.2  -DgroupId=org.myorg.quickstart  -DartifactId=flink-scala-project	 -Dversion=0.1	 -Dpackage=org.myorg.quickstart	-DinteractiveMode=false
-DinteractiveMode=false::为时-DinteractiveMode=false，则以批处理模式创建项目，而为时-DinteractiveMode=true，则以交互模式创建项目。
```
