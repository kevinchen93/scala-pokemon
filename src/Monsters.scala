import java.awt.{Toolkit, Image}
import java.io.{IOException, FileNotFoundException, FileReader, BufferedReader}
import java.util.StringTokenizer

/**
 * Created by kevinchen on 5/23/15.
 */
object Monsters {

  private var normal    = 0
  private var fire      = 1
  private var water     = 2
  private var grass     = 3
  private var electric  = 4
  private var ice       = 5
  private var fighting  = 6
  private var poison    = 7
  private var ground    = 8
  private var flying    = 9
  private var psychic   = 10
  private var bug       = 11
  private var rock      = 12
  private var ghost     = 13
  private var dragon    = 14
  private var dark      = 15
  private var steel     = 16
}

class Monsters {

  var name: String
  var level: Int
  var number: Int
  var hp: Int
  var attack: Int
  var defense: Int
  var spAttack: Int
  var spDef: Int
  var spd: Int

  var baseHp: Int
  var baseAttack: Int
  var baseDef: Int
  var baseSpAttack: Int
  var baseSpDef: Int
  var baseSpd: Int

  var curHp: Int
  var curAttack: Int
  var curDef: Int
  var curSpAttack: Int
  var curSpDef: Int
  var curSpd: Int

  var evHp: Int
  var evAttack: Int
  var evDef: Int
  var evSpAttack: Int
  var evSpDef: Int
  var evSpd: Int

  var exp: Double
  var curExp: Double

  // TODO fix setters
  def backSprite_=(sprite: Image): Unit = _backSprite = sprite
  def frontSprite_=(sprite: Image): Unit = _frontSprite = sprite

  private var _backSprite: Image = null
  private var _frontSprite: Image = null
  private var backSpriteShiny: Image = null
  private var frontSpriteShiny: Image = null
  private var partyIcon: Image = null

  var move: Attacks
  var move1 = ""
  var move2 = ""
  var move3 = ""
  var move4 = ""
  var attackDamage: Int

  var statusEffect = 0

  private var weak = new Array[Boolean](16)
  private var strong = new Array[Boolean](16)
  private var shiny = false

  def loseHp(i: Int): Unit = {
    curHp -= i
  }

  def healPokemon(): Unit = {
    curHp = hp
    curAttack = attack
    curDef = defense
    curSpAttack = spAttack
    curSpDef = spDef
    curSpd = spd
    statusEffect = 0
    println("Player's Pokemon have been healed back to full.")
  }

  def frontSprite() = if (shiny) frontSpriteShiny else _frontSprite
  def backSprite() = if (shiny) backSpriteShiny else _backSprite

  def levelUp(): Unit = {
    level += 1
    curExp = 0
    exp = math.pow(level, 3)
  }

