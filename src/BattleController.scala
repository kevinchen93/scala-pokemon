/**
 * Created by kevinchen on 5/26/15.
 */
class BattleController(game: PokemonGameEngine) {

  var encounter: BattleScene

  def checkForFaintedPokemon(): Unit = {
    if (encounter.playerPokemon.curHp <= 0) {
      println("Player Pokemon has fainted")
      println(s"${game.gold.name} is all out of usable Pokemon!")
      println(s"${game.gold.name} whited out.")
      encounter.whiteOut

      val pc = game.playerController
      pc.currentX_loc += 42 - pc.posX_tile
      pc.currentY_loc += 107 - pc.posY_tile
      pc.posX_tile = 42
      pc.posY_tile = 107
      game.gold.sprite = Sprites.playerUp
      game.lastdir = 1
      game.pokemonparty.head.healPokemon
      game.soundController.play(SoundController.pokeCenter)
    }
    if (encounter.enemyPokemon.curHp <= 0) {
      encounter.playerWon = true
      System.out.println("Wild Pokemon has fainted")
      encounter.Win
    }
    if (!encounter.playerTurn) {
      wait(1)
      encounter.enemyTurn
    }
  }

}
