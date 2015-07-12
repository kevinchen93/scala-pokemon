

/**
 * Created by kevinchen on 5/26/15.
 */
class PlayerController(game: PokemonGameEngine) {

  var name = "Gold"
  val gold = new Player(10, 9, name, Sprites.player)
  var pokemonParty = List[Monster]()

  var stepsCount = 0
  var money = 2000
  var timePlayed: Long = 0
  var currentTime: Long = 0

  val mainItems = Array.ofDim[UsableItem](33, 99)

  def seedMoney(): Unit = money = 2000

  def seedItems(): Unit = {
    mainItems(0)(3) = Items.Potion
  }

  def seedPokemon(): Unit = {
    pokemonParty = List(
      Monster.create(25),
      Monster.create(0),
      Monster.create(0),
      Monster.create(0),
      Monster.create(0),
      Monster.create(0)
    )
  }

}
