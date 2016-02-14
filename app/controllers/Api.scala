package controllers

import controllers.Application._
import models.User
import play.api.db.DB
import play.api.libs.json.{JsObject, JsString}
import play.api.libs.ws.WSResponse
import play.api.mvc.{Action, Controller}
import reference.{DBReference, AuthReference}
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current

object Api extends Controller {

	def auth = Action.async { request =>
		AuthReference.googleAuth.withQueryString("id_token" -> request.getQueryString("id").get).get().map{ response =>
			if ((response.json \ "aud").toString == AuthReference.googleClientId)
				Ok(JsObject(Seq("redirect" -> JsString("/user/" + request.getQueryString("name").get))))
			else
				Ok(JsObject(Seq("redirect" -> JsString("/login"))))
		}
	}

	def submitPrompt = Action { request =>
		DB.withConnection(implicit conn => {
			var user: String = ""
			var content: String = ""
			request.body.asJson.foreach(json => {
				user = (json \ "user").as[String]
				content = (json \ "content").as[String]
				DBReference.submitPrompt.on("user" -> user, "content" -> content).executeInsert()
			})
			Ok(JsObject(Seq("data" -> JsString(user + " | " + content))))
		})
	}

}
