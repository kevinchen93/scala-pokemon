import java.awt._
import java.awt.event.{ActionEvent, ActionListener}
import java.awt.geom.AffineTransform
import javax.swing.{JPanel, Timer}

import scala.compat.Platform
import scala.swing._
import scala.util.Random

/**
 * Created by kevinchen on 5/24/15.
 */
class PokemonGameEngine extends JPanel with ActionListener {

  // DEBUG VARS
  var debugNoBattle = false
  var debugNoClip = false

  val mapLoader = new MapController(this)

  var gameStarted = false
  var startVisible = true

  var inBattle = false
  var movable = true
  var walking = false

  var atTitle = true
  var atContinueScreen = false
  var concurrentMenuItem = 0

  val soundController = new SoundController()
  soundController.play(SoundController.title)

  // MENU ITEMS
  var inMenu = false
  val menu = new MenuScene(this)
  var disable_start = false

  val gameTimer = new Timer(350, this)
  gameTimer.start()

  var elapsedSeconds = 0

  val playerController = new PlayerController(this)
  val battleController = new BattleController(this)

  var timePlayed: Long = 0

  val random = new Random()

  def start(continued: Boolean): Unit = {
    mapLoader.loadTileSet()
    mapLoader.loadMap()
    if (continued) {
      GameLoader.loadGame(this)
    }
    else {
      mapLoader.setInitialLocation()
      playerController.seedPokemon()
      playerController.seedMoney()
    }
    playerController.seedItems()
    soundController.play(SoundController.cherryGroveCity)
    atTitle = false
    atContinueScreen = false
    gameStarted = true
    movable = true
    timePlayed = Platform.currentTime
  }

  private def wait(n: Int): Unit = {
    var t0: Long = 0L
    var t1: Long = 0L
    t0 = System.currentTimeMillis
    do {
      t1 = System.currentTimeMillis
    } while ((t1 - t0) < (n * 1000))
  }

  override def actionPerformed(e: ActionEvent): Unit = {
    val currentTime = System.currentTimeMillis()
    if (gameStarted) {

      // process battle, check for fainted pokemon
      if (inBattle) {
        battleController.checkForFaintedPokemon()
      }

      // update clock
      elapsedSeconds = (currentTime / 1000).toInt

      val gold = playerController.gold
      gold.x = mapLoader.posX_tile
      gold.y = mapLoader.posY_tile

      // check for wild pokemon encounters
      checkBattle()

      // Teleport code
      mapLoader.transfer()

      // can't walk outside map array
      mapLoader.updateMobilityConstraints()
      mapLoader.movementScrolling()
      mapLoader.movementReset()
      mapLoader.spriteAnimations()

    } else {
      startVisible = !startVisible
    }
    repaint
  }

  // wild pokemon encounter check
  private def checkBattle(): Unit = {
    val r = ((5 - 1) * Math.random + 1).toInt
    val randomInt = random.nextInt(22) + 11
    if (!debugNoBattle) {
      if (playerController.stepsCount >= randomInt) {
        soundController.play(SoundController.battleBGM)
        var wildPokemon = List[Monster]()
        r match {
          case 1 => wildPokemon = Monster.create(198) :: wildPokemon // wild Murkrow
          case 2 => wildPokemon = List(Monster.create(4)) // wild Charmander
          case 3 => wildPokemon = List(Monster.create(25)) // wild Pikachu
          case 4 => wildPokemon = List(Monster.create(220)) // wild Swinub
          case _ => wildPokemon = List(Monster.create(158)) // wild Totodile
        }
        wait(1)
        inBattle = true
        disable_start = true
        battleController.encounter = Some(new WildBattleScene(this, playerController.pokemonParty, wildPokemon))
        playerController.stepsCount = 0
        try {
          Thread.sleep(500)
        }
        catch {
          case e: InterruptedException => {
          }
        }

      }
    }
  }

  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)
    val g2 = g.asInstanceOf[Graphics2D]
    val at = new AffineTransform()
    g2.setTransform(at)

    // Draw Title Screen
    if (atTitle) {
      g.drawImage(Sprites.titlescreen, 0, 0, null)
      if (startVisible) {
        g.drawImage(Sprites.start_symbol, 0, 260, null)
      }
    }

    // Draw Continue Screen
    else if (atContinueScreen) {
      g.drawImage(Sprites.continuescreen, 0, 0, null)
      concurrentMenuItem match {
        case 0 => g.drawImage(Sprites.arrow, 13, 20, null)
        case 1 => g.drawImage(Sprites.arrow, 13, 52, null)
        case 2 => g.drawImage(Sprites.arrow, 13, 84, null)
      }
    }

    // Draw Battle Scene
    else {
      if (inBattle) {
        battleController.encounter.paint(g)
      }
      else {
        mapLoader.paintComponent(g)
      }
      if (inMenu) {
        menu.paint(g)
      }
    }
  }
}
