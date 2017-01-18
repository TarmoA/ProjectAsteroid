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

class SmallAsteroid(x0:Double, y0:Double, movingSpeed: Double) extends EnemyShip(new Image("file:Images/uusin_asteroidi.png", 50, 50, true, false)) {  // Alkuperäinen koko 20x20
  var speed = 120.0 + movingSpeed * 1.2 // pixels per second
  x = x0
  y = y0
  var health = 2
  def playDeathAnimation {
    ProjectAsteroid.GameArea.score += 1
    if (ProjectAsteroid.isSoundOn)ProjectAsteroid.explSound.play
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
		checkOutOfBounds
  }
  
}

class BigAsteroid(x0:Double, y0:Double, movingSpeed: Double) extends EnemyShip(new Image("file:Images/uusin_iso_asteroidi.png", 110, 110, true, false)) {  // Alkuperäinen koko 30x30
  var speed = 80.0 + movingSpeed * 2 // pixels per second
  x = x0
  y = y0
  var health = 6
  def playDeathAnimation {
    ProjectAsteroid.GameArea.score += 3
    if (ProjectAsteroid.isSoundOn)ProjectAsteroid.explSound.play
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
    checkOutOfBounds
  }
  
}

class AlienShip (x0:Double, y0:Double) extends ShootingEnemy(new Image("file:Images/uusin_asteroidi.png", 80, 80, false, false)) {
  var speed = 100.0 // pixels per second
  x = x0
  y = y0
  var health = 1
  var timePerShot = 2.0
  
  def playDeathAnimation {
     if (ProjectAsteroid.isSoundOn)ProjectAsteroid.explSound.play
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
    checkOutOfBounds
  }
  
   def shoot = {
    var bullet = new EnemyBullet(x.value, y.value)
      ProjectAsteroid.GameArea.content += bullet
      ProjectAsteroid.GameArea.enemyBullets += bullet
      true
  }
}