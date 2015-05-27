/**
 * Created by kevinchen on 5/26/15.
 */
class PlayerController(game: PokemonGameEngine) {
  private var movespritepixels: Int = 0
  private var walking: Boolean = false
  private var up: Boolean = false
  private var down: Boolean = false
  private var left: Boolean = false
  private var right: Boolean = false
  var movable_up: Boolean = true
  var movable_down: Boolean = true
  var movable_left: Boolean = true
  var movable_right: Boolean = true
  private var talkable: Boolean = false
  private var collision: Boolean = false
  private var footsprite: Boolean = false
  private var text: String = ""
  private var posX: Int = 224 // multiple of 32
  private var posY: Int = 118 // -10 b/c height is 42, not 32
  var currentX_loc: Int = 0 // start loc of player (rows)
  var currentY_loc: Int = 0 // start loc of player (cols)
  var posX_tile: Int = 0 // player loc (rows)
  var posY_tile: Int = 0 // player loc (cols)

  private var stepscount: Int = 0
  var money: Int = 2000
  var timePlayed: Long = 0
  var currentTime: Long = 0
}
