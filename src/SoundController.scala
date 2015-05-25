import sound.JukeBox

/**
 * Created by kevinchen on 5/25/15.
 */
object SoundController {
  val title: MidiPlayer = new MidiPlayer("Audio/BGM/Title.mid", true)
  val continueBgm: MidiPlayer = new MidiPlayer("Audio/BGM/Continue.mid", true)
  val newBarkTown: MidiPlayer = new MidiPlayer("Audio/BGM/NewBarkTown.mid", true)
  val route29: MidiPlayer = new MidiPlayer("Audio/BGM/Route29.mid", true)
  val cherryGroveCity: MidiPlayer = new MidiPlayer("Audio/BGM/CherrygroveCity.mid", true)
  val route30: MidiPlayer = new MidiPlayer("Audio/BGM/Route30.mid", true)
  val violetCity: MidiPlayer = new MidiPlayer("Audio/BGM/VioletCity.mid", true)
  val pokeCenter: MidiPlayer = new MidiPlayer("Audio/BGM/PokemonCenter.mid", true)
  val pokeMart: MidiPlayer = new MidiPlayer("Audio/BGM/PokeMart.mid", true)
  val gym: MidiPlayer = new MidiPlayer("Audio/BGM/Gym.mid", true)
  val championBGM: MidiPlayer = new MidiPlayer("Audio/BGM/ChampionBattle.mid", true)
  val battleBGM: MidiPlayer = new MidiPlayer("Audio/BGM/WildBattle.mid", true)
  val victoryJingle: MidiPlayer = new MidiPlayer("Audio/SE/Wild_Victory.mid", false)
}

class SoundController {

  // Sound effects
  val col = new JukeBox()
  col.loadClip("Audio/SE/Collision.wav", "Collision", 1)
  col.loadClip("Audio/SE/Select.wav", "Select", 1)
  col.loadClip("Audio/SE/Menu.wav", "Menu", 1)
  col.loadClip("Audio/SE/Damage.wav", "Damage", 1)

  // Background music
  var currentBGM: MidiPlayer = null
  var lastBGM: MidiPlayer = null

  def play(x: MidiPlayer): Unit = {
    lastBGM = currentBGM
    if (currentBGM != null) currentBGM.stop()
    currentBGM = x
    currentBGM.start()
  }
}
