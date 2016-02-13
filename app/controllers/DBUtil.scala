package controllers

import anorm._
import anorm.SqlParser._

object DBUtil {

	val getUser = SQL("select * from users where username = {user}")
	val getUserParser = for {
		username <- str("username")
		email <- str("email")
		accountType <- int("accountType")
	} yield new User(username, email, accountType)

}
