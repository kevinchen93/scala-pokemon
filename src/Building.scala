import java.awt.{Graphics, Image}

/**
 * Created by kevinchen on 5/23/15.
 */
class Building(x: Int, y: Int, buildingType: String) extends Actor(x, y) {

  var sprite: Image = null
  var sprites = new Array[Image](16)

  private def tile(location: String, num: String): Image = {
    ImageUtils.createImage(s"Graphics/Tiles/${location}/tiles-${num}.png")
  }
  private def pokeMartTile(num: String): Image = tile("Mart", num)
  private def pokeCenterTile(num: String): Image = tile("PokeCenter", num)

  def paint(graphics: Graphics): Unit = {
    buildingType match {
      case "PokeMart" => {
        for (i <- 0 to 15) {
          sprites(i) = pokeMartTile(i.toString)
          graphics.drawImage(sprites(i), x + i * 32, y + i * 32, null)
        }
      }
      case "PokeCenter" => {
        for (i <- 0 to 19) {
          sprites(i) = pokeCenterTile(i.toString)
          graphics.drawImage(sprites(i), x + i * 32, y + i * 32, null)
        }
      }
      case _ => println(s"${getClass.getSimpleName} -- Implement")
    }
  }

  def transportLocation = 1


}
