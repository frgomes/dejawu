package controllers

import play.api._
import play.api.mvc._


object Application extends Application


trait Application extends Controller {

  private val logger = Logger("controllers.Application")

  def index = Action {
    Redirect("/purejs")
  }

  def purejs = Action {
    Ok(views.html.purejs())
  }

  def app = Action {
    Ok(views.html.app())
  }
}
