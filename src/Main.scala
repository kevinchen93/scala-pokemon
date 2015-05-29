import java.awt.Toolkit
import javax.swing.JFrame

/**
 * Created by kevinchen on 5/25/15.
 */
object Main {

  def main (args: Array[String]) {
    val frame = new JFrame("Pokemon: Metallic Silver")

    val game = new PokemonGameEngine
    frame.add(game)
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
    frame.setResizable(false)
    frame.pack()

    // Center stuff on screen
    val dim = Toolkit.getDefaultToolkit.getScreenSize
    val w = frame.getSize.width
    val h = frame.getSize.height
    val x = (dim.width - w) / 2
    val y = (dim.height - h) / 2
    frame.setLocation(x, y)

    // Set focus
    frame.setVisible(true)
    game.requestFocus(true)


  }
}