  def create(n: Int): Unit = {

    def battler(num: String) = s"Graphics/Battlers/${num}.png"
    def icon(num: String) = s"Graphics/Icons/icon${num}.png"

    n match {

      case 4 => {
        name = "Charmander"
        level = 5
        number = 4
        _backSprite = ImageUtils.createImage(battler("220b"))
        _frontSprite = ImageUtils.createImage(battler("220"))
        backSpriteShiny = ImageUtils.createImage(battler("220sb"))
        frontSpriteShiny = ImageUtils.createImage(battler("220s"))
        partyIcon = ImageUtils.createImage(icon("220"))
        hp = 15
        curHp = 15
        exp = 200
        curExp = 0
        attack = 15
        curAttack = 15
        defense = 20
        curDef = 20
        spAttack = 40
        spDef = 10
        move1 = "Icy Wind"
        move2 = "Scratch"
        move3 = "Dig"
        move4 = "Growl"
        /*for(int x=0; x<16; x++) {
				weak[x]=false;
				strong[x]=false;
			}
			weak[water]=true;
			weak[ground]=true;
			strong[grass]=true;
			strong[steel]=true;
			strong[bug]=true;
			strong[ice]=true;*/
      }
      case 220 => {
        name = "Swinub"
        level = 3
        number = 220
        _backSprite = ImageUtils.createImage(battler("220b"))
        _frontSprite = ImageUtils.createImage(battler("220"))
        backSpriteShiny = ImageUtils.createImage(battler("220sb"))
        frontSpriteShiny = ImageUtils.createImage(battler("220s"))
        partyIcon = ImageUtils.createImage(icon("220"))
        hp = 15
        curHp = 15
        exp = 200
        curExp = 0
        attack = 15
        curAttack = 15
        defense = 20
        curDef = 20
        spAttack = 40
        spDef = 10
        move1 = "Icy Wind"
        move2 = "Scratch"
        move3 = "Dig"
        move4 = "Growl"
        /*for(int x=0; x<16; x++) {
          weak[x]=false;
          strong[x]=false;
        }
        weak[fire]=true;
        weak[fighting]=true;
        weak[steel]=true;
        weak[rock]=true;
        strong[dragon]=true;
        strong[ground]=true;
        strong[grass]=true;
        strong[flying]=true;*/
      }
      case 158 => {
        name = "Totodile"
        level = 5
        number = 158
        _backSprite = ImageUtils.createImage(battler("158b"))
        _frontSprite = ImageUtils.createImage(battler("158"))
        backSpriteShiny = ImageUtils.createImage(battler("158sb"))
        frontSpriteShiny = ImageUtils.createImage(battler("158s"))
        partyIcon = ImageUtils.createImage(icon("158"))
        hp = 25
        curHp = 25
        exp = 113
        curExp = 0
        attack = 16
        curAttack = 16
        defense = 30
        curDef = 30
        spAttack = 40
        spDef = 10
        move1 = "Water Gun"
        move2 = "Scratch"
        move3 = "Tail Whip"
        move4 = "Toxic"
        /*for(int x=0; x<16; x++) {
				weak[x]=false;
				strong[x]=false;
			}
			weak[electric]=true;
			weak[grass]=true;
			strong[fire]=true;
			strong[rock]=true;
			strong[ground]=true;*/
      }
      case 25 => {
        name = "Pikachu"
        level = 5
        number = 25
        _backSprite = ImageUtils.createImage(battler("025b"))
        _frontSprite = ImageUtils.createImage(battler("025"))
        backSpriteShiny = ImageUtils.createImage(battler("025sb"))
        frontSpriteShiny = ImageUtils.createImage(battler("025s"))
        partyIcon = ImageUtils.createImage(icon("025"))
        hp = 23
        curHp = 23
        exp = 200
        curExp = 0
        attack = 12
        curAttack = 12
        defense = 10
        curDef = 10
        spAttack = 40
        spDef = 10
        move1 = "Thundershock"
        move2 = "Quick Attack"
        move3 = "Tail Whip"
        move4 = "Thunderwave"
        /*for(int x=0; x<16; x++) {
				weak[x]=false;
				strong[x]=false;
			}
			weak[ground]=true;
			weak[rock]=true;
			strong[water]=true;
			strong[flying]=true;*/
      }
      case 198 => {
        name = "Murkrow"
        level = 4
        number = 198
        _backSprite = ImageUtils.createImage(battler("198b"))
        _frontSprite = ImageUtils.createImage(battler("198"))
        backSpriteShiny = ImageUtils.createImage(battler("198sb"))
        frontSpriteShiny = ImageUtils.createImage(battler("198s"))
        partyIcon = ImageUtils.createImage(icon("198"))
        hp = 19
        curHp = 19
        exp = 200
        curExp = 0
        attack = 13
        curAttack = 13
        defense = 15
        curDef = 15
        spAttack = 10
        spDef = 10
        move1 = "Peck"
        move2 = "Pursuit"
        move3 = "Quick Attack"
        move4 = "Growl"
        /*for(int x=0; x<16; x++) {
				weak[x]=false;
				strong[x]=false;
			}
			weak[electric]=true;
			weak[ice]=true;
			weak[rock]=true;
			strong[grass]=true;
			strong[fighting]=true;
			strong[bug]=true;*/
      }
      case _ => {
        println(s"TODO, ${Monsters.getClass.getSimpleName}.scala -- read in from file")
        name = "MissingNo"
        level = 255
        number = 0
        _backSprite = ImageUtils.createImage(battler("000b"))
        _frontSprite = ImageUtils.createImage(battler("000"))
        backSpriteShiny = ImageUtils.createImage(battler("000sb"))
        frontSpriteShiny = ImageUtils.createImage(battler("000s"))
        partyIcon = ImageUtils.createImage(icon("000"))
        hp = 255
        attack = 255
        exp = 200
        curExp = 0
        curAttack = 255
        defense = 255
        curDef = 255
        spAttack = 255
        spDef = 255
        move1 = "Sky Attack"
        move2 = "Flamethrower"
        move3 = "Tackle"
        move4 = "Hyper Beam"
        /*for(int x=0; x<16; x++) {
          weak[x]=false;
          strong[x]=false;
        }
        weak[electric]=true;
        weak[ice]=true;
        weak[rock]=true;
        strong[grass]=true;
        strong[fighting]=true;
        strong[bug]=true;*/
      }
    }
  }

