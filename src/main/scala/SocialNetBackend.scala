package cap.primes

import cap.jeeves.JeevesLib
import scala.collection.mutable.Map

class SocialNetBackend extends JeevesLib {
	class UnimplementedError extends Exception
	class NoSuchUserException extends Exception

	private var users: Map[String, User] = Map[String, User]()
	private var _users: List[User] = List[User]()

	def getUser(username: String): User = {
		_users.find((t: User) => t.getUsername() == username) match {
			case Some(user: User) => user
			case None => throw new NoSuchUserException
		}
	}

	def searchByNetwork(network: String): List[User] = {
		_users.filter(_.getNetwork() == Network(network))
	}
	
	def addUser(user: User) = {
		users(user.username.username) = user
		_users = user :: _users
	}
	
	def addLink(u1: String, u2: String) = {
		users(u1).addFriend(users(u2))
		users(u2).addFriend(users(u1))
	}
}

object SocialNetBackend extends SocialNetBackend()
