@echo off

if %command%==tables (
    java -jar libs/demo-1.0-SNAPSHOT.jar tables %url% %user% %pwd%
) else if %command%==generate (
    java -jar libs/demo-1.0-SNAPSHOT.jar generate %url% %user% %pwd% %table_name% %package%
) else (
    echo "todo 都走到了generate"
)

echo task finished
echo.