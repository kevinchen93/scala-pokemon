import java.awt.geom.AffineTransform
import java.awt._
import javax.swing.JPanel

/**
 * Created by kevinchen on 5/25/15.
 */
class MyJPanel(game: PokemonGameEngine) extends JPanel {

  setBackground(Color.BLACK)
  setPreferredSize(new Dimension(480, 320))
  addKeyListener(new MyKeyListener(game))
  
  override def paintComponent(g: Graphics): Unit = {
    super.paintComponent(g)

    val g2: Graphics2D = g.asInstanceOf[Graphics2D]
    val at: AffineTransform = new AffineTransform
    g2.setTransform(at)
    if (game.atTitle) {
      g.drawImage(game.titlescreen, 0, 0, null)
      if (game.start_visible) {
        g.drawImage(game.start_symbol, 0, 260, null)
      }
    }
    else if (game.atContinueScreen) {
      g.drawImage(game.continuescreen, 0, 0, null)
      game.concurrentMenuItem match {
        case 0 => g.drawImage(arrow, 13, 20, null)
        case 1 => g.drawImage(arrow, 13, 52, null)
        case 2 => g.drawImage(arrow, 13, 84, null)
      }
    }
    else {
      if (game.inBattle) {
        g2.setClip(new Rectangle(posX - 240, posY - 160, posX + 480, posY + 320))
        g2.translate(offsetX - (currentX_loc * 32), offsetY - (currentY_loc * 32))
        for (y <- 1 to mapTilesY) {
          for (x <- 1 to mapTilesX) {
            if (currentMap0(tile_number) != 0) {
              g.drawImage(tileset(currentMap0(tile_number) - 1), x_coor, y_coor, null)
            }
            if (currentMap1(tile_number) != 0) {
              g.drawImage(tileset(currentMap1(tile_number) - 1), x_coor, y_coor, null)
            }
            if (!tilesLoaded) {
              for (i <- impassibleTiles.indices) {
                if (currentMap0(tile_number) == impassibleTiles(i) || currentMap1(tile_number) == impassibleTiles(i)) {
                  if (noClip == false) {
                    currentMapStaticTiles(tile_number) = new StaticTile(x_coor / 32, y_coor / 32, null)
                  }
                }
              }
            }

            x_coor = x_coor + 32
            tile_number = tile_number + 1
          }
          x_coor = 0
          y_coor = y_coor + 32
        }
        tilesLoaded = true
        tile_number = 0
        x_coor = 0
        y_coor = 0

        // NPC Sprites
        for (i <- currentMapNPC.indices) {
          g.drawImage(currentMapNPC(i).getSprite,
            currentMapNPC(i).getCurrentX * TILE_WIDTH_PIXELS,
            currentMapNPC(i).getCurrentY * TILE_HEIGHT_PIXELS - 10,
            null)
        }

        // Reset to 0,0
        g2.translate(-offsetX, -offsetY)

        // Player sprites
        g2.setTransform(at)
        g.drawImage(gold.getSprite, posX, posY, null)
        g.setFont(pokefont)
        g.setColor(Color.WHITE)
        g.drawString("" + posX_tile + "," + posY_tile, 10, 25)
        showMessageBox(g)
      }
      else {
        encounter.paint(g)
      }
      if (game.inMenu) {
        game.menu.paint(g)
      }
    }


  }


}
