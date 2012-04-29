package cap.primes

import java.io.PrintStream

class Logger(fileName: String) extends PrintStream(fileName) {
	override def println(output: String): Unit = {
		if(output.indexOf("***") != -1) super.println(output)
	}
}
