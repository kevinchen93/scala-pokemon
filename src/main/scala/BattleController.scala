/**
 * Created by kevinchen on 5/26/15.
 */
class BattleController(game: PokemonGameEngine) {

  var encounter: Option[BattleScene] = None

  def checkForFaintedPokemon(): Unit = {
    encounter match {
      case Some(encounter) => {
        if (encounter.playerPokemon.curHp <= 0) {
          println("Player Pokemon has fainted")
          println(s"${game.playerController.gold.name} is all out of usable Pokemon!")
          println(s"${game.playerController.gold.name} whited out.")
          encounter.whiteOut

          val pc = game.mapLoader
          pc.currentX_loc += 42 - pc.posX_tile
          pc.currentY_loc += 107 - pc.posY_tile
          pc.posX_tile = 42
          pc.posY_tile = 107
          game.playerController.gold.sprite = Sprites.playerUp
          // TODO
          //      game.mapLoader.lastdir = 1
          game.playerController.pokemonParty.head.healPokemon
          game.soundController.play(SoundController.pokeCenter)
        }
        if (encounter.enemy.curHp <= 0) {
          encounter.playerWon = true
          System.out.println("Wild Pokemon has fainted")
          encounter.win()
        }
        if (!encounter.playerTurn) {
          wait(1)
          encounter.enemyTurn
        }
      }
    }

  }

}
