@echo off
echo ========================================
echo   BIEN DICH VA CHAY TAT CA TEST
echo ========================================
echo.

cd /d "%~dp0"

:: Kiểm tra JUnit JAR
if not exist "lib" mkdir lib

if not exist "lib\junit-4.13.2.jar" (
    echo Dang tai JUnit...
    echo Vui long tai file junit-4.13.2.jar va hamcrest-core-1.3.jar vao thu muc lib\
    echo.
    echo Download tu:
    echo https://repo1.maven.org/maven2/junit/junit/4.13.2/junit-4.13.2.jar
    echo https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar
    echo.
    pause
    exit /b 1
)

:: Tạo thư mục build
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes
if not exist "build\test-classes" mkdir build\test-classes

echo Bien dich source code...
javac -encoding UTF-8 -d build\classes src\main\java\com\testing\*.java

echo Bien dich test code...
javac -encoding UTF-8 -cp "build\classes;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" -d build\test-classes src\test\java\com\testing\*.java

echo.
echo Chay test cases...
echo.
java -cp "build\classes;build\test-classes;lib\junit-4.13.2.jar;lib\hamcrest-core-1.3.jar" org.junit.runner.JUnitCore com.testing.PaymentCalculatorTest

echo.
pause
