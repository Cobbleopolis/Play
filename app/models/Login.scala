package models

import play.api.data.Form
import play.api.data.Forms._

object Login {
	val form = Form(
		mapping(
			"Username" -> text,
			"Password" -> text
		)(LoginData.apply)(LoginData.unapply)
	)
}

case class LoginData(username: String, password: String)
