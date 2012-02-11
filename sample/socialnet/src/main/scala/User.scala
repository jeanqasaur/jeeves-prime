package cap.jeeves.pat

/*
 * User records for jconf case study.
 */

import cap.scalasmt._
import SocialNetBackend._

case class Name (name: String) extends JeevesRecord
case class Password (val pwd: String) extends JeevesRecord
case class Email (val email: String) extends JeevesRecord
/* Optional: Use traits and pattern matching to represent the month. */
/*
sealed trait Month extends JeevesRecord
object January extends UserStatus
object February extends UserStatus
// More months here...
*/
case class Birthday (val month: Int, val day: Int, val year: Int)
  extends JeevesRecord
case class Network (val name: String) extends JeevesRecord
case class Username (val username: String) extends JeevesRecord

object Random {
  val defaultFriend: User = new User(Username(""), Name(""), Password(""), Email(""),
                                    Birthday(0, 0, 0), Network(""))
}

case class User (
    val username: Username
  , private val _name: Name
  , private val _pwd: Password
  , private val _email: Email
  , private val _birthday: Birthday
  , private val _network: Network
  ) extends JeevesRecord {
  
  def init() {}

  /* Variables */
  private val _friendL = mkLevel()
  private val _networkL = mkLevel()
  private val _privateL = mkLevel()
  private val _publicL = mkLevel()
  private var friends: List[User] = List[User]()

  /* Levels */
  private val isSelf: Formula =
    CONTEXT.viewer.username === this.username
  private val sameNetwork: Formula =
    CONTEXT.viewer.getNetwork() === getNetwork()
  private val isFriend: Formula =
    friends contains CONTEXT.viewer

  /* Policies */
  policy(_networkL, !(sameNetwork || isFriend || isSelf), LOW)
  policy(_friendL, !(isFriend || isSelf), LOW)
  policy(_privateL, !isSelf, LOW)
  policy(_publicL, false, LOW)

  /* Getters */
  def getUsername(): Symbolic = mkSensitive(_publicL, username, username)
  def showUsername(ctxt: SocialNetContext): String =
    (concretize(ctxt, getUsername())).asInstanceOf[Username].username

  def getName(): Symbolic = mkSensitive(_networkL, _name, Name("Anonymous"))
  def showName(ctxt: SocialNetContext): String =
    (concretize(ctxt, getName())).asInstanceOf[Name].name
  
  def getPwd(): Symbolic = mkSensitive(_privateL, _pwd, Password("****"))
  def showPwd(ctxt: SocialNetContext): String =
    (concretize(ctxt, getPwd())).asInstanceOf[Password].pwd
  
  def getEmail(): Symbolic = mkSensitive(_networkL, _email, Email("...."))
  def showEmail(ctxt: SocialNetContext): String =
    (concretize(ctxt, getEmail())).asInstanceOf[Email].email
  
  def getBirthday(): Symbolic = mkSensitive(_networkL, _birthday, Birthday(0, 0, 0))
  def showBirthday(ctxt: SocialNetContext): String = {
    val day = (concretize(ctxt, getBirthday())).asInstanceOf[Birthday]
    day.month + "/" + day.day + "/" + day.year
  }
  
  def getNetwork(): Symbolic = mkSensitive(_networkL, _network, Network("No permission"))
  def showNetwork(ctxt: SocialNetContext): String =
    (concretize(ctxt, getNetwork())).asInstanceOf[Network].name
  
  def getFriends(): List[Symbolic] = {
    friends.map((friend: User) => mkSensitive(_friendL, friend, Random.defaultFriend))
  }
  def showFriends(ctxt: SocialNetContext): List[User] =
    (getFriends()).map((friend: Symbolic) => concretize(ctxt, friend).asInstanceOf[User])

  /* Mutators */
  def addFriend(friend: User) {
    if(!(friends contains friend)) friends ::= friend
  }

}
