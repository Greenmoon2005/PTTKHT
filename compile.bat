@echo off
chcp 65001 > nul
title LIBMAN - BIÊN DỊCH SOURCE CODE JAVA (B23DCCN015)
echo ================================================================================
echo   BIÊN DỊCH SOURCE CODE JAVA HỆ THỐNG LIBMAN (MÃ SV: B23DCCN015 - HẬU TỐ 5)
echo ================================================================================

if not exist "build" mkdir "build"

set JAVAC_CMD=javac
if exist "C:\Program Files\Java\jdk-23\bin\javac.exe" (
    set JAVAC_CMD="C:\Program Files\Java\jdk-23\bin\javac.exe"
)

echo Đang biên dịch toàn bộ source code trong src/...
powershell -Command "& %JAVAC_CMD% -encoding UTF-8 -cp 'lib\mysql-connector-j-8.0.33.jar' -d 'build' (Get-ChildItem -Path 'src' -Recurse -Filter '*.java').FullName"

if %ERRORLEVEL% EQU 0 (
    echo [THÀNH CÔNG] Toàn bộ mã nguồn đã được biên dịch thành công vào thư mục build/!
) else (
    echo [LỖI] Quá trình biên dịch xảy ra lỗi. Vui lòng kiểm tra lại.
)

pause

