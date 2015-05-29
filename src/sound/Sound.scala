package sound

import javax.sound.sampled.{AudioInputStream, Clip, LineEvent, LineListener}

/**
 * Created by kevinchen on 5/23/15.
 */
class Sound(clip: Clip,
            ais: AudioInputStream,
            val name: String,
            val id: Int,
            jukeBox: JukeBox) extends LineListener {

  var looping = false
  clip.addLineListener(this)
  clip.open(ais)

  override def update(event: LineEvent): Unit = event.getType match {
    case LineEvent.Type.STOP => {
      if (looping) return
      clip.stop()
      clip.setFramePosition(0)
      jukeBox.makeAvailable(this)
    }
  }

  def play(): Unit = clip.start()

  def loop(numberOfLoops: Int): Unit = {
    clip.setLoopPoints(0, -1)
    looping = true
    clip.loop(numberOfLoops)
  }

  def stop(): Unit = {
    looping = false
    clip.stop()
  }

}