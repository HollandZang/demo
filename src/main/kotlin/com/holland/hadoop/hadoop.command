Hadoop运行单词统计
1.创建input文件夹

hadoop fs -mkdir input
2.上传文件到hadoop

hadoop fs -put /root/data/output.txt input
3.运行wordcount（运行前删除旧的output文件夹，可以使用eclipse删除）
hadoop jar ./hadoop-examples-3.2.1.jar wordcount input output


随机返回指定行数的样本数据
hadoop fs -cat /test/gonganbu/scene_analysis_suggestion/* | shuf -n 5

返回前几行的样本数据
hadoop fs -cat /test/gonganbu/scene_analysis_suggestion/* | head -100

返回最后几行的样本数据
hadoop fs -cat /test/gonganbu/scene_analysis_suggestion/* | tail -5

查看文本行数
hadoop fs -cat hdfs://172.16.0.226:8020/test/sys_dict/sysdict_case_type.csv |wc -l

查看文件大小(单位byte)
hadoop fs -du hdfs://172.16.0.226:8020/test/sys_dict/*

hadoop fs -count hdfs://172.16.0.226:8020/test/sys_dict/*