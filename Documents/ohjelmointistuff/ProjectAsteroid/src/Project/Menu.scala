package Project
import scala.swing._
import javax.swing.ImageIcon

class Menu extends MainFrame {
  private var height = 640 
  private var width = 480
  
  this.preferredSize = new Dimension(height, width)
  
  var frame = new BoxPanel(Orientation.Vertical)
  frame.preferredSize = new Dimension(640, 480)
  frame.background = new Color(255,255,255)
  
  var startButton = new Button("Start")
  startButton.preferredSize = new Dimension(200,50)
  startButton.horizontalAlignment = Alignment.Center
  
  var square = new Label
  square.icon = new ImageIcon("square.png")
  square.visible = true
  
  val grid = new GridPanel(height,width)
  grid
  
  frame.contents += startButton
  frame.contents += new Button("Asd")
  frame.contents += new Button("Quit")
  frame.contents += square
  this.contents = frame
  
  
  
  
  
}