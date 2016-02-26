package controllers

import models.{Register, Login}
import play.api.mvc._
import util.DBUtil

object Auth extends Controller {

	def login = Action { implicit request =>
		val header = views.html.header(request.session)
		Ok(views.html.login(header)(Login.form))
	}

	def submitLogin = Action(implicit request => {
		val header = views.html.header(request.session)
		Login.form.bindFromRequest.fold(
			formWithErrors => {
				// binding failure, you retrieve the form containing errors:
				BadRequest(views.html.login(header)(formWithErrors))
			},
			userData => {
				/* binding success, you get the actual value. */
				Redirect(routes.Application.user(userData.username)).withSession(Security.username -> userData.username)
			}
		)
	})

	def register = Action { implicit request =>
		val header = views.html.header(request.session)
		Ok(views.html.register(header)(Register.form))
	}

	def submitRegister = Action(implicit request => {
		val header = views.html.header(request.session)
		Register.form.bindFromRequest().fold(
			formWithErrors => {
				BadRequest(views.html.register(header)(formWithErrors))
			},
			registerData => {
				DBUtil.registerUser(registerData.getNewUserData)
				Redirect(routes.Application.user(registerData.username)).withSession(Security.username -> registerData.username)
			}
		)
	})

	def logout = Action {
		Redirect(routes.Auth.login()).withNewSession
	}
}
