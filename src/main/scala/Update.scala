package cap.primes

import cap.jeeves._
import SocialNetBackend._
import cap.scalasmt._
import cap.scalasmt.Expr._

case class Message(val message: String) extends JeevesRecord

case class Update(private val message: Message, private val author: User) extends JeevesRecord {
	private var tags: List[Username] = List[Username]()
	
	private val visible = mkLevel()	
	policy(visible, !(getAccessors().has(CONTEXT.viewer.username)), LOW)
	
	
	def isTaggedS(user: Symbolic) = tags.has(user.username)
	def isTagged(user: User) = tags contains user.username
	def tag(user: Username) = {
		if (!tags.contains(user)) {
			tags = user :: tags
		}
	}
	
	def getMessage(): Symbolic = mkSensitive(visible, message, Message("Unauthorized"))
	def showMessage(ctxt: SocialNetContext): String = concretize(ctxt, getMessage()).asInstanceOf[Message].message
	def getAccessors(): List[Username] = {
		var accessors: List[Username] = List[Username]()
		accessors ::= author.username
		accessors :::= author.getFriendsBackend() 
		accessors :::= tags
		for (u: Username <- tags) {
			accessors :::= SocialNetBackend.get(u.username).getFriendsBackend()
		}
		accessors
	}
}