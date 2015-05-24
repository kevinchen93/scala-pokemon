import java.awt.{Toolkit, Image}

/**
 * Created by kevinchen on 5/23/15.
 */
object ImageUtils {

  def createImage(fileName: String): Image = {
    Toolkit.getDefaultToolkit.createImage(fileName)
  }


}
