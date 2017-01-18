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
import scala.reflect._

class GameArea(isSoundOn: Boolean, val difficulty: Int) extends Scene(1280, 720) {
  val player = new PlayerShip(this)
  content = player
  var enemies = Buffer[EnemyShip]()
  var shootingEnemies = Buffer[ShootingEnemy]()
  var playerBullets = Buffer[PlayerBullet]()
  var enemyBullets = Buffer[EnemyBullet]()
  var stars = Buffer[Star]()
  var score = 0
  var speed=0
  val acceleration = 25
  
  fill = BLACK //asettaa taustan värin mustaksi
  
  
  val scoreText = new Label{
    font = new Font("Arial", 19)
    textFill = (WHITE)
    text = "Score: " + score
  }
  
  val lifeText = new Label{
    font = new Font("Arial", 20)
    textFill = (WHITE)
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
    // bullet-player collision
    val bulletsCollidingWithPlayer = enemyBullets.filter(_.collidesWith(player))
    if (!bulletsCollidingWithPlayer.isEmpty) {
      player.damage(1)
      lifeText.text = "Life: " + player.health
      bulletsCollidingWithPlayer.foreach((bullet: EnemyBullet) => {
        bullet.destroy
        enemyBullets.remove(enemyBullets.indexOf(bullet))
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
  
    if (action == "accelerate") {
    if (Keys.pressed("right")) player.accelerate("right", delta)
    if (Keys.pressed("left")) player.accelerate("left", delta)
    
    if (Keys.pressed("down")) player.accelerate("down", delta)
    if (Keys.pressed("up")) player.accelerate("up", delta)
    true
    }
    
    if (action == "shoot") {
      if (Keys.pressed("shoot")) {
          player.shoot
        } else false
    } 
    else false
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
      if (e.code == P)     pause()  // avaa pausemenun
      if (e.code == Z) EnemySpawner.spawn("AlienShip")

     // if (e.code.isDigitKey) player.speed = e.code.name.toInt
      }
    onKeyReleased = (e: KeyEvent) => {
      if (e.code == RIGHT) pressed("right") = false
      if (e.code == LEFT)  pressed("left") = false
      if (e.code == DOWN)  pressed("down") = false
      if (e.code == UP)    pressed("up") = false
      if (e.code == X)     pressed("shoot") = false
      // Toggle auto-fire
      if (e.code == A)     if (pressed("shoot") == false)
                             pressed("shoot") = true
                           else
                             pressed("shoot") = false
    }
  }
  
  var paused = false
  def pause() = {
    Keys.pressed.transform((a:String,b:Boolean) => false)
    if (!paused) new PauseMenu
    paused = true
  }
  
  object EnemySpawner {
    val random = new Random()
    val speedRandom = new Random()
    
    def spawn(enemyType: String) = {
      if (enemyType == "asteroid") {
        val enemy = new SmallAsteroid(width.value.toInt, (player.image.value.height.toDouble / 2) + random.nextInt(height.value.toInt) - player.image.value.height.toDouble, speedRandom.nextInt(100))  // Jotta asteroidit eivät spawnaa niin reunalle, ettei niitä pysty ampumaan, asteroidien spawnaukselle annetaan korkeussuunnassa rajat.
        content += enemy
        enemies += enemy
      }
      if (enemyType == "bigasteroid") {
        val enemy = new BigAsteroid(width.value.toInt, (player.image.value.height.toDouble / 2) + random.nextInt(height.value.toInt) - player.image.value.height.toDouble, speedRandom.nextInt(100))
        content += enemy
        enemies += enemy
      }
      if (enemyType == "star") {
        val star = new Star(width.value.toInt, random.nextInt(height.value.toInt))
        content += star
        stars += star
        star.toBack
      }
      
      if (enemyType == "initStars") {
        for (n <- 1 to 1536) {  // Ruudulla on kerrallaan tähtiä määrä: (ruudun leveys / tähtien liikkumisnopeus pikseleinä sekunneissa * ruudun päivitysnopeus) eli (width.value.toDouble / 50.0 * 60).toInt)
          val star = new Star(random.nextInt(width.value.toInt), random.nextInt(height.value.toInt))
          content += star
          stars += star
          star.toBack
        }
      }
      
      if (enemyType == "AlienShip") {
        val enemy: ShootingEnemy = new AlienShip(width.value.toInt, random.nextInt(height.value.toInt))
        content += enemy
        enemies += enemy
        shootingEnemies += enemy
      }
      
    }
  }
  EnemySpawner.spawn("initStars")
  
  object GameTimer {
       // 1e9 = 1000000000 ns = 1 s
    val timePerShot = 0.25
    var oldTime: Long = 0L
    var lastSmallAsteroid: Double = 3
    var lastBigAsteroid: Double = 10
    var scoreTime = 0.0
    val mainTimer = AnimationTimer(t =>{
      if (oldTime > 0) {
        val playerLastShot: Long = player.lastShot
        val delta = (t - oldTime)/1e9
        val shotDelta = (t-playerLastShot)/1e9
        //val asteroidDelta = (t-lastSmallAsteroid)/1e9
        checkForActions(delta, "move")
        checkForActions(delta, "accelerate")
        player.move(delta)
        if(!Keys.pressed("right") && !Keys.pressed("left")){
          player.slowDownHorizontal(delta)
        }
         if(!Keys.pressed("up") && !Keys.pressed("down")){
          player.slowDownVertical(delta)
        }
        
        enemies.foreach(_.move(delta))
        playerBullets.foreach(_.move(delta))
        enemyBullets.foreach(_.move(delta))
        stars.foreach(_.move(delta))
        checkCollisions
        scoreTime += delta
        if (scoreTime >= 1) {
          score += 1 
          println("score: " + score)
          scoreTime = 0
        }
        // Spawnaa tähtiä
        EnemySpawner.spawn("star")
        
        // Spawnaa asteroideja vaikeusasteen mukaan
        spawnAsteroids(Difficulty.factor) //TODO:
        
        def spawnAsteroids(difficulty: Int) = {
          val timePerSmallAsteroid: Double = 5.0 / difficulty
          val timePerBigAsteroid: Double = 25.0 / difficulty
          if (lastSmallAsteroid <= 0) {              // Periaatteessa pitää väkisin kirjaa siitä ajasta, milloin seuraava asteroidi laitetaan liikkeelle
            EnemySpawner.spawn("asteroid")      // Seuraava asteroidi laitetaan liikkeelle, kun asteroidin asettama aika saavuttaa nollan
            lastSmallAsteroid = timePerSmallAsteroid
          }
          else lastSmallAsteroid -= 0.1
          
          if (lastBigAsteroid <= 0) {              // Periaatteessa pitää väkisin kirjaa siitä ajasta, milloin seuraava asteroidi laitetaan liikkeelle
            EnemySpawner.spawn("bigasteroid")      // Seuraava asteroidi laitetaan liikkeelle, kun asteroidin asettama aika saavuttaa nollan
            lastBigAsteroid = timePerBigAsteroid
          }
          else lastBigAsteroid -= 0.1
        }
        
        shootingEnemies.foreach{enemy: ShootingEnemy =>
          val lastShot = enemy.lastShot
          val delta = (t-lastShot)/1e9
          if (lastShot == 0 || delta >= enemy.timePerShot) {
            enemy.shoot
            enemy.lastShot = t
          }
        }
        
        if (playerLastShot == 0 || shotDelta >= timePerShot) {
        if (checkForActions(delta,"shoot")) player.lastShot = t
        }
        
        
        // Tämän tässä on tarkoitus poistaa Buffereista kaikki kuvan ulkopuolelle siirtyneet asiat:
        // enemies, playerBullets, enemyBullets, stars
        removeOutOfBoundsObjects
        
        
        
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
    
    val enemiesToRemove = enemies.filter(!_.isAlive)
    val starsToRemove = stars.filter(!_.isAlive)
    val enemyBulletsToRemove = enemyBullets.filter(!_.isAlive)
    val playerBulletsToRemove = playerBullets.filter(!_.isAlive)
    
    enemies.foreach((e: EnemyShip) => if (enemiesToRemove.contains(e)) {
      enemies.remove(enemies.indexOf(e))
      if (content.contains(e)) content.remove(content.indexOf(e))
    })
    
    stars.foreach((e: Star) => if (starsToRemove.contains(e)) {
      stars.remove(stars.indexOf(e))
      if (content.contains(e)) {
        content.remove(content.indexOf(e))
      }
    })
    
    enemyBullets.foreach((e: EnemyBullet) => if (enemyBulletsToRemove.contains(e)) {
      enemyBullets.remove(enemyBullets.indexOf(e))
      if (content.contains(e)) content.remove(content.indexOf(e))
    })
    
    playerBullets.foreach((e: PlayerBullet) => if (playerBulletsToRemove.contains(e)) {
      playerBullets.remove(playerBullets.indexOf(e))
      if (content.contains(e)) content.remove(content.indexOf(e))
    })
      }
}