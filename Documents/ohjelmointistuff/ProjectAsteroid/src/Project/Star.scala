package Project

import scalafx.scene.image.Image

/**
 * Used to generate background's stars, extends SpaceObject , star picture original size is 2*1 pixels
 */
class Star(x0: Double, y0: Double) extends SpaceObject(new Image("file:Images/Star.png", 4, 2, true, false)) {
  //Speed is pixels per second
  var speed: Double = 50
  x = x0
  y = y0
  
  def move(delta: Double) = {
    x = x.value - this.speed * delta
    checkOutOfBounds
  }
  
  override def destroy = Unit
}