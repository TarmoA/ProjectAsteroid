package Project

import scala.swing._
import java.awt.Color
import processing.core._

/**
 * Oman aluksen tiedot
 */
object PlayerShip extends PApplet {
  
  private var xKor: Int = 1000
  private var yKor: Int = 350
  val speed: Int = 5
  val direction: Int = -1 //vain x akselilla
  val lives = Difficulty.setDifficulty()
  val ship: PImage = loadImage("X.png") //kuva 80x80
  
  def move(): Unit = {
    this.xKor = this.xKor + ( this.direction )
    //(this.xKor, this.yKor)
  }
  def getx(): Int = {
    this.xKor
  }
  def gety(): Int = {
    this.yKor
  }
}