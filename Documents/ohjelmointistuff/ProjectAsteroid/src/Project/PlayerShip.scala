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
import scalafx.scene.media._
import scala.math._

/**
 * Player ship
 */
class PlayerShip(gameArea: GameArea) extends SpaceShip(new Image("file:Images/PlayerShip.png", 56, 53, true, false)) {  // alus_1.png:n alkuperäinen koko on 42 x 40 pikseliä
  var speed = 250.0 // pixels per second
  var xSpeed = 0
  var ySpeed = 0
  val acceleration = 15
  val slowingSpeed = 5
  x = 25
  y = 304 // GameArea.height.value.toInt / 2 - PlayerShip.height.value
  
  var lastShot: Long = 0L
  
  
  //val shootSound = ProjectAsteroid.shootSound
  
  // Player health is defined by the level of difficulty
  var health = {
    if      (Difficulty.definition == "easy")   9
    else if (Difficulty.definition == "normal") 5
    else if (Difficulty.definition == "hard")   2
    else                                        0
  }
  
  def playDeathAnimation = {
    gameArea.GameTimer.stop
    new DeathMenu()
  }
  
  def shoot = {
    var bullet = new PlayerBullet(x.value, y.value)
      ProjectAsteroid.GameArea.content += bullet
      ProjectAsteroid.GameArea.playerBullets += bullet
      Sound.shootSound()
      true
  }
  
  def move(delta: Double) = { //method moves the ship into given direction and distance depends on delta
    if (xSpeed > 0) {
      if (x.value + xSpeed*delta <= scene.value.width.toInt - this.image.value.width.toInt) x = x.value + xSpeed*delta
      else x = scene.value.width.toInt - this.image.value.width.toInt
    }
    
    if (xSpeed < 0) {
      if (x.value + xSpeed*delta >= 0) x = x.value + xSpeed*delta
      else x = 0
    }
    
    if (ySpeed >0) {
      if (y.value + ySpeed*delta <= scene.value.height.toInt - this.image.value.height.toInt) y = y.value + ySpeed*delta
      else y = scene.height.toInt - this.image.value.height.toInt
    }
    
    if (ySpeed <0) {
      if (y.value + ySpeed*delta >= 0) y = y.value + ySpeed*delta
      else y = 0
    }
  }
  
  def slowDownVertical(delta: Double)={
    if (ySpeed < 0) ySpeed += slowingSpeed
    if(ySpeed > 0) ySpeed -= slowingSpeed
  }
  
  def slowDownHorizontal(delta: Double)={
    if (xSpeed < 0) xSpeed += slowingSpeed
    if(xSpeed > 0) xSpeed -= slowingSpeed
  }
  
  def accelerate(dir: String, delta: Double) = { //method moves the ship into given direction and distance depends on delta
    
    if (dir =="right") {
      if(xSpeed < 250) xSpeed += acceleration

    }
    
    if (dir =="left") {
      if(xSpeed > -250) xSpeed -= acceleration
    }
    
    if (dir =="down") {
      if(ySpeed < 250) ySpeed += acceleration
    }
    
    if (dir =="up") {
      if(ySpeed > -250) ySpeed -= acceleration
    }
  }
}