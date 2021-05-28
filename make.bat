@echo off
rem ---------------------------------------------------------------------------
chcp 65001 1>nul
mkdir dist 2>nul
rem ---------------------------------------------------------------------------
echo Compiling...
call mvnw clean package  > compiling.log
if %errorlevel% neq 0 (
  echo "Compilation was failed. Check compiling.log for more details"
  exit /b
)
rem ---------------------------------------------------------------------------
copy target\Poll-app-0.0.1-SNAPSHOT.jar dist /Y 1>nul
copy scripts\tl.bat dist /Y 1>nul
rmdir /s /q target\ 1>nul
rem ---------------------------------------------------------------------------
echo ...successful v

