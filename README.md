# Spring Data Elasticsearch

https://docs.spring.io/spring-data/elasticsearch/docs/current/reference/html/#elasticsearch.clients

https://spring.io/projects/spring-data-elasticsearch    

https://www.elastic.co/guide/en/elasticsearch/reference/current/getting-started-install.html

https://www.elastic.co/guide/en/elasticsearch/reference/current/settings.html

https://www.elastic.co/guide/en/kibana/current/windows.html

https://stackoverflow.com/questions/60115752/failed-to-start-elasticsearch-error-opening-log-file-gc-log-permission-deniPS C:\Program Files\elasticsearch-7.10.1\bin> .\elasticsearch.bat
Exception in thread "main" java.lang.RuntimeException: starting java failed with [1]
output:
[0.007s][error][logging] Error opening log file 'logs/gc.log': Permission denied
[0.007s][error][logging] Initialization of output 'file=logs/gc.log' using options 'filecount=32,filesize=64m' failed.
error:
Java HotSpot(TM) 64-Bit Server VM warning: Option UseConcMarkSweepGC was deprecated in version 9.0 and will likely be removed in a future release.
Invalid -Xlog option '-Xlog:gc*,gc+age=trace,safepoint:file=logs/gc.log:utctime,pid,tags:filecount=32,filesize=64m', see error log for details.
Error: Could not create the Java Virtual Machine.
Error: A fatal exception has occurred. Program will exit.
at org.elasticsearch.tools.launchers.JvmErgonomics.flagsFinal(JvmErgonomics.java:126)
at org.elasticsearch.tools.launchers.JvmErgonomics.finalJvmOptions(JvmErgonomics.java:88)
at org.elasticsearch.tools.launchers.JvmErgonomics.choose(JvmErgonomics.java:59)
at org.elasticsearch.tools.launchers.JvmOptionsParser.jvmOptions(JvmOptionsParser.java:137)
at org.elasticsearch.tools.launchers.JvmOptionsParser.main(JvmOptionsParser.java:95)
PS C:\Program Files\elasticsearch-7.10.1\bin>


    