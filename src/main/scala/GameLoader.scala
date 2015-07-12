import java.io._

/**
 * Created by kevinchen on 5/24/15.
 */
object GameLoader {

  def loadGame(game: PokemonGameEngine): Unit = {
    val file: File = new File("Data/profile.sav")
    var fis: FileInputStream = null
    var bis: BufferedInputStream = null
    var dis: DataInputStream = null
    try {
      fis = new FileInputStream(file)
      bis = new BufferedInputStream(fis)
      dis = new DataInputStream(bis)
      game.playerController.gold.name = dis.readLine
      game.playerController.gold.id = dis.readLine.toInt

      game.mapLoader.currentMap = dis.readLine
      game.mapLoader.currentX_loc = dis.readLine.toInt - 7
      game.mapLoader.currentY_loc = dis.readLine.toInt - 4
      game.mapLoader.posX_tile = game.mapLoader.currentX_loc + 7
      game.mapLoader.posY_tile = game.mapLoader.currentY_loc + 4
      game.playerController.money = dis.readLine.toInt
      for (i <- 1 to 6) {
        game.playerController.pokemonParty = Monster.create(dis.readLine().toInt) :: game.playerController.pokemonParty
      }
      println(game.playerController.gold.name + dis.readLine)
      dis.close()
    }
    catch {
      case e: FileNotFoundException => {
        e.printStackTrace()
      }
      case e: IOException => {
        e.printStackTrace()
      }
    }
  }

}
