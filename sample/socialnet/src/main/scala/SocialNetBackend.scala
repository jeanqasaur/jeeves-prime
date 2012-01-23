package cap.jeeves.socialnet

import cap.scalasmt._
import cap.jeeves._

import scala.collection.mutable.Map

object SocialNetBackend extends JeevesLib {
  class UnimplementedError extends Exception

  private val users: Map[String, User] = Map[String, User]()

  def getUser(username: String): User = {
    /* Replace with implementation. */
    throw new UnimplementedError
  }

  def searchByNetwork(network: String): List[User] = {
    throw  new UnimplementedError
  }
}
