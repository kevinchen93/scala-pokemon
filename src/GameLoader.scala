import java.io._

/**
 * Created by kevinchen on 5/24/15.
 */
object GameLoader {

  def loadGame(game: Pokemon): Unit = {
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
      game.currentMap = dis.readLine
      game.currentX_loc = dis.readLine.toInt - 7
      game.currentY_loc = dis.readLine.toInt - 4
      game.posX_tile = game.currentX_loc + 7
      game.posY_tile = game.currentY_loc + 4
      game.money = dis.readLine.toInt
      game.playerPokemon1.create(dis.readLine.toInt)
      game.playerPokemon2.create(dis.readLine.toInt)
      game.playerPokemon3.create(dis.readLine.toInt)
      game.playerPokemon4.create(dis.readLine.toInt)
      game.playerPokemon5.create(dis.readLine.toInt)
      game.playerPokemon6.create(dis.readLine.toInt)
      game.pokemonparty(0) = game.playerPokemon1
      game.pokemonparty(1) = game.playerPokemon2
      game.pokemonparty(2) = game.playerPokemon3
      game.pokemonparty(3) = game.playerPokemon4
      game.pokemonparty(4) = game.playerPokemon5
      game.pokemonparty(5) = game.playerPokemon6
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
