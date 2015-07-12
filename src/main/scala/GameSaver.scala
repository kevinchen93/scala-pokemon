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
      val pc = game.playerController
      val ml = game.mapLoader
      bufferedWriter.write(pc.gold.name)
      bufferedWriter.newLine
      bufferedWriter.write("" + pc.gold.id)
      bufferedWriter.newLine
      bufferedWriter.write("" + ml.currentMap)
      bufferedWriter.newLine
      bufferedWriter.write("" + ml.posX_tile)
      bufferedWriter.newLine
      bufferedWriter.write("" + ml.posY_tile)
      bufferedWriter.newLine
      bufferedWriter.write("" + pc.money)
      bufferedWriter.newLine
      for (i <- 0 to 5) {
        bufferedWriter.write("" + pc.pokemonParty(i).number)
        bufferedWriter.newLine
      }
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
