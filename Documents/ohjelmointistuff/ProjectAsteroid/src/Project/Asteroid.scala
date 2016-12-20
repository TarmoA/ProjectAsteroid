package Project

import processing.core._
import Array._

class Asteroid extends PApplet{
  val asteroidImage: PImage = loadImage("O.png"); //jotta loadimage toimii täytyy olla extends PApplet. vaihda käyttää swingiä?
  val speed: Int = 5
  val direction: Int = -1 //vain x akselilla alussa
  
  def move() = {
    //vaihtaa koordinaatteja
  }
}