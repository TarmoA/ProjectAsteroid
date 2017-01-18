package Project

/**
 * Store and modifies difficulty settings
 */
object Difficulty {
  var definition: String = ""
  val easyValue: Int = 1
  val normalValue: Int = 2
  val hardValue: Int = 3
  
  //methods used to set difficulty
  def easy(): Int = {
    definition = "easy"
    easyValue
  }
  def normal(): Int = {
    definition = "normal"
    normalValue
  }
  def hard(): Int = {
    definition = "hard"
    hardValue
  }
}