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

class Asteroid(x0:Double, y0:Double) extends EnemyShip(new Image("file:Images/asteroid.png", 50, 50, false, false)) {
  val speed = 100.0 // pixels per second
  x = x0
  y = y0
  var health = 1
  def playDeathAnimation {
    
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
  }
  

}