package controllers

import models.{Register, Login}
import play.api.mvc._
import util.DBUtil

object Auth extends Controller {

	def login = Action {
		Ok(views.html.login(Login.form))
	}

	def submitLogin = Action(implicit request => {
		Login.form.bindFromRequest.fold(
			formWithErrors => {
				// binding failure, you retrieve the form containing errors:
				BadRequest(views.html.login(formWithErrors))
			},
			userData => {
				/* binding success, you get the actual value. */
				Redirect(routes.Application.user(userData.username)).withSession(Security.username -> userData.username)
			}
		)
	})

	def register = Action {
		Ok(views.html.register(Register.form))
	}

	def submitRegister = Action(implicit request => {
		Register.form.bindFromRequest().fold(
			formWithErrors => {
				BadRequest(views.html.register(formWithErrors))
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
