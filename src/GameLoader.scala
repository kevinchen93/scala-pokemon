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
      game.gold.name = dis.readLine
      game.gold.id = dis.readLine.toInt
      val pc = game.playerController
      game.playerController.currentMap = dis.readLine
      pc.currentX_loc = dis.readLine.toInt - 7
      pc.currentY_loc = dis.readLine.toInt - 4
      pc.posX_tile = pc.currentX_loc + 7
      pc.posY_tile = pc.currentY_loc + 4
      game.money = dis.readLine.toInt
      for (i <- 1 to 6) {
        game.pokemonparty = Monsters.create(dis.readLine().toInt) :: game.pokemonparty
      }
      println(game.gold.name + dis.readLine)
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
