package cap.primes

object Main extends Application {
	var daemon: Server = Server()
	daemon.init("socialist.map")
	server.run()
}
