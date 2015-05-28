import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{Image, Dimension, Color}
import java.io._
import java.util.StringTokenizer
import javax.swing.{Timer, JFrame, JPanel}

import scala.compat.Platform

/**
 * Created by kevinchen on 5/24/15.
 */
class PokemonGameEngine extends ActionListener {

  // DEBUG VARS
  var debugNoBattle = false
  var debugNoClip = false

  // PLAYER VARS
  var name = "Gold"

  val gold = new Player(10, 9, name, Sprites.player)
  var pokemonparty: List[Monsters] = Nil

  var currentMapName = "Data/Johto.map"
  val mapLoader = new MapLoader(this)

  val mainitems = Array.ofDim[UsableItem](33, 99)

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

  sealed trait Direction

  case object Up extends Direction

  case object Down extends Direction

  case object Left extends Direction

  case object Right extends Direction

  def movableInDirection(dir: Direction): Boolean = dir match {
    case Up => playerController.movable_up
    case Down => playerController.movable_down
    case Left => playerController.movable_left
    case Right => playerController.movable_right
  }

  def start(continued: Boolean): Unit = {
    mapLoader.loadTileSet()
    mapLoader.loadMap(currentMapName)
    if (continued) {
      GameLoader.loadGame(this)
      mainitems(0)(3) = Items.Potion
    }
    else {
      playerController.currentX_loc = 6 - 7
      playerController.currentY_loc = 67 - 4
      playerController.posX_tile = playerController.currentX_loc + 7
      playerController.posY_tile = playerController.currentY_loc + 4

      pokemonparty = Monsters.create(25) :: pokemonparty
      pokemonparty = Monsters.create(0) :: pokemonparty
      pokemonparty = Monsters.create(0) :: pokemonparty
      pokemonparty = Monsters.create(0) :: pokemonparty
      pokemonparty = Monsters.create(0) :: pokemonparty
      pokemonparty = Monsters.create(0) :: pokemonparty
      mainitems(0)(3) = Items.Potion
      playerController.money = 2000
    }
    soundController.play(SoundController.cherryGroveCity)
    atTitle = false
    atContinueScreen = false
    gameStarted = true
    movable = true
    timePlayed = Platform.currentTime
  }

  def wait(n: Int): Unit = {
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

      // todo player should be in PlayerController
      gold.x = playerController.posX_tile
      gold.y = playerController.posY_tile

      // check for wild pokemon encounters
      checkBattle()

      // Teleport code
      playerController.transfer()

      // can't walk outside map array
      playerController.updateMobilityConstraints()
      playerController.movementScrolling()
      playerController.movementReset()
      playerController.spriteAnimations()

    } else {
      startVisible = !startVisible
    }
    repaint
  }

  // wild pokemon encounter check
  def checkBattle(): Unit = {
    val r = ((5 - 1) * Math.random + 1).toInt
    val rndwildmodify = randGen.nextInt(22) + 11
    if (!debugNoBattle) {
      if (stepscount >= rndwildmodify) {
        soundController.play(SoundController.battleBGM)
        var wildPokemon: Monsters = null
        r match {
          case 1 => wildPokemon = Monsters.create(198) // wild Murkrow
          case 2 => wildPokemon = Monsters.create(4) // wild Charmander
          case 3 => wildPokemon = Monsters.create(25) // wild Pikachu
          case 4 => wildPokemon = Monsters.create(220) // wild Swinub
          case _ => wildPokemon = Monsters.create(158) // wild Totodile
        }
        wait(1)
        inBattle = true
        disable_start = true
        battleController.encounter = new BattleScene(this, pokemonparty, wildPokemon)
        stepscount = 0
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


}
