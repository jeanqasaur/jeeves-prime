import sbt._

class ScalaSMT(info: ProjectInfo) extends DefaultProject(info) {
	System.setProperty("smt.home", "C:\\Program Files (x86)\\Microsoft Research\\Z3-2.18\\bin\\z3")

	override def compileOptions =
		CompileOption("-Xexperimental") ::
			Unchecked ::
			Deprecation ::
			super.compileOptions.toList

	//Drivers for other supported databases : 

	val h2 = "com.h2database" %% "h2" % "1.2.127"
	val mysqlDriver = "mysql" %% "mysql-connector-java" % "5.1.10"
	val posgresDriver = "postgresql" %% "postgresql" % "8.4-701.jdbc4"
	val msSqlDriver = "net.sourceforge.jtds" %% "jtds" % "1.2.4"
	val derbyDriver = "org.apache.derby" %% "derby" % "10.7.1.1"
	val scalatest = "org.scalatest" %% "scalatest_2.9.0" % "1.7.1" % "test"
	super.libraryDependencies += "org.scalatest" %% "scalatest_2.9.0" % "1.7.1" % "test"
}
