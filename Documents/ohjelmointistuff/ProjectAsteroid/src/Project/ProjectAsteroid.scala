package Project

import scalafx.application.JFXApp


object ProjectAsteroid extends JFXApp {
  
  HighScoreFile.read() //reads highscore file and saves its results in buffers
  var GameArea: GameArea = _
  
  stage = new JFXApp.PrimaryStage {
    resizable = false
    centerOnScreen()
    title = "ProjectAsteroid"
    
    scene = Menu
    
  }
  
}