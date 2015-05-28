import java.awt._
import java.awt.geom.AffineTransform
import java.io._
import java.util.StringTokenizer

import scala.io.Source
import scala.swing.Graphics2D

/**
 * Created by kevinchen on 5/26/15.
 */
class MapLoader(game: PokemonGameEngine) {

  // Map dimensions
  var mapTilesX: Int = 0
  var mapTilesY: Int = 0

  // Map tileset
  var tileset: String = ""

  // Map data stored in layers
  val currentMap0 = new Array[Int](12500)
  val currentMap1 = new Array[Int](12500)

  var disableTalk = true
  var talkable = false
  private var collision = false
  private var footSprite = false

  private lazy val impassibleTiles = loadImpassibleTilesFromFile()

  private def impassibleTilesFile = ""
  def loadImpassibleTilesFromFile(): Unit = {
    Source.fromFile(impassibleTilesFile).getLines.map { line => line.toInt }.toArray
  }

  private var movespritepixels: Int = 0
  private var walking: Boolean = false
  private var up: Boolean = false
  private var down: Boolean = false
  private var left: Boolean = false
  private var right: Boolean = false

  var movable_up = true
  var movable_down = true
  var movable_left = true
  var movable_right = true

  var x_coor = 0
  var y_coor = 0

  // multiple of 32
  private var posX = 224
  // multiple of 32 (128), minus 10 b/c height is 42, not 32
  private var posY = 118

  var tile_number = 0

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
    case Up => movable_up
    case Down => movable_down
    case Left => movable_left
    case Right => movable_right
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

  def setInitialLocation(): Unit = {
    currentX_loc = 6 - 7
    currentY_loc = 67 - 4
    posX_tile = currentX_loc + 7
    posY_tile = currentY_loc + 4
  }

