package Project

import scala.swing._
import java.util.Timer
import java.awt.Color

object ProjectAsteroid extends SimpleSwingApplication{
  
  //muuttujat
  var isRunning: Boolean = true
  val width: Int = 1280
  val height: Int = 720
  //var laskuri: Int = 0
  
  //pääikkuna
  val PAWindow = new MainFrame
  PAWindow.title = "Project Asteroid"
  PAWindow.preferredSize = new Dimension(width, height)
  PAWindow.resizable = false
  
  def top = PAWindow
  
  val timer = new Timer();
  timer.schedule(new gameLoop(), 0, 1000/30);
  
}

private class gameLoop extends java.util.TimerTask {
  
  def run(): Unit = {
    Update.doGameUpdates();
    Update.render();
    
    if(!ProjectAsteroid.isRunning) {
      ProjectAsteroid.timer.cancel();
    }
  }
}

object Update {
  def doGameUpdates() = { //pelin logiikka
    PlayerShip.move()
  }
  def render() = { //renderöi pelin
    
  }
}









/**
  private val width: Int = 1280
  private val height: Int = 720
  
  
  
  def top = this.ProjectAsteroidWindow
  **/