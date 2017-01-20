package Project

import scalafx.application.JFXApp
import scalafx.scene.media.AudioClip

object ProjectAsteroid extends JFXApp {
  var isSoundOn: Boolean = true
  val menuSoundFile = new AudioClip("file:audio/menu.wav")
  val shootSound = new AudioClip("file:audio/bullet1.wav")
  val explSound = new AudioClip("file:audio/explosion.wav")
  
  HighScoreFile.read() //reads highscore file and saves its results in buffers
  
  var GameArea: GameArea = _ // this will later be used to store the GameArea for easier access to some values and methods.
  
  def menuSound() {
    if (isSoundOn) menuSoundFile.play
  }
  
  /* The game window in scalaFX is called a stage. Initially sets the Stage's Scene as the singleton object Menu
   */
  stage = new JFXApp.PrimaryStage {
    resizable = false
    centerOnScreen()
    title = "ProjectAsteroid"
    
    scene = Menu
    
  }
  
}







