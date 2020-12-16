@echo off

@rem Read db config from db.conf
for /f "tokens=1,2 delims= " %%a in (db.conf) do (
    set %%a=%%b
)

@rem Complete init_data
if %url%==() (set /p url=Plz input 'url' ^(such: jdbc:oracle:thin:@localhost:1521/orcl^)^: )^
else echo Read from db.conf: url=%url%
if %user%==() (set /p user=Plz input 'user' : )^
else echo Read from db.conf: user=%user%
if %pwd%==() (set /p pwd=Plz input 'pwd' : )^
else echo Read from db.conf: pwd=%pwd%
if %package%==() (set /p package=Plz input 'package' : )^
else echo Read from db.conf: package=%package%

@rem Input command to generate
:command
echo Plz input command ^(input 'helper' to quick started^)^:
set /p command=

@rem sys_command
if %command%==helper goto helper
if %command%==exit goto end

@rem db_command
if %command%==tables goto tables

@rem generate
for /f "delims= " %%a in("%command%") do(
  if
  set table_name=%%a
  set password=%%b
)

::set /p table_name=Plz input table_name:
::echo %table_name%
::java -jar ../build/libs/demo-1.0-SNAPSHOT.jar %url% %user% %pwd% %table_name% %package%

:helper
echo.
echo Helper
echo    System command:
echo        helper     user guide
echo        exit       exit this cmd
echo    Database command:
echo        tables     get the name of all the tables
echo    Generate parameter: ^<database table name^> [params...]
echo        -a,-all    generate all, include 'pojo', etc...
echo        -m,-model  generate pojo
echo.
pause
goto command

:tables
java -jar ../build/libs/demo-1.0-SNAPSHOT.jar tables %url% %user% %pwd%

:end