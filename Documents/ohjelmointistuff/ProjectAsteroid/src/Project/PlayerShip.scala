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

/**
 * Player ship
 */

// PLAYERSHIP KULKEE NYT RUUDUN ALAREUNAN OHITSE

class PlayerShip(gameArea: GameArea) extends SpaceShip(new Image("file:Images/alus_1.png", 56, 53, true, false)) {  // alus_1.png:n alkuperäinen koko on 42 x 40 pikseliä
  val speed = 250.0 // pixels per second
  //TODO: Alustukseen sellainen kohta mikä asettaa aluksen y koordinaateiksi noin ruudun puolivälin
  x = 25
  y = 304 // GameArea.height.value.toInt / 2 - this.image.value.height.toInt
  
  var lastShot: Long = 0L
  
  
  val shootSound = ProjectAsteroid.shootSound
  
  // Player health is defined by the level of difficulty
  var health = {
    if      (Difficulty.definition == "easy")   9
    else if (Difficulty.definition == "normal") 5
    else if (Difficulty.definition == "hard")   2
    else                                        0  // Kirjaimellisesti mahdoton vaikeusaste
  }
  
  def playDeathAnimation = {
    gameArea.GameTimer.stop
    new DeathMenu()
  }
  
  def shoot = {
    var bullet = new PlayerBullet(x.value, y.value)
      ProjectAsteroid.GameArea.content += bullet
      ProjectAsteroid.GameArea.playerBullets += bullet
      if (ProjectAsteroid.isSoundOn)shootSound.play
      true
  }
  
  def move(dir: String, delta: Double) = { //method moves the ship into given direction and distance depends on delta
    
    if (dir =="right") {
      if (x.value + speed*delta <= scene.value.width.toInt - this.image.value.width.toInt) x = x.value + speed*delta
      else x = scene.value.width.toInt - this.image.value.width.toInt
    }
    
    if (dir =="left") {
      if (x.value - speed*delta >= 0) x = x.value - speed*delta
      else x = 0
    }
    
    if (dir =="down") {
      if (y.value + speed*delta <= scene.value.height.toInt - this.image.value.height.toInt) y = y.value + speed*delta
      else y = scene.height.toInt - this.image.value.height.toInt
    }
    
    if (dir =="up") {
      if (y.value - speed*delta >= 0) y = y.value - speed*delta
      else y = 0
    }
  }
}