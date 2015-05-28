import java.awt.{Color, Rectangle, Graphics, Image}
import java.io._
import java.util.StringTokenizer

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

  // COLOR
  var r: Float = 1
  var g: Float = 1
  var b: Float = 1
  var h: Float = 0
  var s: Float = 1
  var hasColourEffect = false

  // Tile data
  val tileSet = new Array[Image](1112)

  def loadTileSet(): Unit = {
    val dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("Data/Tiles.tileset"))))
    for (i <- tileSet.indices)
      tileSet(i) = ImageUtils.createImage("Graphics/" + dis.readLine())
    dis.close()
  }

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

  def paintComponent(g2: Graphics2D): Unit = {
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

}
