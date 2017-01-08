package Project
import scala.collection.mutable.Map
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

/**
 * Player ship
 */
class PlayerShip(gameArea: GameArea) extends SpaceShip(new Image("file:Images/alus_1.png", 50, 50, false, false)) {  // alus_1.png:n alkuperäinen koko on 42 x 40 pikseliä
  val speed = 250.0 // pixels per second
  x = 50
  y = 50
  
  var health = 10 //player health
  
  def playDeathAnimation = {
    gameArea.GameTimer.stop
    ProjectAsteroid.stage.scene = (Menu)
  }
  
  def move(dir: String, delta: Double) = { //method moves the ship into given direction and distance depends on delta
    
    if (dir =="right") {
      if (x.value + speed*delta <= scene.value.width.toInt - 50) x = x.value + speed*delta
      else x = scene.value.width.toInt - 50
    }
    
    if (dir =="left") {
      if (x.value - speed*delta >= 0) x = x.value - speed*delta
      else x = 0
    }
    
    if (dir =="down") {
      if (y.value + speed*delta <= scene.value.height.toInt - 50) y = y.value + speed*delta
      else y = scene.height.toInt - 50
    }
    
    if (dir =="up") {
      if (y.value - speed*delta >= 0) y = y.value - speed*delta
      else y = 0
    }
  }
}