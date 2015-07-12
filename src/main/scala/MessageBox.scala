import java.awt.Image

/**
 * Created by kevinchen on 5/23/15.
 */
class MessageBox(text: String, style: Int) {

  var messageBox: Image = ImageUtils.createImage("Graphics/Pictures/Message_Text.png")

  def displayTest() = ???

  def messageStyle: Image = style match {
    case 0 => messageBox // regular text box
    case 1 => messageBox // sign post
    case _ => messageBox
  }

}
