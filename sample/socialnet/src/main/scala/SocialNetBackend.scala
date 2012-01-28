package cap.jeeves.socialnet

import cap.jeeves._
import scala.collection.mutable.Map

class NoSuchUserException extends Exception

object SocialNetBackend extends JeevesLib {
  class UnimplementedError extends Exception

  private val users: Map[String, User] = Map[String, User]()

  def getUser(username: String): User = {
    if(!users.containsKey(username)) throw new NoSuchUserException
    users.get(username)
  }

  def searchByNetwork(network: String): List[User] = {
    users.filter((name: String, user: User) => user.getNetwork().name === network)
  }
}
