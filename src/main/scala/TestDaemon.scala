package cap.primes

trait Test {
	def run(network: SocialNetBackend): Unit
}

class TestDaemon {
	var classLoader = null
	var backend = null
	
	private def initClassLoader(testFile: String) = {
		classLoader = new java.net.URLClassLoader(
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
			testRunner.run(backend)
		} catch {
			case e: java.lang.ClassNotFoundException => {
				printf("Test failed, %s not found.", clazzName)
			}
		}
	}
	def init(graphDef: String) = {
		initClassLoader(testFile)
		if(graphDef != null) {
			
		}
	}
}