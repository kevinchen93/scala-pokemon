/**
 * Created by kevinchen on 5/23/15.
 */
class Attacks (move: String) {

  var pp: Int = 10
  var currentPP: Int = pp

  def printNoEffect() = println("It had no effect!")

  def damage(enemy: Monster): Int = move match {
    case "Growl" => {
      if (enemy.curAttack <= 0) printNoEffect()
      else {
        enemy.curAttack -= 1
        println(s"${enemy.name}'s attack has been lowered to ${enemy.curAttack} / ${enemy.attack}")
      }
      0
    }
    case "Tail Whip" => {
      if (enemy.curDef <= 0) printNoEffect()
      else {
        enemy.curDef -= 1
        println(s"${enemy.name}'s defense has been lowered to ${enemy.curDef} / ${enemy.defense}")
      }
      0
    }
    case "Thunderwave" => {
      if (enemy.statusEffect == 1) printNoEffect()
      else {
        enemy.statusEffect = 1
        println(s"${enemy.name} has been paralyzed")
      }
      0
    }
    case "Toxic" => {
      if (enemy.statusEffect == 3) printNoEffect()
      else {
        enemy.statusEffect = 3
        println(s"${enemy.name} has been poisoned")
      }
      0
    }
    case "Sleep Powder" => {
      if (enemy.statusEffect == 4) printNoEffect()
      else {
        enemy.statusEffect = 4
        println(s"${enemy.name} has fallen asleep")
      }
      0
    }
    case "Fire Spin" => {
      if (enemy.statusEffect == 2) 4
      else {
        enemy.statusEffect = 2
        println(s"${enemy.name} has been burned")
        3
      }
    }
    case "Peck" => 4
    case "Pursuit" => 4
    case "Tackle" => 5
    case "Quick Attack" => 6
    case "Dig" => 7
    case "Ember" => 5
    case "Scratch" => 7
    case "Icy Wind" => 6
    case "Thundershock" => 5
    case "Water Gun" => 7
    case _ => 7

  }



}
