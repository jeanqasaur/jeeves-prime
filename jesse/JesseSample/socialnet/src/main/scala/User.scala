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


case class User (
    val username: Username = Username("")
  , private val _name: Name
  , private val _pwd: Password
  , private val _email: Email
  , private val _birthday: Birthday
  , private val _network: Network
  ) extends JeevesRecord {
  
  private val self: Formula = (CONTEXT.viewer.username === this.username);
  private val network: Formula = (CONTEXT.viewer._network === this._network);
    
    
    private val selfL = mkLevel ();
    policy (selfL, !self, LOW);
    
    private val publicL = mkLevel ();
    policy (publicL, false, LOW);
      
    private val sameNetworkL = mkLevel ();
    policy (sameNetworkL, !network, LOW);
    
   /* Setters */
    def setUsername (p: String) = username
    def setName (p: String) = _name
    def setPassword (p: String) = _pwd
    def setEmail (p: String) = _email
    def setBirthday (p: Int, q: Int, r: Int) = _birthday
    def setNetwork (p: String) = _network
    
   /* Getters */
    def getUsername (): Symbolic = mkSensitive(publicL, username, Username("--"))
    def getName (): Symbolic = mkSensitive(publicL, _name, Name("--"))
    def getPassword (): Symbolic = mkSensitive(selfL, _pwd, Password("--"))
    def getEmail (): Symbolic = mkSensitive(sameNetworkL, _email, Email("--"))
    def getBirthday (): Symbolic = mkSensitive(publicL, _birthday, Birthday(0,0,0))
    def getNetwork (): Symbolic = mkSensitive(publicL, _network, Network("--"))
   
    /* Concretize */
      def showUsername(ctxt: SocialNetContext): String =
    (concretize(ctxt, getUsername())).asInstanceOf[Username].username
      def showName(ctxt: SocialNetContext): String =
    (concretize(ctxt, getName())).asInstanceOf[Name].name
      def showPassword(ctxt: SocialNetContext): String =
    (concretize(ctxt, getPassword())).asInstanceOf[Password].pwd
      def showEmail(ctxt: SocialNetContext): String =
    (concretize(ctxt, getEmail())).asInstanceOf[Email].email
   //   def showBirthday(ctxt: SocialNetContext): String = 
  //  (concretize(ctxt, getBirthday())).asInstanceOf[Birthday]      
      def showNetwork(ctxt: SocialNetContext): String =
    (concretize(ctxt, getNetwork())).asInstanceOf[Network].network

}
