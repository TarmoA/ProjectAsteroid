package Project

import scalafx.application.JFXApp

/**
 * Main object where program is started.
 */
object ProjectAsteroid extends JFXApp {
  //Reads highscore file and saves its results in buffers
  HighScoreFile.read()
  // this will later be used to store the GameArea for easier access to some values and methods.
  var GameArea: GameArea = _
  
  //Defines new PrimaryStage
  
  /* The game window in scalaFX is called a stage. Initially sets the Stage's Scene as the singleton object Menu
   */
  stage = new JFXApp.PrimaryStage {
    resizable = false
    centerOnScreen()
    title = "ProjectAsteroid"
    
    scene = Menu
  }
}