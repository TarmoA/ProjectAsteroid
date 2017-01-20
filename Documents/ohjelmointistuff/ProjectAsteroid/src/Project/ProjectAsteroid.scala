package Project

import scalafx.application.JFXApp

/**
 * Main object where program is started.
 */
object ProjectAsteroid extends JFXApp {
  //Reads highscore file and saves its results in buffers
  HighScoreFile.read()
  //variable used to acces GameArea class from outside
  var GameArea: GameArea = _
  
  //Defines new PrimaryStage
  stage = new JFXApp.PrimaryStage {
    resizable = false
    centerOnScreen()
    title = "ProjectAsteroid"
    
    //Defines scene
    scene = Menu
  }
}