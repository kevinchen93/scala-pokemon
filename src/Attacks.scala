/**
 * Created by kevinchen on 5/23/15.
 */
class Attacks (move: String) {

  var pp: Int = 10
  var currentPP: Int = pp

  def damage(enemy: Monsters): Int = move match {
    case "Growl" => 6
    case _ => 7
  }



}
