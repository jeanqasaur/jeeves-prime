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

  def searchByNetwork(network: String): List[User] = {
    throw  new UnimplementedError
   // val it: Iterator[User] = users.iterator[]
   // val it: Iterator[User] = users.valuesIterator
  // val a: List[User]
  // a
   //    users.filter((name: String, user: User) => 1==1)
  }
}