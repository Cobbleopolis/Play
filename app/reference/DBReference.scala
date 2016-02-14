package reference

import anorm.SqlParser._
import anorm._
import models.User

object DBReference {

	val getUser = SQL("select * from users where username = {user}")
	val getUserParser = for {
		username <- str("username")
		email <- str("email")
		accountType <- int("accountType")
	} yield new User(username, email, accountType)

	val insertUser = SQL("insert into users (email, username, accountType) values ({email}, {username}, {accountType})")

	val userExists = SQL("select 1 from users where users.email = {email}")

	val submitPrompt = SQL("insert into prompts (user, content) values ({user}, {content});")

}
