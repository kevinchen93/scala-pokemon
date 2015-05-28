import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.util.*;
import java.awt.geom.*;
import java.io.*;

public class pPokemon extends JPanel implements KeyListener, ActionListener {
    //-----------------------------------------------------------------
    // Pokemon: Metallic Silver
    //-----------------------------------------------------------------
    // Zach Harsh
    // Ron Groom
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // DEBUG Variables
    //-----------------------------------------------------------------
    private boolean noClip = false;
    private boolean noBattle = false;
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // Window and Window Accesories
    //-----------------------------------------------------------------
    private int offsetX = 0, offsetY = 0;
    private int TILE_WIDTH_PIXELS = 32;
    private int TILE_HEIGHT_PIXELS = 32;
    private int concurrentMenuItem = 0;
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // Player Variables
    //-----------------------------------------------------------------
    private int movespritepixels = 0;
    private boolean walking = false;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean movable_up = true;
    private boolean movable_down = true;
    private boolean movable_left = true;
    private boolean movable_right = true;
    private boolean talkable = false;
    private boolean collision = false;
    private boolean footsprite = false;
    private String text = "";
    private boolean movable = true;
    private static Random randGen = new Random();
    public int badges = 0;

    private int stepscount = 0;
    public int money = 2000;
    public long seconds = 0;
    public long timePlayed = 0;
    public long currentTime = 0;
    public Monsters[][] pokedex = new Monsters[493][40];
    public Monsters wildPokemon = new Monsters();
    public Items[] items = new Items[2];
    public Items[][] mainitems = new Items[30][99];
    public Items[][] balls = new Items[30][99];
    public Items[][] keyitems = new Items[30][99];
    private Items potion = new Items(1);
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // Map Variables
    //-----------------------------------------------------------------
    private Image[] tileset = new Image[1112];
    private String Johto = "Data/Johto.map";
    private String currentMap = "Cherrygrove City";
    private String currentMapName = Johto;
    private boolean changemap = false;
    private boolean tilesLoaded = false;
    private int[] impassibleTiles = new int[]{
            3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15, 16, 18, 19,
            20, 21, 22, 23, 24, 40, 41, 42, 48, 49, 50, 51,
            52, 56, 57, 58, 59, 60, 61, 64, 65, 66, 67, 68,
            69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
            81, 82, 83, 84, 86, 87, 88, 89, 90, 91, 92, 93,
            94, 95, 96, 97, 98, 99, 100, 101, 102, 103, 104,
            105, 106, 107, 108, 109, 110, 111, 112, 113, 114,
            115, 116, 117, 118, 119, 120, 121, 122, 123, 124,
            125, 126, 127, 128, 129, 130, 131, 132, 134,
            135, 136, 137, 138, 139, 140, 141, 142, 143, 144,
            145, 146, 147, 148, 149, 150, 151, 152, 153, 154,
            155, 156, 157, 158, 159, 160, 161, 162, 163, 164,
            166, 167, 168, 169, 170, 171, 172, 173, 174,
            175, 176, 177, 178, 179, 180, 181, 182, 183, 184,
            185, 186, 187, 188, 189, 190, 191, 192, 193, 194,
            195, 196, 197, 198, 199, 200, 201, 202, 203, 204,
            205, 206, 207, 208, 209, 210, 211, 212, 213, 214,
            215, 216, 218, 219, 220, 221, 222, 223, 224,
            225, 226, 227, 228, 229, 230, 231, 232, 234,
            235, 236, 237, 238, 239, 240, 241, 242, 243, 244,
            245, 246, 247, 248, 249, 251, 252, 253, 296, 471,
            477, 478, 479, 486, 494, 495, 501, 503, 509, 808,
            809, 810, 811, 812, 813, 816, 817, 818, 819, 820,
            821, 824, 825, 861, 864, 870, 871, 872, 880, 888,
            890, 896, 914, 922, 728, 729, 730, 731, 732, 733,
            736, 737, 738, 739, 740, 741, 742, 743, 746, 770,
            771, 772, 773, 774, 775, 798, 762, 764, 765, 769,
            515, 512, 520, 521, 528, 529};
    private int[] currentMap0 = new int[12500];
    private int[] currentMap1 = new int[12500];
    private StaticTile[] currentMapStaticTiles = new StaticTile[12500];
    private int mapTilesX;
    private int mapTilesY;
    private int x_coor = 0;
    private int y_coor = 0;
    private int tile_number = 0;
    private boolean showmessagebox = false;


    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // Battle Variables
    //-----------------------------------------------------------------
    private TrainerBattleScene trainerencounter;
    public Monsters trainerparty[] = new Monsters[6];
    private BattleScene encounter;
    public boolean inBattle = false;
    private int r;
    private int rndwildmodify = 15;
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // Menu Variables
    //-----------------------------------------------------------------
    private MenuScene menu;
    public boolean inMenu = false;
    private boolean disable_start = false;
    private Image arrow = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Arrow.png"));
    private Image messagebox = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Message_Text.png"));
    //-----------------------------------------------------------------



