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

/*
 * Mainmenu, which is opened at the beginning and at the end if player hasn't closed the program
 */
object Menu extends Scene(1280, 720) {
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
    textFill =WHITE
  }
  val soundLabel = new Label("Sound: " + soundString) {
    textFill =WHITE
  }
  
  //content:
  val soundContent = new HBox(22) {
    content = List(sound, soundLabel)
    alignment = (Pos.CENTER_LEFT)
    minWidth = (200); maxWidth = (200); prefWidth = (200)
  }
  
  val menuContent = new VBox(20) {
    content = List(name, start, soundContent, hiScore, exit)
    alignment = Pos.CENTER
    style = "-fx-background-color: black"
  }
  
  root = menuContent //set scene content
  
  //events:
  start.onAction = (e: ActionEvent) => {
    new DifficultyMenu() //opens new menu where player chooses level of difficulty or returns to mainmenu
  }
  
  sound.onAction = (e: ActionEvent) => {
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
  
  hiScore.onAction = (e: ActionEvent) => {
    //TODO:
  }
  exit.onAction = (e: ActionEvent) => sys.exit(0)
}

/*
 * DifficultyMenu opens when start button is pressed at the mainmenu and player has opportunity to choose
 * games's level of difficulty.
 */
class DifficultyMenu() {
  //Buttons:
  val easy = new Button("Easy") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val normal = new Button("Normal") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val hard = new Button("Hard") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val impossible = new Button("Impossible") {
    //TODO: jos jää aikaa niin tämä, muuten poista nämä ja action tälle
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val backMenu = new Button("Return") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  
  //Label:
  val hardnesLabel = new Label("Choose level of difficulty") {
    textFill = WHITE
  }
  
  //content:
  val difficultyContent = new VBox(20) {
    content = List(hardnesLabel, easy, normal, hard, backMenu)
    alignment = Pos.CENTER
    style = "-fx-background-color: black"
  }
  
  Menu.root = difficultyContent
  
  //Events:
  easy.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, 0)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
  }
  normal.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, 1)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
  }
  hard.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, 2)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
  }
  impossible.onAction = (e: ActionEvent) => {
    //TODO: jos aikaa niin voi tehdä
    //ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn)
    //ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    //ProjectAsteroid.stage.centerOnScreen
    //Menu.root = Menu.menuContent
  }
  backMenu.onAction = (e: ActionEvent) => {
    Menu.root = Menu.menuContent
  }
}

/*
 * PauseMenu activates when p is pressed during game, and pauses game and opens pausemenu which gives player options to do
 */
class PauseMenu() {
  ProjectAsteroid.GameArea.GameTimer.stop
  //buttons:
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
  
  //Labels:
  val soundLabel = new Label("Sound: " + Menu.soundString) {
    textFill = WHITE
  }
  
  //Content:
  val soundContent = new HBox(22) {
    content = List(soundButton, soundLabel)
    alignment = Pos.CENTER_LEFT
    minWidth = 200; prefWidth = 200; maxWidth = 200
  }
  val pauseMenuContent = new VBox(20) {
    alignment = Pos.CENTER
    content = List(continue, soundContent, exit)
    //style = "-fx-background-color: black"
    layoutX = ProjectAsteroid.GameArea.width.toDouble / 2 - 100
    layoutY = ProjectAsteroid.GameArea.height.toDouble / 2 - 100
  }
  
  ProjectAsteroid.GameArea.content += pauseMenuContent //adds pausemenu to gamearea's content
  
  //events:
  continue.onAction = (e: ActionEvent) => {
    if (ProjectAsteroid.GameArea.content.contains(pauseMenuContent)) ProjectAsteroid.GameArea.content -= pauseMenuContent
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
  exit.onAction = (e: ActionEvent) => sys.exit(0) //TODO: palaako päämenuun vai sulkeeko koko ohjelman?
}

/*
 * when player dies DeathMenu is opened, different options are available to player
 */
class DeathMenu() {
  //Buttons:
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
  
  //Label:
  val deathLabel = new Label("Mission failure\nYour score: " + ProjectAsteroid.GameArea.score) {
    textFill = WHITE
  }
  
  //Content:
  val deathMenuContent = new VBox(20) {
    alignment = Pos.CENTER
    content = List(deathLabel, saveScore, restart, returnMenu, exit)
    //style = "-fx-background-color: black"
    layoutX = ProjectAsteroid.GameArea.width.toDouble / 2 - 50
    layoutY = ProjectAsteroid.GameArea.height.toDouble / 2 - 125
  }
  
  ProjectAsteroid.GameArea.content += deathMenuContent //adds DeathMenu to gamearea's content
  
  //Events:
  saveScore.onAction = (e: ActionEvent) => {
    //TODO: asks name and saves highscore, then open mainmenu
  }
  restart.onAction = (e: ActionEvent) => {
    //TODO: varmista että toimii, lähinnäs saako haettua edellisen gamearena difficulty arvon
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, ProjectAsteroid.GameArea.difficulty)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
  }
  returnMenu.onAction = (e: ActionEvent) => {
    ProjectAsteroid.stage.scene = Menu
    ProjectAsteroid.stage.centerOnScreen()
  }
  
  exit.onAction = (e: ActionEvent) => sys.exit(0) //TODO: riittääkö että on palaa päämenuun nappi vai tarviiko tämänkin?
}