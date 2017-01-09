package Project

import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.text._
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.paint.Color._
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.stage.Modality
import scalafx.geometry.Insets

object Menu extends Scene(600, 300) {
  var soundString: String = "On"
  //Buttons:
  val start = new Button("Start") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val sound = new Button("Sound") {
    style = "-fx-background-color: white"
    textFill = BLACK
    minWidth = 100
  }
      
  val hiScore = new Button("HighScore, rikki") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val exit = new Button("Exit") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  //Labels:
  val name = new Label("Project Asteroid") {
    font = new Font("Arial", 30)
    textFill =(WHITE)
  }
  val soundLabel = new Label("Sound: " + soundString) {
    textFill =(WHITE)
  }
  
  //content:
  val soundContent = new HBox(22) {
    content = List(sound, soundLabel)
    alignment = (Pos.CENTER_LEFT)
    minWidth = (200); maxWidth = (200); prefWidth = (200)
  }
  
  
  val vbox = new VBox(20) {
    content = List(name, start, soundContent, hiScore, exit)
    alignment = Pos.CENTER
    style = "-fx-background-color: black"
  }
  
  root = vbox
  
  
  //events:
  sound.onAction = (e:ActionEvent) => {
    if (ProjectAsteroid.isSoundOn) {
      ProjectAsteroid.isSoundOn = false
      soundString = "Off"
      soundLabel.setText("Sound: " + soundString)
    }
    else {
      ProjectAsteroid.isSoundOn = true
      soundString = "On"
      soundLabel.setText("Sound: " + soundString)
    }
  }
  
  start.onAction = (e: ActionEvent) => {
    val gameArea = new GameArea(ProjectAsteroid.isSoundOn)
    ProjectAsteroid.stage.scene = gameArea
    ProjectAsteroid.stage.centerOnScreen
  }
  
  exit.onAction = (e:ActionEvent) => sys.exit(0)
}