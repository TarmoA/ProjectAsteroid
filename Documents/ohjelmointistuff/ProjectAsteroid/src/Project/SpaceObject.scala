package Project

import scalafx.scene.image.{ImageView, Image}
import scalafx.Includes._

abstract class SpaceObject(img: Image) extends ImageView(img){
  var speed: Double
  var isAlive = true
  
  def checkOutOfBounds = {
    if (x.value <= 0 -image.value.width.value || x.value >= ProjectAsteroid.GameArea.width.value + image.value.width.value) {
      isAlive = false
    }
  }
  
  def collidesWith(another: SpaceObject) = {
     this.intersects(another.boundsInLocal.value)
  }
  
  def destroy: Unit
}

abstract class SpaceShip(img: Image) extends SpaceObject(img) {
  
  var health: Int
  
  def damage(amount: Int) = {
    this.health -= amount
    if (health <= 0) this.destroy
  }
  
  def destroy = {
    try {
      scene.value.getChildren.remove(this)
    } finally isAlive = false
    playDeathAnimation
  }
  
  def playDeathAnimation: Unit
}

abstract class EnemyShip(img: Image) extends SpaceShip(img) {
  def move(delta: Double): Unit
}
abstract class ShootingEnemy(img: Image) extends EnemyShip(img) {
  def shoot: Boolean
  var lastShot: Long = 0L
  var timePerShot: Double
}