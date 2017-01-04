package Project
import scala.collection.mutable.Buffer
import scala.collection.mutable.Map
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene._
import scalafx.scene.paint.Color._
import scalafx.scene.shape.Rectangle
import scalafx.scene.input._
import scalafx.scene.input.KeyEvent
import scalafx.scene.input.KeyCode._
import scalafx.event.ActionEvent._
import scalafx.scene.control._
import scalafx.Includes._
import scalafx.animation.AnimationTimer
import scala.util.Random

object ProjectAsteroid extends JFXApp {
  stage = new JFXApp.PrimaryStage {
    title.value = "asd"
    width = 640 
    height = 480
    
    scene = new Scene {
      val player = PlayerShip
      content = player
      var enemies = Buffer[EnemyShip]()
      var playerBullets = Buffer[PlayerBullet]()
      var enemyBullets = Buffer[EnemyBullet]()
      
      
     
     
      
      
      def checkCollisions = {
       
        val enemiesCollidingWithPlayer = enemies.filter(_.collidesWith(player))
        if (!enemiesCollidingWithPlayer.isEmpty) {
          player.damage(1)
          enemiesCollidingWithPlayer.foreach((enemy: SpaceShip) => {
            enemy.destroy
            enemies.remove(enemies.indexOf(enemy))
          })
        }
        
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
        enemies.foreach((a: SpaceShip) => if (deadEnemies.contains(a))enemies.remove(enemies.indexOf(a)))
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
          if (e.code == Z) EnemySpawner.spawn

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
     
      object EnemySpawner {
        val random = new Random(100)
        
        def spawn = {
           val enemy = new Asteroid(width.value.toInt-50, random.nextInt(height.value.toInt-50))
           content += enemy
           enemies += enemy
          println("vihollinen")
        }
      }
      
      object GameTimer {
           // 1e9 = 1000000000 ns = 1 s
        val timePerShot = 0.25
        var oldTime: Long = 0L
        var lastShot: Long = 0L
        val mainTimer = AnimationTimer(t =>{
          if (oldTime > 0) {
            val delta = (t - oldTime)/1e9
            val shotDelta = (t-lastShot)/1e9
            checkForActions(delta, "move")
            checkCollisions
            enemies.foreach(_.move(delta))
            playerBullets.foreach(_.move(delta))
            enemyBullets.foreach(_.move(delta))
            
            if (lastShot == 0 || shotDelta >= timePerShot) {
            if(checkForActions(delta,"shoot")) lastShot = t
            }
            
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
      
    }
  }
}

