package controllers

import auth.Secured
import models.User
import play.api.Play.current
import play.api.db.DB
import play.api.mvc._
import util.DBUtil

import scala.collection.mutable.ArrayBuffer

object Application extends Controller with Secured {

    def index = Action { implicit request =>
        val banner = views.html.banner(request.session)
        val arrayBuffer: ArrayBuffer[String] = ArrayBuffer[String]()
        for (i <- 1 to 5)
            arrayBuffer += "Hello " + i
        Ok(views.html.index(banner)("Hello World!", arrayBuffer.toArray))
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

    def user(username: String) = withUser { user => implicit request =>
        val banner = views.html.banner(request.session)
        val prompts = DBUtil.getPromptsForUser(user)
        Ok(views.html.user(banner)(user, prompts))
    }

    def submit(username: String) = Action { implicit request =>
        val banner = views.html.banner(request.session)
        DB.withConnection(implicit conn => {
            val user: User = DBUtil.getUserFromUsername(username)
            Ok(views.html.submit(banner)(user))
        })
    }

    def userTest = withUser { user => implicit request =>
        val banner = views.html.banner(request.session)
        Ok(views.html.userTest(banner)(user.username))
    }
}