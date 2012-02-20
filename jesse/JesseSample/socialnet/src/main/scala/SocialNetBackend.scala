package cap.jeeves.socialnet

import cap.jeeves._
import cap.scalasmt._
import scala.collection.mutable.Map

object SocialNetBackend extends JeevesLib {
  class UnimplementedError extends Exception

  private val users: Map[String, User] = Map[String, User]()

  def getUser(username: String): User = {
    users.getOrElse(username, null)
  }
  
  def addUser(un: String, uo: User) = {
    users += un -> uo
  }
 
  def searchByNetwork(network: String, uo: User): List[User] = {
    val ResultMap = users.filter(p => p._2.showNetwork(SocialNetContext(uo)) == network)
    var list = List[User]()
	ResultMap foreach {case(key,value) => list = value :: list}
    //println(list)
    list
  }
}