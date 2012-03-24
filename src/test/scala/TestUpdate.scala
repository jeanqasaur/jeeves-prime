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
	val user1 = User(Username("User1"), Name("Name1"), Network("N1"))
	val user2 = User(Username("User2"), Name("Name2"), Network("N1"))
	val user3 = User(Username("User3"), Name("Name3"), Network("N1"))
	val user4 = User(Username("User4"), Name("Name4"), Network("N1"))
	val user5 = User(Username("User5"), Name("Name5"), Network("N1"))
	user1.addFriend(user2)
	user2.addFriend(user5)
	val post: Update = Update(Message("msg"), user1)
	post.tag(user3.username, user1.username)
	
	test("Update - Self") {
		expect("msg") {
			post.showMessage(SocialNetContext(user1))
		}
	}
	test("Update - Friends") {
		expect("msg") {
			post.showMessage(SocialNetContext(user2))
		}
	}
	test("Update - Not Friends") {
		expect("Unauthorized") {
			post.showMessage(SocialNetContext(user3))
		}
	}
	test("Update - Tagged, Not Friends") {
		expect("msg") {
			post.showMessage(SocialNetContext(user4))
		}
	}
	test("Update - Not Tagged, Friends") {
		expect("msg") {
			post.showMessage(SocialNetContext(user5))
		}
	}
}
