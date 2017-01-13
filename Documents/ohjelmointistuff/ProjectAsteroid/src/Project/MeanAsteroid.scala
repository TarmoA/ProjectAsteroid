
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
import scala.util.Random

import scalafx.animation.AnimationTimer

class MeanAsteroid(x0:Double, y0:Double, movingSpeed: Double) extends EnemyShip(new Image("file:Images/asteroid.png", 100, 100, false, false)) {
  var speed = 150.0  + movingSpeed// pixels per second
  x = x0
  y = y0
  var health = 5
  def playDeathAnimation {
    
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
  }
  

}
