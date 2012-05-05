package cap.primes.test
import cap.primes._
import java.io.PrintStream

class TestMain extends Test {
	var out: PrintStream = null

	def testUser(i: Int): (Long, Long, Long) = {
		// Test 1: Username [O(1)]
		out.println("Test 1: Username [O(1)]")
		var tStart0 = System.nanoTime
		backend(i).showUsername(Default.defaultContext)
		var tEnd0 = System.nanoTime
		// Test 2: Fried [O(n)]
		out.println("Test 2: Friend [O(n)]")
		var newFriend = backend(backend(i).getFriendsBackend()(0))
		var tStart1 = System.nanoTime
		backend(i).showName(new SocialNetContext(newFriend))
		var tEnd1 = System.nanoTime
		// Test 3: Friend Of Friend [O(n^2)]
		out.println("Test 3: Friend Of Friend [O(n^2)]")
		newFriend = backend(newFriend.getFriendsBackend()(0))
		var tStart2 = System.nanoTime
		backend(i).showTest(new SocialNetContext(newFriend))
		var tEnd2 = System.nanoTime
		(tEnd0 - tStart0, tEnd1 - tStart1, tEnd2 - tStart2)
	}
	
	def run: Unit = {
		if(out == null) out = Console.out
		super.mStart
		var taken: List[Int] = List[Int]()
		for(it <- 0 to 100) {
			var index = (Math.random * 100).intValue
			while(taken contains index) { index = (Math.random * 100).intValue }
			var tr = testUser(index)
			out.println("Test Results: Times: " + tr._1 + "," + tr._2 + "," + tr._3)
		}
		super.mEnd
	}
}
