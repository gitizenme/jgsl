#!/bin/sh

JGSL_LIB=.

CLASSPATH=${JGSL_LIB}/jgsl.jar:${JGSL_LIB}/javassist.jar:${JGSL_LIB}/log4j-1.2.9.jar:${JGSL_LIB}/idea_forms_rt.jar:${JGSL_LIB}/commons-cli-1.0.jar

echo CLASSPATH=${CLASSPATH}

${JAVA_HOME}/bin/java -classpath ${CLASSPATH} jgsl.JGSL $*
