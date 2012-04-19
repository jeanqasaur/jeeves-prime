#! /bin/sh
../jdk1.7.0_02/bin/java -Xmx712M -Xss2M -XX:+CMSClassUnloadingEnabled -jar `dirname $0`/sbt-launcher.jar "$@"
