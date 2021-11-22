@echo off
REM startup file for tagprocessing

echo =====================================================
echo Preparing to initialize tagprocessing
echo =====================================================
if "%JAVA_HOME%" == "" goto noJRE
:noJRE
set JAVA_HOME=C:\PROGRA~1\Java\jdk1.8.0_241

echo Using JAVA_HOME:       "%JAVA_HOME%"

%JAVA_HOME%\bin\java -Xms2G -Xmx2G -XX:MaxMetaspaceSize=512M -XX:+UseConcMarkSweepGC -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+PrintGCCause -Xloggc:logs/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=logs/ -jar tag-processing*.jar
