package cap.jeeves.pat

import cap.jeeves._
import scala.collection.mutable.Map

object SocialNetBackend extends JeevesLib {
  class UnimplementedError extends Exception
  class NoSuchUserException extends Exception

  private val users: Map[String, User] = Map[String, User]()
  private val _users: List[User] = List[User]()

  def getUser(username: String): User = {
    //if(!_users.contains(username)) throw new NoSuchUserException
    //_users(username)
    _users.find((t: User) => t.getUsername() == username) match {
      case Some(user) => user
      case None => throw new NoSuchUserException
    }
  }

  def searchByNetwork(network: String): List[User] = {
    _users.filter(_.getNetwork() == Network(network))
  }
}
