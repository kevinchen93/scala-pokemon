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
  def movementScrolling(): Unit = {
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
    if (movespritepixels >= 16) {
      movespritepixels = 0
      walking = false
      if (up && movable_up) posY_tile -= 1
      if (down && movable_down) posY_tile += 1
      if (left && movable_left) posX_tile -= 1
      if (right && movable_right) posX_tile += 1
      up = false
      down = false
      left = false
      right = false
      footsprite = !footsprite
      if (playerPokemon1.curHp <= 0) {
        println(playerPokemon1.name + " has fainted.")
        println(s"${game.gold.name} is all out of usable Pokemon.")
        println(s"${game.gold.name} whited out.")
        currentX_loc += 42 - posX_tile
        currentY_loc += 107 - posY_tile
        posX_tile = 42
        posY_tile = 107
        playerPokemon1.healPokemon
        gold.setSprite(playerUp)
      }
      if (playerPokemon1.statusEffect eq 2 || playerPokemon1.statusEffect eq 3) {
        playerPokemon1.curHp -= 1
      }
    }
  }

  def spriteAnimations(): Unit = {
    val gold = game.gold
    //Player Sprite Animations (Up)
    if (up && movable_up) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.sprite = Sprites.playerUp
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.sprite = Sprites.playerUp
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footsprite) {
          gold.sprite = Sprites.playerUp2
        }
        else {
          gold.sprite = Sprites.playerUp1
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
        if (footsprite) {
          gold.sprite = Sprites.playerUp2
        }
        else {
          gold.sprite = Sprites.playerUp1
        }
      }
      else {
        gold.sprite = Sprites.playerUp
      }
    }
    //Player Sprite Animations (Down)
    if (down && movable_down) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.sprite = Sprites.playerDown
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.sprite = Sprites.playerDown
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footsprite) {
          gold.sprite = Sprites.playerDown2
        }
        else {
          gold.sprite = Sprites.playerDown1
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
        if (footsprite) {
          gold.sprite = Sprites.playerDown2
        }
        else {
          gold.sprite = Sprites.playerDown1
        }
      }
      else {
        gold.sprite = Sprites.playerDown
      }
    }
    //Player Sprite Animations (Left)
    if (left == true && movable_left == true) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.setSprite(playerLeft)
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.setSprite(playerLeft)
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footsprite == false) {
          gold.setSprite(playerLeft1)
        }
        else {
          gold.setSprite(playerLeft2)
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
        if (footsprite == false) {
          gold.setSprite(playerLeft1)
        }
        else {
          gold.setSprite(playerLeft2)
        }
      }
      else {
        gold.setSprite(playerLeft)
      }
    }
    //Player Sprite Animations (Right)
    if (right == true && movable_right == true) {
      if (movespritepixels >= 0 && movespritepixels < 4) {
        gold.setSprite(playerRight)
      }
      else if (movespritepixels > 4 && movespritepixels < 8) {
        gold.setSprite(playerRight)
      }
      else if (movespritepixels > 8 && movespritepixels < 12) {
        if (footsprite == false) {
          gold.setSprite(playerRight1)
        }
        else {
          gold.setSprite(playerRight2)
        }
      }
      else if (movespritepixels >= 12 && movespritepixels < 15) {
        if (footsprite == false) {
          gold.setSprite(playerRight1)
        }
        else {
          gold.setSprite(playerRight2)
        }
      }
      else {
        gold.setSprite(playerRight)
      }
    }
  }


}
