package Project

object Difficulty {
  
  var definition: String = ""
  
  def easy(): Int = {
    definition = "easy"
    1
  }
  def normal(): Int = {
    definition = "normal"
    2
  }
  def hard(): Int = {
    definition = "hard"
    3
  }
}