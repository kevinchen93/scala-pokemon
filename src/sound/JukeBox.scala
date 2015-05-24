package sound

import java.io.{IOException, InputStream}
import javax.sound.sampled._

import scala.collection.immutable.HashMap

/**
 * Created by kevinchen on 5/23/15.
 */
class JukeBox {
  private var bs: BackgroundSound = null

  // map sound file names to sounds
  private var availableClips: Map[String, List[Sound]] = null

  // map sound ids to sounds
  private var playingClips: Map[Int, Sound] = null
  private var nextClip: Int = 0
  private var debug: Boolean = false

  def this() {
    this()
    availableClips = new HashMap[String, List[Sound]]
    playingClips = new HashMap[Int, Sound]
  }

  def setDebug(b: Boolean) {
    debug = b
  }

  def loadClip(resourcePath: String, soundName: String, howMany: Int): Boolean = {
    for (i <- 0 until howMany) {
      try {
        val is: InputStream = getClass.getResourceAsStream(resourcePath)
        assert(is != null, "Can't find sound")

        val loaded: Boolean = loadClip(is, soundName)
        assert(loaded, "Can't load sound")
      }
      catch {
        case e: Exception => {
          e.printStackTrace()
          return false
        }
      }
    }
    true
  }

  private def loadClip(is: InputStream, nameWithExtension: String): Boolean = {
    val name = trimExtension(nameWithExtension)

    // add list of sounds if not already in map
    if (!availableClips.contains(name)) {
      availableClips += name -> Nil
    }

    var list: List[Sound] = availableClips(name)
    var audioInputStream: AudioInputStream = null
    try {
      audioInputStream = AudioSystem.getAudioInputStream(is)
    }
    catch {
      case e: Exception => {
        e.printStackTrace()
      }
    }

    assert(audioInputStream != null, "Can't load audio input stream")
    val format: AudioFormat = audioInputStream.getFormat
    val info: DataLine.Info = new DataLine.Info(classOf[Clip], format)
    var clip: Sound = null
    try {
      nextClip += 1
      clip = new Sound(AudioSystem.getLine(info).asInstanceOf[Clip], audioInputStream, name, nextClip, this)
      list = clip :: list
      audioInputStream.close()
    }
    catch {
      case e: LineUnavailableException => {
        e.printStackTrace()
        return false
      }
      case e: IOException => {
        e.printStackTrace()
        return false
      }
    }
    true
  }

  private def trimExtension(name: String): String = {
    val last: Int = name.lastIndexOf(".")
    assert(last != 0, "can't start a name with a dot")
    if (last > -1) name.substring(0, last) else name
  }

  def playClip(name: String): Int = {
    playClip(name, 1)
  }

  // TODO synchronized
  def playClip(nameWithExtension: String, numberOfLoops: Int): Int = {
    val name = trimExtension(nameWithExtension)
    if (!availableClips.contains(name)) {
      return -1
    }
    val clips: List[Sound] = availableClips(name)
    println("gonna playClip " + name + "" + " from the " + clips.size + " available copies")
    println(Clip.LOOP_CONTINUOUSLY + "")
    if (clips.isEmpty) {
      return -1
    }
    val clip: Sound = clips.drop(1).head
    playingClips += clip.id -> clip
    if (numberOfLoops == 1) {
      clip.play()
    }
    else {
      print("continuous looping call")
      clip.loop(numberOfLoops)
    }
    clip.id
  }

  def getSoundLength(is: InputStream): Int = {
    var ais: AudioInputStream = null
    try {
      ais = AudioSystem.getAudioInputStream(is)
    }
    catch {
      case e: UnsupportedAudioFileException => {
        e.printStackTrace()
      }
      case e: IOException => {
        e.printStackTrace()
      }
    }
    assert(ais != null, "Can't load audio input stream")
    val frames: Long = ais.getFrameLength
    val format: AudioFormat = ais.getFormat
    val framesPerSecond: Float = format.getFrameRate
    val seconds: Int = (frames / framesPerSecond).toInt
    seconds
  }

  // TODO synchronized
  def playBackground(is: InputStream): BackgroundSound = {
    if (bs != null) {
      bs.stopBackgroundSound(false)
    }
    var ais: AudioInputStream = null
    try {
      ais = AudioSystem.getAudioInputStream(is)
    }
    catch {
      case e: UnsupportedAudioFileException => {
        e.printStackTrace()
      }
      case e: IOException => {
        e.printStackTrace()
      }
    }
    assert(ais != null, "Can't load audio input stream")
    val format: AudioFormat = ais.getFormat
    val info: DataLine.Info = new DataLine.Info(classOf[SourceDataLine], format)
    var background: SourceDataLine = null
    try {
      background = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]
    }
    catch {
      case e: LineUnavailableException => {
        e.printStackTrace()
      }
    }
    try {
      background.open()
    }
    catch {
      case e: LineUnavailableException => {
        e.printStackTrace()
      }
    }
    background.start()
    bs = new BackgroundSound(ais, background)
    bs
  }

  def stopCurrentBackgroundSound(noMoreSounds: Boolean) {
    if (bs != null) {
      bs.stopBackgroundSound(noMoreSounds)
    }
  }

  // TODO synchronized
  def makeAvailable(sound: Sound) {
    if (availableClips.contains(sound.name)) {
      playingClips = playingClips - sound.id
      // prepend sound to list of available clips
      // TODO does order matter here? should we be appending?
      sound :: availableClips(sound.name)
    }
  }

  // TODO synchronized
  def stopClip(id: Int) = if (playingClips.contains(id)) playingClips(id).stop()

  // TODO synchronized
  def stopAllClips() = playingClips.foreach((e: (Int, Sound)) => e._2.stop())

  // TODO synchronized
  def close() {
    stopAllClips()
    stopCurrentBackgroundSound(true)
  }

}
