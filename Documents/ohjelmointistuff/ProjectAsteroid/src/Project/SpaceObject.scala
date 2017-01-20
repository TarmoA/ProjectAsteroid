package Project

import scalafx.scene.image.{ImageView, Image}
import scalafx.Includes._

/* This is the parent class for all the moving objects in the game. Extends scalafx ImageView and takes a scalafx Image as parameter.
 * Has methods and variables common for all such objects.
 */
abstract class SpaceObject(img: Image) extends ImageView(img){
  var speed: Double
  var isAlive = true
  
  /* 
   * Checks if the object is inside the GameArea's boundaries. If not, marks the object for removal 
   */
  def checkOutOfBounds = {
    if (x.value <= 0 -image.value.width.value || x.value >= ProjectAsteroid.GameArea.width.value + image.value.width.value) {
      isAlive = false
    }
  }
  
  /*
   * Checks for collisions against other SpaceObjects
   */
  def collidesWith(another: SpaceObject) = {
     this.intersects(another.boundsInLocal.value)
  }
  
  def destroy: Unit
}
/*
 * Parent class for player-controlled ships and enemies(asteroids)
 */
abstract class SpaceShip(img: Image) extends SpaceObject(img) {
  
  var health: Int
  

  /*
   * This is called on collisions with bullets or other Spaceships
   */
  def damage(amount: Int) = {
    this.health -= amount
    if (health <= 0) this.destroy
  }
  
  /*
   * Removes the object from the current scene. The object will still need to be removed separately from any other buffers
   * that exist for collision checking or other purposes.
   */
  def destroy = {
    try {
      scene.value.getChildren.remove(this)
    } finally isAlive = false
    playDeathAnimation
  }
  /*
   * Called from the destroy-method.
   */
  def playDeathAnimation: Unit
}

/*
 * Parent class for enemies.
 */
abstract class EnemyShip(img: Image) extends SpaceShip(img) {
  def move(delta: Double): Unit
}
/*
 * Not used
abstract class ShootingEnemy(img: Image) extends EnemyShip(img) {
  def shoot: Boolean
  var lastShot: Long = 0L
  var timePerShot: Double
}*/