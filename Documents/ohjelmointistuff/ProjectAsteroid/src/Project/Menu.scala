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
import scalafx.stage.Modality
import scalafx.geometry.Insets
import scalafx.stage._

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
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
  }
  
  exit.onAction = (e:ActionEvent) => sys.exit(0)
}

class PauseMenu() {//pause valikko, toimii, mutta pystyy sulkemaan pääiikkunan ilman että ohjelma saammuu
  ProjectAsteroid.GameArea.GameTimer.stop
  val pauseMenuStage = new Stage() {
    initModality(Modality.WINDOW_MODAL)
    centerOnScreen()
    title = "PauseMenu"
    
    scene = new Scene(600, 300) { //scenen nimeä ei saa missään tapauaksessa muuttaa, hajottaa koko valikon jostain syystä
      val continue = new Button("Continue") {
        style = ("-fx-background-color: white")
        textFill = (BLACK)
        prefWidth = (200)
      }
      val soundButton = new Button("Sound") {
        style = "-fx-background-color: white"
        textFill = BLACK
        prefWidth = 100
      }
      val exit = new Button("Exit") {
        style = "-fx-background-color: white"
        textFill = BLACK
        prefWidth = 200
      }
      val soundLabel = new Label("Sound: " + Menu.soundString) {
        textFill = WHITE
      }
      val soundContent = new HBox(22) {
        content = List(soundButton, soundLabel)
        alignment = Pos.CENTER_LEFT
        minWidth = 200; prefWidth = 200; maxWidth = 200
      }
      val VBox = new VBox(20) {
        alignment = Pos.CENTER
        content = List(continue, soundContent, exit)
        style = "-fx-background-color: black"
      }
      root = VBox
      
      continue.onAction = (e: ActionEvent) => {
        close()
        ProjectAsteroid.GameArea.GameTimer.oldTime = 0L
        ProjectAsteroid.GameArea.GameTimer.start
      }
      soundButton.onAction = (e: ActionEvent) => {
        if (ProjectAsteroid.isSoundOn) {
          ProjectAsteroid.isSoundOn = false
          Menu.soundString = "Off"
          soundLabel.setText("Sound: " + Menu.soundString)
        }
        else {
          ProjectAsteroid.isSoundOn = true
          Menu.soundString = "On"
          soundLabel.setText("Sound: " + Menu.soundString)
        }
      }
      exit.onAction = (e: ActionEvent) => sys.exit(0)
      
    }
    show()
  }
  pauseMenuStage.setAlwaysOnTop(true)
}

class DeathMenu() {
  val deathMenuStage = new Stage() {
    initModality(Modality.WINDOW_MODAL)
    centerOnScreen()
    title = "Death"
    
    scene = new Scene(600, 300) {
      val deathLabel = new Label("You are dead\nYour score: " + ProjectAsteroid.GameArea.score) { //you are dead vai you died?
        textFill = WHITE
      }
      val saveScore = new Button("Save score") {
        style = "-fx-background-color: white"
        textFill = BLACK
        maxWidth = 200
      }
      val restart = new Button("Restart") {
        style = "-fx-background-color: white"
        textFill = BLACK
        maxWidth = 200
      }
      val returnMenu = new Button("Retun Menu") {
        style = "-fx-background-color: white"
        textFill = BLACK
        maxWidth = 200
      }
      val exit = new Button("Exit") {
        style = "-fx-background-color: white"
        textFill = BLACK
        maxWidth = 200
      }
      val deathMenuContent = new VBox(20) {
        alignment = Pos.CENTER
        content = List(deathLabel, saveScore, restart, returnMenu, exit)
        style = "-fx-background-color: black"
      }
      root = deathMenuContent
      
      saveScore.onAction = (e: ActionEvent) => {
        //TODO: asks name and saves highscore, then open mainmenu
      }
      restart.onAction = (e: ActionEvent) => {
        //TODO: reset game and restart
      }
      returnMenu.onAction = (e: ActionEvent) => {
        close()
        ProjectAsteroid.stage.scene = Menu
        ProjectAsteroid.stage.centerOnScreen()
      }
      
      exit.onAction = (e: ActionEvent) => sys.exit(0)
    }
  show()
  }
  deathMenuStage.setAlwaysOnTop(true)
}