package Project

import scalafx.scene.image.Image
import scalafx.Includes._

/**
 * TODO: kuvaus
 */
abstract class Bullet(img: Image) extends SpaceObject(img) {
  def destroy = {
     try {
       scene.value.getChildren.remove(this)
     } finally isAlive = false
  }
  
  def move(delta: Double): Unit
}

/**
 * TODO: kuvaus
 * Images original size is 20*10 pixels
 */
class PlayerBullet(x0:Double, y0:Double) extends Bullet(new Image("file:Images/Bullet.png", 50, 25, true, false)) {
  //speed is pixels per second
  var speed = 300.0
  x = x0 + 35
  y = y0 + 12.5
  
  //var oldTime: Long = 0L TODO: poista
  
  def move(delta: Double) = {
    x = x.value + speed * delta
    checkOutOfBounds
  }
}
//TODO: poista?
class EnemyBullet(x0:Double, y0:Double) extends Bullet(new Image("file:Images/blast_1.png", 25, 25, false, false)) {
  var speed = 300.0 // pixels per second
  x = x0
  y = y0
   
  def move(delta: Double) = {
    x = x.value - speed * delta
    checkOutOfBounds
  }
}