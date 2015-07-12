/**
 * Created by kevinchen on 5/23/15.
 */
trait MoveableActor extends Actor {
  def name: String
  def moveUp() = y -= 1
  def moveDown() = y += 1
  def moveLeft() = x -= 1
  def moveRight() = x += 1

  def adjacentTo(other: Player) = adjacentHorizontallyTo(other) || adjacentVerticallyTo(other)
  def adjacentHorizontallyTo(other: Player) = sameY(other) && nextToX(other)
  def adjacentVerticallyTo(other: Player) = sameX(other) && nextToY(other)

  def sameX(other: Player) = x == other.x
  def sameY(other: Player) = x == other.y

  private def nextToX(other: Player) = math.abs(x - other.x) == 1
  private def nextToY(other: Player) = math.abs(y - other.y) == 1

}
