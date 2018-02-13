#!/bin/bash

. ./gf.config

# Issue commands to gfsh to start locator and launch a server
echo "Starting locator and server..."
gfsh run --file=serverStart.gf

# Run the Loader client (LoadRegions) to load the data
export REPO=$COURSE_HOME/repository
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/data/spring-data-gemfire/1.7.2.RELEASE/spring-data-gemfire-1.7.2.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/antlr/antlr/2.7.7/antlr-2.7.7.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/slf4j/jcl-over-slf4j/1.7.12/jcl-over-slf4j-1.7.12.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/slf4j/slf4j-api/1.7.12/slf4j-api-1.7.12.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/slf4j/slf4j-log4j12/1.7.5/slf4j-log4j12-1.7.5.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/aspectj/aspectjweaver/1.8.5/aspectjweaver-1.8.5.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/data/spring-data-commons/1.11.2.RELEASE/spring-data-commons-1.11.2.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-core/4.1.8.RELEASE/spring-core-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-beans/4.1.8.RELEASE/spring-beans-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-tx/4.1.8.RELEASE/spring-tx-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-aop/4.1.8.RELEASE/spring-aop-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-context/4.1.8.RELEASE/spring-context-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-expression/4.1.8.RELEASE/spring-expression-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/com/gemstone/gemfire/gemfire/8.2.0/gemfire-8.2.0.jar
CLASSPATH=$CLASSPATH:"$REPO"/com/fasterxml/jackson/core/jackson-core/2.6.0/jackson-core-2.6.0.jar
CLASSPATH=$CLASSPATH:"$REPO"/aopalliance/aopalliance/1.0/aopalliance-1.0.jar
CLASSPATH=$CLASSPATH:"$REPO"/org/springframework/spring-context-support/4.1.8.RELEASE/spring-context-support-4.1.8.RELEASE.jar
CLASSPATH=$CLASSPATH:"$REPO"/log4j/log4j/1.2.17/log4j-1.2.17.jar
CLASSPATH=$CLASSPATH:"$REPO"/cglib/cglib/2.2.2/cglib-2.2.2.jar
CLASSPATH=$CLASSPATH:"$REPO"/asm/asm/3.3.1/asm-3.3.1.jar

export CLASSPATH

echo "Loading data..."
# Engages the basic profile, which won't use PDX serialization
java -Dspring.profiles.active=basic com.gopivotal.training.LoadRegions

# alternatively..
# mvn -f ../pom.xml compile exec:java -Dspring.profiles.active=basic -Dexec.mainClass="com.gopivotal.training.LoadRegions"
