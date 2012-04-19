package cap.primes

object Main extends App {
	System.setProperty("smt.home", "C:\\Program Files (x86)\\Microsoft Research\\Z3\\bin\\z3")
	var daemon: Server = new Server()
	daemon.init("socialist.map")
	daemon.run()
}
