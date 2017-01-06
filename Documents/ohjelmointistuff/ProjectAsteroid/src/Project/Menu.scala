package Project

import scalafx.application.JFXApp
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.paint.Color._
/**import scalafx.Includes._
*import scalafx.scene.control.{Label, MenuBar, Menu, MenuItem, CheckMenuItem, RadioMenuItem, ToggleGroup}
*import scalafx.event.ActionEvent
*/


object Testi extends JFXApp{
  stage = new JFXApp.PrimaryStage {
    title = "testMenu"
    scene = new Scene(600, 300) {
      
      fill = BLUE
      
      val startButton = new Button("Start")
      startButton.layoutX = 100; startButton.layoutY = 0
      
      val soundButton = new Button("Sound")
      soundButton.layoutX = 100; soundButton.layoutY = 50
      
      val highScoreButton = new Button("HighScores")
      highScoreButton.layoutX = 100; highScoreButton.layoutY = 100
      
      content = List(soundButton, highScoreButton, startButton)
      
    }
  }
}