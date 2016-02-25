package reference

import anorm.SqlParser._
import anorm._
import models.{Prompt, User}

object DBReference {

	val getUserFromUsername = SQL("select * from users where username = {user}")
	val getUserFromEmail = SQL("select * from users where email = {email}")
	val getUserParser = for {
		username <- str("username")
		email <- str("email")
		accountType <- int("accountType")
	} yield new User(username, email, accountType)

	val userExists = SQL("select 1 from users where users.email = {email}")

	val submitPrompt = SQL("insert into prompts (user, content) values ({user}, {content});")

	val getAllPromptsForUser = SQL("select * from prompts where user = {user}")
	val getPromptParser = for {
		content <- str("content")
	} yield new Prompt(content)

	val insertUser = SQL(
		"""
		  insert into users (username, email, password, salt, accountType, submissionsOpen)
		  values ({username}, {email}, {password}, {salt}, {accountType}, {submissionsOpen})
		"""
	)

}
