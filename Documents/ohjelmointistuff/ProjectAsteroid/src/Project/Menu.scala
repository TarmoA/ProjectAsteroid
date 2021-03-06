package Project

import scalafx.Includes._
import scalafx.scene.Scene
import scalafx.scene.text.Font
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.paint.Color.{BLACK, WHITE}
import scalafx.event.ActionEvent
import scalafx.geometry.Pos
import scalafx.collections.ObservableBuffer
import scalafx.beans.property.{StringProperty, ObjectProperty}

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
  
  //Labels:
  val help = new Label("Shoot: X\nToggle Autofire: A\nPause: P\nMove: Arrow keys") {
    font = new Font("Arial", 15)
    textFill =WHITE
    layoutX = 50
    layoutY = 50
  }
  
  //content:

  val soundContent = new HBox(20) {
    content = List(sound, soundLabel)
    alignment = (Pos.CENTER_LEFT)
    minWidth = (200); maxWidth = (200); prefWidth = (200)
  }
  
  val menuContent = new VBox(20) {
    content = List(name, start, soundContent, hiScore, exit, help)
    alignment = Pos.CENTER
    style = "-fx-background-color: black"
  }
  
  root = menuContent //set scene content
  
  //events:
  start.onAction = (e: ActionEvent) => {
    new DifficultyMenu() //opens new menu where player chooses level of difficulty or returns to mainmenu
    Sound.menuSound()
  }
  
  sound.onAction = (e: ActionEvent) => {
    if (Sound.isSoundOn) {
      Sound.isSoundOn = false
      soundString = "Off"
      soundLabel.setText("Sound: " + soundString)
    }
    else {
      Sound.isSoundOn = true
      soundString = "On"
      soundLabel.setText("Sound: " + soundString)
    }
    Sound.menuSound()
  }
  
  hiScore.onAction = (e: ActionEvent) => {
    //TODO:
    HighScoreFile.read()
    new HighScoreMenu()
    Sound.menuSound()
  }
  exit.onAction = (e: ActionEvent) => {
    Sound.menuSound()
    sys.exit(0)
  }
}

class HighScoreMenu() {
  //Buttons:
  val back = new Button("Return") {
    style = "-fx-background-color: white"
    textFill = BLACK
    minWidth = (200); maxWidth = (200); prefWidth = (200)
  }
  val reset = new Button("Reset All") {
    style = "-fx-background-color: white"
    textFill = BLACK
    minWidth = (200); maxWidth = (200); prefWidth = (200)
  }
  
  //Labels:
  val easyLabel = new Label("Easy:") {
    textFill =WHITE
    font = new Font("Arial", 20)
  }
  val normalLabel = new Label("Normal:") {
    textFill =WHITE
    font = new Font("Arial", 20)
  }
  val hardLabel = new Label("Hard:") {
    textFill =WHITE
    font = new Font("Arial", 20)
  }
  
  //TableViews:
  //Easy:
  val tableEasy = new TableView(HighScoreFile.easy) {
    minHeight = 146; prefHeight = 146; maxHeight = 146
    prefWidth = 525; maxWidth = 525
    alignmentInParent = Pos.CENTER
    autosize()
  }
  val indexCEasy = new TableColumn[HighScore, Int] ("#") {
    sortable = false
    cellValueFactory = cdf => ObjectProperty(HighScoreFile.easy.indexOf(cdf.getValue) + 1)
    prefWidth = 25
  }
  val nameCEasy = new TableColumn[HighScore, String]("Name") {
    cellValueFactory = cdf => StringProperty(cdf.value.name)
    sortable = false
    prefWidth = 249
  }
  val scoreCEasy = new TableColumn[HighScore, Int]("Score") {
    cellValueFactory = cdf => ObjectProperty(cdf.value.score)
    sortable = false
    prefWidth = 249
  }
  tableEasy.columns ++= List(indexCEasy, nameCEasy, scoreCEasy)
  
