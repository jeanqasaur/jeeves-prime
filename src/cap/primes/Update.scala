package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._

case class Message(val message: String) extends JeevesRecord

case class Update(private val message: Message, private val author: User) extends JeevesRecord {
	private val tags: Set[Username] = Set[Username]()
	
	private val visible = mkLevel()
	policy(visible, !(author.isFriends(CONTEXT.asInstanceOf[SocialNetContext].viewer) || 
			isTagged(CONTEXT.asInstanceOf[SocialNetContext].viewer) ||
			CONTEXT.asInstanceOf[SocialNetContext].viewer.hasFriend(tags) ||
			author.username == CONTEXT.asInstanceOf[SocialNetContext].viewer.username), LOW)
	
	def isTagged(user: User) = tags contains user.username
	def tag(user: Username, editor: Username) = {
		if(author.username == editor) {
			tags += user
		}
	}
	
	def getMessage(): Symbolic = mkSensitive(visible, message, Message("Unauthorized"))
	def showMessage(ctxt: SocialNetContext): String = concretize(ctxt, getMessage()).asInstanceOf[Message].message
}