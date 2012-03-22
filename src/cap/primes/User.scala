package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._

case class Name(val name: String) extends JeevesRecord
case class Network(val name: String) extends JeevesRecord
case class Username(val username: String) extends JeevesRecord

object Default {
	val defaultFriend: User = new User(Username(""), Name(""), Network(""))
}

case class User(
	val username: Username, private val _name: Name, private val _network: Network) extends JeevesRecord {
	/* Variables */
	private val _friendL = mkLevel()
	private val _networkL = mkLevel()
	private val _restrictedL = mkLevel()
	private val _privateL = mkLevel()
	private val _publicL = mkLevel()
	private var friends: Set[Username] = Set[Username]()
	private var followers: Set[Username] = Set[Username]()

	/* Levels */
	private val isSelf: Formula =
		CONTEXT.asInstanceOf[SocialNetContext].viewer.getUsername === getUsername
	private val sameNetwork: Formula =
		CONTEXT.asInstanceOf[SocialNetContext].viewer.getNetwork == getNetwork
	private val isFriend: Formula =
		friends contains CONTEXT.asInstanceOf[SocialNetContext].viewer.username
	private val friendOfFriend: Formula =
		!friends.intersect(CONTEXT.asInstanceOf[SocialNetContext].viewer.followers).isEmpty

	/* Policies */
	policy(_networkL, !(sameNetwork || isFriend || isSelf), LOW)
	policy(_friendL, !(isFriend || isSelf), LOW)
	policy(_restrictedL, !(isFriend && sameNetwork || isSelf), LOW)
	policy(_privateL, !isSelf, LOW)
	policy(_publicL, false, LOW)

	def showSelf(ctxt: SocialNetContext): Boolean =
		(concretize(ctxt, isSelf)).asInstanceOf[Boolean]

	/* Getters */
	def getUsername(): Symbolic = mkSensitive(_publicL, username, username)
	def showUsername(ctxt: SocialNetContext): String =
		username.username

	def getName(): Symbolic = mkSensitive(_restrictedL, _name, Name("Anonymous"))
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
	def isFriends(username: Username): Boolean = friends contains username

	/* Mutators */
	def addFriend(friend: User) {
		friends += friend.username
		friend.followers += username
	}
}