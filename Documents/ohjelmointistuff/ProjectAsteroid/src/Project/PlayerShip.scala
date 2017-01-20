package Project

import scalafx.scene.image.Image
import scalafx.Includes._

/**
 * Player ship extends SpaceShip, original picture size is 42*40 pixels
 */
class PlayerShip(gameArea: GameArea) extends SpaceShip(new Image("file:Images/PlayerShip.png", 56, 53, true, false)) {
  //Pixels per second
  var speed = 250.0
  var xSpeed = 0
  var ySpeed = 0
  val acceleration = 15
  val slowingSpeed = 5
  x = 25
  y = 304
  
  var lastShot: Long = 0L
  
  //Player health is defined by the level of difficulty
  var health = {
    if      (Difficulty.definition == "easy")   9
    else if (Difficulty.definition == "normal") 5
    else if (Difficulty.definition == "hard")   2
    else                                        0
  }
  
  //When player dies timer stops and deathmenu opens
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
  //method moves the ship into given direction and distance depends on delta
  
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
  // method slows down the ships movement when no keys are pressed
  def slowDownVertical(delta: Double)={ 
    if (ySpeed < 0) ySpeed += slowingSpeed
    if(ySpeed > 0) ySpeed -= slowingSpeed
  }
  
  def slowDownHorizontal(delta: Double)={
    if (xSpeed < 0) xSpeed += slowingSpeed
    if(xSpeed > 0) xSpeed -= slowingSpeed
  }
  
  //method accelerates the ship into given direction and distance depends on delta, but no more than
  def accelerate(dir: String, delta: Double) = {
    
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