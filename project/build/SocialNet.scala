import sbt._

class JConf(info: ProjectInfo) extends DefaultWebProject(info) {
  System.setProperty("smt.home", "C:\\Program Files (x86)\\Microsoft Research\\Z3-2.18\\bin\\z3")

  val scalatest = "org.scalatest" % "scalatest_2.9.0" % "1.6.1.RC1" % "test"

  override def compileOptions =
    CompileOption("-Xexperimental") ::
    Unchecked ::
    Deprecation ::
    super.compileOptions.toList

  override def libraryDependencies = Set(
	  "org.scalatest" % "scalatest_2.9.1" % "1.7.1" % "test",
	  "org.scalatra" %% "scalatra" % "2.0.0.RC1",
	  "org.scalatra" %% "scalatra-scalate" % "2.0.0.RC1",
	  "org.eclipse.jetty" % "jetty-webapp" % "7.4.5.v20110725" % "test",
	  "javax.servlet" % "servlet-api" % "2.5" % "provided",
	  "org.scalatra" %% "scalatra-auth" % "2.0.0.RC1",
	  "org.clapper" %% "classutil" % "0.4.5"
  ) ++ super.libraryDependencies
}
