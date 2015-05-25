import java.awt.event.{KeyEvent, KeyListener}

/**
 * Created by kevinchen on 5/24/15.
 */
class MyKeyListener(game: Pokemon) extends KeyListener {

  override def keyTyped(e: KeyEvent): Unit = ???
  override def keyReleased(e: KeyEvent): Unit = ???

  override def keyPressed(e: KeyEvent): Unit = {
    val keyCode = e.getKeyCode
    if (game.atTitle) {
      if (keyCode == KeyEvent.VK_ENTER) {
        game.gameTimer.setDelay(20)
        game.atTitle = false
        game.currentBGM.stop
        game.currentBGM = continuebgm
        game.currentBGM.start
        game.atContinueScreen = true
      }
    }
    else if (game.atContinueScreen) {
      keyCode match {
        case KeyEvent.VK_UP => playSelectButtonSound; if (game.concurrentMenuItem > 0) game.concurrentMenuItem -= 1
        case KeyEvent.VK_DOWN => playSelectButtonSound; if (game.concurrentMenuItem < 2) game.concurrentMenuItem += 1
        case KeyEvent.VK_Z => {
          playSelectButtonSound
          game.concurrentMenuItem match {
            case 0 => game.startgame(true) // continue
            case 1 => game.startgame(false) // new game
            case 2 => ??? // options
          }
        }
      }
    }
    else if (game.gameStarted) {

    }


  }

  private def playSelectButtonSound = game.col.playClip("Select")

}
