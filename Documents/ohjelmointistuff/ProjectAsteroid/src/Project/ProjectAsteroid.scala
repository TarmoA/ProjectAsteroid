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
      var enemies = Buffer[SpaceShip]()
      var playerBullets = Buffer[PlayerBullet]()
      var enemiBullets = Buffer[EnemyBullet]()
      
      
      // 1e9 = 1000000000 ns = 1 s
     /* var oldTime: Long = 0L
      val timer = AnimationTimer(t =>{
        if (oldTime > 0) {
          val delta = (t - oldTime)/1e9
          playerMove(delta) 
          checkCollisions
        }
        oldTime = t
      })
      timer.start */
     
      
      
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
              bullet.destroy 
              collidingBullets -= bullet
              bulletsToDestroy += bullet// playerBullets.remove(playerBullets.indexOf(bullet))
            })
            enemy.damage(1)
          }
        })
        bulletsToDestroy.foreach((bullet: Bullet) => playerBullets.remove(playerBullets.indexOf(bullet)))
        
        val deadEnemies = enemies.filter(!_.isAlive)
        enemies.foreach((a: SpaceShip) => if (deadEnemies.contains(a))enemies.remove(enemies.indexOf(a)))
        }
      
      
   /*   def playerShoot = {
        val timePerShot = 0.25 //sekuntia
        var lastShot: Long = 0L
        val timer = AnimationTimer(t =>{
          val delta = (t-lastShot)/1e9
          if (lastShot == 0 || delta >= timePerShot) {
            if (Keys.pressed("shoot")) {
            var bullet = new PlayerBullet(player.x.value, player.y.value)
            content += bullet
            playerBullets += bullet
            lastShot = t
            }
            
          }
        })
        timer.start
      }
      playerShoot
      
      */
      
      
      
      def playerMove(delta: Double) = {
       // if (Keys.pressed.count(_ == true)<=2) {
          if (Keys.pressed("right")) player.x = player.x.value + player.speed*delta
          if (Keys.pressed("left")) player.x = player.x.value - player.speed*delta
          if (Keys.pressed("down")) player.y = player.y.value + player.speed*delta
          if (Keys.pressed("up")) player.y = player.y.value - player.speed*delta
         
          
          
         // println(val random = new Random(400)player.x.value + " " + player.y.value)
       // }
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
        var oldTime: Long = 0L
        
        val timer = AnimationTimer(t =>{
          if (oldTime > 0) {
            val delta = (t - oldTime)/1e9
            playerMove(delta)
            checkCollisions
            
          }
          oldTime = t
        })
        
        val timePerShot = 0.25 //sekuntia
        var lastShot: Long = 0L
        val shotTimer = AnimationTimer(t =>{
          val delta = (t-lastShot)/1e9
          if (lastShot == 0 || delta >= timePerShot) {
            if (Keys.pressed("shoot")) {
            var bullet = new PlayerBullet(player.x.value, player.y.value)
            content += bullet
            playerBullets += bullet
            lastShot = t
            }
          }
        })
        
        def start = {
          timer.start
          shotTimer.start
        }
        def stop = {
          timer.stop
          shotTimer.stop
        }
        
      }
      GameTimer.start
      
    }
  }
}

