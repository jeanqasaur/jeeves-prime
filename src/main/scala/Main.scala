package cap.primes

object Main extends App {
	System.setProperty("smt.home", "C:\\Program Files (x86)\\Microsoft Research\\Z3\\bin\\z3")
	var daemon = new TestDaemon()
	daemon.init("socialist.map")
	daemon.runTest("C:\\Users\\Pat\\workspace\\jeeves-prime\\target\\scala_2.9.1\\classes\\cap\\primes\\test", "cap.primes.test.TestMain")
}
