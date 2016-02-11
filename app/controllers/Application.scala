package controllers

import anorm.{Row, SQL}
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._

import scala.collection.mutable.ArrayBuffer

object Application extends Controller {

	val getUser = SQL("select * from users where username = {user}")

	def index = Action {
		val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
		for (i <- 1 to 4)
			arrayBuffer += "Hello " + i
		Ok(views.html.index("Hello World!", arrayBuffer.toArray))
	}

	def user(username: String) = Action {
		DB.withConnection(implicit conn => {
			val user: User = getUser.on("user" -> username).map({
				case Row(username: String, email: String, accountType: Int) => new User(username, email, accountType)
			}).single
			Ok(views.html.user(user))
		})
	}
}