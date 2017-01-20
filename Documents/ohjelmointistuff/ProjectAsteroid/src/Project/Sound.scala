package Project

import scalafx.scene.media.AudioClip

/**
 * Games Sounds are in this object and it has methods which play specific sound file if sound is on
 */
object Sound {
  var isSoundOn: Boolean = true
  //Sound Files
  val menuSoundFile = new AudioClip("file:audio/menu.wav")
  val shootSoundFile = new AudioClip("file:audio/bullet1.wav")
  val explosionSoundFile = new AudioClip("file:audio/explosion.wav")
  
  //Methods check if sound is on and only then play their sound file
  def menuSound() {
    if (isSoundOn) menuSoundFile.play
  }
  def shootSound() {
    if (isSoundOn) shootSoundFile.play
  }
  def explosionSound() {
    if (isSoundOn) explosionSoundFile.play
  }
}