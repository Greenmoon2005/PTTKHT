@echo off
chcp 65001 > nul
title LIBMAN - HỆ THỐNG QUẢN LÝ THƯ VIỆN ĐẠI HỌC (B23DCCN015)
echo ================================================================================
echo   KHỞI CHẠY HỆ THỐNG QUẢN LÝ THƯ VIỆN LIBMAN - MÃ SV: B23DCCN015 (HẬU TỐ 5)
echo ================================================================================

set JAVA_CMD=java
if exist "C:\Program Files\Java\jdk-23\bin\java.exe" (
    set JAVA_CMD="C:\Program Files\Java\jdk-23\bin\java.exe"
)

%JAVA_CMD% -Dfile.encoding=UTF-8 -cp "build;lib\mysql-connector-j-8.0.33.jar" vn.edu.ptit.libman.Main5
pause

