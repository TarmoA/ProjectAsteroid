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






object PlayerShip extends SpaceShip(new Image("file:Images/PlayerShip.png",50,50,false,false)) {
  val speed = 100.0 // pixels per second
  x = 50
  y = 50
  
  var health = 10
  
  def playDeathAnimation = {
    
  }
  
  def move(dir: String, delta: Double) = {
    
    if (dir =="right") x = x.value + speed*delta
    if (dir =="left") x = x.value - speed*delta
    if (dir =="down") y = y.value + speed*delta
    if (dir =="up") y = y.value - speed*delta
  }
  

  
  

}