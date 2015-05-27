import java.awt.{Image}
import java.io.{IOException, FileNotFoundException, FileReader, BufferedReader}
import java.util.StringTokenizer

/**
 * Created by kevinchen on 5/23/15.
 */
object Monsters {

  private var normal = 0
  private var fire = 1
  private var water = 2
  private var grass = 3
  private var electric = 4
  private var ice = 5
  private var fighting = 6
  private var poison = 7
  private var ground = 8
  private var flying = 9
  private var psychic = 10
  private var bug = 11
  private var rock = 12
  private var ghost = 13
  private var dragon = 14
  private var dark = 15
  private var steel = 16

  val statusUnaffected = 0
  val statusParalyzed = 1
  val statusBurned = 2
  val statusPoisoned = 3
  val statusAsleep = 4
  val statusFrozen = 5

  lazy val statusPAR = ImageUtils.createImage("Graphics/Pictures/StatusPAR.png")
  lazy val statusBRN = ImageUtils.createImage("Graphics/Pictures/StatusBRN.png")
  lazy val statusPSN = ImageUtils.createImage("Graphics/Pictures/StatusPSN.png")
  lazy val statusSLP = ImageUtils.createImage("Graphics/Pictures/StatusSLP.png")
  lazy val statusFRZ = ImageUtils.createImage("Graphics/Pictures/StatusFRZ.png")


