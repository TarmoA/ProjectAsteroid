package Project
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene._
import scalafx.scene.paint.Color._
import scalafx.scene.image.ImageView
import scalafx.scene.image.Image
import scalafx.scene.input._
import scalafx.scene.Node
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode._
import scalafx.event.ActionEvent._
import scalafx.scene.control._
import scalafx.Includes._
import scalafx.animation.AnimationTimer

abstract class SpaceObject(img: Image) extends ImageView(img){
  
  val speed: Double
  
  def collidesWith(another: SpaceObject) = {
     this.intersects(another.boundsInLocal.value)
  }
  var isAlive = true
  
  def destroy: Unit
  
}

abstract class SpaceShip(img: Image) extends SpaceObject(img) {
  
  var health: Int
  
  
  def damage(amount: Int) = {
    this.health -= amount
    if (health <= 0) this.destroy
    println(this.health)
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