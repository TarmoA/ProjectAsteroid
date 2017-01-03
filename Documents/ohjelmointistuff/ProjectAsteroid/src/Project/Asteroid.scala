package Project
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene._
import scalafx.scene.paint.Color._
import scalafx.scene.image.Image
import scalafx.scene.input._
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode._
import scalafx.event.ActionEvent._
import scalafx.scene.control._
import scalafx.Includes._
import scalafx.animation.AnimationTimer

class Asteroid(x0:Double, y0:Double) extends SpaceShip(new Image("file:Images/Asteroid.png")) {
  val speed = 100.0 // pixels per second
  x = x0
  y = y0
  var health = 1
  def playDeathAnimation {
    
  }
  
  var oldTime: Long = 0L
  val timer = AnimationTimer(t =>{
    if (oldTime > 0) {
      val delta = (t - oldTime)/1e9
      x = x.value - speed*delta
    }
    oldTime = t
  })
  timer.start 

}