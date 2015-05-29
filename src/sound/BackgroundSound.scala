package sound

import java.io.IOException
import javax.sound.sampled.{AudioInputStream, SourceDataLine}

/**
 * Created by kevinchen on 5/23/15.
 */
class BackgroundSound(ais: AudioInputStream, sdl: SourceDataLine, var bso: BackgroundSoundObserver) extends Thread {
  private var done: Boolean = false
  private var kill: Boolean = false

  def this(ais: AudioInputStream, sdl: SourceDataLine) = this(ais, sdl, null)

  def registerObserver(bso: BackgroundSoundObserver) = this.bso = bso

  def stopBackgroundSound(noMoreSounds: Boolean): Unit = {
    if (done) return
    done = true
    kill = noMoreSounds
  }

  override def run(): Unit = {
    var allBytesRead = false
    while (!done) {
      var nBytesRead = 0
      val abData = new Array[Byte](2048)
      while (nBytesRead != -1 && !done) {
        try {
          nBytesRead = ais.read(abData, 0, abData.length)
        }
        catch {
          case e: IOException => {
            e.printStackTrace()
          }
        }
        if (nBytesRead >= 0) {
          sdl.write(abData, 0, nBytesRead)
        }
        else {
          done = true
          allBytesRead = true
        }
      }
    }
    sdl.drain()
    sdl.stop()
    sdl.close()
    if (!kill && bso != null) {
      bso.soundDone(this, allBytesRead)
    }
  }

}