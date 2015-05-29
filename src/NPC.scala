import java.awt.Image

/**
 * Created by kevinchen on 5/23/15.
 */

object NPC {
  val imgBaldMan = ImageUtils.createImage("Graphics/Characters/NPC/Baldman.png")
  val imgBeauty = ImageUtils.createImage("Graphics/Characters/NPC/Beauty.png")
  val imgBill = ImageUtils.createImage("Graphics/Characters/NPC/Bill.png")
  val imgBirdKeeperL = ImageUtils.createImage("Graphics/Characters/NPC/BirdKeeperL.png")
  val imgBirdKeeperR = ImageUtils.createImage("Graphics/Characters/NPC/BirdKeeperR.png")
  val imgBoy = ImageUtils.createImage("Graphics/Characters/NPC/Boy.png")
  val imgBugCatcher = ImageUtils.createImage("Graphics/Characters/NPC/BugCatcher.png")
  val imgCamperD = ImageUtils.createImage("Graphics/Characters/NPC/CamperD.png")
  val imgCamperL = ImageUtils.createImage("Graphics/Characters/NPC/CamperL.png")
  val imgFalkner = ImageUtils.createImage("Graphics/Characters/NPC/Falkner.png")
  val imgFatMan = ImageUtils.createImage("Graphics/Characters/NPC/FatMan.png")
  val imgFisher = ImageUtils.createImage("Graphics/Characters/NPC/Fisher.png")
  val imgGuideGent = ImageUtils.createImage("Graphics/Characters/NPC/GuideGent.png")
  val imgLass = ImageUtils.createImage("Graphics/Characters/NPC/Lass.png")
  val imgMom = ImageUtils.createImage("Graphics/Characters/NPC/Mom.png")
  val imgMrPokemon = ImageUtils.createImage("Graphics/Characters/NPC/MrPokemon.png")
  val imgNurse = ImageUtils.createImage("Graphics/Characters/NPC/Nurse.png")
  val imgPickNicker = ImageUtils.createImage("Graphics/Characters/NPC/Picknicher.png")
  val imgOak = ImageUtils.createImage("Graphics/Characters/NPC/ProfOak.png")
  val imgShopKeep = ImageUtils.createImage("Graphics/Characters/NPC/ShopKeep.png")
  val imgYoungster = ImageUtils.createImage("Graphics/Characters/NPC/Youngster.png")

  var violetCitizen1 = new NPC(42, 30, "Citizen", "We care about the traditional buildings around here.", NPC.imgBaldMan, null)
  var violetCitizen2 = new NPC(34, 34, "Citizen", "It is rumored that there are ghost pokemon in the Sprout " +
    "Tower.", NPC.imgBill, null)
  var violetCitizen3 = new NPC(30, 23, "Citizen", "Hey, your a pokemon trainer! If you beat the gym leader, " +
    "you'll be ready for the big time.", NPC.imgCamperL, null)
  var violetCitizen4 = new NPC(24, 26, "Citizen", "Falkner, from Violet City pokemon gym, is a fine trainer.",
    NPC.imgBoy, null)
  var violetCitizen5 = new NPC(11, 23, "Citizen", "You can't have your pokemon out with you in all places.",
    NPC.imgFatMan, null)
  var violetIndoors1 = new NPC(8, 108, "Citizen", "There are many wild Pokemon in the tall grass.", NPC.imgBaldMan,
    null)
  var violetIndoors2 = new NPC(3, 107, "Citizen", "Do you want to trade Pokemon?", NPC.imgBoy, null)
  var schoolStudent1 = new NPC(23, 90, "Citizen", "I want to learn how to become a Pokemon Master.",
    NPC.imgYoungster, null)
  var schoolStudent2 = new NPC(25, 90, "Citizen", "What type of Pokemon is Pikachu?", NPC.imgBoy, null)
  var schoolStudent3 = new NPC(26, 90, "Citizen", "Are you a Pokemon trainer?", NPC.imgBeauty, null)
  var schoolStudent4 = new NPC(23, 88, "Citizen", "The Sprout Tower is a shrine to Bellsprout.", NPC.imgYoungster, null)
  var schoolStudent5 = new NPC(25, 86, "Citizen", "Whadaya want from me!", NPC.imgCamperL, null)
  var schoolStudent6 = new NPC(24, 84, "Citizen", "You're never too old to learn about Pokemon.", NPC.imgBaldMan, null)
  var martCustomer1 = new NPC(24, 106, "Citizen", "I wonder if they carry Pokeballs.", NPC.imgYoungster, null)
  var storeClerk = new NPC(20, 107, "Citizen", "Can I help you with something?", NPC.imgShopKeep, null)
  var centerVisitor1 = new NPC(40, 107, "Citizen", "You can heal your Pokemon by talking to Nurse Joy.",
    NPC.imgFatMan,
    null)
  var nurseJoy = new NPC(42, 105, "Citizen", "We hope to see you again!", NPC.imgNurse, null)
  var birdKeeper1 = new NPC(5, 89, "Citizen", "I want to be like Falkner.", NPC.imgBirdKeeperR, null)
  var birdKeeper2 = new NPC(10, 85, "Citizen", "Falkner is the best.", NPC.imgBirdKeeperL, null)
  var leaderFaulkner = new NPC(7, 81, "Citizen", "My precious bird Pokemon are unstopable.", NPC.imgFalkner, null)
  var oldMan1 = new NPC(88, 102, "Citizen", "I can guide you around cherrygrove!", NPC.imgGuideGent, null)
  var cherrygroveCitizen1 = new NPC(84, 105, "Citizen", "I'm hungry.", NPC.imgFatMan, null)
  var cherrygroveCitizen2 = new NPC(83, 108, "Citizen", "Cherrygrove City is beautiful.", NPC.imgLass, null)
  var cherrygroveCitizen3 = new NPC(78, 104, "Citizen", "Nothing interesting happens here.", NPC.imgBoy, null)
}


class NPC(var x: Int, var y: Int, val name: String, text: String, var sprite: Image, battleSprite: Image) extends
MoveableActor {

  var battleText = text
  var charset: Array[Image] = null
  var direction = 1

  def width = sprite.getWidth(null) / 4

  def height = sprite.getHeight(null) / 4

  def text(other: Player) = if (adjacentTo(other)) text else ""
}