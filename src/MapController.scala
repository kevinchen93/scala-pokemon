import java.awt._
import java.io._
import java.util.StringTokenizer

import scala.io.Source
import scala.swing.Graphics2D

/**
 * Created by kevinchen on 5/26/15.
 */
class MapController(game: PokemonGameEngine) {

  // Map dimensions
  var mapTilesX: Int = 0
  var mapTilesY: Int = 0

  // Map tileset
  var tileset = new Array[Image](1112)

  // Map data stored in layers
  val currentMap0 = new Array[Int](12500)
  val currentMap1 = new Array[Int](12500)

  var disableTalk = true
  var talkable = false
  private var collision = false
  private var footSprite = false

  private lazy val impassibleTiles = loadImpassibleTilesFromFile()

  private def impassibleTilesFile = ""
  def loadImpassibleTilesFromFile(): Array[Int] = {
    Source.fromFile(impassibleTilesFile).getLines.map { line => line.toInt }.toArray
  }

  val TILE_WIDTH_PIXELS = 32
  val TILE_HEIGHT_PIXELS = 32

  private var moveSpritePixels: Int = 0
  private var walking: Boolean = false
  private var up: Boolean = false
  private var down: Boolean = false
  private var left: Boolean = false
  private var right: Boolean = false

  var tilesLoaded = false

  var movableUp = true
  var movableDown = true
  var movableLeft = true
  var movableRight = true

  var xCoor = 0
  var yCoor = 0

  // multiple of 32
  private var posX = 224
  // multiple of 32 (128), minus 10 b/c height is 42, not 32
  private var posY = 118

  var tileNumber = 0

  var currentX_loc = 0
  // start loc of player (rows)
  var currentY_loc = 0
  // start loc of player (cols)
  var posX_tile = 0
  // player loc (rows)
  var posY_tile = 0 // player loc (cols)

  var offsetX = 0
  var offsetY = 0

  var currentMap = "Cherrygrove City"
  var currentMapName = "Data/Johto.map"
  val currentMapStaticTiles = new Array[StaticTile](12500)
  var currentMapNPC = Array[NPC](
    NPC.violetCitizen1,
    NPC.violetCitizen2,
    NPC.violetCitizen3,
    NPC.violetCitizen4,
    NPC.violetCitizen5,
    NPC.violetIndoors1,
    NPC.violetIndoors2,
    NPC.schoolStudent1,
    NPC.schoolStudent2,
    NPC.schoolStudent3,
    NPC.schoolStudent4,
    NPC.schoolStudent5,
    NPC.schoolStudent6,
    NPC.martCustomer1,
    NPC.storeClerk,
    NPC.centerVisitor1,
    NPC.nurseJoy,
    NPC.birdKeeper1,
    NPC.birdKeeper2,
    NPC.leaderFaulkner,
    NPC.oldMan1,
    NPC.cherrygroveCitizen1,
    NPC.cherrygroveCitizen2,
    NPC.cherrygroveCitizen3
  )

  sealed trait Direction

  case object Up extends Direction

  case object Down extends Direction

  case object Left extends Direction

  case object Right extends Direction

  def movableInDirection(dir: Direction): Boolean = dir match {
    case Up => movableUp
    case Down => movableDown
    case Left => movableLeft
    case Right => movableRight
  }

  // COLOR
  var r: Float = 1
  var g: Float = 1
  var b: Float = 1
  var h: Float = 0
  var s: Float = 1
  var hasColourEffect = false

  // Tile data
  val tileSet = new Array[Image](1112)

  def loadTileSet(): Unit = MapLoader.loadTileSet(this)
  def loadMap(): Unit = MapLoader.loadMap(this, currentMapName)

  private def gold: Player = game.playerController.gold
  private def pokemonParty: List[Monster] = game.playerController.pokemonParty

  def setInitialLocation(): Unit = {
    currentX_loc = 6 - 7
    currentY_loc = 67 - 4
    posX_tile = currentX_loc + 7
    posY_tile = currentY_loc + 4
  }

