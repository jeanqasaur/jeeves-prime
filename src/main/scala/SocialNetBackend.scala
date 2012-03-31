package cap.primes

import cap.jeeves.JeevesLib
import scala.collection.mutable.Map

class SocialNetBackend extends JeevesLib {
	class UnimplementedError extends Exception
	class NoSuchUserException extends Exception

	private var users: Map[String, User] = Map[String, User]()
	private var _unm: Map[Symbolic, Username] = Map[Symbolic, Username]()
	private var _users: List[User] = List[User]()

	def get(username: Symbolic): User = getUser(username)
	
	def getUser(username: Symbolic): User = {
		getUser(getUsername(username).username)
	}
	
	def get(username: String): User = getUser(username)
	
	def getUser(username: String): User = {
		users.getOrElse(username, Default.defaultFriend)
	}
	
	def getUsername(username: Symbolic): Username = {
		_unm.getOrElse(username, Username(""))
	}

	def searchByNetwork(network: String): List[User] = {
		_users.filter(_.getNetwork() == Network(network))
	}
	
	def addUser(user: User) = {
		users += user.username.username -> user
		_unm += user.getUsername -> user.username
		_users = user :: _users
	}
	
	def addLink(u1: String, u2: String) = {
		users(u1).addFriend(users(u2))
		users(u2).addFriend(users(u1))
	}
}

object SocialNetBackend extends SocialNetBackend()
