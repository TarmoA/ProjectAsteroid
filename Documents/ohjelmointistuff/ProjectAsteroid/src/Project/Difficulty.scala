package Project

/**
 * Asettaa pelin vaikeustason halutulle tasolle
 * testaus tällä hetkellä, paluattaa vain Int arvon
 */
object Difficulty {
  def easy() = {
    1
  }
  def normal() = {
    2
  }
  def hard(): Int = {
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