package cap.primes.test
import cap.primes._

class TestMain extends Test {
	def run: Unit = {
		super.mStart
		var tStart: Long = 0
		var tEnd: Long = 0
		{ // Test 1: ....
			out.println("Test 1: Username [O(1)]")
			tStart = System.nanoTime
			SocialNetBackend(1).showUsername(Default.defaultContext)
			tEnd = System.nanoTime
			out.println("Time: " + (tEnd - tStart).toString)
		}

		SocialNetBackend += new User("bob", "Bob", "default")
		SocialNetBackend.addLink("1", "bob")
		var newFriend = SocialNetBackend.get("1").getFriendsBackend()(0)
		{ // Test 2: ....
			out.println("Test 1: Friend Of Friend [O(n^2)]")
			tStart = System.nanoTime
			SocialNetBackend(newFriend).showTest(Default.defaultContext)
			tEnd = System.nanoTime
			out.println("Time: " + (tEnd - tStart).toString)
		}
		super.mEnd
	}
}
