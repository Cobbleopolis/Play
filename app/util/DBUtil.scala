package util

import models.{NewUserData, RegisterData, Prompt, User}
import play.api.Play.current
import play.api.db.DB
import reference.DBReference

object DBUtil {

	def getUserFromUsername(username: String): User = {
		DB.withConnection(implicit conn => {
			DBReference.getUserFromUsername.on("user" -> username).as(DBReference.getUserParser.singleOpt)
		}.orNull)
	}

	def getUserFromEmail(email: String): User = {
		DB.withConnection(implicit conn => {
			DBReference.getUserFromUsername.on("email" -> email).as(DBReference.getUserParser.singleOpt)
		}.orNull)
	}

	def getPromptsForUser(user: User): List[Prompt] = {
		DB.withConnection(implicit conn => {
			DBReference.getAllPromptsForUser.on("user" -> user.username).as(DBReference.getPromptParser.*)
		})
	}

	def registerUser(newUserData: NewUserData): Unit = {
		DB.withConnection(implicit conn => {
			DBReference.insertUser.on(
				"username" -> newUserData.username, 
				"email" -> newUserData.email,
				"password" -> newUserData.password,
				"accountType" -> newUserData.accountType,
				"submissionsOpen" -> newUserData.submissionsOpen
			).executeInsert()
		})
	}

}
