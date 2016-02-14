package util

import models.User
import play.api.Play.current
import play.api.db.DB
import reference.DBReference

object DBUtil {

	def getUserFromUsername(username: String): User = {
		DB.withConnection(implicit conn => {
			DBReference.getUserFromUsername.on("user" -> username).as(DBReference.getUserParser.single)
		})
	}

	def getUserFromEmail(email: String): User = {
		DB.withConnection(implicit conn => {
			DBReference.getUserFromUsername.on("email" -> email).as(DBReference.getUserParser.single)
		})
	}

}
