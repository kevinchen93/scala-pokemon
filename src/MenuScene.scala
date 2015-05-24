import java.awt._

import sound.JukeBox

/**
 * Created by kevinchen on 5/23/15.
 */
class MenuScene(game: Pokemon) {

  val col = new JukeBox
  col.loadClip("Audio/SE/Menu.wav", "Menu", 1)
  col.loadClip("Audio/SE/Select.wav", "Select", 1)

  var currentSelectionMain: Int = 2
  var currentSelectionItemX: Int = 0
  var currentSelectionItemY: Int = 0
  var currentSelectionPokeGear: Int = 0
  var currentSelectionSave: Int = 0
  var currentSelectionOption: Int = 0
  var inMain = true
  var inPokeDex = false
  var inPokemon = false
  var inBag = false
  var inPokeGear = false
  var inTrainerCard = false
  var inSave = false
  var inOption = false
  var cancelbutton = false

  private var pokefont: Font = new Font("pokesl1", Font.PLAIN, 18)
  private var arrow: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/Arrow.png"))
  private var mainMenu: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/Menu.png"))
  private var pokedex: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/pokedexbg.png"))
  private var pokemon: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/pokeselbg.png"))
  private var bag: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/BagScreen.png"))
  private var pokegear: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/Pokegearback.png"))
  private var trainercard: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/TrainerCard.png"))
  private var save: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/Save.png"))
  private var option: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/Option.png"))
  private var partyscreenfirstbox: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/Box.png"))
  private var partyscreenboxes: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/SelectionBar.png"))
  private var partyscreencancel: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/pokeselcancel.png"))
  private var partyscreencancelselect: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/pokeselcancelsel.png"))
  private var entryMap: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/MapEntry.png"))
  private var entryRadio: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/RadioEntry.png"))
  private var entryPhone: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/PhoneEntry.png"))
  private var entryExit: Image = Toolkit.getDefaultToolkit.getImage(getClass.getResource("Graphics/Pictures/ExitEntry.png"))

  def pokeDex(): Unit = {
    inMain = false
    inPokeDex = true
  }

  def pokemon(): Unit = {
    inMain = false
    inPokemon = true
    println("Pokemon")
  }

  def bag(): Unit = {
    inMain = false
    inBag = true
    println("Bag")
  }

  def pokeGear(): Unit = {
    inMain = false
    inPokeGear = true
    println("PokeGear")
  }

  def trainerCard(): Unit = {
    inMain = false
    inTrainerCard = true
    println("Trainer Card")
  }

  def save(): Unit = {
    inMain = false
    inSave = true
    println("Save")
  }

  def option(): Unit = {
    inMain = false
    inOption = true
    println("Option")
  }

  def exit(): Unit = {
    currentSelectionMain = 2
    currentSelectionItemX = 0
    currentSelectionItemY = 0
    currentSelectionSave = 0
    inMain = false
    game.inMenu = false
    println("Exit")
  }

  def paint(g: Graphics): Unit = {
    g.setFont(pokefont)
    g.setColor(Color.BLACK)
    if (inMain) {
      g.drawImage(mainMenu, 0, 0, null)
      if (currentSelectionMain == 0) {
        g.drawImage(arrow, 335, 20, null)
      }
      else if (currentSelectionMain == 1) {
        g.drawImage(arrow, 335, 52, null)
      }
      else if (currentSelectionMain == 2) {
        g.drawImage(arrow, 335, 84, null)
      }
      else if (currentSelectionMain == 3) {
        g.drawImage(arrow, 335, 116, null)
      }
      else if (currentSelectionMain == 4) {
        g.drawImage(arrow, 335, 148, null)
      }
      else if (currentSelectionMain == 5) {
        g.drawImage(arrow, 335, 180, null)
      }
      else if (currentSelectionMain == 6) {
        g.drawImage(arrow, 335, 212, null)
      }
      else if (currentSelectionMain == 7) {
        g.drawImage(arrow, 335, 244, null)
      }
    }
    if (inPokeDex) {
      g.drawImage(pokedex, 0, 0, null)
    }
    if (inPokemon) {
      g.drawImage(pokemon, 0, 0, null)
      g.drawImage(partyscreenfirstbox, 40, 20, null)
      g.drawImage(partyscreenboxes, 190, 20, null)
      g.drawImage(partyscreenboxes, 190, 70, null)
      g.drawImage(partyscreenboxes, 190, 120, null)
      g.drawImage(partyscreenboxes, 190, 170, null)
      g.drawImage(partyscreenboxes, 190, 220, null)
      if (game.pokemonparty.length == 2) {
        g.drawImage(partyscreenboxes, 190, 20, null)
      }
      g.drawImage(game.playerPokemon1.icon, 45, 30, null)
      g.drawString(game.playerPokemon1.name, 80, 120)
      if (!cancelbutton) {
        g.drawImage(partyscreencancel, 370, 280, null)
      }
      else {
        g.drawImage(partyscreencancelselect, 370, 280, null)
      }
    }
    if (inBag) {
      g.drawImage(bag, 0, 0, null)
    }
    if (inPokeGear) {
      g.drawImage(pokegear, 0, 0, null)
      if (currentSelectionPokeGear == 0) {
        g.drawImage(entryMap, 0, 0, null)
      }
      else if (currentSelectionPokeGear == 1) {
        g.drawImage(entryRadio, 0, 0, null)
      }
      else if (currentSelectionPokeGear == 2) {
        g.drawImage(entryPhone, 0, 0, null)
      }
      else if (currentSelectionPokeGear == 3) {
        g.drawImage(entryExit, 0, 0, null)
      }
    }
    if (inTrainerCard) {
      g.drawImage(trainercard, 0, 0, null)
      g.drawImage(game.cardSprite, 320, 100, null)
      g.drawString("ID:     " + game.gold.getID, 295, 54)
      g.drawString("Name:     " + game.gold.getName, 64, 93)
      g.drawString("Money:                 $" + game.money, 64, 150)
      g.drawString("Pokedex:                      " + game.gold.getPokemonOwned, 64, 183)
      if (game.seconds < 10) {
        g.drawString("Time:              0" + game.hours + ": 0" + game.minutes + ": 0" + game.seconds, 64, 213)
      }
      else {
        g.drawString("Time:              0" + game.hours + ": 0" + game.minutes + ": " + game.seconds, 64, 213)
      }
    }
    if (inSave) {
      g.drawImage(save, 0, 0, null)
      g.drawString("" + game.gold.getName, 100, 68)
      g.drawString("" + game.badges, 100, 101)
      g.drawString("1", 110, 134)
      g.drawString("0" + game.hours + ": 0" + game.minutes + ": " + game.seconds, 76, 166)
      if (currentSelectionSave == 0) {
        g.drawImage(arrow, 394, 148, null)
      }
      else if (currentSelectionSave == 1) {
        g.drawImage(arrow, 394, 180, null)
      }
    }
    if (inOption) {
      g.drawImage(option, 0, 0, null)
      if (currentSelectionOption == 0) {
        g.drawImage(arrow, 22, 85, null)
      }
      else if (currentSelectionOption == 1) {
        g.drawImage(arrow, 22, 117, null)
      }
      else if (currentSelectionOption == 2) {
        g.drawImage(arrow, 22, 149, null)
      }
      else if (currentSelectionOption == 3) {
        g.drawImage(arrow, 22, 181, null)
      }
      else if (currentSelectionOption == 4) {
        g.drawImage(arrow, 22, 213, null)
      }
      else if (currentSelectionOption == 5) {
        g.drawImage(arrow, 22, 245, null)
      }
    }

  }


}
