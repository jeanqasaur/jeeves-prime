package test.cap.primes

import cap.primes._

import org.scalatest.FunSuite
import org.scalatest.Assertions.{expect}

class TestUser extends FunSuite {
	System.setProperty("smt.home", "C:\\Program Files (x86)\\Microsoft Research\\Z3-2.18\\bin\\z3")
	/*
	test ("[test name]") {
		expect([value you expect]) {
			[code that should produce that value]
		}
	}
	*/
	SocialNetBackend.addUser(User(Username("User1"), Name("Name1"), Network("N1")))
	SocialNetBackend.addUser(User(Username("User2"), Name("Name2"), Network("N1")))
	SocialNetBackend.addUser(User(Username("User3"), Name("Name3"), Network("N2")))
	SocialNetBackend.addUser(User(Username("User4"), Name("Name4"), Network("N1")))
	SocialNetBackend.addUser(User(Username("User5"), Name("Name5"), Network("N2")))
	SocialNetBackend.addLink("User1", "User2")
	SocialNetBackend.addLink("User1", "User5")

	test("Name - Self") {
		expect("Name1") {
			SocialNetBackend.get("User1").showName(SocialNetContext(SocialNetBackend.get("User1")))
		}
	}
	
	test("Name - Friends") {
		expect("Name1") {
			SocialNetBackend.get("User1").showName(SocialNetContext(SocialNetBackend.get("User2")))
		}
	}
	
	test("Name - Not Friends") {
		expect("Anonymous") {
			SocialNetBackend.get("User1").showName(SocialNetContext(SocialNetBackend.get("User3")))
		}
	}
	
	test("Name - Same Network, Not Friends") {
		expect("Name1") {
			SocialNetBackend.get("User1").showName(SocialNetContext(SocialNetBackend.get("User4")))
		}
	}
	
	test("Name - Different Network, Friends") {
		expect("Name1") {
			SocialNetBackend.get("User1").showName(SocialNetContext(SocialNetBackend.get("User5")))
		}
	}
}
