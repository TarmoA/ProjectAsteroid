package Project
/**
 * Ylläpitää hichScore tilastoa teksti tiedostossa.
 */
class HighScore {
  private val file = "highscore.txt" //tiedosto johon tulokset tallennetaan ja jota myös käsitellään.
  
  /**
   * write metodia käytetään uuden tuloksen lisäämiseen tiedostoon
   * Vaikeustasojen myötä voi olla että ne lisätään huomioitavaksi tilastoon.
   */
  def write(name: String, points: Int) = {
    
  }
  /**
   * read palauttaa HighScoret suuruusjärjestyksessä (suurin -> pienin) tiedostosta.
   */
  def read(): Array[String] = {
    val testi = Array[String]("ekanimi|ekanpisteet", "tokanimi|tokanpisteet", "kolmasnimi|kolmannenpisteet")
    putInOrder(testi)
  }
  /**
   * read metodin apumetodi jolla järjestetään tulokset pienimmästä suurimpaan.
   */
  private def putInOrder(scores: Array[String]): Array[String] = {
    
    scores
  }
  
}