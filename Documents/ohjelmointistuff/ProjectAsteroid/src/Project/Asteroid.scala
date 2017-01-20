package Project

import scalafx.scene.image.Image
import scalafx.Includes._

/**
 * Original picture size 20*20 pixels
 */
class SmallAsteroid(x0:Double, y0:Double, movingSpeed: Double) extends EnemyShip(new Image("file:Images/SmallAsteroid.png", 50, 50, true, false)) {
  //Speed is pixels per second
  var speed = 120.0 + movingSpeed * 1.2
  x = x0
  y = y0
  var health = 2
  
  def playDeathAnimation {
    ProjectAsteroid.GameArea.score += 1
    Sound.explosionSound()
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
    checkOutOfBounds
  }
}

/**
 * Original picture size 30*30 pixels
 */
class BigAsteroid(x0:Double, y0:Double, movingSpeed: Double) extends EnemyShip(new Image("file:Images/BigAsteroid.png", 110, 110, true, false)) {
  //Speed is pixels per second
  var speed = 80.0 + movingSpeed * 2
  x = x0
  y = y0
  var health = 6
  
  def playDeathAnimation {
    ProjectAsteroid.GameArea.score += 3
    Sound.explosionSound()
  }
  
  def move(delta: Double) = {
    x = x.value - speed*delta
    checkOutOfBounds
  }
  
}

/*Not used
 * class AlienShip (x0:Double, y0:Double) extends ShootingEnemy(new Image("file:Images/uusin_asteroidi.png", 80, 80, false, false)) {
  var speed = 100.0 // pixels per second
  x = x0
  y = y0
  var health = 1
  var timePerShot = 2.0
  
  def playDeathAnimation {
     Sound.explosionSound()
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
}*/