  def loadTileSet(): Unit = {
    val dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("Data/Tiles.tileset"))))
    for (i <- tileSet.indices)
      tileSet(i) = ImageUtils.createImage("Graphics/" + dis.readLine())
    dis.close()
  }

  def loadMap(): Unit = loadMap(currentMapName)

  def loadMap(map: String): Unit = {
    try {

      val reader: BufferedReader = new BufferedReader(new FileReader(map))
      var line: String = reader.readLine
      var tokens: StringTokenizer = new StringTokenizer(line)

      // first line contains map dimensions
      mapTilesX = tokens.nextToken.toInt
      mapTilesY = tokens.nextToken.toInt
      tileset = tokens.nextToken

      // next line may have color
      line = reader.readLine
      tokens = new StringTokenizer(line)

      // Read color info
      if (tokens.nextToken.equalsIgnoreCase("colorization")) {
        hasColourEffect = true
        r = tokens.nextToken.toFloat
        g = tokens.nextToken.toFloat
        b = tokens.nextToken.toFloat
        h = tokens.nextToken.toFloat
        s = tokens.nextToken.toFloat
      }

      // Skip the dots
      while (line != ".") {
        line = reader.readLine
      }

      // next line has map data
      for (layers <- 0 until 2) {
        line = reader.readLine
        tokens = new StringTokenizer(line)
        for (y <- 0 until mapTilesX * mapTilesY) {
          val code: String = tokens.nextToken
          if (layers == 0) {
            currentMap0(y) = code.toInt
          }
          else if (layers == 1) {
            currentMap1(y) = code.toInt
          }
        }
      }
      reader.close()
    }
    catch {
      case e: FileNotFoundException => {
        e.printStackTrace()
      }
      case i: IOException => {
        i.printStackTrace()
      }
    }
  }

  def paintComponent(g: Graphics): Unit = {
    val g2 = g.asInstanceOf[Graphics2D]
    // Draw the map
    g2.setClip(new Rectangle(posX - 240, posY - 160, posX + 480, posY + 320))
    g2.translate(offsetX - (currentX_loc * 32), offsetY - (currentY_loc * 32))

    for (y <- 1 to mapTilesY) {
      for (x <- 1 to mapTilesX) {
        // Layer 0
        if (currentMap0(tile_number) != 0) {
          g.drawImage(tileset(currentMap0(tile_number) - 1), x_coor, y_coor, null)
        }
        //Layer 1
        if (currentMap1(tile_number) != 0) {
          g.drawImage(tileset(currentMap1(tile_number) - 1), x_coor, y_coor, null)
        }
        // Impassible tiles
        if (!tilesLoaded) {
          for (i <- impassibleTiles.indices) {
            val tile = impassibleTiles(i)
            if (currentMap0(tile_number) == tile || currentMap1(tile_number) == tile) {
              if (!game.debugNoClip) {
                currentMapStaticTiles(tile_number) = new StaticTile(x_coor / 32, y_coor / 32, null)
              }
            }
          }
        }
        x_coor += 32
        tile_number += 1
      }
      x_coor = 0
      y_coor += 32
    }
    tilesLoaded = true
    tile_number = 0
    x_coor = 0
    y_coor = 0

    // NPC sprites
    for (i <- currentMapNPC.indices) {
      val npc = currentMapNPC(i)
      g2.drawImage(npc.sprite, npc.x * TITLE_WIDTH_PIXLES, npc.y * TITLE_HEIGHT_PIXELS - 10, null)
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
    if (talkable && !movable_up) {
      if (gold.sprite == Sprites.playerUp) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
    if (talkable && !movable_down) {
      if (gold.sprite == Sprites.playerDown) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
    if (talkable && !movable_left) {
      if (gold.sprite == Sprites.playerLeft) {
        g.drawImage(Sprites.messagebox, 0, 0, null)
        g.drawString(text, 25, 255)
      }
    }
    if (talkable && !movable_right) {
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
    movable_left = posX_tile > 0
    movable_right = posX_tile < ml.mapTilesX - 1
    movable_up = posY_tile > 0
    movable_down = posY_tile < ml.mapTilesY - 1
  }

  def crashTesting(): Unit = {
    movable_up = true
    movable_down = true
    movable_left = true
    movable_right = true
    if (!game.debugNoClip) {

      // crash into NPCs
      for (i <- currentMapNPC.indices) {
        gold.crashTest(currentMapNPC(i)) match {
          case 1 => movable_up = false; disableTalk = false
          case 2 => movable_down = false; disableTalk = false
          case 3 => movable_left = false; disableTalk = false
          case 4 => movable_right = false; disableTalk = false
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
          case 1 => movable_up = false
          case 2 => movable_down = false
          case 3 => movable_left = false
          case 4 => movable_right = false
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
      movespritepixels += 1
      if (up && movable_up) {
        offsetY += 2
      }
      if (down && movable_down) {
        offsetY -= 2
      }
      if (left && movable_left) {
        offsetX += 2
      }
      if (right && movable_right) {
        offsetX -= 2
      }
    }
  }

  def movementReset(): Unit = {
    if (movespritepixels >= 16) {
      movespritepixels = 0
      walking = false
      if (up && movable_up) posY_tile -= 1
      if (down && movable_down) posY_tile += 1
      if (left && movable_left) posX_tile -= 1
      if (right && movable_right) posX_tile += 1
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
    if (up && movable_up) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.sprite = Sprites.playerUp
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.sprite = Sprites.playerUp
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerUp2
        }
        else {
          gold.sprite = Sprites.playerUp1
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
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
    if (down && movable_down) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.sprite = Sprites.playerDown
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.sprite = Sprites.playerDown
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerDown2
        }
        else {
          gold.sprite = Sprites.playerDown1
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
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
    if (left && movable_left) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.sprite = Sprites.playerLeft
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.sprite = Sprites.playerLeft
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerLeft2
        }
        else {
          gold.sprite = Sprites.playerLeft1
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
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
    if (right && movable_right) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.sprite = Sprites.playerRight
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.sprite = Sprites.playerRight
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footSprite) {
          gold.sprite = Sprites.playerRight2
        }
        else {
          gold.sprite = Sprites.playerRight1
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
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
