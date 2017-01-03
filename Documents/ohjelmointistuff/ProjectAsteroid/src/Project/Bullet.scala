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

abstract class Bullet(img: Image) extends SpaceObject(img) {
  def destroy = {
     try {
       scene.value.getChildren.remove(this)
     } finally isAlive = false
  }
  
}

class PlayerBullet(x0:Double, y0:Double) extends Bullet(new Image("file:Images/Bullet.png")) {
  val speed = 300.0 // pixels per second
  x = x0
  y = y0
  var oldTime: Long = 0L
  val timer = AnimationTimer(t =>{
    if (oldTime > 0) {
      val delta = (t - oldTime)/1e9
      x = x.value + speed * delta
    }
    oldTime = t
  })
  timer.start 

}
class EnemyBullet(x0:Double, y0:Double) extends Bullet(new Image("file:Images/Bullet.png")) {
  val speed = 300.0 // pixels per second
  x = x0
  y = y0
  var oldTime: Long = 0L
  val timer = AnimationTimer(t =>{
    if (oldTime > 0) {
      val delta = (t - oldTime)/1e9
      x = x.value + speed * delta
    }
    oldTime = t
  })
  timer.start 

}