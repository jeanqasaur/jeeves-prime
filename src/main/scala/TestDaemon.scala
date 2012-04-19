package cap.primes

import java.io._
import java.net._

trait Test extends Runnable {
	private var startTime: Long = 0
	private var endTime: Long = 0
	protected var backend: SocialNetBackend = null
	
	def setBackend(backend: SocialNetBackend): Unit = {
		this.backend = backend
	}
	
	/**
	 * Mark start time
	 */
	def mStart = {
		startTime = System.nanoTime
	}
	
	/**
	 * Mark end time
	 */
	def mEnd = {
		endTime = System.nanoTime
	}
	
	/**
	 * Get running time
	 */
	def runTime: Long = endTime - startTime
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
