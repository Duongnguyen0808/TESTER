@echo off
echo ========================================
echo   KHOI DONG GIAO DIEN PAYMENT CALCULATOR
echo ========================================
echo.

cd /d "%~dp0"

:: Tạo thư mục build nếu chưa có
if not exist "build" mkdir build

echo Dang bien dich chuong trinh...
echo.

:: Compile các file Java
javac -encoding UTF-8 -d build src\main\java\com\testing\*.java

if errorlevel 1 (
    echo.
    echo [LOI] Bien dich that bai! Vui long kiem tra Java da duoc cai dat.
    echo.
    echo Cach kiem tra: Chay lenh "java -version" trong terminal
    echo.
    pause
    exit /b 1
)

echo Bien dich thanh cong!
echo.
echo Dang khoi dong giao dien...
echo.

:: Chạy ứng dụng
java -cp build com.testing.PaymentCalculatorGUI

pause
