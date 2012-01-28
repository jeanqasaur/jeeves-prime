package cap.jeeves.socialnet

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

case class User (
    val username: String
  , private val _name: Name
  , private val _pwd: Password
  , private val _email: Email
  , private val _birthday: Birthday
  , private val _network: Network
  ) extends JeevesRecord {
  /* Variables */
  private var _friendL = mkLevel()
  private var _privateL = mkLevel()
  
  /* Policies */
  private val isSelf: Formula =
    CONTEXT.viewer === this
  private val sameNetwork: Formula =
    CONTEXT.viewer.getNetwork() === getNetwork()
  policy(_privateL, isSelf, LOW)
  policy(_friendL, sameNetwork, LOW)

  /* Getters */
  def getName(): Symbolic = mkSensitive(_friendL, _name, Name("Anonymous"))
  def showName(ctxt: SocialNetContext): String =
    (concretize(ctxt, getName())).asInstanceOf[Name].name
  
  def getPwd(): Symbolic = mkSensitive(_privateL, _pwd, Password("****"))
  def showPwd(ctxt: SocialNetContext): String =
    (concretize(ctxt, getPwd())).asInstanceOf[Password].pwd
  
  def getEmail(): Symbolic = mkSensitive(_friendL, _email, Name("...."))
  def showEmail(ctxt: SocialNetContext): String =
    (concretize(ctxt, getEmail())).asInstanceOf[Email].email
  
  def getBirthday(): Symbolic = mkSensitive(_friendL, _birthday, Name("--/--/----"))
  def showBirthday(ctxt: SocialNetContext): String = {
    val day = (concretize(ctxt, getBirthday())).asInstanceOf[Birthday]
    day.month + "/" + day.day + "/" + day.year
  }
  
  def getNetwork(): Symbolic = mkSensitive(_friendL, _network, Network("No permission"))
  def showNetwork(ctxt: SocialNetContext): String =
    (concretize(ctxt, getNetwork())).asInstanceOf[Network].name
}
