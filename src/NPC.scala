import java.awt.Image

/**
 * Created by kevinchen on 5/23/15.
 */
class NPC private(x: Int,
                  y: Int,
                  name: String,
                  text: String,
                  sprite: Image,
                  battleSprite: Image)
  extends MoveableActor(x, y) {

  var battleText = text
  var charset: Array[Image]
  var direction = 1

  def width = sprite.getWidth(null) / 4
  def height = sprite.getHeight(null) / 4


  override def act(): Unit = ???

  override def move(): Unit = ???

  def canTalkTo(other: Player) = (sameX(other) && nextToY(other)) || (sameY(other) && nextToX(other))

  def text(other: Player) = {
    if ((sameX(other) && nextToY(other)) || (sameY(other) && nextToX(other))) {
      text
    } else ""
  }

  def textLength = text.length

}
