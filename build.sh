#!/bin/sh

run_javacc() {
  JAR="javacc-6.0/bin/lib/javacc.jar"
  case "$(uname)" in
       CYGWIN*) JAR="$(cygpath --windows -- "$JAR")" ;;
  esac
  java -classpath "$JAR" build.sh -OUTPUT_DIRECTORY=src/parser "$@"
}

run_jasmin() {
  rm ./classes/* && 
  java -jar jasmin.jar ./"$1"/*.j -d ./classes &&
  cd ./classes &&
  java Main
}

case "$1" in
"parser")
  shift
  run_javacc "$@"
  ;;
"jasmin")
  shift
  run_jasmin "$@"
  ;;
esac
