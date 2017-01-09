package Project
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene._
import scalafx.scene.paint.Color._
import scalafx.scene.input._
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode._
import scalafx.event.ActionEvent
import scalafx.scene.control._
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scala.util.Random
import scalafx.scene.text._
import scalafx.scene.layout._
import scalafx.stage._
import scalafx.geometry.Pos
import scalafx.stage.Modality
import scalafx.geometry.Insets

class GameArea(isSoundOn: Boolean) extends Scene(1280, 720) {
  val player = new PlayerShip(this)
  content = player
  var enemies = Buffer[EnemyShip]()
  var playerBullets = Buffer[PlayerBullet]()
  var enemyBullets = Buffer[EnemyBullet]()
  var stars = Buffer[Star]()
  var score = 0
  
  fill = BLACK //asettaa taustan värin mustaksi
  
  
  val scoreText = new Label{
    font = new Font("Arial", 15)
    textFill = (RED)
    text = "Score: " + score
  }
  
  val lifeText = new Label{
    font = new Font("Arial", 15)
    textFill = (RED)
    text = "Life: " + player.health
  }
  val textBox = new VBox {
    spacing = 2
    content = List(scoreText,lifeText)
  }
  content += textBox
  
  def checkCollisions = {
   // enemy-player collision
    val enemiesCollidingWithPlayer = enemies.filter(_.collidesWith(player))
    if (!enemiesCollidingWithPlayer.isEmpty) {
      player.damage(1)
      lifeText.text = "Life: " + player.health
      enemiesCollidingWithPlayer.foreach((enemy: SpaceShip) => {
        enemy.destroy
        enemies.remove(enemies.indexOf(enemy))
      })
    }
    //bullet-enemy collision
    var bulletsToDestroy = Buffer[Bullet]()
    enemies.foreach((enemy: SpaceShip) => {
      val collidingBullets = playerBullets.filter(_.collidesWith(enemy))
      if (!collidingBullets.isEmpty) {
        collidingBullets.foreach((bullet: PlayerBullet) => {
          if (content.contains(bullet)) {
            bullet.destroy 
            collidingBullets -= bullet
            bulletsToDestroy += bullet// playerBullets.remove(playerBullets.indexOf(bullet))
          }
        })
        enemy.damage(1)
      }
    })
    bulletsToDestroy.foreach((bullet: Bullet) => playerBullets.remove(playerBullets.indexOf(bullet)))
    
    val deadEnemies = enemies.filter(!_.isAlive)
    enemies.foreach((a: SpaceShip) => {
      if (deadEnemies.contains(a)){
        enemies.remove(enemies.indexOf(a))
        score += 1
      }
    })
  }
  
  
  def checkForActions(delta: Double, action: String): Boolean = {
  
    if (action == "move") {
    
    if (Keys.pressed("right")) player.move("right", delta)
    if (Keys.pressed("left")) player.move("left", delta)
    if (Keys.pressed("down")) player.move("down", delta)
    if (Keys.pressed("up")) player.move("up", delta)
    true
    }
    
    if (action == "shoot") {
    if (Keys.pressed("shoot")) {
        var bullet = new PlayerBullet(player.x.value, player.y.value)
        content += bullet
        playerBullets += bullet
        true
        } else false
    } else false
  }
  
  
  object Keys {
    val pressed = Map[String,Boolean]( "right" -> false, "left" -> false, "up" -> false, "down" -> false, "shoot" -> false)
                            
    onKeyPressed = (e: KeyEvent) => { 
      if (e.code == RIGHT) pressed("right") = true
      if (e.code == LEFT)  pressed("left") = true
      if (e.code == DOWN)  pressed("down") = true
      if (e.code == UP)    pressed("up") = true
      if (e.code == X)     pressed("shoot") = true
      if (e.code == ESCAPE) sys.exit(0)  // sulkee koko pelin
      if (e.code == P)     pause()  // avaa pausemenun, ei toimi kunnolla

     // if (e.code.isDigitKey) player.speed = e.code.name.toInt
      }
    onKeyReleased = (e: KeyEvent) => {
      if (e.code == RIGHT) pressed("right") = false
      if (e.code == LEFT)  pressed("left") = false
      if (e.code == DOWN)  pressed("down") = false
      if (e.code == UP)    pressed("up") = false
      if (e.code == X)     pressed("shoot") = false
    }
  }
  
  def pause() = {
    Keys.pressed.transform((a:String,b:Boolean) => false)
    new PauseMenu
  }
  
  object EnemySpawner {
    val random = new Random(100)
    
