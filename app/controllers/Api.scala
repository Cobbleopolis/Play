package controllers

import play.api.libs.json.{JsObject, JsString}
import play.api.libs.ws.WSResponse
import play.api.mvc.{Action, Controller}
import reference.AuthReference
import scala.concurrent.ExecutionContext.Implicits.global

object Api extends Controller {

	def auth = Action.async { request =>
		AuthReference.googleAuth.withQueryString("id_token" -> request.getQueryString("id").get).get().map{ response =>
			if ((response.json \ "aud").toString == AuthReference.googleClientId)
				Ok(JsObject(Seq("redirect" -> JsString("/user/" + request.getQueryString("name").get))))
			else
				Ok(JsObject(Seq("redirect" -> JsString("/login"))))
		}
	}

}
