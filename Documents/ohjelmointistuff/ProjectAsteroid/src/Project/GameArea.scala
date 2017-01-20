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

/* This is the main view where the game happens. This Scene is set as the content of the main stage when the game begins.
 *  Setting the size here also ssets the size for the game window.
 */
class GameArea(val difficultyFactor: Int) extends Scene(1280, 720) {
  val player = new PlayerShip(this)
  
  // Every Node-object (such as all SpaceObjects) needs to be added to content to be visible and considered to be in the scene.
  content = player
  
  // Buffers for storing different types of SpaceObjects for purposes such as collisionchecking.
  var enemies = Buffer[EnemyShip]()
  //var shootingEnemies = Buffer[ShootingEnemy]()
  var playerBullets = Buffer[PlayerBullet]()
  var enemyBullets = Buffer[EnemyBullet]()
  var stars = Buffer[Star]()
  
  var score = 0
  var speed = 0
  
  //Sets backgroundcolor
  fill = BLACK
  
  
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
    toFront
  }
  content += textBox
  
  /* This checks for any collisions between SpaceObjects. Any colliding objects will either take damage or be destroyed.
   * This is called on every tick of the GameTimer
   */
  def checkCollisions = {
    
   // Enemy-player collision
    
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
            bulletsToDestroy += bullet
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
      }
    })
  }
  
  /*
   * Checks the Keys.pressed-Map for any keys that are pressed and calls the players methods for movement and shooting.
   * Called on every tick of the GameTimer
   */
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
  
  /* This listens to events from the keyboard and reacts when the correct keys are pressed and released
   * 
   */
  object Keys {
    val pressed = Map[String,Boolean]( "right" -> false, "left" -> false, "up" -> false, "down" -> false, "shoot" -> false)
                            
    onKeyPressed = (e: KeyEvent) => { 
      if (e.code == RIGHT) pressed("right") = true
      if (e.code == LEFT)  pressed("left") = true
      if (e.code == DOWN)  pressed("down") = true
      if (e.code == UP)    pressed("up") = true
      if (e.code == X)     pressed("shoot") = true
      if (e.code == P)     pause()  // avaa pausemenun

    
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
  
  /*
   * This is called when P is pressed. Creates a new instance of PauseMenu which effectively pauses the game
   */
  var paused = false
  def pause() = {
    Keys.pressed.transform((a:String,b:Boolean) => false)
    if (!paused) new PauseMenu
    paused = true
  }
  
  /*
   * This object handles the spawning of enemies
   */
  object Spawner {
    val random = new Random()
    val speedRandom = new Random()
    
    /* This spawns an enemy to random coordinates near the right edge of the screen, with the enemyType given as parameter.
     * Adds the spawned enemy to the Scenes content and the relevant enemytype-buffer.
     */
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
      
      /*
       * Fills the background with stars at the start of the game. 1536 is the number of stars per screen created with the regular star-spawning method
       */
      if (enemyType == "initStars") {
        for (n <- 1 to 1536) {  
          val star = new Star(random.nextInt(width.value.toInt), random.nextInt(height.value.toInt))
          content += star
          stars += star
          star.toBack
        }
      }
      
     /* Not used
      * if (enemyType == "AlienShip") {
        val enemy: ShootingEnemy = new AlienShip(width.value.toInt, random.nextInt(height.value.toInt))
        content += enemy
        enemies += enemy
        shootingEnemies += enemy
      }*/
      
    }
  }
  Spawner.spawn("initStars")
  
  
  /* The mainTimer(scalafx AnimationTimer) in this object ticks at a non-regular rate and returns the current system time in nanoseconds wheneer it does.
   * This can be used to calculate all the movement and other timer related tasks independent of the rate of ticks.
   * All the game-updating methods are called on every tick of the timer.
   */
  object GameTimer {
       // 1e9 = 1000000000 ns = 1 s
    var secondTimer: Double = 0
    val timePerSpeedIncrease = 3
    var timePerSmallAsteroid: Double = 5.0 / difficultyFactor
    var timePerBigAsteroid: Double = 25.0 / difficultyFactor
    val timePerShot = 0.25
    var oldTime: Long = 0L
    var lastSmallAsteroid: Double = 3
    var lastBigAsteroid: Double = 10
    var scoreTime = 0.0
    
    val mainTimer = AnimationTimer(t =>{
      if (oldTime > 0) {
        val playerLastShot: Long = player.lastShot
        
        //this is the time in seconds since the last tick of the timer. Used to calculate movement since the tick-rate of the timer is iregular
        val delta = (t - oldTime)/1e9
        val shotDelta = (t-playerLastShot)/1e9
        
        checkForActions(delta, "accelerate")
        player.move(delta)
        
        //slows down only when no keys are pressed
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
        //Adds one score every second
        if (scoreTime >= 1) {
          score += 1
          scoreTime = 0
        }
        
        /*
         * This speeds up the rate at which asteroids spawn every second
         */
        secondTimer += delta
        if (secondTimer >= timePerSpeedIncrease && !(timePerSmallAsteroid  <= 0.7)) {
          secondTimer -= timePerSpeedIncrease
          timePerSmallAsteroid = timePerSmallAsteroid *0.97
          timePerBigAsteroid = timePerBigAsteroid*0.97
        }
        
        // Spawns stars
        Spawner.spawn("star")
        
        spawnAsteroids()
        
        def spawnAsteroids() = {
          if (lastSmallAsteroid <= 0) {              // Periaatteessa pitää väkisin kirjaa siitä ajasta, milloin seuraava asteroidi laitetaan liikkeelle
            Spawner.spawn("asteroid")      // Seuraava asteroidi laitetaan liikkeelle, kun asteroidin asettama aika saavuttaa nollan
            lastSmallAsteroid = timePerSmallAsteroid
          }
          else lastSmallAsteroid -= 0.1
          
          if (lastBigAsteroid <= 0) {              // Periaatteessa pitää väkisin kirjaa siitä ajasta, milloin seuraava asteroidi laitetaan liikkeelle
            Spawner.spawn("bigasteroid")      // Seuraava asteroidi laitetaan liikkeelle, kun asteroidin asettama aika saavuttaa nollan
            lastBigAsteroid = timePerBigAsteroid
          }
          else lastBigAsteroid -= 0.1
        }
        
        /*shootingEnemies.foreach{enemy: ShootingEnemy =>
          val lastShot = enemy.lastShot
          val delta = (t-lastShot)/1e9
          if (lastShot == 0 || delta >= enemy.timePerShot) {
            enemy.shoot
            enemy.lastShot = t
          }
        }*/
        
        /* 
         * This only allows the player to shoot every n seconds, n == timePerShot
         */
        if (playerLastShot == 0 || shotDelta >= timePerShot) {
        if (checkForActions(delta,"shoot")) player.lastShot = t
        }
        
        removeOutOfBoundsObjects
        
        //updates the score-text on the UI
        scoreText.text = "Score: " + score
        textBox.toFront
      }
      oldTime = t
    })
    
    // starts the MainTimer
    def start = {
      mainTimer.start
    }
    
    //Stops the mainTimer. the oldTime value of the timer needs to be reset when starting the time again.
    def stop = {
      mainTimer.stop
    }
    
  }
  
  GameTimer.start
  
 
  /* This removes all out-oof-bounds objects from the GameAreas content-buffer as well as each object-types own buffer.
   * This is called on each tick of the GameTimer.
   */
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