    //-----------------------------------------------------------------
    // Paint Code
    //-----------------------------------------------------------------

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform at = new AffineTransform();
        g2.setTransform(at);
        if (atTitle == true) {
            g.drawImage(titlescreen, 0, 0, null);
            if (start_visible == true) {
                g.drawImage(start_symbol, 0, 260, null);
            }
        } else if (atContinueScreen == true) {
            g.drawImage(continuescreen, 0, 0, null);
            if (concurrentMenuItem == 0) {
                g.drawImage(arrow, 13, 20, null);
            } else if (concurrentMenuItem == 1) {
                g.drawImage(arrow, 13, 52, null);
            } else if (concurrentMenuItem == 2) {
                g.drawImage(arrow, 13, 84, null);
            }
        } else {
            if (inBattle == false) {
                //Draw the Map
                g2.setClip(new Rectangle(posX - 240, posY - 160, posX + 480, posY + 320));
                g2.translate(offsetX - (currentX_loc * 32), offsetY - (currentY_loc * 32));
                for (int y = 1; y <= mapTilesY; y++) {
                    for (int x = 1; x <= mapTilesX; x++) {
                        //Layer 0
                        if (currentMap0[tile_number] != 0) {
                            g.drawImage(tileset[currentMap0[tile_number] - 1], x_coor, y_coor, null);
                        }
                        //Layer 1
                        if (currentMap1[tile_number] != 0) {
                            g.drawImage(tileset[currentMap1[tile_number] - 1], x_coor, y_coor, null);
                        }
                        //Impassible Tiles
                        if (tilesLoaded == false) {
                            for (int i = 0; i < impassibleTiles.length; i++) {
                                if (currentMap0[tile_number] == impassibleTiles[i] ||
                                        currentMap1[tile_number] == impassibleTiles[i]) {
                                    if (noClip == false) {
                                        currentMapStaticTiles[tile_number] = new StaticTile(x_coor / 32, y_coor / 32, null);
                                    }
                                }
                            }
                        }
                        x_coor = x_coor + 32;
                        tile_number = tile_number + 1;
                    }
                    x_coor = 0;
                    y_coor = y_coor + 32;
                }
                tilesLoaded = true;
                tile_number = 0;
                x_coor = 0;
                y_coor = 0;
                //NPC Sprites
                for (int i = 0; i < currentMapNPC.length; i++) {
                    g.drawImage(currentMapNPC[i].getSprite(),
                            currentMapNPC[i].getCurrentX() * TILE_WIDTH_PIXELS,
                            currentMapNPC[i].getCurrentY() * TILE_HEIGHT_PIXELS - 10, null);
                      /*g.drawImage(currentMapNPC[i].getSprite(),
                      currentMapNPC[i].getCurrentX()*32,
	              	currentMapNPC[i].getCurrentY()*32,
	              	currentMapNPC[i].getWidth(),
	              	currentMapNPC[i].getHeight(),
	              	currentMapNPC[i].getWidth(),
	              	currentMapNPC[i].getHeight(),
	              	null);*/
                }

                //Reset to 0,0
                g2.translate(-offsetX, -offsetY);
                //Player Sprites
                g2.setTransform(at);
                g.drawImage(gold.getSprite(), posX, posY, null);
                g.setFont(pokefont);
                g.setColor(Color.WHITE);
                g.drawString("" + posX_tile + "," + posY_tile, 10, 25);
                showMessageBox(g);
            } else {
                encounter.paint(g);
            }
            if (inMenu == true) {
                menu.paint(g);
            }
        }
    }

    //-----------------------------------------------------------------
    // KeyListener Code
    //-----------------------------------------------------------------
    public void crashTest(int[] map) {
        //Wild Pokemon Grass
        if (map[(posY_tile * mapTilesX) + posX_tile] == 17) {
            stepscount++;
        }
    }

    public void collision() {
        if (collision == true) {
            col.playClip("Collision");
            collision = false;
        }
    }

    public void transfer() {

    }



    //-----------------------------------------------------------------
    // Battle Code
    //-----------------------------------------------------------------

    public void checkBattle() {
        if (noBattle == false) {
            if (stepscount >= rndwildmodify) {
                lastBGM = currentBGM;
                currentBGM.stop();
                currentBGM = battleBGM;
                currentBGM.start();
                if (r == 1) {
                    wildPokemon.create(198); //Creates a wild Murkrow
                } else if (r == 2) {
                    wildPokemon.create(4); //Creates a wild Charmander
                } else if (r == 3) {
                    wildPokemon.create(25); //Creates a wild Pikachu
                } else if (r == 4) {
                    wildPokemon.create(220); //Creates a wild Swinub
                } else {
                    wildPokemon.create(158); //Creates a wild Totodile
                }
                wait(1);
                inBattle = true;
                disable_start = true;
                encounter = new BattleScene(this, pokemonparty, wildPokemon, items);
                stepscount = 0;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] Args) {
        //Create the window
        jf = new JFrame("Pokemon: Metallic Silver");
        //Create an instance of Pokemon and insert into the window
        pPokemon pokemon = new pPokemon();
        jf.add(pokemon);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(false);
        jf.pack();
        //Center the Game on the Screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int w = jf.getSize().width;
        int h = jf.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;
        jf.setLocation(x, y);
        //Set focus to the Panel
        jf.setVisible(true);
        pokemon.requestFocus(true);
    }
}