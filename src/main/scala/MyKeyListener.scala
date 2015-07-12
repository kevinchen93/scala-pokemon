import java.awt.event.{KeyEvent, KeyListener}

import scala.util.Random

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
      game.soundController.currentBGM.stop
      game.soundController.currentBGM = SoundController.continueBgm
      game.soundController.currentBGM.start
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

    // GAME SCENE
    if (!game.inMenu && game.movable && !game.inBattle) {
      processGameScene(keyCode)
    }

    // MENU SCENE
    if (game.inMenu) {
      processMenuScene(keyCode)
    }

    // BATTLE SCENE
    if (game.inBattle) {
      processBattleScene(keyCode)
    }

    def processGameScene(keyCode: Int): Unit = {
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

    def processMenuScene(keyCode: Int): Unit = {
      val menu = game.menu
      if (menu.inMain) {

        keyCode match {
          case KeyEvent.VK_UP => {
            if (menu.currentSelectionMain > 0) {
              menu.currentSelectionMain -= 1
            }
            playSelectButtonSound
          }
          case KeyEvent.VK_DOWN => {
            if (menu.currentSelectionMain < 7) {
              menu.currentSelectionMain += 1
            }
            playSelectButtonSound
          }
          case KeyEvent.VK_Z => {
            menu.currentSelectionMain match {
              case 0 => menu.pokeDex()
              case 1 => menu.pokemon()
              case 2 => menu.bag()
              case 3 => menu.pokeGear()
              case 4 => menu.trainerCard()
              case 5 => menu.save()
              case 6 => menu.option()
              case 7 => menu.exit()
            }
            playSelectButtonSound
          }
          case KeyEvent.VK_X => {
            menu.exit()
            playSelectButtonSound
          }
        }
      }

      if (menu.inPokeDex) {
        if (keyCode == KeyEvent.VK_X) {
          menu.inPokeDex = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
      if (menu.inPokemon) {
        if (keyCode == KeyEvent.VK_X) {
          menu.inPokemon = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
      if (menu.inBag) {
        if (keyCode == KeyEvent.VK_X) {
          menu.inBag = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
      if (menu.inPokeGear) {
        if (keyCode == KeyEvent.VK_UP) {
          if (menu.currentSelectionPokeGear > 0) {
            menu.currentSelectionPokeGear -= 1
          }
          playSelectButtonSound
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
          if (menu.currentSelectionPokeGear < 3) {
            menu.currentSelectionPokeGear += 1
          }
          playSelectButtonSound
        }
        if (keyCode == KeyEvent.VK_Z) {
          menu.currentSelectionPokeGear match {
            case 0 => println("Map")
            case 1 => println("Radio")
            case 2 => println("Phone")
            case 3 => {
              println("Exit")
              menu.currentSelectionPokeGear = 0
              menu.inPokeGear = false
              menu.inMain = true
            }
          }
          playSelectButtonSound
        }
        if (keyCode == KeyEvent.VK_X) {
          menu.inPokeGear = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
      if (menu.inTrainerCard) {
        if (keyCode == KeyEvent.VK_X) {
          menu.inTrainerCard = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
      if (menu.inSave) {
        if (keyCode == KeyEvent.VK_UP) {
          menu.currentSelectionSave = 0
          playSelectButtonSound
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
          menu.currentSelectionSave = 1
          playSelectButtonSound
        }
        if (keyCode == KeyEvent.VK_Z) {
          if (menu.currentSelectionSave eq 0) {
            game.save
            println(name + "'s Game has been saved!")
          }
          else {
            menu.inSave = false
            menu.inMain = true
          }
          playSelectButtonSound
        }
        if (keyCode == KeyEvent.VK_X) {
          menu.inSave = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
      if (menu.inOption) {
        if (keyCode == KeyEvent.VK_UP) {
          if (menu.currentSelectionOption > 0) {
            menu.currentSelectionOption -= 1
          }
          playSelectButtonSound
        }
        else if (keyCode == KeyEvent.VK_DOWN) {
          if (menu.currentSelectionOption < 5) {
            menu.currentSelectionOption += 1
          }
          playSelectButtonSound
        }
        if (keyCode == KeyEvent.VK_Z) {
          playSelectButtonSound
        }
        if (keyCode == KeyEvent.VK_X) {
          menu.inOption = false
          menu.inMain = true
          playSelectButtonSound
        }
      }
    }

    def processBattleScene(keyCode: Int): Unit = {
      if (encounter.inMain == true) {
        if (encounter.playerTurn == true) {
          if (keyCode == KeyEvent.VK_UP) {
            encounter.currentSelectionMainY = 0
            button_pressed
          }
          else if (keyCode == KeyEvent.VK_DOWN) {
            encounter.currentSelectionMainY = 1
            button_pressed
          }
          else if (keyCode == KeyEvent.VK_LEFT) {
            encounter.currentSelectionMainX = 0
            button_pressed
          }
          else if (keyCode == KeyEvent.VK_RIGHT) {
            encounter.currentSelectionMainX = 1
            button_pressed
          }
          if (keyCode == KeyEvent.VK_Z) {
            if (encounter.currentSelectionMainX == 0 && encounter.currentSelectionMainY == 0) {
              encounter.Fight
            }
            if (encounter.currentSelectionMainX == 1 && encounter.currentSelectionMainY == 0) {
              encounter.Pokemon
            }
            if (encounter.currentSelectionMainX == 0 && encounter.currentSelectionMainY == 1) {
              encounter.Item
            }
            if (encounter.currentSelectionMainX == 1 && encounter.currentSelectionMainY == 1) {
              encounter.Run
            }
            button_pressed
          }
        }
      }
      if (encounter.inFight == true) {
        if (encounter.playerTurn == true) {
          if (keyCode == KeyEvent.VK_UP) {
            encounter.currentSelectionFightY = 0
            button_pressed
          }
          else if (keyCode == KeyEvent.VK_DOWN) {
            encounter.currentSelectionFightY = 1
            button_pressed
          }
          else if (keyCode == KeyEvent.VK_LEFT) {
            encounter.currentSelectionFightX = 0
            button_pressed
          }
          else if (keyCode == KeyEvent.VK_RIGHT) {
            encounter.currentSelectionFightX = 1
            button_pressed
          }
          if (keyCode == KeyEvent.VK_Z) {
            val rr: Random = new Random
            val wakeupthaw: Int = rr.nextInt(5)
            if (wakeupthaw <= 1) {
              if (encounter.playerPokemon.statusEffect eq 4) {
                System.out.println(encounter.playerPokemon.name + " has woken up.")
              }
              if (encounter.playerPokemon.statusEffect eq 5) {
                System.out.println(encounter.playerPokemon.name + " has broken free from the ice.")
              }
              encounter.playerPokemon.statusEffect = 0
            }
            if (encounter.playerPokemon.statusEffect ne 4 || encounter.playerPokemon.statusEffect ne 5) {
              if (encounter.currentSelectionFightX == 0 && encounter.currentSelectionFightY == 0) {
                System.out.println("Attack 1 Selected")
              }
              if (encounter.currentSelectionFightX == 1 && encounter.currentSelectionFightY == 0) {
                System.out.println("Attack 2 Selected")
                if (encounter.playerPokemon.statusEffect eq 1) {
                  val r: Random = new Random
                  val rand: Int = r.nextInt(2)
                  if (rand <= 0) {
                    encounter.enemyPokemon.setCurrentHP(new Attacks(encounter.playerPokemon.move2).getDamage(encounter.enemyPokemon))
                    System.out.println(encounter.enemyPokemon.getCurrentHP)
                  }
                  else {
                    System.out.println(encounter.playerPokemon.name + " is paralyzed. It can't move.")
                  }
                }
                else {
                  encounter.enemyPokemon.setCurrentHP(new Attacks(encounter.playerPokemon.move2).getDamage(encounter.enemyPokemon))
                  System.out.println(encounter.enemyPokemon.getCurrentHP)
                }
                if (encounter.playerPokemon.statusEffect eq 2) {
                  encounter.playerPokemon.curHp -= 2
                  System.out.println(encounter.playerPokemon.name + " has been hurt by its burn")
                }
                else if (encounter.playerPokemon.statusEffect eq 3) {
                  encounter.playerPokemon.curHp -= 2
                  System.out.println(encounter.playerPokemon.name + " has been hurt by its poison")
                }
                encounter.playerTurn = false
                encounter.inMain = true
                encounter.inFight = false
                encounter.currentSelectionMainX = 0
                encounter.currentSelectionMainY = 0
                encounter.currentSelectionFightX = 0
                encounter.currentSelectionFightY = 0
                col.playClip("Damage")
              }
              if (encounter.currentSelectionFightX == 0 && encounter.currentSelectionFightY == 1) {
                System.out.println("Attack 3 Selected")
                if (encounter.playerPokemon.statusEffect eq 1) {
                  val r: Random = new Random
                  val rand: Int = r.nextInt(2)
                  if (rand <= 0) {
                    encounter.enemyPokemon.setCurrentHP(new Attacks(encounter.playerPokemon.move3).getDamage(encounter.enemyPokemon))
                    System.out.println(encounter.enemyPokemon.getCurrentHP)
                  }
                  else {
                    System.out.println(encounter.playerPokemon.name + " is paralyzed. It can't move.")
                  }
                }
                else {
                  encounter.enemyPokemon.setCurrentHP(new Attacks(encounter.playerPokemon.move3).getDamage(encounter.enemyPokemon))
                  System.out.println(encounter.enemyPokemon.getCurrentHP)
                }
                if (encounter.playerPokemon.statusEffect eq 2) {
                  encounter.playerPokemon.curHp -= 2
                  System.out.println(encounter.playerPokemon.name + " has been hurt by its burn")
                }
                else if (encounter.playerPokemon.statusEffect eq 3) {
                  encounter.playerPokemon.curHp -= 2
                  System.out.println(encounter.playerPokemon.name + " has been hurt by its poison")
                }
                encounter.playerTurn = false
                encounter.inMain = true
                encounter.inFight = false
                encounter.currentSelectionMainX = 0
                encounter.currentSelectionMainY = 0
                encounter.currentSelectionFightX = 0
                encounter.currentSelectionFightY = 0
                col.playClip("Damage")
              }
              if (encounter.currentSelectionFightX == 1 && encounter.currentSelectionFightY == 1) {
                System.out.println("Attack 4 Selected")
                if (encounter.playerPokemon.statusEffect eq 1) {
                  val r: Random = new Random
                  val rand: Int = r.nextInt(2)
                  if (rand <= 0) {
                    encounter.enemyPokemon.setCurrentHP(new Attacks(encounter.playerPokemon.move4).getDamage(encounter.enemyPokemon))
                    System.out.println(encounter.enemyPokemon.getCurrentHP)
                  }
                  else {
                    System.out.println(encounter.playerPokemon.name + " is paralyzed. It can't move.")
                  }
                }
                else {
                  encounter.enemyPokemon.setCurrentHP(new Attacks(encounter.playerPokemon.move4).getDamage(encounter.enemyPokemon))
                  System.out.println(encounter.enemyPokemon.getCurrentHP)
                }
                if (encounter.playerPokemon.statusEffect eq 2) {
                  encounter.playerPokemon.curHp -= 2
                  System.out.println(encounter.playerPokemon.name + " has been hurt by its burn")
                }
                else if (encounter.playerPokemon.statusEffect eq 3) {
                  encounter.playerPokemon.curHp -= 2
                  System.out.println(encounter.playerPokemon.name + " has been hurt by its poison")
                }
                encounter.playerTurn = false
                encounter.inMain = true
                encounter.inFight = false
                encounter.currentSelectionMainX = 0
                encounter.currentSelectionMainY = 0
                encounter.currentSelectionFightX = 0
                encounter.currentSelectionFightY = 0
                col.playClip("Damage")
              }
            }
            else {
              System.out.println("Can't Attack")
            }
            button_pressed
          }
          if (keyCode == KeyEvent.VK_X) {
            encounter.currentSelectionMainX = 0
            encounter.currentSelectionMainY = 0
            encounter.currentSelectionFightX = 0
            encounter.currentSelectionFightY = 0
            encounter.inFight = false
            encounter.inMain = true
            button_pressed
          }
        }
      }
      if (encounter.inRun) {
        if (keyCode == KeyEvent.VK_Z) {
          encounter.confirmBattleEnd = true
        }
      }
    }
  }

  private def playSelectButtonSound = game.soundController.col.playClip("Select")

}
