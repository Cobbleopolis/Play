package controllers

import models.{Login, LoginData}
import play.api.mvc._

object Auth extends Controller {

	def login = Action {
		Ok(views.html.login(Login.form))
	}

	def submitLogin = Action(implicit request => {
		val loginData: LoginData = Login.form.bindFromRequest.get
		Redirect(routes.Application.user(loginData.username))
	})
}
