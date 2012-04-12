package cap.primes

import java.io._
import java.net._

object Conf {
	val USERNAME: String = "jeeves"
	val PASSWORD: String = "primes"
	val HOSTNAME: String = "<insert hostname here>"
	val PORT: Int = 31416
}

class Server {
	val ss: ServerSocket = new ServerSocket(Conf.PORT)
	var td: TestDaemon = null

	def init(graphDef: String) = {
		td = new TestDaemon()
		td.init(graphDef)
	}
	
	def run() = {
		try {
			while(true) {
				var s = ss.accept
				var stream: InputStream = s.getInputStream
				var in: BufferedReader = new BufferedReader(new InputStreamReader(stream))
				while(!in.ready()) {
					try {
						Thread.sleep(20)
					} catch {
						case e: InterruptedException => {
						}
					}
				}
				var command: String = in.readLine
				var parts: Array[String] = command.split(" ")
				td.runTest(parts(0), parts(1))
			}
		} catch {
			case e: IOException => {
			}
		}
	}
}
