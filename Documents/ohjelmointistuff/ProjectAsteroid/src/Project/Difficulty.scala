package Project

/**
 * Asettaa pelin vaikeustason halutulle tasolle
 * testaus tällä hetkellä, paluattaa vain Int arvon
 */
object Difficulty {
  
  var gameDifficulty: String = ""
  
  def easy() = {
//    gameDifficulty = "easy"
    1
  }
  def normal() = {
//    gameDifficulty = "normal"
    2
  }
  def hard() = {
//    gameDifficulty = "hard"
    3
  }
  /**
   * käytetään valitsemaan haluttu vaikeus
   * alustava tapa, keksikää fiksumpi
   */
  
  def setDifficulty() = {
    this.normal() //testaus
  }
}