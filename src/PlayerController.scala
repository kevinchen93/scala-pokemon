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

  var movable_up = true
  var movable_down = true
  var movable_left = true
  var movable_right = true
  private var talkable = false
  private var collision = false
  private var footsprite = false
  private var text = ""
  private var posX = 224
  // multiple of 32
  private var posY = 118
  // -10 b/c height is 42, not 32
  var currentX_loc = 0
  // start loc of player (rows)
  var currentY_loc = 0
  // start loc of player (cols)
  var posX_tile = 0
  // player loc (rows)
  var posY_tile = 0 // player loc (cols)

  private var stepscount = 0
  var money = 2000
  var timePlayed: Long = 0
  var currentTime: Long = 0

  def updateMobilityConstraints(): Unit = {
    val ml = game.mapLoader
    movable_left = posX_tile > 0
    movable_right = posX_tile < ml.mapTilesX - 1
    movable_up = posY_tile > 0
    movable_down = posY_tile < ml.mapTilesY - 1
  }

  // Movement scrolling
  def move(): Unit = {
    if (walking) {
      movespritepixels += 1
      if (up && movable_up) {
        offsetY += 2
      }
      if (down && movable_down) {
        offsetY -= 2
      }
      if (left && movable_left) {
        offsetX += 2
      }
      if (right && movable_right) {
        offsetX -= 2
      }
    }
  }

  def movementReset(): Unit = {
    // TODO
  }

  def spriteAnimations(): Unit = {
    // TODO
  }


}
