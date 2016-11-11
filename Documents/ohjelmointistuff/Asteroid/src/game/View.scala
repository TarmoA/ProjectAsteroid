package game

import scala.swing._
import scala.swing.event._
import java.awt.Color

object View extends SimpleSwingApplication {
  val width = 20
  val height = 10
  val cellSize = 50
  var world: Array[Array[Spot]] = Array.fill(width, height)(Floor)
  val r = scala.util.Random
  val player = new Player(1, 1)

  val canvas = new GridPanel(rows0 = height, cols0 = width) {
    
  preferredSize = new Dimension(width * cellSize, height * cellSize)

    override def paintComponent(g: Graphics2D) {
      for (i <- 0 until width) {
        for (k <- 0 until height) { // Loop through the world grid
          world(i)(k) match {       // Match what is found in every position
            case Wall => {          // If a wall is there, change color to black and paint a black tile representing a wall
              g.setColor(Color.BLACK)
              g.fillRect(i * cellSize, k * cellSize, cellSize, cellSize)
            }
            case Floor => {         // If a floor is there, change color to cyan and paint a cyan tile representing floor
              g.setColor(Color.CYAN)
              g.fillRect(i * cellSize, k * cellSize, cellSize, cellSize)
            }
          }
        }
      }
      g.setColor(Color.ORANGE)      // Set color for the player to be drawn
      g.fillOval(player.x * 50, player.y * 50, 50, 50) // Draw player to its location
      g.setColor(Color.GRAY)
    }
  }

  def top = new MainFrame {
    title = "Game"
    preferredSize = new Dimension(width * cellSize, height * cellSize)

    contents = canvas

    //tekee seinät
//    for (i <- 0 until 20) {
//      world(i)(0) = Wall
//      world(19)(Math.min(i, 9)) = Wall
//      world(0)(Math.min(i, 9)) = Wall
//      world(i)(9) = Wall
//      world(r.nextInt(20))(r.nextInt(10)) = Wall
//      world(r.nextInt(20))(r.nextInt(10)) = Wall
//    }

    listenTo(canvas.mouse.clicks)
    reactions += {
      case MouseClicked(canvas, point, _, _, _) => {
        if (point.x <= width * cellSize && point.y <= height * cellSize) {
          if (player.canMoveTo(point.x / cellSize, point.y / cellSize) &&
            world(point.x / cellSize)(point.y / cellSize) != Wall) player.move(point.x / cellSize, point.y / cellSize)
            repaint()
        }
      }
    }
  }
}