package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._
import cap.scalasmt.Expr._
import cap.jeeves.JeevesTypes._
import scala.collection.mutable.ArrayBuffer

case class Name(val name: String) extends JeevesRecord
case class Network(val name: String) extends JeevesRecord
case class Username(val username: String) extends JeevesRecord

object Default {
	val defaultFriend: User = new User("", "")
	val defaultContext: SocialNetContext = new SocialNetContext(defaultFriend)
}

case class User(
	val username: Username, private val _name: Name) extends JeevesRecord {

	def this(username: String, name: String) = this(Username(username), Name(name))
	
	/* Variables */
	private val _friendL = mkLevel()
	private val _publicL = mkLevel()
	private var friends: List[Username] = List[Username]()
	private var groups: ArrayBuffer[List[Username]] = ArrayBuffer[List[Username]]()
	private var followers: List[Username] = List[Username]()
	private var posts: List[Update] = List[Update]()
	
	/* Levels */
	private def isSelf(CONTEXT: Sensitive): Formula =
		CONTEXT === username
	private def isFriend(CONTEXT: Sensitive): Formula =
		isFriends(CONTEXT)

	/* Policies */
	restrict(_publicL, (CONTEXT: Sensitive) => false)
	restrict(_friendL, (CONTEXT: Sensitive) => !(isFriend(CONTEXT) || isSelf(CONTEXT)))
	
	
	/* Getters */
	def getUsername(): Sensitive = mkSensitive(_publicL, username, username)
	def showUsername(ctxt: Sensitive): String =
		username.username

	def getName(): Sensitive = mkSensitive(_friendL, _name, Name("Anonymous"))
	def showName(ctxt: Sensitive): String =
		(concretize(ctxt, getName())).asInstanceOf[Name].name

	def getFriends(): List[Sensitive] = {
		friends.toList.map((friend: Username) => mkSensitive(_friendL, friend, Default.defaultFriend))
	}
	def showFriends(ctxt: Sensitive): List[Username] =
		getFriends().map((friend: Sensitive) => concretize(ctxt, friend).asInstanceOf[Username])
	
	def getFriendsBackend(): List[Username] = friends
	
	def isFriends(user: User): Boolean = isFriends(user.username)
	def isFriends(username: Username): Boolean = !(friends.find((t: Username) => t == username).isEmpty)
	
	def isFriends(username: Sensitive): Formula = friends.has(username)
	
	def post(msg: String) = {
		posts = Update(Message(msg), this, getRandomSubset()) :: posts
	}
	
	def getPost(index: Int): Update = posts(index)
	def getNumPosts(): Int = posts.length

	def formGroups() {
		val num_groups = friends.length / 20
		for(i <- 1 to num_groups) {
			groups += List[Username]()
		}
		for(i <- 0 to friends.length - 1) {
			groups((Math.random * num_groups).toInt) ::= friends(i)
		}
		for(i <- 1 to num_groups * 15) {
			post("Post num: " + i.toString)
		}
	}
	
	def getRandomSubset(): List[Username] = {
		(Math.random * 3).toInt match {
			case 0 => friends
			case 1 => groups((Math.random * groups.length).toInt)
			case 2 => null
		}
	}

	/* Mutators */
	def addFriend(friend: User) {
		friends ::= friend.username
		friend.followers ::= username
	}
}
