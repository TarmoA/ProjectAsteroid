package Project

object Difficulty {
  
  var definition: String = ""
  var factor: Int = 2
  
  def easy(): Int = {
    definition = "easy"
    factor = 1
    1
  }
  def normal(): Int = {
    definition = "normal"
    factor = 2
    2
  }
  def hard(): Int = {
    definition = "hard"
    factor = 3
    3
  }
}