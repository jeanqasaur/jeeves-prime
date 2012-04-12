package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._

object GraphLoader {
	def lineToTuple(line: String): (Int, Int) = {
		var intermediary: Array[String] = line.split(":")
		(intermediary(0).toInt, intermediary(1).toInt)
	}
	def loadGraph(path: String): List[(Int, Int)] = {
		scala.io.Source.fromFile(path).getLines.toList.map(lineToTuple)
	}
	def makeNetwork(basicGraph: List[(Int, Int)], backend: SocialNetBackend): Unit = {
		var network: Map[Int, User] = Map[Int, User]()
		for (x: (Int, Int) <- basicGraph) {
			if (!backend.hasUser(x._1.toString)) {
				backend += User(Username(x._1.toString), Name(x._1.toString), Network("Common"))
			}
			if (!backend.hasUser(x._2.toString)) {
				backend += User(Username(x._2.toString), Name(x._2.toString), Network("Common"))
			}
			backend.addLink(x._1.toString, x._2.toString)
		}
	}
	
	def createBackend(path: String): SocialNetBackend = {
		var backend: SocialNetBackend = new SocialNetBackend()
		var graph = loadGraph(path)
		makeNetwork(graph, backend)
		backend
	}
}