  def paintComponent(g: Graphics): Unit = {
    val g2 = g.asInstanceOf[Graphics2D]
    // Draw the map
    g2.setClip(new Rectangle(posX - 240, posY - 160, posX + 480, posY + 320))
    g2.translate(offsetX - (currentX_loc * 32), offsetY - (currentY_loc * 32))

    for (y <- 1 to mapTilesY) {
      for (x <- 1 to mapTilesX) {
        // Layer 0
        if (currentMap0(tileNumber) != 0) {
          g.drawImage(tileset(currentMap0(tileNumber) - 1), xCoor, yCoor, null)
        }
        //Layer 1
        if (currentMap1(tileNumber) != 0) {
          g.drawImage(tileset(currentMap1(tileNumber) - 1), xCoor, yCoor, null)
        }
        // Load static/Impassible tiles the first time we paint
        if (!tilesLoaded) {
          impassibleTiles.foreach { tile =>
            if (currentMap0(tileNumber) == tile || currentMap1(tileNumber) == tile) {
              if (!game.debugNoClip) {
                currentMapStaticTiles(tileNumber) = new StaticTile(xCoor / 32, yCoor / 32, null)
              }
            }
          }
        }
        xCoor += 32
        tileNumber += 1
      }
      xCoor = 0
      yCoor += 32
    }
    tilesLoaded = true
    tileNumber = 0
    xCoor = 0
    yCoor = 0

    // NPC sprites
    for (i <- currentMapNPC.indices) {
      val npc = currentMapNPC(i)
      g2.drawImage(npc.sprite, npc.x * TILE_WIDTH_PIXELS, npc.y * TILE_HEIGHT_PIXELS - 10, null)
    }

    // Reset to 0,0
    g2.translate(-offsetX, -offsetY)
    // Player sprites
    g2.setTransform(at)
    g2.drawImage(gold.sprite, posX, posY, null)
    g2.setFont(Fonts.pokefont)
    g2.setColor(Color.WHITE)
    g2.drawString("" + posX_tile + "," + posY_tile, 10, 25)
    showMessageBox(g)
  }

