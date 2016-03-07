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

@if exist "%JAVA_HOME%\bin" goto sys_jre

:local_jre
@echo Errore: JAVA_HOME non settata o non trovata nella cartella [%JAVA_HOME%], utilizzero' java locale [%JAVA_EXEC%]
%JAVA_EXEC% -Xmx500M -Xms256M -Drootpath=D:/lotto_amico_20101218/ -cp "%c%" it.emacro.main.Main %DOWNLOAD_EXTRACTIONS_FILE% %DB_LOADER% %SYSTEM_LOOK_AND_FEEL%
goto :end_

:sys_jre
@echo Info: verra' utilizzata JAVA di sistema [%JAVA_HOME%]
java -Xmx500M -Xms256M -Drootpath=D:/lotto_amico_20101218/ -cp "%c%" it.emacro.main.Main %DOWNLOAD_EXTRACTIONS_FILE% %DB_LOADER% %SYSTEM_LOOK_AND_FEEL%


:end_
@echo on


