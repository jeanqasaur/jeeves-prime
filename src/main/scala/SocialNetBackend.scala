package cap.primes

import cap.jeeves.JeevesLib
import scala.collection.mutable.Map

class SocialNetBackend extends JeevesLib {
	class UnimplementedError extends Exception
	class NoSuchUserException extends Exception

	private var users: Map[String, User] = Map[String, User]()
	private var _users: List[User] = List[User]()
	
	def hasUser(username: String): Boolean = {
		users.contains(username)
	}
	
	def get(username: String): User = getUser(username)
	
	def getUser(username: String): User = {
		users.getOrElse(username, Default.defaultFriend)
	}
	
	def addUser(user: User) = {
		users += user.username.username -> user
		_users = user :: _users
	}
	
	def addLink(u1: String, u2: String) = {
		users(u1).addFriend(users(u2))
		users(u2).addFriend(users(u1))
	}
	
	def +=(user: User) = addUser(user)

	def update(_users: List[User], users: Map[String, User]): Unit = {
		this.users = users
		this._users = _users
	}
	def update(backend: SocialNetBackend): Unit = update(backend._users, backend.users)

	def apply(username: String): User = getUser(username)
	def apply(username: Username): User = getUser(username.username)
	def apply(userID: Int): User = _users(userID)
	
	def getNumUsers: Int = _users.length
}

object SocialNetBackend extends SocialNetBackend()