  //Normal:
  val tableNormal = new TableView(HighScoreFile.normal) {
    minHeight = 146; prefHeight = 146; maxHeight = 146
    prefWidth = 525; maxWidth = 525
    alignmentInParent = Pos.CENTER
    autosize()
  }
  val indexCNormal = new TableColumn[HighScore, Int] ("#") {
    sortable = false
    cellValueFactory = cdf => ObjectProperty(HighScoreFile.normal.indexOf(cdf.getValue) + 1)
    prefWidth = 25
  }
  val nameCNormal = new TableColumn[HighScore, String]("Name") {
    cellValueFactory = cdf => StringProperty(cdf.value.name)
    sortable = false
    prefWidth = 249
  }
  val scoreCNormal = new TableColumn[HighScore, Int]("Score") {
    cellValueFactory = cdf => ObjectProperty(cdf.value.score)
    sortable = false
    prefWidth = 249
  }
  tableNormal.columns ++= List(indexCNormal, nameCNormal, scoreCNormal)
  
  //Hard:
  val tableHard = new TableView(HighScoreFile.hard) {
    minHeight = 146; prefHeight = 146; maxHeight = 146
    prefWidth = 525; maxWidth = 525
    alignmentInParent = Pos.CENTER
    autosize()
  }
  val indexCHard = new TableColumn[HighScore, Int] ("#") {
    sortable = false
    cellValueFactory = cdf => ObjectProperty(HighScoreFile.hard.indexOf(cdf.getValue) + 1)
    prefWidth = 25
  }
  val nameCHard = new TableColumn[HighScore, String]("Name") {
    cellValueFactory = cdf => StringProperty(cdf.value.name)
    sortable = false
    prefWidth = 249
  }
  val scoreCHard = new TableColumn[HighScore, Int]("Score") {
    cellValueFactory = cdf => ObjectProperty(cdf.value.score)
    sortable = false
    prefWidth = 249
  }
  tableHard.columns ++= List(indexCHard, nameCHard, scoreCHard)
  
  //Content:
  val buttonsContent = new HBox(50) {
    content = List(back, reset)
    alignment = (Pos.CENTER)
    minWidth = (450); maxWidth = (450); prefWidth = (450)
  }
  val HiScMenuContent = new VBox(20) {
    content = List(easyLabel, tableEasy, normalLabel, tableNormal, hardLabel, tableHard, buttonsContent)
    alignment = Pos.CENTER
    style = "-fx-background-color: black"
  }
  Menu.root = HiScMenuContent
  
  //Events:
  back.onAction = (e: ActionEvent) => {
    Menu.root = Menu.menuContent
    Sound.menuSound()
  }
  reset.onAction = (e: ActionEvent) => {
    HighScoreFile.reset()
    Sound.menuSound()
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
    ProjectAsteroid.GameArea = new GameArea(Difficulty.easy())
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
    Sound.menuSound()
  }
  normal.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(Difficulty.normal())
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
    Sound.menuSound()
  }
  hard.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(Difficulty.hard())
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Menu.root = Menu.menuContent
    Sound.menuSound()
  }
  backMenu.onAction = (e: ActionEvent) => {
    Menu.root = Menu.menuContent
    Sound.menuSound()
  }
}

/**
 * PauseMenu activates when p is pressed during game, and pauses game and opens pausemenu which gives player options to do
 **/
class PauseMenu() {
  ProjectAsteroid.GameArea.GameTimer.stop
  Sound.menuSound()
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
    Sound.menuSound()
  }
  soundButton.onAction = (e: ActionEvent) => {
    if (Sound.isSoundOn) {
      Sound.isSoundOn = false
      Menu.soundString = "Off"
      soundLabel.setText("Sound: " + Menu.soundString)
    }
    else {
      Sound.isSoundOn = true
      Menu.soundString = "On"
      soundLabel.setText("Sound: " + Menu.soundString)
    }
    Sound.menuSound()
  }
  returnMenu.onAction = (e: ActionEvent) => {
    ProjectAsteroid.stage.scene = Menu
    ProjectAsteroid.stage.centerOnScreen()
    Sound.menuSound()
  }
  exit.onAction = (e: ActionEvent) => {
    Sound.menuSound()
    sys.exit(0)
  }
}

