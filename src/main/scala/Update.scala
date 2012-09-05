package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._
import cap.scalasmt.Expr._
import cap.jeeves.JeevesTypes._

case class Message(val message: String) extends JeevesRecord

case class Update(private val message: Message, private val author: User, private val authorized: List[Username]) extends JeevesRecord {	
	private val visible = mkLevel()
	restrict(visible, (CONTEXT: Sensitive) => !(authorized == null || authorized.has(CONTEXT) || (CONTEXT === author.username)) )

	def canSee(uname: Username): Boolean = authorized == null || authorized.contains(uname) || uname == author.username
	
	def getMessage(): Sensitive = mkSensitive(visible, message, Message("Unauthorized"))
	def showMessage(ctxt: Sensitive): String = concretize(ctxt, getMessage()).asInstanceOf[Message].message
}