    def spawn(enemyType: String) = {
      if (enemyType == "asteroid") {
        val enemy = new Asteroid(width.value.toInt, random.nextInt(height.value.toInt))  // Tässä parametreissä oli alunperin myös - 50, jotta asteroidit eivät spawnaisi kuvan reunalla
        content += enemy
        enemies += enemy
      println("vihollinen")
      }
      if (enemyType == "star") {
        val star = new Star(width.value.toInt, random.nextInt(height.value.toInt))
        content += star
        stars += star
        star.toBack
      }
      
      if (enemyType == "initStars") {
        for (n <- 1 to 1000) {
          val star = new Star(random.nextInt(width.value.toInt), random.nextInt(height.value.toInt))
          content += star
          stars += star
          star.toBack
        }
      }
      
    }
  }
  EnemySpawner.spawn("initStars")
  
  object GameTimer {
       // 1e9 = 1000000000 ns = 1 s
    val timePerShot = 0.25
    var oldTime: Long = 0L
    var lastShot: Long = 0L
    var lastAsteroid: Double = 3
    var scoreTime = 0.0
    val mainTimer = AnimationTimer(t =>{
      if (oldTime > 0) {
        val delta = (t - oldTime)/1e9
        val shotDelta = (t-lastShot)/1e9
        val asteroidDelta = (t-lastAsteroid)/1e9
        checkForActions(delta, "move")
        checkCollisions
        enemies.foreach(_.move(delta))
        playerBullets.foreach(_.move(delta))
        enemyBullets.foreach(_.move(delta))
        stars.foreach(_.move(delta))
        scoreTime += delta
        if (scoreTime >= 1) {
          score += 1 
          println("score: " + score)
          scoreTime = 0
        }
        // Spawnaa tähtiä
        EnemySpawner.spawn("star")
        
        // Spawnaa asteroideja vaikeusasteen mukaan
        spawnAsteroids(Difficulty.setDifficulty)
        
        def spawnAsteroids(difficulty: Int) = {
          val timePerAsteroid: Double = 5.0 / difficulty
          if (lastAsteroid <= 0) {              // Periaatteessa pitää väkisin kirjaa siitä ajasta, milloin seuraava asteroidi laitetaan liikkeelle
            EnemySpawner.spawn("asteroid")      // Seuraava asteroidi laitetaan liikkeelle, kun asteroidin asettama aika saavuttaa nollan
            lastAsteroid = timePerAsteroid
          }
          else lastAsteroid -= 0.1
        }
        
        if (lastShot == 0 || shotDelta >= timePerShot) {
        if(checkForActions(delta,"shoot")) lastShot = t
        
        // Tämän tässä on tarkoitus poistaa Buffereista kaikki kuvan ulkopuolelle siirtyneet asiat:
        // enemies, playerBullets, enemyBullets, stars
        removeOutOfBoundsObjects
        
        }
        
        scoreText.text = "Score: " + score
      }
      oldTime = t
    })
    
    def start = {
      mainTimer.start
    }
    def stop = {
      mainTimer.stop
    }
    
  }
  
  GameTimer.start
  
  
  // Tämän tässä on tarkoitus poistaa Buffereista kaikki kuvan ulkopuolelle siirtyneet asiat:
  // enemies, playerBullets, enemyBullets, stars
  def removeOutOfBoundsObjects: Unit = {
    
//        for (enemy <- enemies)
//          if ((enemy.x.toInt > scene.value.width.toInt * 1.2) ||  // Elementtien poistolle asetetut rajat ovat nähtävän alueen ulkopuolella, nähtävän alueen koon mukaan suhteutettuna
//              (enemy.x.toInt < -scene.value.width.toInt * 0.2) ||
//              (enemy.y.toInt > scene.value.height.toInt * 1.2) ||
//              (enemy.y.toInt < -scene.value.height.toInt * 0.2)) {
//            content -= enemy
//            enemies -= enemy
//          }
    
//        for (playerBullet <- playerBullets)                        // Tämä kohta jäänyt kesken, sitä voi jatkaa kun ylempi osuus toimii oikein
//          if ((playerBullet.x.toInt > scene.value.width.toInt) ||
//              (playerBullet.x.toInt < 0) ||
//              (playerBullet.y.toInt > scene.value.height.toInt) ||
//              (playerBullet.y.toInt < 0)) {
//            content -= playerBullet
//            playerBullets -= playerBullet
//          }
//            
//        for (enemyBullet <- enemyBullets)
//          if ((enemyBullet.x.toInt > scene.value.width.toInt) ||
//              (enemyBullet.x.toInt < 0) ||
//              (enemyBullet.y.toInt > scene.value.height.toInt) ||
//              (enemyBullet.y.toInt < 0)) {
//            content -= enemyBullet
//            enemyBullets -= enemyBullet
//          }
//        for (star <- stars)
//          if ((star.x.toInt > scene.value.width.toInt) ||
//              (star.x.toInt < 0) ||
//              (star.y.toInt > scene.value.height.toInt) ||
//              (star.y.toInt < 0)) {
//            content -= star
//            stars -= star
//          }
    //println("Detected threats: " + enemies.size)  // asteroidien tulkintaa varten
      }
}