/*
 * When player dies DeathMenu is opened, different options are available to player
 */
class DeathMenu() {
  //Buttons:
  val saveMenu = new Button("Save score") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val restart = new Button("Restart") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val returnMenu1 = new Button("Retun Menu") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val returnMenu2 = new Button("Retun Menu") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val exit1 = new Button("Exit") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val exit2 = new Button("Exit") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  val saveScore = new Button("Save") {
    style = "-fx-background-color: white"
    textFill = BLACK
    maxWidth = 200
  }
  
  //Label:
  val deathLabel = new Label("Game over\nYour score: " + ProjectAsteroid.GameArea.score) {
    textFill = WHITE
    font = new Font("Arial", 20)
    style = "-fx-background-color: black"
  }
  val nameLabel = new Label("Your name:") {
    alignment = Pos.CENTER
    textFill = WHITE
    font = new Font("Arial", 20)
    style = "-fx-background-color: black"
  }
  
  //TextField:
  val textField = new TextField() {
    alignment = Pos.CENTER
  }
  
  //Content:
  //Deathmenu content:
  val deathMenuContent = new VBox(20) {
    alignment = Pos.CENTER
    content = List(deathLabel, saveMenu, restart, returnMenu1, exit1)
    minWidth = (200); maxWidth = (200); prefWidth = (200)
    layoutX = ProjectAsteroid.GameArea.width.toDouble / 2 - 100
    layoutY = ProjectAsteroid.GameArea.height.toDouble / 2 - 115
  }
  //deathmenu's scoremenu content:
  val nameAreaContent= new HBox(20) {
    content = List(nameLabel, textField)
  }
  val saveHighScoreContent = new VBox(20) {
    content = List(nameAreaContent, saveScore, returnMenu2, exit2)
    alignment = Pos.CENTER
    layoutX = ProjectAsteroid.GameArea.width.toDouble / 2 - 130
    layoutY = ProjectAsteroid.GameArea.height.toDouble / 2 - 80//TODO: layout
  }
  
  ProjectAsteroid.GameArea.content += deathMenuContent //adds DeathMenu to gamearea's content
  
  //Events:
  saveScore.onAction = (e: ActionEvent) => {
    //TODO: reads textfield and saves highscore
    if (!textField.getText().isEmpty()) {
      HighScoreFile.saveHighScore(ProjectAsteroid.GameArea.difficultyFactor,
          textField.getText(),
          ProjectAsteroid.GameArea.score)
      ProjectAsteroid.stage.scene = Menu
      ProjectAsteroid.stage.centerOnScreen()
    }
    Sound.menuSound()
  }
  saveMenu.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea.content -= deathMenuContent
    ProjectAsteroid.GameArea.content += saveHighScoreContent
    Sound.menuSound()
    
  }
  restart.onAction = (e: ActionEvent) => {
    ProjectAsteroid.GameArea = new GameArea(ProjectAsteroid.GameArea.difficultyFactor)
    ProjectAsteroid.stage.scene = ProjectAsteroid.GameArea
    ProjectAsteroid.stage.centerOnScreen
    Sound.menuSound()
  }
  returnMenu1.onAction = (e: ActionEvent) => {
    ProjectAsteroid.stage.scene = Menu
    ProjectAsteroid.stage.centerOnScreen()
    Sound.menuSound()
  }
  returnMenu2.onAction = (e: ActionEvent) => {
    ProjectAsteroid.stage.scene = Menu
    ProjectAsteroid.stage.centerOnScreen()
    Sound.menuSound()
  }
  exit1.onAction = (e: ActionEvent) => {
    Sound.menuSound()
    sys.exit(0)
  }
  exit2.onAction = (e: ActionEvent) => {
    Sound.menuSound()
    sys.exit(0)
  }
}