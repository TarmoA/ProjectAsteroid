package Project

import scala.collection.mutable.Buffer
import scala.io.Source

/**
 * Ylläpitää hichScore tilastoa teksti tiedostossa.
 */
object HighScore {
  private val fileName = "highscore.txt" //tiedosto johon tulokset tallennetaan ja jota myös käsitellään.
  
  def write(name: String, points: Int) = {
    
  }
  
  def read() = {
    val hardness0 = Buffer[String]()
    val hardness1 = Buffer[String]()
    val hardness2 = Buffer[String]()
    val file = Source.fromFile(fileName)
    try {
      val fileVector = file.getLines.toVector
      var testi2: Int = 0
      for (i <- fileVector.tail) {
        if (i(0) == '0') hardness0 += i
        if (i(0) == '1') hardness1 += i
        if (i(0) == '2') hardness2 += i
      }
      //println()
      Buffer(hardness0, hardness1, hardness2)
      //fileVector.tail
    }
      finally {
        file.close()
    }
    
  }
  
  private def putInOrder(scores: Buffer[String]): Buffer[String] = {
    
    scores
  }
  
  def reset() {
    println("Reset")
  }
  
}