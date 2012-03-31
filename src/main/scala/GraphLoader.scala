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
		scala.io.Source.fromFile("socialist.map").getLines.toList.map(lineToTuple)
	}
	def makeNetwork(basicGraph: List[(Int, Int)]): Map[Int, User] = {
		var network: Map[Int, User] = Map[Int, User]()
		for (x: (Int, Int) <- basicGraph) {
			if (!network.contains(x._1)) {
				network += x._1 -> User(Username(x._1.toString), Name(x._1.toString), Network("Common"))
			}
			if (!network.contains(x._2)) {
				network += x._2 -> User(Username(x._2.toString), Name(x._2.toString), Network("Common"))
			}
			network(x._1).addFriend(network(x._2))
			network(x._2).addFriend(network(x._1))
		}
		network
	}
}