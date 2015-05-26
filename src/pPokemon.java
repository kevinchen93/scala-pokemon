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
    public static JFrame jf;
    public Toolkit tk = jf.getToolkit();
    private javax.swing.Timer gameTimer;
    private Font pokefont = new Font("pokesl1", Font.PLAIN, 18);
    private Image titlescreen = tk.createImage(getClass().getResource("Graphics/Titles/Pic_2.png"));
    private Image start_symbol = tk.createImage(getClass().getResource("Graphics/Titles/Start.png"));
    private Image continuescreen = tk.createImage(getClass().getResource("Graphics/Pictures/Continue.png"));
    private boolean atTitle = true;
    private boolean atContinueScreen = false;
    private boolean start_visible = true;
    private boolean gamestarted = false;
    private int offsetX = 0, offsetY = 0;
    private int TILE_WIDTH_PIXELS = 32;
    private int TILE_HEIGHT_PIXELS = 32;
    private int concurrentMenuItem = 0;
    public long seconds = 0;
    private MidiPlayer title = new MidiPlayer("Audio/BGM/Title.mid", true);
    private MidiPlayer continuebgm = new MidiPlayer("Audio/BGM/Continue.mid", true);
    //-----------------------------------------------------------------

    //-----------------------------------------------------------------
    // Player Variables
    //-----------------------------------------------------------------
    public String name = "Gold";
    private Image player = tk.createImage(getClass().getResource("Graphics/Characters/Player/Down.png"));
    private Image playerUp = tk.createImage(getClass().getResource("Graphics/Characters/Player/Up.png"));
    private Image playerUp1 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Up1.png"));
    private Image playerUp2 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Up2.png"));
    private Image playerDown = tk.createImage(getClass().getResource("Graphics/Characters/Player/Down.png"));
    private Image playerDown1 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Down1.png"));
    private Image playerDown2 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Down2.png"));
    private Image playerLeft = tk.createImage(getClass().getResource("Graphics/Characters/Player/Left.png"));
    private Image playerLeft1 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Left1.png"));
    private Image playerLeft2 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Left2.png"));
    private Image playerRight = tk.createImage(getClass().getResource("Graphics/Characters/Player/Right.png"));
    private Image playerRight1 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Right1.png"));
    private Image playerRight2 = tk.createImage(getClass().getResource("Graphics/Characters/Player/Right2.png"));
    public Player gold = new Player(10, 9, name, player);
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
    private int posX = 224; //Multiple of 32
    private int posY = 118; //-10 because height is 42, not 32.
    private int currentX_loc; //Starting Location of player in terms of rows
    private int currentY_loc; //Starting Location of player in terms of columns
    private int posX_tile; //Location of player in terms of rows
    private int posY_tile; //Location of player in terms of columns
    private boolean movable = true;
    private static Random randGen = new Random();
    public int badges = 0;

    private int stepscount = 0;
    public int money = 2000;
    public long timePlayed = 0;
    public long currentTime = 0;
    public Monsters[][] pokedex = new Monsters[493][40];
    public Monsters wildPokemon = new Monsters();
    public Monsters playerPokemon1 = new Monsters();
    public Monsters playerPokemon2 = new Monsters();
    public Monsters playerPokemon3 = new Monsters();
    public Monsters playerPokemon4 = new Monsters();
    public Monsters playerPokemon5 = new Monsters();
    public Monsters playerPokemon6 = new Monsters();
    public Monsters pokemonparty[] = new Monsters[6];
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

    //-----------------------------------------------------------------
    // Pokemon Constructor
    //-----------------------------------------------------------------

    public Pokemon() {
        currentBGM = title;
        currentBGM.start();
        menu = new MenuScene(this);
        col.loadClip("Audio/SE/Collision.wav", "Collision", 1);
        col.loadClip("Audio/SE/Select.wav", "Select", 1);
        col.loadClip("Audio/SE/Menu.wav", "Menu", 1);
        col.loadClip("Audio/SE/Damage.wav", "Damage", 1);
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(480, 320));
        addKeyListener(this);
        gameTimer = new javax.swing.Timer(350, this);
        gameTimer.start();
    }

    //-----------------------------------------------------------------
    // ActionListener
    //-----------------------------------------------------------------

    public void actionPerformed(ActionEvent e) {
        currentTime = java.lang.System.currentTimeMillis();
        if (gamestarted == true) {
            //-----------------------------------------------------------------
            // Battle Scene
            //-----------------------------------------------------------------
            if (inBattle == true) {
                if (encounter.playerPokemon.getCurrentHP() <= 0) {
                    //Whited Out
                    System.out.println("Player Pokemon has fainted");
                    System.out.println(gold.getName() + " is all out of usable Pokemon!");
                    System.out.println(gold.getName() + " whited out.");
                    encounter.whiteOut();
                    currentX_loc += 42 - posX_tile;
                    currentY_loc += 107 - posY_tile;
                    posX_tile = 42;
                    posY_tile = 107;
                    gold.setSprite(playerUp);
                    lastdir = 1;
                    playerPokemon1.healPokemon();
                    changeBGM(pokecenter);
                }
                if (encounter.enemyPokemon.getCurrentHP() <= 0) {
                    encounter.playerWon = true;
                    System.out.println("Wild Pokemon has fainted");
                    encounter.Win();
                }
                if (encounter.playerTurn == false) {
                    wait(1);
                    encounter.enemyTurn();
                }
            }
            //-----------------------------------------------------------------
            seconds = (currentTime - timePlayed) / 1000;
            gold.setCurrentX(posX_tile);
            gold.setCurrentY(posY_tile);
            //Random Encounter Variables
            r = (int) ((5 - 1) * Math.random() + 1);
            rndwildmodify = randGen.nextInt(22) + 11;
            //Wild Pokemon Encounter Check
            checkBattle();
            //Teleport Code
            transfer();
            //Can't walk outside of the Map Array
            if (posX_tile <= 0) {
                movable_left = false;
            }
            if (posX_tile >= mapTilesX - 1) {
                movable_right = false;
            }
            if (posY_tile <= 0) {
                movable_up = false;
            }
            if (posY_tile >= mapTilesY - 1) {
                movable_down = false;
            }
            //Crashtesting with NPC's
            movable_up = true;
            movable_down = true;
            movable_left = true;
            movable_right = true;
            if (noClip == false) {
                for (int i = 0; i < currentMapNPC.length; i++) {
                    if (gold.crashTest(currentMapNPC[i]) == 1) {
                        movable_up = false;
                        disable_talk = false;
                    } else if (gold.crashTest(currentMapNPC[i]) == 2) {
                        movable_down = false;
                        disable_talk = false;
                    } else if (gold.crashTest(currentMapNPC[i]) == 3) {
                        movable_left = false;
                        disable_talk = false;
                    } else if (gold.crashTest(currentMapNPC[i]) == 4) {
                        movable_right = false;
                        disable_talk = false;
                    }
                    if (gold.crashTest(currentMapNPC[i]) != 0) {
                        if (collision == true) {
                            col.playClip("Collision");
                            collision = false;
                        }
                    }
                }
                //Crashtesting with Inpassible Objects
                for (int i = 0; i < currentMapStaticTiles.length; i++) {
                    if (gold.crashTest(currentMapStaticTiles[i]) == 1) {
                        movable_up = false;
                    }
                    if (gold.crashTest(currentMapStaticTiles[i]) == 2) {
                        movable_down = false;
                    }
                    if (gold.crashTest(currentMapStaticTiles[i]) == 3) {
                        movable_left = false;
                    }
                    if (gold.crashTest(currentMapStaticTiles[i]) == 4) {
                        movable_right = false;
                    }
                    if (gold.crashTest(currentMapStaticTiles[i]) != 0) {
                        if (collision == true) {
                            col.playClip("Collision");
                            collision = false;
                        }
                    }
                }
            }
            //Movement Scrolling
            if (walking == true) {
                movespritepixels++;
                if (up == true && movable_up == true) {
                    offsetY += 2;
                }
                if (down == true && movable_down == true) {
                    offsetY -= 2;
                }
                if (left == true && movable_left == true) {
                    offsetX += 2;
                }
                if (right == true && movable_right == true) {
                    offsetX -= 2;
                }
            }
            //Movement Reset
            if (movespritepixels >= 16) {
                movespritepixels = 0;
                walking = false;
                if (up == true && movable_up == true) posY_tile -= 1;
                if (down == true && movable_down == true) posY_tile += 1;
                if (left == true && movable_left == true) posX_tile -= 1;
                if (right == true && movable_right == true) posX_tile += 1;
                up = false;
                down = false;
                left = false;
                right = false;
                footsprite = !footsprite;
                if (playerPokemon1.curHp <= 0) {
                    System.out.println(playerPokemon1.name + " has fainted.");
                    System.out.println(gold.getName() + " is all out of usable Pokemon.");
                    System.out.println(gold.getName() + " whited out.");
                    currentX_loc += 42 - posX_tile;
                    currentY_loc += 107 - posY_tile;
                    posX_tile = 42;
                    posY_tile = 107;
                    playerPokemon1.healPokemon();
                    gold.setSprite(playerUp);
                }
                if (playerPokemon1.statusEffect == 2 || playerPokemon1.statusEffect == 3) {
                    playerPokemon1.curHp -= 1;
                }
            }
            //Player Sprite Animations (Up)
            if (up == true && movable_up == true) {
                if (movespritepixels >= 0 && movespritepixels < 4) {
                    gold.setSprite(playerUp);
                } else if (movespritepixels > 4 && movespritepixels < 8) {
                    gold.setSprite(playerUp);
                } else if (movespritepixels > 8 && movespritepixels < 12) {
                    if (footsprite == false) {
                        gold.setSprite(playerUp1);
                    } else {
                        gold.setSprite(playerUp2);
                    }
                } else if (movespritepixels >= 12 && movespritepixels < 15) {
                    if (footsprite == false) {
                        gold.setSprite(playerUp1);
                    } else {
                        gold.setSprite(playerUp2);
                    }
                } else {
                    gold.setSprite(playerUp);
                }
            }
            //Player Sprite Animations (Down)
            if (down == true && movable_down == true) {
                if (movespritepixels >= 0 && movespritepixels < 4) {
                    gold.setSprite(playerDown);
                } else if (movespritepixels > 4 && movespritepixels < 8) {
                    gold.setSprite(playerDown);
                } else if (movespritepixels > 8 && movespritepixels < 12) {
                    if (footsprite == false) {
                        gold.setSprite(playerDown1);
                    } else {
                        gold.setSprite(playerDown2);
                    }
                } else if (movespritepixels >= 12 && movespritepixels < 15) {
                    if (footsprite == false) {
                        gold.setSprite(playerDown1);
                    } else {
                        gold.setSprite(playerDown2);
                    }
                } else {
                    gold.setSprite(playerDown);
                }
            }
            //Player Sprite Animations (Left)
            if (left == true && movable_left == true) {
                if (movespritepixels >= 0 && movespritepixels < 4) {
                    gold.setSprite(playerLeft);
                } else if (movespritepixels > 4 && movespritepixels < 8) {
                    gold.setSprite(playerLeft);
                } else if (movespritepixels > 8 && movespritepixels < 12) {
                    if (footsprite == false) {
                        gold.setSprite(playerLeft1);
                    } else {
                        gold.setSprite(playerLeft2);
                    }
                } else if (movespritepixels >= 12 && movespritepixels < 15) {
                    if (footsprite == false) {
                        gold.setSprite(playerLeft1);
                    } else {
                        gold.setSprite(playerLeft2);
                    }
                } else {
                    gold.setSprite(playerLeft);
                }
            }
            //Player Sprite Animations (Right)
            if (right == true && movable_right == true) {
                if (movespritepixels >= 0 && movespritepixels < 4) {
                    gold.setSprite(playerRight);
                } else if (movespritepixels > 4 && movespritepixels < 8) {
                    gold.setSprite(playerRight);
                } else if (movespritepixels > 8 && movespritepixels < 12) {
                    if (footsprite == false) {
                        gold.setSprite(playerRight1);
                    } else {
                        gold.setSprite(playerRight2);
                    }
                } else if (movespritepixels >= 12 && movespritepixels < 15) {
                    if (footsprite == false) {
                        gold.setSprite(playerRight1);
                    } else {
                        gold.setSprite(playerRight2);
                    }
                } else {
                    gold.setSprite(playerRight);
                }
            }
        } else {
            //Title Screen
            start_visible = !start_visible;
        }
        repaint();
    }

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
        //Music
	 	/*if (posX_tile == 73 && posY_tile == 94) {
	 		if (down == true) {
	 			changeBGM(cherrygrovecity);
	 		}
	 		if (up == true) {
	 			changeBGM(route30);
	 		}
	 	}*/
        //Cherrygrove to PokeCenter
        if (posX_tile == 85 && posY_tile == 99) {
            currentX_loc -= 43;
            currentY_loc += 11;
            posX_tile = 42;
            posY_tile = 110;
            playerPokemon1.healPokemon();
            changeBGM(pokecenter);
        }
        //Cherrygrove to PokeMart
        if (posX_tile == 79 && posY_tile == 99) {
            currentX_loc -= 58;
            currentY_loc += 11;
            posX_tile = 21;
            posY_tile = 110;
            changeBGM(pokemart);
        }
        //Cherrygrove City to House
        if (posX_tile == 73 && posY_tile == 103) {
            currentX_loc -= 33;
            currentY_loc -= 10;
            posX_tile = 40;
            posY_tile = 93;
        }
        //Cherrygrove City to Violet City
        if (posX_tile == 40 && posY_tile == 94) {
            currentX_loc += 33;
            currentY_loc += 10;
            posX_tile = 73;
            posY_tile = 104;
        }
        //Cherrygrove City to Player Home
        if (posX_tile == 81 && posY_tile == 105) {
            currentX_loc -= 54;
            currentY_loc -= 36;
            posX_tile = 27;
            posY_tile = 69;
        }
        //Player Home to Cherrygrove City
        if (posX_tile == 27 && posY_tile == 70) {
            currentX_loc += 54;
            currentY_loc += 36;
            posX_tile = 81;
            posY_tile = 106;
        }
        //Route 30 to Berry House
        if (posX_tile == 73 && posY_tile == 81) {
            currentX_loc -= 34;
            currentY_loc -= 4;
            posX_tile = 39;
            posY_tile = 77;
        }
        //Berry House to Route 30
        if (posX_tile == 39 && posY_tile == 78) {
            currentX_loc += 34;
            currentY_loc += 4;
            posX_tile = 73;
            posY_tile = 82;
        }
        //Violet City to PokeCenter
        if (posX_tile == 37 && posY_tile == 31) {
            currentX_loc += 5;
            currentY_loc += 79;
            posX_tile = 42;
            posY_tile = 110;
            playerPokemon1.healPokemon();
            changeBGM(pokecenter);
        }
        //Violet City to PokeMart
        if (posX_tile == 15 && posY_tile == 23) {
            currentX_loc += 6;
            currentY_loc += 87;
            posX_tile = 21;
            posY_tile = 110;
            changeBGM(pokemart);
        }
        //PokeCenter to Town
        if (posX_tile == 42 && posY_tile == 111) {
            if (currentMap == "Cherrygrove City") {
                currentX_loc += 43;
                currentY_loc -= 11;
                posX_tile = 85;
                posY_tile = 100;
                changeBGM(cherrygrovecity);
            } else if (currentMap == "Violet City") {
                currentX_loc -= 5;
                currentY_loc -= 79;
                posX_tile = 37;
                posY_tile = 32;
                changeBGM(violetcity);
            } else {
                System.out.println("Haaaaaaaaaaaaaaaaaaaax");
            }
        }
        //PokeMart to Town
        if (posX_tile == 21 && posY_tile == 111) {
            if (currentMap == "Cherrygrove City") {
                currentX_loc += 58;
                currentY_loc -= 11;
                posX_tile = 79;
                posY_tile = 100;
                changeBGM(cherrygrovecity);
            } else if (currentMap == "Violet City") {
                currentX_loc -= 6;
                currentY_loc -= 87;
                posX_tile = 15;
                posY_tile = 24;
                changeBGM(violetcity);
            } else {
                System.out.println("Haaaaaaaaaaaaaaaaaaaax");
            }
        }
        //Violet City to House
        if (posX_tile == 27 && posY_tile == 35) {
            currentX_loc -= 21;
            currentY_loc += 75;
            posX_tile = 6;
            posY_tile = 110;
        }
        //House to Violet City
        if (posX_tile == 6 && posY_tile == 111) {
            currentX_loc += 21;
            currentY_loc -= 75;
            posX_tile = 27;
            posY_tile = 36;
        }
        //Violet City to Violet Gym
        if (posX_tile == 24 && posY_tile == 23) {
            currentX_loc -= 17;
            currentY_loc += 70;
            posX_tile = 7;
            posY_tile = 93;
            changeBGM(gym);
        }
        //Violet Gym to Violet City
        if (posX_tile == 7 && posY_tile == 94) {
            currentX_loc += 17;
            currentY_loc -= 70;
            posX_tile = 24;
            posY_tile = 24;
            changeBGM(violetcity);
        }
        //Violet City to Pokemon School
        if (posX_tile == 36 && posY_tile == 23) {
            currentX_loc -= 12;
            currentY_loc += 70;
            posX_tile = 24;
            posY_tile = 93;
        }
        //Pokemon School to Violet City
        if (posX_tile == 24 && posY_tile == 94) {
            currentX_loc += 12;
            currentY_loc -= 70;
            posX_tile = 36;
            posY_tile = 24;
            changeBGM(violetcity);
        }
        //Route 31 to Violet City
        if (posX_tile == 50 && posY_tile == 31) {
            currentX_loc -= 6;
            currentY_loc -= 0;
            posX_tile = 44;
            posY_tile = 31;
            changeBGM(violetcity);
        }
        if (posX_tile == 50 && posY_tile == 30) {
            currentX_loc -= 6;
            currentY_loc -= 0;
            posX_tile = 44;
            posY_tile = 30;
            changeBGM(violetcity);
        }
        //Violet City to Route 31
        if (posX_tile == 45 && posY_tile == 31) {
            currentX_loc += 6;
            currentY_loc += 0;
            posX_tile = 51;
            posY_tile = 31;
            changeBGM(route30);
        }
        if (posX_tile == 45 && posY_tile == 30) {
            currentX_loc += 6;
            currentY_loc += 0;
            posX_tile = 51;
            posY_tile = 30;
            changeBGM(route30);
        }
        //Player Room to Player House
        if (posX_tile == 11 && posY_tile == 64) {
            currentX_loc += 18;
            currentY_loc += 0;
            posX_tile = 29;
            posY_tile = 64;
            gold.setSprite(playerDown);
            //changeBGM(route30);
        }
        //Player House to Player Room
        if (posX_tile == 29 && posY_tile == 63) {
            currentX_loc -= 18;
            currentY_loc += 2;
            posX_tile = 11;
            posY_tile = 65;
            gold.setSprite(playerDown);
            //changeBGM(route30);
        }
    }

    public void showMessageBox(Graphics g) {
        g.setColor(Color.BLACK);
        //Trainer Pokemon Battles
	 	/*if (talkable == true) {
	 		for (int i = 0; i < currentMapNPC.length; i++) {
	 			if (currentMapNPC[i].getTalkable(gold) == true) {
	 				lastBGM = currentBGM;
			 		currentBGM.stop();
			 		currentBGM = battleBGM;
			 		currentBGM.start();
			 		//Trainer
			 		if (currentMapNPC[i] == bird_keeper1) {
			 			wildPokemon.create(198);
			 			trainerparty[0] = wildPokemon;
			 		}
			 		//Trainer
			 		wait(1);
			 		inBattle = true;
			 		disable_start = true;
			 		trainerencounter = new TrainerBattleScene(this,currentMapNPC[i],pokemonparty,trainerparty,items);
			 		try{Thread.sleep(500);}
		    		catch(InterruptedException e){}
	 			}
	 		}
     	}*/
        //NPC Talking
        if (talkable == true) {
            for (int i = 0; i < currentMapNPC.length; i++) {
                if (currentMapNPC[i].getTalkable(gold) == true) {
                    text = currentMapNPC[i].getText(gold);
                }
            }
        }
        if (talkable == true && movable_up == false) {
            if (gold.getSprite() == playerUp) {
                g.drawImage(messagebox, 0, 0, null);
                g.drawString(text, 25, 255);
            }
        }
        if (talkable == true && movable_down == false) {
            if (gold.getSprite() == playerDown) {
                g.drawImage(messagebox, 0, 0, null);
                g.drawString(text, 25, 255);
            }
        }
        if (talkable == true && movable_left == false) {
            if (gold.getSprite() == playerLeft) {
                g.drawImage(messagebox, 0, 0, null);
                g.drawString(text, 25, 255);
            }
        }
        if (talkable == true && movable_right == false) {
            if (gold.getSprite() == playerRight) {
                g.drawImage(messagebox, 0, 0, null);
                g.drawString(text, 25, 255);
            }
        }
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

    //-----------------------------------------------------------------
    // Main
    //-----------------------------------------------------------------

    public void startgame(boolean continued) {
        loadTileSet();
        loadMap(currentMapName);
        if (continued == true) {
            loadGame();
            mainitems[0][3] = potion;
        } else {
            name = "Gold";
            gold.setName(name);
            gold.createTrainerID();
            gold.createSecretID();
            currentX_loc = 6 - 7;
            currentY_loc = 67 - 4;
            posX_tile = currentX_loc + 7;
            posY_tile = currentY_loc + 4;
            playerPokemon1.create(25);
            playerPokemon2.create(0);
            playerPokemon3.create(0);
            playerPokemon4.create(0);
            playerPokemon5.create(0);
            playerPokemon6.create(0);
            pokemonparty[0] = playerPokemon1;
            pokemonparty[1] = playerPokemon2;
            pokemonparty[2] = playerPokemon3;
            pokemonparty[3] = playerPokemon4;
            pokemonparty[4] = playerPokemon5;
            pokemonparty[5] = playerPokemon6;
            mainitems[0][3] = potion;
            money = 2000;
        }
        currentBGM.stop();
        currentBGM = cherrygrovecity;
        currentBGM.start();
        player = playerDown;
        atTitle = false;
        atContinueScreen = false;
        gamestarted = true;
        movable = true;
        timePlayed = java.lang.System.currentTimeMillis();
    }


    public void loadTileSet() {
        File file = new File("Data/Tiles.tileset");
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);
            for (int i = 0; i < tileset.length; i++) {
                tileset[i] = tk.createImage(getClass().getResource("Graphics/" + dis.readLine()));
            }
            fis.close();
            bis.close();
            dis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String map) {
        try {
            float r = 1;
            float g = 1;
            float b = 1;
            float h = 0;
            float s = 1;
            boolean hasColourEffect = false;
            BufferedReader reader = new BufferedReader(new FileReader(map));
            String line = reader.readLine();
            StringTokenizer tokens = new StringTokenizer(line);
            int width = Integer.parseInt(tokens.nextToken());
            int height = Integer.parseInt(tokens.nextToken());
            mapTilesX = width;
            mapTilesY = height;
            String tileset = tokens.nextToken();
            line = reader.readLine();
            tokens = new StringTokenizer(line);
            if (tokens.nextToken().equalsIgnoreCase("colorization")) {
                hasColourEffect = true;
                r = Float.parseFloat(tokens.nextToken());
                g = Float.parseFloat(tokens.nextToken());
                b = Float.parseFloat(tokens.nextToken());
                h = Float.parseFloat(tokens.nextToken());
                s = Float.parseFloat(tokens.nextToken());
            }
            while (!line.equals(".")) {
                line = reader.readLine();
            }
            for (int layers = 0; layers < 2; layers++) {
                line = reader.readLine();
                tokens = new StringTokenizer(line);
                for (int y = 0; y < (width * height); y++) {
                    String code = tokens.nextToken();
                    if (layers == 0) {
                        currentMap0[y] = Integer.parseInt(code);
                    } else if (layers == 1) {
                        currentMap1[y] = Integer.parseInt(code);
                    }
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static void wait(int n) {
        long t0, t1;
        t0 = System.currentTimeMillis();
        do {
            t1 = System.currentTimeMillis();
        }
        while ((t1 - t0) < (n * 1000));
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