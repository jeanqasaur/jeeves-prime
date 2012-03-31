package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._

case class Message(val message: String) extends JeevesRecord

case class Update(private val message: Message, private val author: User) extends JeevesRecord {
	private var tags: Set[Username] = Set[Username]()
	
	private val visible = mkLevel()
	policy(visible, false, LOW)
	/*
	policy(visible, !(author.isFriends(CONTEXT.viewer.asInstanceOf[User]) || 
			isTagged(CONTEXT.viewer.asInstanceOf[User]) ||
			CONTEXT.viewer.asInstanceOf[User].hasFriend(tags) ||
			author.username == CONTEXT.viewer.asInstanceOf[User].username), LOW)
	*/
	
	def isTagged(user: User) = tags contains user.username
	def tag(user: Username) = {
		tags += user
	}
	
	def getMessage(): Symbolic = mkSensitive(visible, message, Message("Unauthorized"))
	def showMessage(ctxt: SocialNetContext): String = concretize(ctxt, getMessage()).asInstanceOf[Message].message
}