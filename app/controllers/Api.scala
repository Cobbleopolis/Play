package controllers

import play.api.libs.json.{JsString, JsObject}
import play.api.mvc.{Action, Controller}

object Api extends Controller {

	def auth = Action { request =>
		Ok(JsObject(Seq("redirect" -> JsString("/user/" + request.getQueryString("name").get))))
	}

}
