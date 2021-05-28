@echo off
chcp 65001
java -Dsun.stdout.encoding=UTF-8 -jar %~dp0Poll-app-0.0.1-SNAPSHOT.jar %*
