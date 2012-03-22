package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._

case class Message(val message: String) extends JeevesRecord

case class Update(private val message: Message, private val author: User) extends JeevesRecord {
	private val tags: Set[Username] = Set[Username]()
	
	private val visible = mkLevel()
	policy(visible, !(author.isFriends(CONTEXT.asInstanceOf[SocialNetContext].viewer) || 
			isTagged(CONTEXT.asInstanceOf[SocialNetContext].viewer)), LOW)
	
	def isTagged(user: User) = tags contains user.username
	
	def getMessage(): Symbolic = mkSensitive(visible, message, Message("Unauthorized"))
	def showMessage(ctxt: SocialNetContext): String = concretize(ctxt, getMessage()).asInstanceOf[Message].message
}