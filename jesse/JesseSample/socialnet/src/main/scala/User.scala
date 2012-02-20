package cap.jeeves.socialnet

/*
 * User records for jconf case study.
 */

import SocialNetBackend._
import cap.scalasmt._

case class Username (username: String) extends JeevesRecord
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
case class Network (val network: String) extends JeevesRecord

//case class Friends (val friendslist: List[String])

case class User (
    var username: Username
  , private var _name: Name
  , private var _pwd: Password
  , private var _email: Email
  , private var _birthday: Birthday
  , private var _network: Network
  , private var _friends: List[String]
  ) extends JeevesRecord {
  
  private val self: Formula = (CONTEXT.viewer.username === this.username);
  private val nw: Formula = (CONTEXT.viewer._network === this._network);
    
    
    private val selfL = mkLevel ();
    policy (selfL, !self, LOW);
    
    private val publicL = mkLevel ();
    policy (publicL, false, LOW);
      
    private val sameNetworkL = mkLevel ();
    policy (sameNetworkL, !nw, LOW);
    
   /* Setters */
    def setUsername (p: Username) = username = p
    def setName (p: Name) = _name = p
    def setPassword (p: Password) = _pwd = p
    def setEmail (p: Email) = _email = p
    def setBirthday (p: Birthday) = _birthday = p
    def setNetwork (p: Network) = _network = p
        
    def setFriends (p: List[String]) = _friends = p
    
   /* Getters */
    def getUsername (): Symbolic = mkSensitive(publicL, username, Username("--"))
    def getName (): Symbolic = mkSensitive(publicL, _name, Name("--"))
    def getPassword (): Symbolic = mkSensitive(selfL, _pwd, Password("--"))
    def getEmail (): Symbolic = mkSensitive(sameNetworkL, _email, Email("--"))
    def getBirthday (): Symbolic = mkSensitive(publicL, _birthday, Birthday(0,0,0))
    def getNetwork (): Symbolic = mkSensitive(sameNetworkL, _network, Network("--"))
    
    def getFriends (): List[String] = _friends
       
    /* Concretize */
      def showUsername(ctxt: SocialNetContext): String =
    (concretize(ctxt, getUsername())).asInstanceOf[Username].username
      def showName(ctxt: SocialNetContext): String =
    (concretize(ctxt, getName())).asInstanceOf[Name].name
      def showPassword(ctxt: SocialNetContext): String =
    (concretize(ctxt, getPassword())).asInstanceOf[Password].pwd
      def showEmail(ctxt: SocialNetContext): String =
    (concretize(ctxt, getEmail())).asInstanceOf[Email].email
    
      def showBirthday(ctxt: SocialNetContext): String = {
       val b = (concretize(ctxt, getBirthday())).asInstanceOf[Birthday]
       b.month + b.day + b.year + ""
      }
    
      def showNetwork(ctxt: SocialNetContext): String =
    (concretize(ctxt, getNetwork())).asInstanceOf[Network].network

}
