import java.io._

/**
 * Created by kevinchen on 5/25/15.
 */
object GameSaver {

  def saveGame(game: PokemonGameEngine): Unit = {
    var bufferedWriter: BufferedWriter = null
    try {
      val oldSave: File = new File("Data/profile.sav")
      oldSave.delete
      val newSave: File = new File("Data/profile.sav")
      newSave.createNewFile
      bufferedWriter = new BufferedWriter(new FileWriter("Data/profile.sav"))
      bufferedWriter.write(game.gold.name)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.gold.id)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.currentMap)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.posX_tile)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.posY_tile)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.money)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.playerPokemon1.getNumber)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.playerPokemon2.getNumber)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.playerPokemon3.getNumber)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.playerPokemon4.getNumber)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.playerPokemon5.getNumber)
      bufferedWriter.newLine
      bufferedWriter.write("" + game.playerPokemon6.getNumber)
      bufferedWriter.newLine
      bufferedWriter.write("'s Save File has been loaded.")
    }
    catch {
      case ex: FileNotFoundException => {
        ex.printStackTrace()
      }
      case ex: IOException => {
        ex.printStackTrace()
      }
    } finally {
      try {
        if (bufferedWriter != null) {
          bufferedWriter.flush
          bufferedWriter.close
        }
      }
      catch {
        case ex: IOException => {
          ex.printStackTrace()
        }
      }
    }
  }

}
