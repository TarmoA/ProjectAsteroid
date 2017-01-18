package Project

import scalafx.scene.media.AudioClip

object Sound {
  var isSoundOn: Boolean = true
  val menuSoundFile = new AudioClip("file:audio/menu.wav")
  val shootSoundFile = new AudioClip("file:audio/bullet1.wav")
  val explosionSoundFile = new AudioClip("file:audio/explosion.wav")
  
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