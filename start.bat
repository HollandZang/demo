@set /p input=Pls input 'url' (such: jdbc:oracle:thin:@localhost:1521/orcl) \:
@set url=%input%
@echo %url%

@set user=yb_acd
@set pwd=yb_acd
@set table_name=SYS_USER
@set package=com.stardon.stardon_main

java -jar build/libs/demo-1.0-SNAPSHOT.jar %url% %user% %pwd% %table_name% %package%