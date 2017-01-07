package Project

import scalafx.application.JFXApp

/*
 * Main program which runs game, gametimer and etc...
 */
object ProjectAsteroid extends JFXApp {
  
  var isPaused: Boolean = true
  
  
  stage = new JFXApp.PrimaryStage {
    resizable = false
    title = "ProjectAsteroid"
    //width = 640 
    //height = 480
    
    
    scene = Menu
    
  }
}