  def create(n: Int): Monsters = {

    def battler(num: String) = s"Graphics/Battlers/${num}.png"
    def icon(num: String) = s"Graphics/Icons/icon${num}.png"

    val monster = new Monsters

    def setStats(monster: Monsters,
                 name: String,
                 level: Int,
                 number: Int,
                 backSprite: Image,
                 frontSprite: Image,
                 backSpriteShiny: Image,
                 frontSpriteShiny: Image,
                 partyIcon: Image,
                 hp: Int,
                 curHp: Int,
                 exp: Int,
                 curExp: Int,
                 attack: Int,
                 curAttack: Int,
                 defense: Int,
                 curDef: Int,
                 spAttack: Int,
                 spDef: Int,
                 move1: String,
                 move2: String,
                 move3: String,
                 move4: String): Unit = {
      monster.name = name
      monster.level = level
      monster.number = number
      monster._backSprite = backSprite
      monster._frontSprite = frontSprite
      monster.backSpriteShiny = backSpriteShiny
      monster.frontSpriteShiny = frontSpriteShiny
      monster.partyIcon = partyIcon
      monster.hp = hp
      monster.curHp = curHp
      monster.exp = exp
      monster.curExp = curExp
      monster.attack = attack
      monster.curAttack = curAttack
      monster.defense = defense
      monster.curDef = curDef
      monster.spAttack = spAttack
      monster.spDef = spDef
      monster.move1 = move1
      monster.move2 = move2
      monster.move3 = move3
      monster.move4 = move4

    }

    n match {

      // Charmander
      case 4 => {
        setStats(monster, "Charmander", 5, n,
          ImageUtils.createImage(battler("004b")),
          ImageUtils.createImage(battler("004")),
          ImageUtils.createImage(battler("004sb")),
          ImageUtils.createImage(battler("004s")),
          ImageUtils.createImage(icon("004")),
          20, 20, 200, 0, 14, 14, 15, 15, 40, 10,
          "Ember", "Scratch", "Tail Whip", "Fire Spin")
        /*for(int x=0; x<16; x++) { weak[x]=false;strong[x]=false;}
			weak[water]=true;
			weak[ground]=true;
			strong[grass]=true;
			strong[steel]=true;
			strong[bug]=true;
			strong[ice]=true;*/
      }

      // Swinub
      case 220 => {
        setStats(monster, "Swinub", 3, n,
          ImageUtils.createImage(battler("220b")),
          ImageUtils.createImage(battler("220")),
          ImageUtils.createImage(battler("220sb")),
          ImageUtils.createImage(battler("220s")),
          ImageUtils.createImage(icon("220")),
          15, 15, 200, 0, 15, 15, 20, 20, 40, 10,
          "Icy Wind", "Scratch", "Dig", "Growl")
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

      // Totodile
      case 158 => {
        setStats(monster, "Totodile", 5, n,
          ImageUtils.createImage(battler("158b")),
          ImageUtils.createImage(battler("158")),
          ImageUtils.createImage(battler("158sb")),
          ImageUtils.createImage(battler("158s")),
          ImageUtils.createImage(icon("158")),
          25, 25, 113, 0, 16, 16, 30, 30, 40, 10,
          "Water Gun", "Scratch", "Tail Whip", "Toxic")
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

      // Pikachu
      case 25 => {
        setStats(monster, "Pickachu", 5, n,
          ImageUtils.createImage(battler("025b")),
          ImageUtils.createImage(battler("025")),
          ImageUtils.createImage(battler("025sb")),
          ImageUtils.createImage(battler("025s")),
          ImageUtils.createImage(icon("025")),
          23, 23, 200, 0, 12, 12, 10, 10, 40, 10,
          "Thundershock", "Quick Attack", "Tail Whip", "Thunderwave")
        /*for(int x=0; x<16; x++) {
				weak[x]=false;
				strong[x]=false;
			}
			weak[ground]=true;
			weak[rock]=true;
			strong[water]=true;
			strong[flying]=true;*/
      }

      // Murkrow
      case 198 => {
        setStats(monster, "Murkrow", 4, n,
          ImageUtils.createImage(battler("198b")),
          ImageUtils.createImage(battler("198")),
          ImageUtils.createImage(battler("198sb")),
          ImageUtils.createImage(battler("198s")),
          ImageUtils.createImage(icon("198")),
          19, 19, 200, 0, 13, 13, 15, 15, 10, 10,
          "Peck", "Pursuit", "Quick Attack", "Growl")

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
//        name = "MissingNo"
//        level = 255
//        number = 0
//        _backSprite = ImageUtils.createImage(battler("000b"))
//        _frontSprite = ImageUtils.createImage(battler("000"))
//        backSpriteShiny = ImageUtils.createImage(battler("000sb"))
//        frontSpriteShiny = ImageUtils.createImage(battler("000s"))
//        partyIcon = ImageUtils.createImage(icon("000"))
//        hp = 255
//        attack = 255
//        exp = 200
//        curExp = 0
//        curAttack = 255
//        defense = 255
//        curDef = 255
//        spAttack = 255
//        spDef = 255
//        move1 = "Sky Attack"
//        move2 = "Flamethrower"
//        move3 = "Tackle"
//        move4 = "Hyper Beam"
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

    monster
  }
}

class Monsters {

  var name: String = ""
  var level: Int = 0
  var number: Int = 0
  var hp: Int = 0
  var attack: Int = 0
  var defense: Int = 0
  var spAttack: Int = 0
  var spDef: Int = 0
  var spd: Int = 0

  var baseHp: Int = 0
  var baseAttack: Int = 0
  var baseDef: Int = 0
  var baseSpAttack: Int = 0
  var baseSpDef: Int = 0
  var baseSpd: Int = 0

  var curHp: Int = 0
  var curAttack: Int = 0
  var curDef: Int = 0
  var curSpAttack: Int = 0
  var curSpDef: Int = 0
  var curSpd: Int = 0

  var evHp: Int = 0
  var evAttack: Int = 0
  var evDef: Int = 0
  var evSpAttack: Int = 0
  var evSpDef: Int = 0
  var evSpd: Int = 0

  var exp: Double = 0
  var curExp: Double = 0

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

  def statusImage(): Image = {
    statusEffect match {
      case Monsters.statusParalyzed => Monsters.statusPAR
      case Monsters.statusBurned => Monsters.statusBRN
      case Monsters.statusPoisoned => Monsters.statusPSN
      case Monsters.statusAsleep => Monsters.statusSLP
      case Monsters.statusFrozen => Monsters.statusFRZ
    }
    null
  }

  override def toString: String = s"Pokemon: $name Level: $level HP: $curHp / $hp"

  def unaffected = statusEffect == Monsters.statusUnaffected

  def affected = !unaffected

  def paralyzed = statusEffect == Monsters.statusParalyzed

  def burned = statusEffect == Monsters.statusBurned

  def poisoned = statusEffect == Monsters.statusPoisoned

  def asleep = statusEffect == Monsters.statusAsleep

  def frozen = statusEffect == Monsters.statusFrozen

  def stabilizeStatus(): Unit = {
    statusEffect match {
      case 4 => println(s"$name has woken up.")
      case 5 => println(s"$name has broken free from the ice.")
    }
    statusEffect = 0
  }

  def reiterateStatus(): Unit = {
    statusEffect match {
      case 4 => println(s"$name is still asleep.")
      case 5 => println(s"$name is frozen solid.")
    }
  }

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

  def loadPokemon() = {
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
