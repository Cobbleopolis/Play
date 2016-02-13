package controllers

import anorm.{Row, SQL}
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

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
			val user: User = DBUtil.getUser.on("user" -> username).as(DBUtil.getUserParser.single)
			Ok(views.html.user(user))
		})
	}
}