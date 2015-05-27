import java.awt.{Toolkit, Image}

/**
 * Created by kevinchen on 5/25/15.
 */
object Sprites {
  lazy val player = ImageUtils.createImage("Graphics/Characters/Player/Down.png")
  lazy val playerUp = ImageUtils.createImage("Graphics/Characters/Player/Up.png")
  lazy val playerUp1 = ImageUtils.createImage("Graphics/Characters/Player/Up1.png")
  lazy val playerUp2 = ImageUtils.createImage("Graphics/Characters/Player/Up2.png")
  lazy val playerDown = ImageUtils.createImage("Graphics/Characters/Player/Down.png")
  lazy val playerDown1 = ImageUtils.createImage("Graphics/Characters/Player/Down1.png")
  lazy val playerDown2 = ImageUtils.createImage("Graphics/Characters/Player/Down2.png")
  lazy val playerLeft = ImageUtils.createImage("Graphics/Characters/Player/Left.png")
  lazy val playerLeft1 = ImageUtils.createImage("Graphics/Characters/Player/Left1.png")
  lazy val playerLeft2 = ImageUtils.createImage("Graphics/Characters/Player/Left2.png")
  lazy val playerRight = ImageUtils.createImage("Graphics/Characters/Player/Right.png")
  lazy val playerRight1 = ImageUtils.createImage("Graphics/Characters/Player/Right1.png")
  lazy val playerRight2 = ImageUtils.createImage("Graphics/Characters/Player/Right2.png")

  lazy val titlescreen = ImageUtils.createImage("Graphics/Titles/Pic_2.png")
  lazy val start_symbol = ImageUtils.createImage("Graphics/Titles/Start.png")
  lazy val continuescreen = ImageUtils.createImage("Graphics/Pictures/Continue.png")
  lazy val arrow = ImageUtils.createImage("Graphics/Pictures/Arrow.png")
  lazy val messagebox = ImageUtils.createImage("Graphics/Pictures/Message_Text.png")

  // backgrounds
  lazy val BG = ImageUtils.createImage("Graphics/Pictures/BG.png")
  lazy val battleMainBG = ImageUtils.createImage("Graphics/Pictures/Battle.png")
  lazy val battleFightBG = ImageUtils.createImage("Graphics/Pictures/Battle2.png")
}
