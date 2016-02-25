package models

import play.api.data.Form
import play.api.data.Forms._

object Login {
	val form = Form(
		mapping(
			"Username" -> nonEmptyText,
			"Password" -> nonEmptyText
		) (LoginData.apply)(LoginData.unapply)
			verifying ("Invalid username or password", f => check(f)
	))

	def check(loginData: LoginData): Boolean = {
		loginData.username == "cobbleopolis" && loginData.password == "loganjt"
	}
}

case class LoginData(username: String, password: String)
