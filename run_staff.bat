@echo off
chcp 65001 > nul
title LIBMAN - MODULE 2: THỦ THƯ CHO BẠN ĐỌC MƯỢN TÀI LIỆU (B23DCCN015)
echo ================================================================================
echo   KHỞI CHẠY MODULE 2: THỦ THƯ CHO BẠN ĐỌC MƯỢN TÀI LIỆU (MÃ SV: B23DCCN015)
echo ================================================================================

set JAVA_CMD=java
if exist "C:\Program Files\Java\jdk-23\bin\java.exe" (
    set JAVA_CMD="C:\Program Files\Java\jdk-23\bin\java.exe"
)

%JAVA_CMD% -Dfile.encoding=UTF-8 -cp "build;lib\mysql-connector-j-8.0.33.jar" vn.edu.ptit.libman.presentation.staff_ui.MainStaffFrm5
pause