  def showMessageBox(g: Graphics) {
    g.setColor(Color.BLACK)
    var text: String = ""
    if (talkable) {
      for (i <- currentMapNPC.indices) {
        if (currentMapNPC(i).talkable(gold)) {
          text = currentMapNPC(i).text(gold)
        }
      }
    }
    if (talkable && !movableUp) {
      if (gold.sprite == Sprites.playerUp) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
    if (talkable && !movableDown) {
      if (gold.sprite == Sprites.playerDown) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
    if (talkable && !movableLeft) {
      if (gold.sprite == Sprites.playerLeft) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
    if (talkable && !movableRight) {
      if (gold.sprite == Sprites.playerRight) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
  }

  def transfer(): Unit = {

    def atPos(x: Int, y: Int): Boolean = posX_tile == x && posY_tile == y
    def updatePos(x: Int, y: Int): Unit = {
      currentX_loc += (x - posX_tile)
      currentY_loc += (y - posY_tile)
      posX_tile = x
      posY_tile = y
    }
    //Music
    /*if (posX_tile == 73 && posY_tile == 94) {
      if (down == true) {
        changeBGM(cherrygrovecity);
      }
      if (up == true) {
        changeBGM(route30);
      }
    }*/

    val myPokemon = pokemonParty.head

    //Cherrygrove to PokeCenter
    if (atPos(85, 99)) {
      updatePos(42, 110)
      myPokemon.healPokemon
      game.soundController.play(SoundController.pokeCenter)
    }
    //Cherrygrove to PokeMart
    if (atPos(79, 99)) {
      updatePos(21, 110)
      game.soundController.play(SoundController.pokeMart)
    }
    //Cherrygrove City to House
    if (atPos(73, 103)) {
      updatePos(40, 93)
    }
    //Cherrygrove City to Violet City
    if (atPos(40, 94)) {
      updatePos(73, 104)
    }
    //Cherrygrove City to Player Home
    if (atPos(81, 105)) {
      updatePos(27, 69)
    }
    //Player Home to Cherrygrove City
    if (atPos(27, 70)) {
      updatePos(81, 106)
    }
    //Route 30 to Berry House
    if (atPos(73, 81)) {
      updatePos(39, 77)
    }
    //Berry House to Route 30
    if (atPos(39, 78)) {
      updatePos(73, 82)
    }
    //Violet City to PokeCenter
    if (atPos(37, 31)) {
      updatePos(42, 110)
      myPokemon.healPokemon
      game.soundController.play(SoundController.pokeCenter)
    }
    //Violet City to PokeMart
    if (atPos(15, 23)) {
      updatePos(21, 110)
      game.soundController.play(SoundController.pokeMart)
    }
    //PokeCenter to Town
    if (atPos(42, 111)) {
      if (currentMap == "Cherrygrove City") {
        updatePos(85, 100)
        game.soundController.play(SoundController.cherryGroveCity)
      }
      else if (currentMap == "Violet City") {
        updatePos(37, 32)
        game.soundController.play(SoundController.violetCity)
      }
      else {
        println("Haaaaaaaaaaaaaaaaaaaax")
      }
    }
    //PokeMart to Town
    if (atPos(21, 111)) {
      if (currentMap == "Cherrygrove City") {
        updatePos(79, 100)
        game.soundController.play(SoundController.cherryGroveCity)
      }
      else if (currentMap == "Violet City") {
        updatePos(15, 24)
        game.soundController.play(SoundController.violetCity)
      }
      else {
        println("Haaaaaaaaaaaaaaaaaaaax")
      }
    }
    //Violet City to House
    if (atPos(27, 35)) {
      updatePos(6, 110)
    }
    //House to Violet City
    if (atPos(6, 111)) {
      updatePos(27, 36)
    }
    //Violet City to Violet Gym
    if (atPos(24, 23)) {
      updatePos(7, 93)
      game.soundController.play(SoundController.gym)
    }
    //Violet Gym to Violet City
    if (atPos(7, 94)) {
      updatePos(24, 24)
      game.soundController.play(SoundController.violetCity)
    }
    //Violet City to Pokemon School
    if (atPos(36, 23)) {
      updatePos(24, 93)
    }
    //Pokemon School to Violet City
    if (atPos(24, 94)) {
      updatePos(36, 24)
      game.soundController.play(SoundController.violetCity)
    }
    //Route 31 to Violet City
    if (atPos(50, 31)) {
      updatePos(44, 31)
      game.soundController.play(SoundController.violetCity)
    }
    if (atPos(50, 30)) {
      updatePos(44, 30)
      game.soundController.play(SoundController.violetCity)
    }
    //Violet City to Route 31
    if (atPos(45, 31)) {
      updatePos(51, 31)
      game.soundController.play(SoundController.route30)
    }
    if (atPos(45, 30)) {
      updatePos(51, 30)
      game.soundController.play(SoundController.route30)
    }
    //Player Room to Player House
    if (atPos(11, 64)) {
      updatePos(29, 64)
      gold.sprite = Sprites.playerDown
    }
    //Player House to Player Room
    if (atPos(29, 63)) {
      updatePos(11, 65)
      gold.sprite = Sprites.playerDown
    }
  }

  def updateMobilityConstraints(): Unit = {
    val ml = game.mapLoader
    movableLeft = posX_tile > 0
    movableRight = posX_tile < ml.mapTilesX - 1
    movableUp = posY_tile > 0
    movableDown = posY_tile < ml.mapTilesY - 1
  }

  def crashTesting(): Unit = {
    movableUp = true
    movableDown = true
    movableLeft = true
    movableRight = true
    if (!game.debugNoClip) {

      // crash into NPCs
      for (i <- currentMapNPC.indices) {
        gold.crashTest(currentMapNPC(i)) match {
          case 1 => movableUp = false; disableTalk = false
          case 2 => movableDown = false; disableTalk = false
          case 3 => movableLeft = false; disableTalk = false
          case 4 => movableRight = false; disableTalk = false
        }

        if (gold.crashTest(currentMapNPC(i)) != 0) {
          if (collision) {
            game.soundController.col.playClip("Collision")
            collision = false
          }
        }
      }

      // crash into impassible objects
      for (i <- currentMapStaticTiles.indices) {
        val x = gold.crashTest(currentMapStaticTiles(i))
        x match {
          case 1 => movableUp = false
          case 2 => movableDown = false
          case 3 => movableLeft = false
          case 4 => movableRight = false
        }
        if (x != 0) {
          if (collision) {
            game.soundController.col.playClip("Collision")
            collision = false
          }
        }

      }
    }
  }

  // Movement scrolling
  def movementScrolling(): Unit = {
    if (walking) {
      moveSpritePixels += 1
      if (up && movableUp) {
        offsetY += 2
      }
      if (down && movableDown) {
        offsetY -= 2
      }
      if (left && movableLeft) {
        offsetX += 2
      }
      if (right && movableRight) {
        offsetX -= 2
      }
    }
  }

  def movementReset(): Unit = {
    if (moveSpritePixels >= 16) {
      moveSpritePixels = 0
      walking = false
      if (up && movableUp) posY_tile -= 1
      if (down && movableDown) posY_tile += 1
      if (left && movableLeft) posX_tile -= 1
      if (right && movableRight) posX_tile += 1
      up = false
      down = false
      left = false
      right = false
      footSprite = !footSprite
      val myPokemon = pokemonParty.head
      if (myPokemon.curHp <= 0) {
        println(s"${myPokemon.name} has fainted.")
        println(s"${gold.name} is all out of usable Pokemon.")
        println(s"${gold.name} whited out.")
        currentX_loc += 42 - posX_tile
        currentY_loc += 107 - posY_tile
        posX_tile = 42
        posY_tile = 107
        myPokemon.healPokemon
        gold.sprite = Sprites.playerUp
      }
      if (myPokemon.burned || myPokemon.poisoned) {
        myPokemon.curHp -= 1
      }
    }
  }

  def spriteAnimations(): Unit = {
    //Player Sprite Animations (Up)
    if (up && movableUp) {
      if (moveSpritePixels >= 0 && moveSpritePixels < 4) {
        gold.sprite = Sprites.playerUp
      }
      else if (moveSpritePixels > 4 && moveSpritePixels < 8) {
        gold.sprite = Sprites.playerUp
      }
      else if (moveSpritePixels > 8 && moveSpritePixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerUp2
        }
        else {
          gold.sprite = Sprites.playerUp1
        }
      }
      else if (moveSpritePixels >= 12 && moveSpritePixels < 15) {
        if (footSprite) {
          gold.sprite = Sprites.playerUp2
        }
        else {
          gold.sprite = Sprites.playerUp1
        }
      }
      else {
        gold.sprite = Sprites.playerUp
      }
    }
    //Player Sprite Animations (Down)
    if (down && movableDown) {
      if (moveSpritePixels >= 0 && moveSpritePixels < 4) {
        gold.sprite = Sprites.playerDown
      }
      else if (moveSpritePixels > 4 && moveSpritePixels < 8) {
        gold.sprite = Sprites.playerDown
      }
      else if (moveSpritePixels > 8 && moveSpritePixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerDown2
        }
        else {
          gold.sprite = Sprites.playerDown1
        }
      }
      else if (moveSpritePixels >= 12 && moveSpritePixels < 15) {
        if (footSprite) {
          gold.sprite = Sprites.playerDown2
        }
        else {
          gold.sprite = Sprites.playerDown1
        }
      }
      else {
        gold.sprite = Sprites.playerDown
      }
    }
    //Player Sprite Animations (Left)
    if (left && movableLeft) {
      if (moveSpritePixels >= 0 && moveSpritePixels < 4) {
        gold.sprite = Sprites.playerLeft
      }
      else if (moveSpritePixels > 4 && moveSpritePixels < 8) {
        gold.sprite = Sprites.playerLeft
      }
      else if (moveSpritePixels > 8 && moveSpritePixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerLeft2
        }
        else {
          gold.sprite = Sprites.playerLeft1
        }
      }
      else if (moveSpritePixels >= 12 && moveSpritePixels < 15) {
        if (footSprite) {
          gold.sprite = Sprites.playerLeft2
        }
        else {
          gold.sprite = Sprites.playerLeft1
        }
      }
      else {
        gold.sprite = Sprites.playerLeft
      }
    }
    //Player Sprite Animations (Right)
    if (right && movableRight) {
      if (moveSpritePixels >= 0 && moveSpritePixels < 4) {
        gold.sprite = Sprites.playerRight
      }
      else if (moveSpritePixels > 4 && moveSpritePixels < 8) {
        gold.sprite = Sprites.playerRight
      }
      else if (moveSpritePixels > 8 && moveSpritePixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerRight2
        }
        else {
          gold.sprite = Sprites.playerRight1
        }
      }
      else if (moveSpritePixels >= 12 && moveSpritePixels < 15) {
        if (footSprite) {
          gold.sprite = Sprites.playerRight2
        }
        else {
          gold.sprite = Sprites.playerRight1
        }
      }
      else {
        gold.sprite = Sprites.playerRight
      }
    }
  }

}