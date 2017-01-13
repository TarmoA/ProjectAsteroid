package Project

import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.text.Font
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.paint.Color.{BLACK, WHITE}
import scalafx.event.ActionEvent
import scalafx.geometry.Pos

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
      
  val hiScore = new Button("HighScore") {
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
    font = new Font("Arial", 15)
  }
  
  //content:
  val soundContent = new HBox(20) {
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
    ProjectAsteroid.menuSound()
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
    ProjectAsteroid.menuSound()
  }
  
  hiScore.onAction = (e: ActionEvent) => {
    //TODO:
    new HighScoreMenu()
    ProjectAsteroid.menuSound()
  }
  exit.onAction = (e: ActionEvent) => sys.exit(0)
}

class HighScoreMenu() {
  val back = new Button("Return") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val reset = new Button("Reset") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val test = new Button("Test") { //TODO: when done remove
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val buttonsContent = new HBox(20) {
    content = List(back, reset, test)
    alignment = (Pos.CENTER)
    minWidth = (200); maxWidth = (200); prefWidth = (200)
  }
  val HiScMenuContent = new VBox(20) {
    content = List(buttonsContent)
    alignment = Pos.CENTER
    style = "-fx-background-color: black"
  }
  Menu.root = HiScMenuContent
  
  //Events:
  back.onAction = (e: ActionEvent) => {
    Menu.root = Menu.menuContent
    ProjectAsteroid.menuSound()
  }
  reset.onAction = (e: ActionEvent) => {
    HighScore.reset
    ProjectAsteroid.menuSound()
  }
  test.onAction = (e: ActionEvent) => {
    println(HighScore.read())
  }
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
    font = new Font("Arial", 20)
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
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, 1)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
    ProjectAsteroid.menuSound()
  }
  normal.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, 2)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
    ProjectAsteroid.menuSound()
  }
  hard.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, 3)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
    ProjectAsteroid.menuSound()
  }
  impossible.onAction = (e: ActionEvent) => {
    //TODO: jos aikaa niin voi tehdä
    //ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn)
    //ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    //ProjectAsteroid.stage.centerOnScreen
    //Menu.root = Menu.menuContent
    //if (ProjectAsteroid.isSoundOn) ProjectAsteroid.menuSound.play
  }
  backMenu.onAction = (e: ActionEvent) => {
    Menu.root = Menu.menuContent
    ProjectAsteroid.menuSound()
  }
}

/*
 * PauseMenu activates when p is pressed during game, and pauses game and opens pausemenu which gives player options to do
 */
class PauseMenu() {
  ProjectAsteroid.GameArea.GameTimer.stop
  ProjectAsteroid.menuSound()
  //buttons:
  val continue = new Button("Continue") {
    style = "-fx-background-color: white"
    textFill = BLACK
    prefWidth = 200
  }
  val soundButton = new Button("Sound") {
    style = "-fx-background-color: white"
    textFill = BLACK
    prefWidth = 100
  }
  val returnMenu = new Button("Return Menu") {
    style = "-fx-background-color: white"
    textFill = BLACK
    prefWidth = 200
  }
  
  val exit = new Button("Exit") {
    style = "-fx-background-color: white"
    textFill = BLACK
    prefWidth = 200
  }
  
  //Labels:
  val pauseLabel = new Label("Pause Menu") {
    textFill = WHITE
    font = new Font("Arial", 20)
    style = "-fx-background-color: black"
  }
  
  val soundLabel = new Label("Sound: " + Menu.soundString) {
    textFill = WHITE
    style = "-fx-background-color: black"
    font = new Font("Arial", 15)
  }
  
  //Content:
  val soundContent = new HBox(20) {
    content = List(soundButton, soundLabel)
    alignment = Pos.CENTER_LEFT
    minWidth = 200; prefWidth = 200; maxWidth = 200
  }
  val pauseMenuContent = new VBox(20) {
    alignment = Pos.CENTER
    content = List(pauseLabel, continue, soundContent, returnMenu, exit)
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
    ProjectAsteroid.GameArea.paused = false
    ProjectAsteroid.menuSound()
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
    ProjectAsteroid.menuSound()
  }
  returnMenu.onAction = (e: ActionEvent) => {
    ProjectAsteroid.stage.scene = Menu
    ProjectAsteroid.stage.centerOnScreen()
    ProjectAsteroid.menuSound()
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
    font = new Font("Arial", 20)
    style = "-fx-background-color: black"
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
    ProjectAsteroid.menuSound()
  }
  restart.onAction = (e: ActionEvent) => {
    //TODO: varmista että toimii, lähinnäs saako haettua edellisen gamearena difficulty arvon
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.isSoundOn, ProjectAsteroid.GameArea.difficulty)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    ProjectAsteroid.menuSound()
  }
  returnMenu.onAction = (e: ActionEvent) => {
    ProjectAsteroid.stage.scene = Menu
    ProjectAsteroid.stage.centerOnScreen()
    ProjectAsteroid.menuSound()
  }
  
  exit.onAction = (e: ActionEvent) => sys.exit(0) //TODO: riittääkö että on palaa päämenuun nappi vai tarviiko tämänkin?
}