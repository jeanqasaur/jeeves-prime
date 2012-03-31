package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._
import cap.scalasmt.Expr._

case class Name(val name: String) extends JeevesRecord
case class Network(val name: String) extends JeevesRecord
case class Username(val username: String) extends JeevesRecord

object Default {
	val defaultFriend: User = new User(Username(""), Name(""), Network(""))
	val defaultContext: SocialNetContext = new SocialNetContext(defaultFriend)
}

case class User(
	val username: Username, private val _name: Name, val _network: Network) extends JeevesRecord {
	/* Variables */
	private val _friendL = mkLevel()
	private val _networkL = mkLevel()
	private val _restrictedL = mkLevel()
	private val _privateL = mkLevel()
	private val _publicL = mkLevel()
	private var friends: List[Username] = List[Username]()
	private var followers: List[Username] = List[Username]()
	private var posts: List[Update] = List[Update]()

	/* Levels */
	lazy private val isSelf: Formula =
		CONTEXT.viewer.username === username
	lazy private val sameNetwork: Formula =
		CONTEXT.viewer._network === _network
	lazy private val isFriend: Formula =
		isFriends(CONTEXT.viewer.username)
	lazy private val friendOfFriend: Formula = false
	//	!(friends.intersect(CONTEXT.viewer.followers).isEmpty)

	/* Policies */
	policy(_publicL, false, LOW)
	policy(_networkL, !(sameNetwork || isFriend || isSelf), LOW)
	policy(_friendL, !(isFriend || isSelf), LOW)
	policy(_restrictedL, !(isFriend && sameNetwork || isSelf), LOW)
	policy(_privateL, !isSelf, LOW)
	
	/* Getters */
	def getUsername(): Symbolic = mkSensitive(_publicL, username, username)
	def showUsername(ctxt: SocialNetContext): String =
		username.username

	def getName(): Symbolic = mkSensitive(_networkL, _name, Name("Anonymous"))
	def showName(ctxt: SocialNetContext): String =
		(concretize(ctxt, getName())).asInstanceOf[Name].name

	def getNetwork(): Symbolic = mkSensitive(_publicL, _network, _network)
	def showNetwork(ctxt: SocialNetContext): String =
		(concretize(ctxt, getNetwork())).asInstanceOf[Network].name

	def getFriends(): List[Symbolic] = {
		friends.toList.map((friend: Username) => mkSensitive(_publicL, friend, Default.defaultFriend))
	}
	def showFriends(ctxt: SocialNetContext): List[Username] =
		(getFriends()).map((friend: Symbolic) => concretize(ctxt, friend).asInstanceOf[Username])
	
	def isFriends(user: User): Boolean = isFriends(user.username)
	def isFriends(username: Username): Boolean = !(friends.find((t: Username) => t == username).isEmpty)
	def isFriends(username: Symbolic): Formula = isFriends(SocialNetBackend.getUsername(username))
	def hasFriend(usernames: List[Username]): Boolean = !(friends.intersect(usernames).isEmpty)
	
	def post(msg: String) = {
		posts = Update(Message(msg), this) :: posts
	}
	
	def tagPost(index: Int, user: Username) = {
		posts(index).tag(user)
	}
	
	def getPost(index: Int): Update = posts(index)

	/* Mutators */
	def addFriend(friend: User) {
		friends ::= friend.username
		friend.followers ::= username
	}
}