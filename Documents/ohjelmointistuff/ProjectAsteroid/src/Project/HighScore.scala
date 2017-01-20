package Project

import scala.io.Source
import java.io.PrintWriter
import scalafx.collections.ObservableBuffer

//Saves players' results as HighScore-class
case class HighScore(hardness: Int, val name: String, val score: Int)

//Reads and modifies HighScore file's data and passes it elsewhere.
object HighScoreFile {
  //Filename of the file where all HighScores are saved
  private val fileName = "highscore.txt"
  
  //Buffers where HighScore classes are stored and which HighScoreMenu reads
  var easy = ObservableBuffer[HighScore] ()
  var normal = ObservableBuffer[HighScore] ()
  var hard = ObservableBuffer[HighScore] ()
  
  //Overrides old file and writes what's on buffers there.
  private def write() = {
    val file = new PrintWriter(fileName)
    try {
      putInOrder()
      file.println("//Don't change this file, hardnessLevel|name|score")
      //goes through all buffers and prints them to the file
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
  
  //Method which reads file and creates and add all HighScore classes to buffers
  def read() = {
    val file = Source.fromFile(fileName)
    try {
      val fileVector = file.getLines.toVector
      
      resetBuffer()
      //Goes through every line except the first line in the file and extracts data from lines and creates HighScore class and adds it to correct buffer
      for (i <- fileVector.tail) {
        val array = i.split('|')
        if (array(0) == Difficulty.easyValue.toString()) this.easy += HighScore(1, array(1), array(2).toInt)
        if (array(0) == Difficulty.normalValue.toString()) this.normal += HighScore(2, array(1), array(2).toInt)
        if (array(0) == Difficulty.hardValue.toString()) this.hard += HighScore(3, array(1), array(2).toInt)
      }
      putInOrder()
    }
    catch {
      //In case of file error, resets file and all HighScore buffers
      case ioException: Throwable => reset(); println("FileError, file reset")
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
  
  //Resets buffers and file
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
  
  //Resets only buffers
  private def resetBuffer() {
    this.easy.clear()
    this.normal.clear()
    this.hard.clear()
  }
  
  //Method is used to add new highscore to buffers and file
  def saveHighScore(hardness: Int, name: String, score: Int) = {
    if (hardness == Difficulty.easyValue) this.easy += HighScore(1, name, score)
    if (hardness == Difficulty.normalValue) this.normal += HighScore(2, name, score)
    if (hardness == Difficulty.hardValue) this.hard += HighScore(3, name, score)
    write()
  }
}