  def loadPokemon = {
    try {
      val reader = new BufferedReader(new FileReader("Data/pokemon.txt"))
      var line = ""
      var tokens = new StringTokenizer(line)

      number = reader.readLine.toInt
      println(number)

      name = reader.readLine
      println(name)

      val internalName = reader.readLine
      println(internalName)

      val kind = reader.readLine
      println(kind)

      val pokedexEntry = reader.readLine
      println(pokedexEntry)

      val type1 = reader.readLine
      println(type1)

      val type2 = reader.readLine
      println(type2)


      line = reader.readLine
      tokens = new StringTokenizer(line)
      baseHp = tokens.nextToken.toInt
      println(baseHp)

      baseAttack = tokens.nextToken.toInt
      println(baseAttack)

      baseSpAttack = tokens.nextToken.toInt
      println(baseSpAttack)

      baseDef = tokens.nextToken.toInt
      println(baseDef)

      baseSpDef = tokens.nextToken.toInt
      println(baseSpDef)

      baseSpd = tokens.nextToken.toInt
      println(baseSpd)

      val rareness = reader.readLine.toInt
      println(rareness)

      val baseEXP = reader.readLine.toInt
      println(baseEXP)

      val happiness = reader.readLine.toInt
      println(happiness)

      val growthRate = reader.readLine
      println(growthRate)

      val stepsToHatch: Int = reader.readLine.toInt
      println(stepsToHatch)

      val color = reader.readLine
      println(color)

      val habitat = reader.readLine
      println(habitat)


      line = reader.readLine
      tokens = new StringTokenizer(line)

      evHp = tokens.nextToken.toInt
      println(evHp)

      evAttack = tokens.nextToken.toInt
      println(evAttack)

      evSpAttack = tokens.nextToken.toInt
      println(evSpAttack)

      evDef = tokens.nextToken.toInt
      println(evDef)

      evSpDef = tokens.nextToken.toInt
      println(evSpDef)

      evSpd = tokens.nextToken.toInt
      println(evSpd)

      val abilities = reader.readLine
      println(abilities)

      val compatibility = reader.readLine
      println(compatibility)

      val height = reader.readLine.toDouble
      println(height)

      val weight = reader.readLine.toDouble
      println(weight)

      val genderRate = reader.readLine
      println(genderRate)

      val moves = reader.readLine
      println(moves)

      val eggMoves = reader.readLine
      println(eggMoves)


      line = reader.readLine
      tokens = new StringTokenizer(line)

      val evolutionName = tokens.nextToken
      println(evolutionName)

      val evolutionType = tokens.nextToken
      println(evolutionType)

      val evolutionLevel = tokens.nextToken.toInt
      println(evolutionLevel)

      val battleOffsetPlayer = reader.readLine.toInt
      println(battleOffsetPlayer)

      val battleOffsetEnemy = reader.readLine.toInt
      println(battleOffsetEnemy)

      val battleAltitude = reader.readLine.toInt
      println(battleAltitude)

      reader.close()
    }
    catch {
      case e: FileNotFoundException => {
        e.printStackTrace()
      }
      case i: IOException => {
        i.printStackTrace()
      }
    }
  }

}
