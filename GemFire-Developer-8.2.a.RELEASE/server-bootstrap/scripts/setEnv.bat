rem "JAVA_HOME must not have spaces in it"
set JAVA_HOME=C:\Java_64bit\jdk1.8.0
set COURSE_HOME=C:\GemFire-Developer-8.2.a.RELEASE
set LAB_HOME=%COURSE_HOME%\GemFire-Developer-8.2.a.RELEASE
set GEMFIRE=%COURSE_HOME%\Pivotal_GemFire_820_b17919_Windows
set PATH=%PATH%;%GEMFIRE%\bin;%JAVA_HOME%\bin
set GF_LIB=%GEMFIRE%\lib
set GF_SAMPLES=%GEMFIRE%\SampleCode

set CLASSPATH=.;../bin;./bin;./target/classes;../target/classes;..\..\target\classes;%GF_LIB%\gemfire.jar;%GF_LIB%\antlr.jar;%GF_LIB%\gfSecurityImpl.jar;%GF_SAMPLES%\helloworld\classes;%GF_SAMPLES%\quickstart\classes;%COURSE_HOME%\repository\com\gopivotal\training\server-bootstrap\1.0.0.CI-SNAPSHOT\server-bootstrap-1.0.0.CI-SNAPSHOT.jar




