package controllers

import auth.Secured
import models.User
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._
import util.DBUtil

import scala.collection.mutable.ArrayBuffer

object Application extends Controller with Secured {

	def index = Action {
		val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
		for (i <- 1 to 5)
			arrayBuffer += "Hello " + i
		Ok(views.html.index("Hello World!", arrayBuffer.toArray))
	}

	//	def user(username: String) = Action {
	//		val user: User = DBUtil.getUserFromUsername(username)
	//		DB.withConnection(implicit conn => {
	//			val prompts: List[Prompt] =
	//                if(user != null)
	//                    DBReference.getAllPromptsForUser.on("user" -> user.email).as(DBReference.getPromptParser.*)
	//                else
	//                    List[Prompt]()
	//			Ok(views.html.user(user, prompts))
	//		})
	//	}

	def user = withUser { user => implicit request =>
		val prompts = DBUtil.getPromptsForUser(user)
		Ok(views.html.user(user, prompts))
	}

	def submit(username: String) = Action {
		DB.withConnection(implicit conn => {
			val user: User = DBUtil.getUserFromUsername(username)
			Ok(views.html.submit(user))
		})
	}
}