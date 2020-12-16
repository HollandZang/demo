@echo off

@rem Read db config from db.conf
for /f "tokens=1,2 delims= " %%a in (db.conf) do (
    set %%a=%%b
)
@rem Complete init_data
if (%url%)==() (
    set /p url=Plz input 'url' :
) else (
    echo Read from db.conf: url=%url%
)
if (%user%)==() (
    set /p user=Plz input 'user' :
) else (
    echo Read from db.conf: user=%user%
)
if (%pwd%)==() (
    set /p pwd=Plz input 'pwd' :
) else (
    echo Read from db.conf: pwd=%pwd%
)
if (%package%)==() (
    set /p package=Plz input 'package' :
) else (
    echo Read from db.conf: package=%package%
)
echo url       %url%>db.conf
echo user      %user%>>db.conf
echo pwd       %pwd%>>db.conf
echo package   %package%>>db.conf
echo.

@rem Input command to generate
:command
echo Plz input command ^(input 'helper' to quick started^)^:
set /p line=
echo.

@rem sys_command
if "%line%"=="helper" goto helper
if "%line%"=="exit" goto end

@rem db_command
if "%line%"=="tables" goto tables

@rem generate
setlocal enabledelayedexpansion
for /f "tokens=1,* delims= " %%a in ("%line%") do (
    set table_name=%%a
    set params=%%b
    goto execute
)
:execute
set command=generate
call task.bat %command% %url% %user% %pwd% %table_name% %package%

goto command

:helper
cat ".helper"
pause
goto command

:tables
set command=tables
call task.bat %command% %url% %user% %pwd%

:end