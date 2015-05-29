import java.awt.{Color, Graphics}

import scala.util.Random

/**
 * Created by kevinchen on 5/27/15.
 */
abstract class BattleScene(game: PokemonGameEngine,
                            pokemon: List[Monster],
                            enemies: List[Monster],
                            wildFight: Boolean) {

  var playerTurn = true
  var playerPokemon = pokemon.head
  var enemy = enemies.head
  println(s"Player's $playerPokemon")
  println(s"Wild $enemy")

  var currentSelectionMainX = 0
  var currentSelectionFightX = 0
  var currentSelectionMainY = 0
  var currentSelectionFightY = 0

  var random = new Random()

  case class Scene(name: String)

  case object Main extends Scene("Main")

  case object Fight extends Scene("Fight")

  case object Item extends Scene("Item")

  case object Pokemon extends Scene("Pokemon")

  case object Run extends Scene("Run")

  var scene: Scene = Main

  var playerWon: Boolean = false
  var pokemonfainted: Boolean = false

  // TODO check for faintedness?
  def switchPokemon(i: Int) = playerPokemon = pokemon(i)

  def enemySwitchPokemon(i: Int): Unit = {
    enemy = enemies(i)
  }


  def switchScene(newScene: Scene): Unit = {
    scene = newScene
    println(newScene.name)
  }

  def fightScene(): Unit = switchScene(Fight)

  def itemScene(): Unit = switchScene(Item)

  def pokemonScene(): Unit = switchScene(Pokemon)

  def giveEXP(): Unit = {
    playerPokemon.curExp += 47
    if (playerPokemon.curExp >= playerPokemon.exp) {
      playerPokemon.exp += 93
      playerPokemon.curExp = playerPokemon.exp - playerPokemon.curExp
      playerPokemon.level += 1
      playerPokemon.hp += 3
      playerPokemon.attack += 2
      playerPokemon.spAttack += 2
      playerPokemon.defense += 2
      playerPokemon.spDef += 2
      playerPokemon.curHp += 3
      playerPokemon.curAttack = playerPokemon.attack
      playerPokemon.curSpAttack = playerPokemon.spAttack
      playerPokemon.curDef = playerPokemon.defense
      playerPokemon.curSpDef = playerPokemon.spDef
    }
    println(s"Current EXP: ${playerPokemon.curExp} / ${playerPokemon.exp}")
  }

  private def leaveBattle(): Unit = {
    switchScene(Run)
    enemy.statusEffect = 0
    game.soundController.endBattleMusic()
    game.inBattle = false
  }

  def runScene(): Unit = {
    leaveBattle()
    println("Got away safely!")
  }

  def win(): Unit = {
    giveEXP()
    leaveBattle()
  }

  def lose(): Unit = leaveBattle()

  def whiteOut(): Unit = {
    pokemonfainted = true
    lose()
  }

  def paint(g: Graphics) {
    g.setFont(Fonts.pokefont)
    g.setColor(Color.BLACK)
    g.drawImage(Sprites.BG, 0, 0, null)

    g.drawString(playerPokemon.name, 316, 174)
    g.drawString("" + playerPokemon.level, 401, 174)
    g.drawString("" + playerPokemon.curHp, 361, 207)
    g.drawString("" + playerPokemon.hp, 403, 207)
    g.drawImage(playerPokemon.backSprite, 30, 113, null)
    g.drawString(enemy.name, 24, 26)
    g.drawString("" + enemy.level, 144, 26)
    g.drawString("" + enemy.curHp, 70, 45)
    g.drawString("" + enemy.hp, 112, 45)
    g.drawImage(enemy.frontSprite, 300, 25, null)

    // draw statuses
    if (playerPokemon.affected)
      g.drawImage(playerPokemon.statusImage(), 415, 140, null)
    if (enemy.affected)
      g.drawImage(enemy.statusImage(), 18, 60, null)

    // top-level scene of battle
    if (scene == Main) {
      paintMain(g)
    }

    // choosing a move
    if (scene == Fight) {
      paintFight(g)
    }

  }

  private def paintMain(g: Graphics): Unit = {
    g.drawImage(Sprites.battleMainBG, 0, 0, null)
    val greeting = if (wildFight) s"Wild ${enemy.name} Appeared!" else s"Trainer sent out ${enemy.name}!"
    g.drawString(greeting, 30, 260)
    g.drawString("FIGHT", 290, 260)
    g.drawString("PKMN", 400, 260)
    g.drawString("ITEM", 290, 290)
    g.drawString("RUN", 400, 290)
    if (currentSelectionMainX == 0 && currentSelectionMainY == 0) {
      g.drawImage(Sprites.arrow, 274, 240, null)
    }
    else if (currentSelectionMainX == 0 && currentSelectionMainY == 1) {
      g.drawImage(Sprites.arrow, 274, 270, null)
    }
    else if (currentSelectionMainX == 1 && currentSelectionMainY == 0) {
      g.drawImage(Sprites.arrow, 384, 240, null)
    }
    else if (currentSelectionMainX == 1 && currentSelectionMainY == 1) {
      g.drawImage(Sprites.arrow, 384, 270, null)
    }
  }

  private def paintFight(g: Graphics): Unit = {
    g.drawImage(Sprites.battleFightBG, 0, 0, null)
    g.drawString("Select a Move", 30, 260)
    g.drawString(playerPokemon.move1, 200, 260)
    g.drawString(playerPokemon.move2, 345, 260)
    g.drawString(playerPokemon.move3, 200, 290)
    g.drawString(playerPokemon.move4, 345, 290)
    if (currentSelectionFightX == 0 && currentSelectionFightY == 0) {
      g.drawImage(Sprites.arrow, 184, 240, null)
    }
    else if (currentSelectionFightX == 0 && currentSelectionFightY == 1) {
      g.drawImage(Sprites.arrow, 184, 270, null)
    }
    else if (currentSelectionFightX == 1 && currentSelectionFightY == 0) {
      g.drawImage(Sprites.arrow, 329, 240, null)
    }
    else if (currentSelectionFightX == 1 && currentSelectionFightY == 1) {
      g.drawImage(Sprites.arrow, 329, 270, null)
    }
  }


  def enemyTurn(): Unit = {
    if (!playerWon) {
      var i = 0
      if (enemy.asleep || enemy.frozen) {
        attemptToStabilize(enemy)
      }
      else {
        i = diceRoll(4) + 1
        println(s"Enemy chose move $i")
      }

      // have enemy attack
      if (doleOutAttack(enemy, playerPokemon, i)) println("Enemy's turn is over")

      enemy.statusEffect match {
        case Monster.statusBurned => {
          enemy.curHp -= 2
          println(s"${enemy.name} has been hurt by its burn")
        }
        case Monster.statusPoisoned => {
          enemy.curHp -= 2
          println(s"${enemy.name} has been hurt by its poison")
        }
      }

      playerTurn = true

    }

    def doleOutAttack(attacker: Monster, recipient: Monster, moveNumber: Int): Boolean = {
      if (attacker.paralyzed && odds(1, 2)) {
        println(s"${attacker.name} is paralyzed. It can't move.")
        return false
      }

      assume(moveNumber >= 1 && moveNumber <= 4, "move number should be 1-4")
      moveNumber match {
        case 1 => recipient.loseHp(new Attacks(attacker.move1).damage(recipient))
        case 2 => recipient.loseHp(new Attacks(attacker.move2).damage(recipient))
        case 3 => recipient.loseHp(new Attacks(attacker.move3).damage(recipient))
        case 4 => recipient.loseHp(new Attacks(attacker.move4).damage(recipient))
      }

      game.soundController.col.playClip("Damage")
      true
    }

    def diceRoll(sides: Int) = random.nextInt(sides)
    def odds(numerator: Int, denominator: Int): Boolean = diceRoll(denominator) < numerator
    def attemptToStabilize(pokemon: Monster): Boolean = {
      if (odds(2, 5)) {
        enemy.stabilizeStatus()
        true
      } else {
        enemy.reiterateStatus()
        false
      }
    }


  }


}
