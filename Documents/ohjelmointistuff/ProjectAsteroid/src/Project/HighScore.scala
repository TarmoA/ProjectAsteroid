package Project

import scala.io.Source
import java.io.PrintWriter
import scalafx.collections.ObservableBuffer

//saves players' results as HighScore-class
case class HighScore(hardness: Int, val name: String, val score: Int)

//reads and modifies highscore file's data and passes it elsewhere.
object HighScoreFile {
  //filename of the file where all highscores are saved
  private val fileName = "highscore.txt" //tiedosto johon tulokset tallennetaan ja jota myös käsitellään.
  
  //Buffers where HighScore classes are stored and which HighScoreMenu reads
  var easy = ObservableBuffer[HighScore] ()
  var normal = ObservableBuffer[HighScore] ()
  var hard = ObservableBuffer[HighScore] ()
  
  //overrides old file and writes whats on buffers there.
  def write() = {
    val file = new PrintWriter(fileName)
    try {
      putInOrder()
      println("kirjoitetaaan")
      file.println("//Don't change this file, hardnessLevel|name|score")
      for (i <- this.easy) {
        file.println(1 + "|" + i.name + "|" + i.score)
      }
      for (i <- this.normal) {
        file.println(2 + "|" + i.name + "|" + i.score)
      }
      for (i <- this.hard) {
        file.println(3 + "|" + i.name + "|" + i.score)
      }
    }
    finally {
      file.close()
    }
  }
  
  //method which reads file and creates and add all highscore classes to buffers
  def read() = {
    val file = Source.fromFile(fileName)
    try {
      val fileVector = file.getLines.toVector
      
      resetBuffer()
      
      for (i <- fileVector.tail) {
        val array = i.split('|')
        println(array(0) +" "+ array(1) +" "+ array(2))
        if (array(0) == "1") this.easy += HighScore(1, array(1), array(2).toInt)
        if (array(0) == "2") this.normal += HighScore(2, array(1), array(2).toInt)
        if (array(0) == "3") this.hard += HighScore(3, array(1), array(2).toInt)
      }
      putInOrder()
    }
    catch {
      //incase of file error, resets file and all highscore buffers
      case ioException: Throwable => reset(); println("FileError, file resetted")
    }
    finally {
      file.close()
    }
    
  }
  
  //Private method which puts all buffers in order from largest score to smallest score
  private def putInOrder() = {
    this.easy = this.easy.sortBy(_.score)
    this.easy = this.easy.reverse
    
    this.normal = this.normal.sortBy(_.score)
    this.normal = this.normal.reverse
    
    this.hard = this.hard.sortBy(_.score)
    this.hard = this.hard.reverse
  }
  
  //resets buffers and file
  def reset() {
    resetBuffer()
    val file = new PrintWriter(fileName)
    try {
      file.println("//Don't change this file, hardnessLevel|name|score")
    }
    finally {
      file.close()
    }
  }
  
  //resets only buffers
  private def resetBuffer() {
    this.easy.clear()
    this.normal.clear()
    this.hard.clear()
  }
  
  //saves player's result
  def saveHighScore(hardness: Int, name: String, score: Int) = {
    if (hardness == 1) this.easy += HighScore(1, name, score)
    if (hardness == 2) this.normal += HighScore(2, name, score)
    if (hardness == 3) this.hard += HighScore(3, name, score)
    write()
  }
}