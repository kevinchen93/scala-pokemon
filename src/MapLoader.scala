import java.awt.Image
import java.io._
import java.util.StringTokenizer

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

}
