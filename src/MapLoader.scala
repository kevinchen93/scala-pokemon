import java.io._
import java.util.StringTokenizer

import scala.io.Source

/**
 * Created by kevinchen on 5/29/15.
 */
object MapLoader {

  def loadTileSet(mc: MapController): Unit = {
    val dis = new DataInputStream(new BufferedInputStream(new FileInputStream(new File("Data/Tiles.tileset"))))
    for (i <- mc.tileSet.indices)
      mc.tileSet(i) = ImageUtils.createImage("Graphics/" + dis.readLine())
    dis.close()
  }

  def loadMap(mc: MapController, map: String): Unit = {
    try {

      val reader: BufferedReader = Source.fromFile(map).bufferedReader()
      var line: String = reader.readLine
      var tokens: StringTokenizer = new StringTokenizer(line)

      // first line contains map dimensions
      mc.mapTilesX = tokens.nextToken.toInt
      mc.mapTilesY = tokens.nextToken.toInt
      mc.tileset = tokens.nextToken

      // next line may have color
      line = reader.readLine
      tokens = new StringTokenizer(line)

      // Read color info
      if (tokens.nextToken.equalsIgnoreCase("colorization")) {
        mc.hasColourEffect = true
        mc.r = tokens.nextToken.toFloat
        mc.g = tokens.nextToken.toFloat
        mc.b = tokens.nextToken.toFloat
        mc.h = tokens.nextToken.toFloat
        mc.s = tokens.nextToken.toFloat
      }

      // Skip the dots
      while (line != ".") {
        line = reader.readLine
      }

      // next line has map data
      for (layers <- 0 until 2) {
        line = reader.readLine
        tokens = new StringTokenizer(line)
        for (y <- 0 until mc.mapTilesX * mc.mapTilesY) {
          val code: String = tokens.nextToken
          if (layers == 0) {
            mc.currentMap0(y) = code.toInt
          }
          else if (layers == 1) {
            mc.currentMap1(y) = code.toInt
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
