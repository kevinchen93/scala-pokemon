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

  // PLAYER VARS
  var name = "Gold"

  val gold = new Player(10, 9, name, Sprites.player)
  var pokemonparty: List[Monsters] = Nil

  var currentMapName = "Data/Johto.map"
  val mapLoader = new MapLoader(this)

  val mainitems = Array.ofDim[UsableItem](33, 99)

  var gameStarted = false
  var startVisible = true

  var inMenu = false
  var inBattle = false
  var movable = true
  var walking = false

  var atTitle = true
  var atContinueScreen = false
  var concurrentMenuItem = 0

  val soundController = new SoundController()
  soundController.play(SoundController.title)
  val menu = new MenuScene(this)

  val gameTimer = new Timer(350, this)
  gameTimer.start()

  val playerController = new PlayerController(this)

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

  override def actionPerformed(e: ActionEvent): Unit = {
    val currentTime = System.currentTimeMillis()
  }


}
