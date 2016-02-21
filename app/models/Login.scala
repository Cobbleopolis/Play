package models

import play.api.data.Form
import play.api.data.Forms._

object Login {
	val form = Form(
		tuple(
			"Username" -> nonEmptyText,
			"Password" -> nonEmptyText
		) verifying ("Invalid email or password", result => result match {
			case (email, password) => check(email, password)
		}
	))

	def check(username: String, password: String): Boolean = {
		username == "cobbleopolis" && password == "loganjt"
	}
}

case class LoginData(username: String, password: String)
