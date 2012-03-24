package cap.primes

import cap.jeeves.JeevesLib
import scala.collection.mutable.Map

class SocialNetBackend extends JeevesLib {
	class UnimplementedError extends Exception
	class NoSuchUserException extends Exception

	private val users: Map[String, User] = Map[String, User]()
	private val _users: List[User] = List[User]()

	def getUser(username: String): User = {
		_users.find((t: User) => t.getUsername() == username) match {
			case Some(user: User) => user
			case None => throw new NoSuchUserException
		}
	}

	def searchByNetwork(network: String): List[User] = {
		_users.filter(_.getNetwork() == Network(network))
	}
	
	def addLink(
}

object SocialNetBackend extends SocialNetBackend()
