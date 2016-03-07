@echo off

cd ..\WebContent\

:: set JAVA_HOME=../java
:: set Path=%Path%;%JAVA_HOME%\bin

set LIB_HOME=WEB-INF\lib
set JAVA_EXEC=..\java\jre6\bin\java

set JARS=%LIB_HOME%\h2.jar
set JARS=%JARS%;%LIB_HOME%\commons-collections-3.1.jar
set JARS=%JARS%;%LIB_HOME%\commons-dbcp-1.2.1.jar
set JARS=%JARS%;%LIB_HOME%\commons-pool-1.3.jar

set CLASSES=WEB-INF\classes
set c=.;%JARS%;%CLASSES%

set DOWNLOAD_EXTRACTIONS_FILE=true
set DB_LOADER=true
set SYSTEM_LOOK_AND_FEEL=false

%JAVA_EXEC% -Xmx500M -Xms256M -cp "%c%" it.emacro.main.Main %DOWNLOAD_EXTRACTIONS_FILE% %DB_LOADER% %SYSTEM_LOOK_AND_FEEL%
@echo on

