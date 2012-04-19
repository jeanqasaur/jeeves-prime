package cap.primes

object Main extends App {
	var daemon: Server = new Server()
	daemon.init("socialist.map")
	daemon.run()
}
