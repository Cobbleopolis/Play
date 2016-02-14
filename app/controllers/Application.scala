package controllers

import models.User
import play.api.Play
import play.api.Play.current
import play.api.db.DB
import play.api.libs.ws.WS
import play.api.mvc._
import reference.{DBReference, AuthReference}

import scala.collection.mutable.ArrayBuffer

object Application extends Controller {

	def index = Action {
		val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
		for (i <- 1 to 5)
			arrayBuffer += "Hello " + i
		Ok(views.html.index("Hello World!", arrayBuffer.toArray))
	}

	def user(username: String) = Action {
		DB.withConnection(implicit conn => {
			val user: User = DBReference.getUser.on("user" -> username).as(DBReference.getUserParser.single)
			Ok(views.html.user(user))
		})
	}

	def login = Action {
		Ok(views.html.login())
	}

	def submit(username: String) = Action {
		DB.withConnection(implicit conn => {
			val user: User = DBReference.getUser.on("user" -> username).as(DBReference.getUserParser.single)
			Ok(views.html.submit(user))
		})
	}
}