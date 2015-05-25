

import java.awt.{Color, Graphics, Font}

import sound.JukeBox

import scala.util.Random

/**
 * Created by kevinchen on 5/24/15.
 */
class TrainerBattleScene(game: PokemonGameEngine,
                         trainer: NPC,
                         var myPokemon: List[Monsters],
                         var enemyPokemon: List[Monsters],
                         var battleItems: List[Items]) {

  var playerTurn = true
  val col = new JukeBox()
  col.loadClip("Audio/SE/Damage.wav", "Damage", 1)

  var currPokemon = myPokemon.head
  var currEnemyPokemon = enemyPokemon.head

  println(s"Player's Pokemon: ${pokemonDiagnosis(currPokemon)}")
  println(s"Wild Pokemon: ${pokemonDiagnosis(currEnemyPokemon)}")

  var currentSelectionMainX = 0
  var currentSelectionFightX = 0
  var currentSelectionMainY = 0
  var currentSelectionFightY = 0

  var inMain = true
  var inFight = false
  var inItem = false
  var inPokemon = false
  var inRun = false

  var playerWon = false
  var pokemonFainted = false

  var random = new Random()

  def diceRoll(sides: Int) = random.nextInt(sides)

  def pokemonDiagnosis(p: Monsters) = s"${p.name} Level: ${p.level} HP: ${p.curHp} / ${p.hp}"

  def fight(): Unit = {
    inMain = false
    inFight = true
    println("Fight")
  }

  def item(): Unit = {
    inItem = true
    println("Item")
  }

  def pokemon(): Unit = {
    inPokemon = true
    println("Pokemon")
  }

  def switchPokemon(i: Int) = currPokemon = myPokemon(i)

  def enemySwitchPokemon(i: Int) = currEnemyPokemon = enemyPokemon(i)

  def giveExp(playerPokemon: Monsters): Unit = {
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

  def leaveBattle(): Unit = {
    inMain = false
    inRun = true
    currEnemyPokemon.statusEffect = 0
    game.currentBGM.stop()
    game.currentBGM = game.lastBGM
    game.currentBGM.start()
    game.inBattle = false
    println("Got away safely!")
  }

  def win(): Unit = {
    giveExp(currPokemon)
    leaveBattle()
  }

  def lose() = leaveBattle()

  def whiteOut(): Unit = {
    pokemonFainted = true
    lose()
  }

  def enemyTurn(): Unit = {
    if (!playerWon) {
      var i = 0
      if (currEnemyPokemon.asleep || currEnemyPokemon.frozen) {
        attemptToStabilize(currEnemyPokemon)
      }
      else {
        i = diceRoll(4) + 1
        println(s"Enemy chose move $i")
      }

      // have enemy attack
      if (doleOutAttack(currEnemyPokemon, currPokemon, i)) println("Enemy's turn is over")

      currEnemyPokemon.statusEffect match {
        case Monsters.statusBurned => {
          currEnemyPokemon.curHp -= 2
          println(s"${currEnemyPokemon.name} has been hurt by its burn")
        }
        case Monsters.statusPoisoned => {
          currEnemyPokemon.curHp -= 2
          println(s"${currEnemyPokemon.name} has been hurt by its poison")
        }
      }

      playerTurn = true

    }
  }

  def doleOutAttack(attacker: Monsters, recipient: Monsters, moveNumber: Int): Boolean = {
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

    col.playClip("Damage")
    true
  }

  def odds(numerator: Int, denominator: Int): Boolean = diceRoll(denominator) < numerator

  private def attemptToStabilize(pokemon: Monsters): Boolean = {
    if (odds(2, 5)) {
      currEnemyPokemon.stabilizeStatus()
      true
    } else {
      currEnemyPokemon.reiterateStatus()
      false
    }
  }

  private val pokeFont = new Font("pokesl1", Font.PLAIN, 18)
  private val BG = ImageUtils.createImage("Graphics/Pictures/BG.png")
  private val battleMainBG = ImageUtils.createImage("Graphics/Pictures/Battle.png")
  private val battleFightBG = ImageUtils.createImage("Graphics/Pictures/Battle2.png")
  private val arrow = ImageUtils.createImage("Graphics/Pictures/Arrow.png")
  private val statusPAR = ImageUtils.createImage("Graphics/Pictures/StatusPAR.png")
  private val statusBRN = ImageUtils.createImage("Graphics/Pictures/StatusBRN.png")
  private val statusPSN = ImageUtils.createImage("Graphics/Pictures/StatusPSN.png")
  private val statusSLP = ImageUtils.createImage("Graphics/Pictures/StatusSLP.png")
  private val statusFRZ = ImageUtils.createImage("Graphics/Pictures/StatusFRZ.png")

  private def paintStatus(pokemon: Monsters, g: Graphics, x: Int, y: Int): Unit = {
    pokemon.statusEffect match {
      case Monsters.statusParalyzed => g.drawImage(statusPAR, x, y, null)
      case Monsters.statusBurned => g.drawImage(statusBRN, x, y, null)
      case Monsters.statusPoisoned => g.drawImage(statusPSN, x, y, null)
      case Monsters.statusAsleep => g.drawImage(statusSLP, x, y, null)
      case Monsters.statusFrozen => g.drawImage(statusFRZ, x, y, null)
      //      case _ => println("what?")
    }
  }

  def paint(g: Graphics): Unit = {
    g.setFont(pokeFont)
    g.setColor(Color.BLACK)
    g.drawImage(BG, 0, 0, null)

    // HUD
    g.drawString(currPokemon.name, 316, 174)
    g.drawString("" + currPokemon.level, 401, 174)
    g.drawString("" + currPokemon.curHp, 361, 207)
    g.drawString("" + currPokemon.hp, 403, 207)
    g.drawImage(currPokemon.backSprite(), 30, 113, null)
    g.drawString(currEnemyPokemon.name, 24, 26)
    g.drawString("" + currEnemyPokemon.level, 144, 26)
    g.drawString("" + currEnemyPokemon.curHp, 70, 45)
    g.drawString("" + currEnemyPokemon.hp, 112, 45)
    g.drawImage(currEnemyPokemon.frontSprite(), 300, 25, null)

    // Status Effect Icons
    paintStatus(currPokemon, g, 415, 140)
    paintStatus(currEnemyPokemon, g, 18, 60)

    // Battle Main Interface
    if (inMain) {
      g.drawImage(battleMainBG, 0, 0, null)
      g.drawString("Trainer sent out " + currEnemyPokemon.name + "!", 30, 260)
      g.drawString("FIGHT", 290, 260)
      g.drawString("PKMN", 400, 260)
      g.drawString("ITEM", 290, 290)
      g.drawString("RUN", 400, 290)
      if (currentSelectionMainX == 0 && currentSelectionMainY == 0) {
        g.drawImage(arrow, 274, 240, null)
      }
      else if (currentSelectionMainX == 0 && currentSelectionMainY == 1) {
        g.drawImage(arrow, 274, 270, null)
      }
      else if (currentSelectionMainX == 1 && currentSelectionMainY == 0) {
        g.drawImage(arrow, 384, 240, null)
      }
      else if (currentSelectionMainX == 1 && currentSelectionMainY == 1) {
        g.drawImage(arrow, 384, 270, null)
      }
    }
    // Battle Fight Interface
    if (inFight) {
      g.drawImage(battleFightBG, 0, 0, null)
      g.drawString("Select a Move", 30, 260)
      g.drawString(currPokemon.move1, 200, 260)
      g.drawString(currPokemon.move2, 345, 260)
      g.drawString(currPokemon.move3, 200, 290)
      g.drawString(currPokemon.move4, 345, 290)
      if (currentSelectionFightX == 0 && currentSelectionFightY == 0) {
        g.drawImage(arrow, 184, 240, null)
      }
      else if (currentSelectionFightX == 0 && currentSelectionFightY == 1) {
        g.drawImage(arrow, 184, 270, null)
      }
      else if (currentSelectionFightX == 1 && currentSelectionFightY == 0) {
        g.drawImage(arrow, 329, 240, null)
      }
      else if (currentSelectionFightX == 1 && currentSelectionFightY == 1) {
        g.drawImage(arrow, 329, 270, null)
      }
    }
    /*if (inRun == true) {
      g.drawString("Got away successfully!", 30, 260);
    }
    if (pokemonfainted == true) {
      g.drawString(game.gold.getName() + " is all out of usable Pokemon!", 30, 260);
    }*/
  }


}
