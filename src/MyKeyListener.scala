import java.awt.event.{KeyEvent, KeyListener}

/**
 * Created by kevinchen on 5/24/15.
 */
class MyKeyListener(game: PokemonGameEngine) extends KeyListener {

  override def keyTyped(e: KeyEvent): Unit = {}

  override def keyReleased(e: KeyEvent): Unit = {
    val keyCode = e.getKeyCode
    keyCode match {
      case KeyEvent.VK_UP => game.lastdir = 1
      case KeyEvent.VK_DOWN => game.lastdir = 2
      case KeyEvent.VK_LEFT => game.lastdir = 3
      case KeyEvent.VK_RIGHT => game.lastdir = 4
    }
  }

  override def keyPressed(e: KeyEvent): Unit = {
    val keyCode = e.getKeyCode
    if (game.atTitle) processKeyPressedAtTitleScreen(keyCode)
    else if (game.atContinueScreen) processKeyPressedAtContinueScreen(keyCode)
    else if (game.gameStarted) processKeyPressedInGame(keyCode)
  }

  private def processKeyPressedAtTitleScreen(keyCode: Int): Unit = {
    if (keyCode == KeyEvent.VK_ENTER) {
      game.gameTimer.setDelay(20)
      game.atTitle = false
      game.currentBGM.stop
      game.currentBGM = continuebgm
      game.currentBGM.start
      game.atContinueScreen = true
    }
  }

  private def processKeyPressedAtContinueScreen(keyCode: Int): Unit = {
    keyCode match {
      case KeyEvent.VK_UP => playSelectButtonSound; if (game.concurrentMenuItem > 0) game.concurrentMenuItem -= 1
      case KeyEvent.VK_DOWN => playSelectButtonSound; if (game.concurrentMenuItem < 2) game.concurrentMenuItem += 1
      case KeyEvent.VK_Z => {
        playSelectButtonSound
        game.concurrentMenuItem match {
          case 0 => game.start(true) // continue
          case 1 => game.start(false) // new game
          case 2 => ??? // options
        }
      }
    }
  }

  private def directionFromKeyCode(keyCode: Int) = keyCode match {
    case KeyEvent.VK_UP => Up
    case KeyEvent.VK_DOWN => Down
    case KeyEvent.VK_LEFT => Left
    case KeyEvent.VK_RIGHT => Right
    case _ => null
  }

  private def isCardinalDirection(keyCode: Int): Boolean = keyCode match {
    case KeyEvent.VK_UP => true
    case KeyEvent.VK_DOWN => true
    case KeyEvent.VK_LEFT => true
    case KeyEvent.VK_RIGHT => true
    case _ => false
  }

  private def processKeyPressedInGame(keyCode: Int): Unit = {
    if (!game.inMenu && game.movable && !game.inBattle) {
      if (!game.walking) {
        if (isCardinalDirection(keyCode)) {
          game.crashTest(currentMap0)
          game.crashTest(currentMap1)
          game.collision = true
          val direction = directionFromKeyCode(keyCode)
          if (game.movableInDirection(direction)) {
            game.moveInDirection(direction)
          } else {
            game.gold.sprite = game.playerUp
          }
        }
        if (keyCode == KeyEvent.VK_UP) {
          if (movable_up) {
            up = true
            walking = true
          }
          else {
            gold.setSprite(playerUp)
          }
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
          crashTest(currentMap0)
          crashTest(currentMap1)
          collision = true
          if (movable_down == true) {
            down = true
            walking = true
          }
          else {
            gold.setSprite(playerDown)
          }
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
          crashTest(currentMap0)
          crashTest(currentMap1)
          collision = true
          if (movable_left == true) {
            left = true
            walking = true
          }
          else {
            gold.setSprite(playerLeft)
          }
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
          crashTest(currentMap0)
          crashTest(currentMap1)
          collision = true
          if (movable_right == true) {
            right = true
            walking = true
          }
          else {
            gold.setSprite(playerRight)
          }
        }
        else if (keyCode == KeyEvent.VK_ENTER) {
          System.out.println("Menu Button")
          col.playClip("Menu")
          menu.inMain = true
          inMenu = true
        }
        if (keyCode == KeyEvent.VK_Z) {
          System.out.println("Action Button")
          if (disable_talk == false) {
            talkable = !talkable
            movable_up = !movable_up
            movable_down = !movable_down
            movable_left = !movable_left
            movable_right = !movable_right
          }
        }
        else if (keyCode == KeyEvent.VK_X) {
          System.out.println("Cancel Button")
        }
      }
    }
  }

  private def playSelectButtonSound = game.col.playClip("Select")

}
