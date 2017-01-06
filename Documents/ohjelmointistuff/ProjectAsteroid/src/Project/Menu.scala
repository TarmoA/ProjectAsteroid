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
/*
object Testi extends JFXApp{
 vv
  
  stage = new JFXApp.PrimaryStage {
    title = "testMenu"
    scene = new Scene(600, 300) {
      fill = BLUE
      
      
      //Buttons:
      val start = new Button("Start")
      start.setStyle("-fx-background-color: white")
      start.textFill_=(BLACK)
      start.setMaxWidth(200)
      
      val sound = new Button("Sound")
      sound.setStyle("-fx-background-color: white")
      sound.textFill_=(BLACK)
      sound.setMinWidth(100)
      
      val hiScore = new Button("HighScore")
      hiScore.setStyle("-fx-background-color: white")
      hiScore.textFill_=(BLACK)
      hiScore.setMaxWidth(200)
      
      val exit = new Button("Exit")
      exit.setStyle("-fx-background-color: white")
      exit.textFill_=(BLACK)
      exit.setMaxWidth(200)
      
      //Labels:
      val name = new Label("Project Asteroid")
      name.font = new Font("Arial", 30)
      name.textFill_=(WHITE)
      
      val soundtext = new Label("Sound: " + soundString)
      soundtext.textFill_=(WHITE)
      
      
      //content:
      val soundContent = new HBox(22)
      soundContent.content = List(sound, soundtext)
      soundContent.setAlignment(Pos.CENTER_LEFT)
      //soundContent.fillHeight_=(false)
      soundContent.setMinWidth(200)
      soundContent.setMaxWidth(200)
      soundContent.setPrefWidth(200)
      
      
      
      val vbox = new VBox(20)
      vbox.content = List(name, start, soundContent, hiScore, exit)
      vbox.setAlignment(Pos.CENTER)
      vbox.setStyle("-fx-background-color: black")
      
      
      root = vbox
      
      
      //events:
      sound.onAction = (e:ActionEvent) => {
        if (isSoundOn) {
          isSoundOn = false
          soundString = "Off"
          soundtext.setText("Sound: " + soundString)
        }
        else {
          isSoundOn = true
          soundString = "On"
          soundtext.setText("Sound: " + soundString)
        }
      }
      
      exit.onAction = (e:ActionEvent) => sys.exit(0)
    }
  }
}*/
object Menu extends Scene(600, 300) {
  
  
   private var isSoundOn: Boolean = true
   private var soundString: String = "On"
      fill = BLUE
      
      
      //Buttons:
      val start = new Button("Start")
      start.setStyle("-fx-background-color: white")
      start.textFill_=(BLACK)
      start.setMaxWidth(200)
      
      val sound = new Button("Sound")
      sound.setStyle("-fx-background-color: white")
      sound.textFill_=(BLACK)
      sound.setMinWidth(100)
      
      val hiScore = new Button("HighScore")
      hiScore.setStyle("-fx-background-color: white")
      hiScore.textFill_=(BLACK)
      hiScore.setMaxWidth(200)
      
      val exit = new Button("Exit")
      exit.setStyle("-fx-background-color: white")
      exit.textFill_=(BLACK)
      exit.setMaxWidth(200)
      
      //Labels:
      val name = new Label("Project Asteroid")
      name.font = new Font("Arial", 30)
      name.textFill_=(WHITE)
      
      val soundtext = new Label("Sound: " + soundString)
      soundtext.textFill_=(WHITE)
      
      
      //content:
      val soundContent = new HBox(22)
      soundContent.content = List(sound, soundtext)
      soundContent.setAlignment(Pos.CENTER_LEFT)
      //soundContent.fillHeight_=(false)
      soundContent.setMinWidth(200)
      soundContent.setMaxWidth(200)
      soundContent.setPrefWidth(200)
      
      
      
      val vbox = new VBox(20)
      vbox.content = List(name, start, soundContent, hiScore, exit)
      vbox.setAlignment(Pos.CENTER)
      vbox.setStyle("-fx-background-color: black")
      
      
      root = vbox
      
      
      //events:
      sound.onAction = (e:ActionEvent) => {
        if (isSoundOn) {
          isSoundOn = false
          soundString = "Off"
          soundtext.setText("Sound: " + soundString)
        }
        else {
          isSoundOn = true
          soundString = "On"
          soundtext.setText("Sound: " + soundString)
        }
      }
      
      start.onAction = (e: ActionEvent) => {
        ProjectAsteroid.stage.scene = new GameArea(isSoundOn)
        ProjectAsteroid.stage.centerOnScreen
      }
      
      exit.onAction = (e:ActionEvent) => sys.exit(0)
    }