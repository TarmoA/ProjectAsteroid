package Project

import scalafx.application.JFXApp
import scalafx.scene.media.AudioClip

object ProjectAsteroid extends JFXApp {
  //pistetään tänne kaikki muuttujat niin ei tarvitse sitten metsästää niitä myöhemmin.
  var isSoundOn: Boolean = true
  val menuSoundFile = new AudioClip("file:audio/menu.wav")
  val shootSound = new AudioClip("file:audio/bullet1.wav")
  val explSound = new AudioClip("file:audio/explosion.wav")
  
  HighScoreFile.read() //reads highscore file and saves its results in buffers
  var GameArea: GameArea = _
  
  def menuSound() {
    if (isSoundOn) menuSoundFile.play
  }
  stage = new JFXApp.PrimaryStage {
    resizable = false
    centerOnScreen()
    title = "ProjectAsteroid"
    
    scene = Menu
    
  }
  
}







