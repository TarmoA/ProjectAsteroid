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

class Star(x0: Double, y0: Double) extends SpaceObject(new Image("file:Images/star_2.png", 4, 2, true, false)) { //tähden kuvan alk. koko 2*1
  var speed: Double = 50  // Pixels per second
  x = x0
  y = y0
  
 
  def move(delta: Double) = {
    x = x.value - this.speed * delta
    checkOutOfBounds
  }
  
  override def destroy = Unit
}