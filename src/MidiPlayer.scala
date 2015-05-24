import java.io.IOException
import java.net.MalformedURLException
import javax.sound.midi._

/**
 * Created by kevinchen on 5/23/15.
 */
class MidiPlayer(fileName: String, bgm: Boolean) {

  var sequencer: Sequencer = null

  def start(): Unit = {
    try {
      val sequence: Sequence = MidiSystem.getSequence(getClass.getResource(fileName))
      sequencer = MidiSystem.getSequencer
      sequencer.open()
      sequencer.setSequence(sequence)
      if (bgm) {
        sequencer.setLoopCount(999)
      }
      sequencer.start()
    }
    catch {
      case e: MalformedURLException => {
      }
      case e: IOException => {
      }
      case e: MidiUnavailableException => {
      }
      case e: InvalidMidiDataException => {
      }
    }
  }

  def stop(): Unit = {
    sequencer.stop()
  }

}
