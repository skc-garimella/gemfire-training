@echo off

call setEnv.bat

echo Starting Locator and Server...
call gfsh run --file=serverStart.gf

REM Run the Loader client (LoadRegions) to load the data


set REPO=%COURSE_HOME%\repository

set CLASSPATH=target\classes;%REPO%\org\springframework\data\spring-data-gemfire\1.7.2.RELEASE\spring-data-gemfire-1.7.2.RELEASE.jar;%REPO%\antlr\antlr\2.7.7\antlr-2.7.7.jar;%REPO%\org\slf4j\jcl-over-slf4j\1.7.12\jcl-over-slf4j-1.7.12.jar;%REPO%\org\aspectj\aspectjweaver\1.8.5\aspectjweaver-1.8.5.jar;%REPO%\org\slf4j\slf4j-api\1.7.12\slf4j-api-1.7.12.jar;%REPO%\org\springframework\data\spring-data-commons\1.11.2.RELEASE\spring-data-commons-1.11.2.RELEASE.jar;%REPO%\com\gemstone\gemfire\gemfire\8.2.0\gemfire-8.2.0.jar;%REPO%\org\springframework\spring-aop\4.1.8.RELEASE\spring-aop-4.1.8.RELEASE.jar;%REPO%\aopalliance\aopalliance\1.0\aopalliance-1.0.jar;%REPO%\org\springframework\spring-context-support\4.1.8.RELEASE\spring-context-support-4.1.8.RELEASE.jar;%REPO%\org\springframework\spring-core\4.1.8.RELEASE\spring-core-4.1.8.RELEASE.jar;%REPO%\com\fasterxml\jackson\core\jackson-core\2.6.0\jackson-core-2.6.0.jar;%REPO%\junit\junit\4.10\junit-4.10.jar;%REPO%\org\hamcrest\hamcrest-core\1.1\hamcrest-core-1.1.jar;%REPO%\org\springframework\spring-test\4.1.8.RELEASE\spring-test-4.1.8.RELEASE.jar;%REPO%\org\springframework\spring-context\4.1.8.RELEASE\spring-context-4.1.8.RELEASE.jar;%REPO%\org\springframework\spring-beans\4.1.8.RELEASE\spring-beans-4.1.8.RELEASE.jar;%REPO%\org\springframework\spring-expression\4.1.8.RELEASE\spring-expression-4.1.8.RELEASE.jar;%REPO%\org\springframework\spring-tx\4.1.8.RELEASE\spring-tx-4.1.8.RELEASE.jar;%REPO%\log4j\log4j\1.2.17\log4j-1.2.17.jar;%REPO%\cglib\cglib\2.2.2\cglib-2.2.2.jar;%REPO%\asm\asm\3.3.1\asm-3.3.1.jar;%REPO%\org\slf4j\slf4j-log4j12\1.7.5\slf4j-log4j12-1.7.5.jar;%CLASSPATH%


echo Running loader...
rem Engages the basic profile, which won't use PDX serialization
java -Dspring.profiles.active=basic com.gopivotal.training.LoadRegions


