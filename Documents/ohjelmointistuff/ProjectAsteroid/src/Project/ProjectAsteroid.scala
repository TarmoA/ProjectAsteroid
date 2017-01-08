package Project

import scalafx.application.JFXApp

object ProjectAsteroid extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    resizable = false
    title = "ProjectAsteroid"
    //width = 640 
    //height = 480
    var GameArea: GameArea = _
    scene = Menu
    
  }
}

