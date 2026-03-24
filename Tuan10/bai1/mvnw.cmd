@REM ----------------------------------------------------------------------------
@REM Maven Wrapper startup batch script
@REM ----------------------------------------------------------------------------
@IF "%__MVNW_ARG0__%"=="" SET __MVNW_ARG0__=%~dpnx0
@SET __MVNW_CMD__=
@SET __MVNW_ERROR__=
@SET __MVNW_PSMODULEP_SAVE=%PSModulePath%
@SET PSModulePath=
@FOR /F "usebackq tokens=1* delims==" %%A IN (`powershell -noprofile "& {$scriptDir='%~dp0telerik'; $env:__MVNW_ARG0__='%__MVNW_ARG0__%'; cmd /c}`) DO @(
    IF "%%A"=="MVN_CMD" SET __MVNW_CMD__=%%B
    IF "%%A"=="MVN_ERROR" SET __MVNW_ERROR__=%%B
)
@SET PSModulePath=%__MVNW_PSMODULEP_SAVE%
@SET __MVNW_PSMODULEP_SAVE=
@SET __MVNW_ARG0__=
@IF NOT "%__MVNW_ERROR__%"=="" GOTO :error

@REM Download maven-wrapper.jar if needed
@SET WRAPPER_JAR="%~dp0.mvn\wrapper\maven-wrapper.jar"
@IF EXIST %WRAPPER_JAR% GOTO :exec

@SET WRAPPER_URL="https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar"
@powershell -Command "&{"^
    "$webclient = new-object System.Net.WebClient;"^
    "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12;"^
    "$webclient.DownloadFile('%WRAPPER_URL:"=%', '%WRAPPER_JAR:"=%')"^
    "}"
@IF "%ERRORLEVEL%" NEQ "0" (
    echo Failed to download maven-wrapper.jar
    echo Please install Maven and run: mvn wrapper:wrapper
    GOTO :error
)

:exec
@SET MVNW_JAVA_EXE="java"
@IF NOT "%JAVA_HOME%"=="" SET MVNW_JAVA_EXE="%JAVA_HOME%\bin\java"
@%MVNW_JAVA_EXE% %MAVEN_OPTS% -jar %WRAPPER_JAR% %*
@IF "%ERRORLEVEL%" NEQ "0" GOTO :error
@GOTO :exit

:error
@SET ERROR_CODE=1

:exit
@EXIT /B %ERROR_CODE%
