package cap.primes.test
import cap.primes._
import java.io.PrintStream

class TestMain extends Test {
	var out: PrintStream = null

	def testUser(i: Int): (Long, Long, Long) = {	
		out.println("User: " + i)
		// Test 1: Username [O(1)]
		out.println("Test 1: Username [O(1)]")
		var tStart0 = System.nanoTime
		backend(i).showUsername(Default.defaultContext)
		var tEnd0 = System.nanoTime
		out.println("Time: " + (tEnd0 - tStart0) + "ns")
		// Test 2: Fried [O(n)]
		out.println("Test 2: Friend [O(n)]")
		var newFriend = backend(backend(i).getFriendsBackend()(0))
		var tStart1 = System.nanoTime
		backend(i).showName(new SocialNetContext(newFriend))
		var tEnd1 = System.nanoTime
		out.println("Time: " + (tEnd1 - tStart1) + "ns")
		// Test 3: Friend Of Friend [O(n^2)]
		out.println("Test 3: Friend Of Friend [O(n^2)]")
		newFriend = backend(newFriend.getFriendsBackend()(0))
		var tStart2 = System.nanoTime
		backend(i).showTest(new SocialNetContext(newFriend))
		var tEnd2 = System.nanoTime
		out.println("Time: " + (tEnd2 - tStart2) + "ns")
		(tEnd0 - tStart0, tEnd1 - tStart1, tEnd2 - tStart2)
	}
	
	def run: Unit = {
		if(out == null) out = Console.out
		super.mStart
		var taken: List[Int] = List[Int]()
		var t1: Long = 0
		var t2: Long = 0
		var t3: Long = 0
		for(it <- 0 to 100) {
			var index = (Math.random * 100).intValue
			while(taken contains index) { index = (Math.random * 100).intValue }
			var tr = testUser(index)
			out.println("Test Results: Times: " + tr._1 + "," + tr._2 + "," + tr._3)
			t1 += (tr._1 / 1000000L)
			t2 += (tr._2 / 1000000L)
			t3 += (tr._3 / 1000000L)
		}
		t1 /= 100
		t2 /= 100
		t3 /= 100
		out.println("End Results: Times: " + t1 + "," + t2 + "," + t3)
		super.mEnd
		out.println("Total Time: " + super.runTime)
	}
}
