package sound

/**
 * Created by kevinchen on 5/23/15.
 */
trait BackgroundSoundObserver {
  def soundDone(bs: BackgroundSound, allBytesRead: Boolean): Unit
}