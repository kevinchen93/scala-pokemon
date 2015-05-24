/**
 * Created by kevinchen on 5/23/15.
 */
abstract class MoveableActor(x: Int, y: Int) extends Actor(x, y) {

  def moveUp() = y -= 1
  def moveDown() = y += 1
  def moveLeft() = x -= 1
  def moveRight() = x += 1
  def sameX(other: Player) = x == other.x
  def sameY(other: Player) = x == other.y
  def nextToX(other: Player) = math.abs(x - other.x) == 1
  def nextToY(other: Player) = math.abs(y - other.y) == 1

}
