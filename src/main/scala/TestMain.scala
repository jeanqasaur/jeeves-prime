package cap.primes.test
import cap.primes._
import java.io.PrintStream

class TestMain extends Test {
	var out: PrintStream = null

	def checkUpdates(i: Int): Long = { // TODO: Switch so that this checks "first" 15 visible updates from all subscriptions
		var tStart = System.nanoTime
		var pn = 0
		var pv = 0
		while(pv < 15 && pn < backend(i).getNumPosts()) {
			var post = backend(i).getPost(pn)
			if(post.canSee(backend(i).username)) {
				post.showMessage(backend(i).username)
				pv += 1
			}
		}
		System.nanoTime - tStart
	}

	def browseProfiles(i: Int): Long = {
		var taken: List[Int] = List[Int]()
		var friends = backend(i).getFriendsBackend()
		var total: Long = 0L
		for(it <- 0 to 50) {
			var index = (Math.random * friends.length).intValue
			while(taken contains index) { index = (Math.random * friends.length).intValue }
			var tr = browseProfile(backend(index).username, backend(i).username)
			total += tr
		}
		total
	}

	def browseProfile(uname: Username, accessor: Username): Long = {
		var tStart = System.nanoTime
		var pn = 0
		var pv = 0
		out.println("I got here")
		while(pv < 15 && pn < backend(uname).getNumPosts()) {
			var post = backend(uname).getPost(pn)
			if(post.canSee(backend(accessor).username)) {
				post.showMessage(accessor)
				pv += 1
			}
		}
		out.println("I got here")
		backend(uname).showFriends(accessor)
		out.println("I got here")
		backend(uname).showName(accessor)
		out.println("I got here")
		backend(uname).showUsername(accessor)
		out.println("I got here")
		System.nanoTime - tStart
	}
	
	def testUser(i: Int): (Long, Long) = {
		out.println("User: " + i)

		out.println("Browsing Profiles")
		var tStart0 = System.nanoTime
		browseProfiles(i)
		var tEnd0 = System.nanoTime
		
		out.println("Time: " + (tEnd0 - tStart0) + "ns")
		
		out.println("Checking Updates (Viewing 15 'latest' updates)")
		var tStart1 = System.nanoTime
		checkUpdates(i)
		var tEnd1 = System.nanoTime
		out.println("Time: " + (tEnd1 - tStart1) + "ns")
		(tEnd0 - tStart0, tEnd1 - tStart1)
	}
	
	def run: Unit = {
		if(out == null) out = Console.out
		super.mStart
		var taken: List[Int] = List[Int]()
		var t1: Long = 0
		var t2: Long = 0
		for(it <- 0 to 5000) {
			var index = (Math.random * 100).intValue
			while(taken contains index) { index = (Math.random * 100).intValue }
			var tr = testUser(index)
			out.println("Test Results: Times: " + tr._1 + "," + tr._2)
			t1 += (tr._1 / 1000000L)
			t2 += (tr._2 / 1000000L)
		}
		super.mEnd
		t1 /= 100
		t2 /= 100
		out.println("End Results: Times: " + t1 + "," + t2)
		out.println("Total Time: " + super.runTime)
	}
}
