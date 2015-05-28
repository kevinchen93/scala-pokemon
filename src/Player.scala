import java.awt.Image

import scala.util.Random

/**
 * Created by kevinchen on 5/23/15.
 */
class Player(var x: Int, var y: Int, name: String, var sprite: Image) extends MoveableActor(x, y) {

  var pokemonOwned = 1
  var random: Random = new Random()
  var id: Int = randomId()
  var secretId: Int = randomId()

  private def randomId() = 5 + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9) + random.nextInt(9)

  def crashTest(other: NPC): Int = {
    // if other is above, if ((other.getCurrentY + 1) eq getCurrentY)  and same X, return 1
    // if other is below, 2
    // if other is right, if ((other.getCurrentX + 1) eq getCurrentX) , return 3
    // if other is left, 4 TODO
    0
  }

  def crashTest(other: StaticTile): Int = {
    0 //same as above TODO
  }

  def crashTest(other: Building): Int = 0 // TODO same as above



}
