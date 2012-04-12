package cap.primes

import java.io._
import java.net._

trait Test extends Runnable {
	def setBackend(backend: SocialNetBackend): Unit
}

class FileClassLoader(urls: Array[URL], parent: ClassLoader) extends URLClassLoader(urls, parent) {
	override def addURL(url: URL) = {
		super.addURL(url)
	}
}

class TestDaemon {
	var classLoader: FileClassLoader = null
	var backend: SocialNetBackend = null

	private def initClassLoader() = {
		classLoader = new FileClassLoader(
			Array[URL](),
			this.getClass.getClassLoader
		)
	}
	
	private def initClassLoader(testFile: String) = {
		classLoader = new FileClassLoader(
			Array(new File(testFile).toURI.toURL),
			this.getClass.getClassLoader
		)
	}

	def runTest[T <: AnyRef](testFile: String, clazzName: String) = {
		if(classLoader == null) {
			initClassLoader(testFile)
		}
		var fileURL = new File(testFile).toURI.toURL
		if(!(classLoader.getURLs contains fileURL)) {
			classLoader.addURL(fileURL)
		}
		try {
			var clazz = classLoader.loadClass(clazzName)
			var testRunner = clazz.newInstance.asInstanceOf[Test]
			testRunner.setBackend(backend)
			var thread = new Thread(testRunner)
			thread.run
		} catch {
			case e: java.lang.ClassNotFoundException => {
				printf("Test failed, %s not found.", clazzName)
			}
		}
	}
	
	def init(graphDef: String) = {
		initClassLoader()
		if(graphDef != null) {
			GraphLoader.createBackend(graphDef)
		}
	}
}
