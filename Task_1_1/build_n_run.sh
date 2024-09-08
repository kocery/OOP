#!/bin/bash

SRC_DIR="src/main/java"
TEST_SRC_DIR="src/test/java"
OUT_DIR="out/production/classes"
TEST_OUT_DIR="out/test/classes"
DOCS_DIR="docs"

JUNIT_API_JAR="libs/junit-jupiter-api-5.9.3.jar"
JUNIT_ENGINE_JAR="libs/junit-jupiter-engine-5.9.3.jar"
JUNIT_PLATFORM_CONSOLE_JAR="libs/junit-platform-console-standalone-1.9.3.jar"

if [ ! -f "$JUNIT_API_JAR" ] || [ ! -f "$JUNIT_ENGINE_JAR" ] || [ ! -f "$JUNIT_PLATFORM_CONSOLE_JAR" ]; then
  echo "Some of JAR's not found!"
  exit 1
fi

mkdir -p $OUT_DIR $TEST_OUT_DIR $DOCS_DIR

echo "Compiling source files..."
javac -d $OUT_DIR $(find $SRC_DIR -name "*.java")
if [ $? -ne 0 ]; then
  echo "Compilation of source files failed!"
  exit 1
fi

echo "Compiling test files..."
javac -cp .:$OUT_DIR:$JUNIT_API_JAR:$JUNIT_ENGINE_JAR -d $TEST_OUT_DIR $(find $TEST_SRC_DIR -name "*.java")
if [ $? -ne 0 ]; then
  echo "Compilation of test files failed!"
  exit 1
fi

echo "Generating Javadoc..."
javadoc -d $DOCS_DIR -sourcepath $SRC_DIR $(find $SRC_DIR -name "*.java")
if [ $? -ne 0 ]; then
  echo "Javadoc generation failed!"
  exit 1
fi

echo "Running tests..."
java -cp .:$TEST_OUT_DIR:$OUT_DIR:$JUNIT_PLATFORM_CONSOLE_JAR org.junit.platform.console.ConsoleLauncher --scan-classpath --class-path $TEST_OUT_DIR
if [ $? -ne 0 ]; then
  echo "Tests failed!"
  exit 1
fi

echo "Build, documentation generation, and test run successful! YEEEy!!"
