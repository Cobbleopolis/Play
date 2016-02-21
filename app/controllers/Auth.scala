package controllers

import models.Login
import play.api.mvc._

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
				Redirect(routes.Application.user).withSession(Security.username -> userData._1)
			}
		)
	})
}
