/**
 * Created by kevinchen on 5/23/15.
 */
abstract class Actor (val x: Int, val y: Int) {
  def originalX: Int = x
  def originalY: Int = y
  def act()
  def move()
  def getText = ""
}
