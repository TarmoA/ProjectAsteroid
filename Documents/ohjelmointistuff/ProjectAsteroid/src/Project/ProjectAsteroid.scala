package Project

import scalafx.application.JFXApp
import scalafx.stage.WindowEvent
import scalafx.application.JFXApp.PrimaryStage
import scalafx.stage.Stage
import scalafx.scene.media._

object ProjectAsteroid extends JFXApp {
  //pistetään tänne kaikki muuttujat niin ei tarvitse sitten metsästää niitä myöhemmin.
  var isSoundOn: Boolean = true
  val menuSound = new AudioClip("file:audio/menu.wav")
  val shootSound = new AudioClip("file:audio/bullet1.wav")
  val explSound = new AudioClip("file:audio/explosion.wav")
  
  var GameArea: GameArea = _
  
  stage = new JFXApp.PrimaryStage {
    resizable = false
    centerOnScreen()
    title = "ProjectAsteroid"
    
    scene = Menu
    
  }
  
}

/*TODO:
 * jos avaa jonkun pop-up ikkunan ja sulkee pääikkunan niin ohjelma ei sulkeudu, fiksaa niin että sulkeutuu
 * Asteroidien läpinäkyvyys pois, tähdet näkyy läpi välillä
 * tyhjeneekö bufferit oikein kun tuhoutuu ja menee pois alueelta?
 * asteroidien sivuttais liike
 * highscore osio
 * seed arvo kun generoi asteroideja randomiksi, tulee ihan samalla tavalla aina nytten.
 */






