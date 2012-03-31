import sbt._

name := "My Project"

version := "0.1"

organization := "MIT"

scalaVersion := "2.9.1"

scalacOptions ++= Seq(
	"-Xexperimental",
	"-unchecked",
	"-deprecation"
)

libraryDependencies += "org.scalatest" % "scalatest_2.9.1" % "1.7.1" % "test"