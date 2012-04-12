package test.cap.primes

import cap.primes._

import org.scalatest.FunSuite
import org.scalatest.Assertions.{expect}

class TestUpdate extends FunSuite {
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
	SocialNetBackend.addUser(User(Username("User3"), Name("Name3"), Network("N1")))
	SocialNetBackend.addUser(User(Username("User4"), Name("Name4"), Network("N1")))
	SocialNetBackend.addUser(User(Username("User5"), Name("Name5"), Network("N1")))
	SocialNetBackend.addLink("User1", "User2")
	SocialNetBackend.addLink("User2", "User5")
	val post: Update = Update(Message("msg"), SocialNetBackend.get("User1"))
	post.tag(Username("User2"))
	post.tag(Username("User4"))
	
	test("Update - Self") {
		expect("msg") {
			post.showMessage(SocialNetContext(SocialNetBackend.get("User1")))
		}
	}
	test("Update - Friends") {
		expect("msg") {
			post.showMessage(SocialNetContext(SocialNetBackend.get("User2")))
		}
	}
	test("Update - Not Friends") {
		expect("Unauthorized") {
			post.showMessage(SocialNetContext(SocialNetBackend.get("User3")))
		}
	}
	test("Update - Tagged, Not Friends") {
		expect("msg") {
			post.showMessage(SocialNetContext(SocialNetBackend.get("User4")))
		}
	}
	test("Update - Not Tagged, Friends") {
		expect("msg") {
			post.showMessage(SocialNetContext(SocialNetBackend.get("User5")))
		}
	}
}
