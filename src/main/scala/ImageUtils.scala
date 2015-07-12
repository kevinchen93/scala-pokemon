import java.awt.{Image, Toolkit}

/**
 * Created by kevinchen on 5/23/15.
 */
object ImageUtils {

  def createImage(fileName: String): Image = {
    Toolkit.getDefaultToolkit.createImage(fileName)
  }


}
