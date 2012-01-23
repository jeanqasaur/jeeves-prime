package cap.jeeves.socialnet

/*
 * User records for jconf case study.
 */

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
  /* Define getters and setters here. */
}
