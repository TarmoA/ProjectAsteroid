package game

class Player(var x: Int, var y: Int) {
  def move(newX: Int, newY: Int) = {
    x = newX
    y = newY
  }

  def canMoveTo(xPos: Int, yPos: Int) = {
    Math.abs(xPos - x) <= 1 && Math.abs(yPos - y) <= 1
  }

}


