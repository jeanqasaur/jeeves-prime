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

	def this(username: String, name: String, network: String) = this(Username(username), Name(name), Network(network))
	
	/* Variables */
	private val _friendL = mkLevel()
	private val _networkL = mkLevel()
	private val _restrictedL = mkLevel()
	private val _privateL = mkLevel()
	private val _publicL = mkLevel()
	private val _testL = mkLevel()
	private var friends: List[Username] = List[Username]()
	private var followers: List[Username] = List[Username]()
	private var posts: List[Update] = List[Update]()
	private var testVar = Name("weird")

	/* Levels */
	private val isSelf: Formula =
		CONTEXT.viewer.username === username
	private val sameNetwork: Formula =
		CONTEXT.viewer._network === _network
	private def isFriend: Formula =
		isFriends(CONTEXT.viewer.username)
	private val friendOfFriend: Formula =
		assembleFriendFriendList().has(CONTEXT.viewer.username)
	//	!(friends.intersect(CONTEXT.viewer.followers).isEmpty)

	/* Policies */
	policy(_publicL, false, LOW) // O(1)
	policy(_networkL, !(sameNetwork || isFriend || isSelf), LOW) // B: O(1) W: O(n)
	policy(_friendL, !(isFriend || isSelf), LOW) // O(n)
	policy(_restrictedL, !(isFriend && sameNetwork || isSelf), LOW) // O(n)
	policy(_privateL, !isSelf, LOW) // O(1)
	policy(_testL, !(friendOfFriend || isSelf), LOW) // O(n^2)
	
	/* Getters */
	def getUsername(): Symbolic = mkSensitive(_publicL, username, username)
	def showUsername(ctxt: SocialNetContext): String =
		username.username

	def getName(): Symbolic = mkSensitive(_friendL, _name, Name("Anonymous"))
	def showName(ctxt: SocialNetContext): String =
		(concretize(ctxt, getName())).asInstanceOf[Name].name

	def getNetwork(): Symbolic = mkSensitive(_publicL, _network, _network)
	def showNetwork(ctxt: SocialNetContext): String =
		(concretize(ctxt, getNetwork())).asInstanceOf[Network].name

	def getTest(): Symbolic = mkSensitive(_testL, testVar, Name("Failed"))
	def showTest(ctxt: SocialNetContext): String =
		(concretize(ctxt, getTest())).asInstanceOf[Name].name

	def getFriends(): List[Symbolic] = {
		friends.toList.map((friend: Username) => mkSensitive(_publicL, friend, Default.defaultFriend))
	}
	def showFriends(ctxt: SocialNetContext): List[Username] =
		(getFriends()).map((friend: Symbolic) => concretize(ctxt, friend).asInstanceOf[Username])
	
	def getFriendsBackend(): List[Username] = friends
	
	def isFriends(user: User): Boolean = isFriends(user.username)
	def isFriends(username: Username): Boolean = !(friends.find((t: Username) => t == username).isEmpty)
	
	def isFriends(username: Symbolic): Formula = friends.has(username)
	
	def hasFriend(usernames: List[Username]): Boolean = !(friends.intersect(usernames).isEmpty)
	def assembleFriendFriendList() : List[Username] = {
		var extended: List[Username] = List[Username]()
		for (f: Username <- friends) {
			extended = SocialNetBackend.getUser(f.username).username :: extended
		}
		extended
	}
	
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
