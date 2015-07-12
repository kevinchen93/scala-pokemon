import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Image.*;
import java.util.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;

public class MenuScene {
	
	private Pokemon game;
	private Font pokefont = new Font("pokesl1", Font.PLAIN, 18);
	private Random r = new Random();
	
	public boolean inMain = false;
	public boolean inPokeDex = false;
	public boolean inPokemon = false;
	public boolean inBag = false;
	public boolean inPokeGear = false;
	public boolean inTrainerCard = false;
	public boolean inSave = false;
	public boolean inOption = false;
	public boolean inExit = false;
	public int currentSelectionMain;
	public int currentSelectionItemX;
	public int currentSelectionItemY;
	public int currentSelectionPokeGear;
	public int currentSelectionSave;
	public int currentSelectionOption;
	public Monsters playerPokemon;
	public Monsters enemyPokemon;
	public Items[] battleItems;
	
	public boolean cancelbutton = false;
	
	private Image arrow = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Arrow.png"));
	private Image mainMenu = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Menu.png"));
	
	private Image pokedex = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/pokedexbg.png"));
	private Image pokemon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/pokeselbg.png"));
	private Image bag = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/BagScreen.png"));
	private Image pokegear = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Pokegearback.png"));
	private Image trainercard = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/TrainerCard.png"));
	private Image save = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Save.png"));
	private Image option = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Option.png"));
	
	private Image partyscreenfirstbox = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/Box.png"));
	private Image partyscreenboxes = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/SelectionBar.png"));
	private Image partyscreencancel = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/pokeselcancel.png"));
	private Image partyscreencancelselect = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/pokeselcancelsel.png"));
	
	private Image entryMap = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/MapEntry.png"));
	private Image entryRadio = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/RadioEntry.png"));
	private Image entryPhone = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/PhoneEntry.png"));
	private Image entryExit = Toolkit.getDefaultToolkit().getImage(getClass().getResource("Graphics/Pictures/ExitEntry.png"));
	
	private JukeBox col = new JukeBox();

    public MenuScene(Pokemon pkmn) {
    	game = pkmn;
    	col.loadClip("Audio/SE/Menu.wav", "Menu", 1);
    	col.loadClip("Audio/SE/Select.wav", "Select", 1);
    	Start();
    }
    
    public void Start() {
    	currentSelectionMain = 2;
		currentSelectionItemX = 0;
		currentSelectionItemY = 0;
		currentSelectionPokeGear = 0;
		currentSelectionSave = 0;
    	inMain = true;
    }
    
    public void PokeDex() {
    	inMain = false;
    	inPokeDex = true;
    }
    
    public void Pokemon() {
    	inMain = false;
    	inPokemon = true;
    	System.out.println("Pokemon");
    }
    
    public void Bag() {
    	inMain = false;
    	inBag = true;
    	System.out.println("Bag");
    }
    
    public void PokeGear() {
    	inMain = false;
    	inPokeGear = true;
    	System.out.println("PokeGear");
    }
    
    public void TrainerCard() {
    	inMain = false;
    	inTrainerCard = true;
    	System.out.println("Trainer Card");
    }
    
    public void Save() {
    	inMain = false;
    	inSave = true;
		System.out.println("Save");
    }
    
    public void Option() {
    	inMain = false;
    	inOption = true;
		System.out.println("Option");
    }
    
    public void Exit() {
    	currentSelectionMain = 2;
		currentSelectionItemX = 0;
		currentSelectionItemY = 0;
		currentSelectionSave = 0;
    	inMain = false;
    	game.inMenu = false;
		System.out.println("Exit");
    }
    
    public void paint(Graphics g) {
    	g.setFont(pokefont);
    	g.setColor(Color.BLACK);
    	if (inMain == true) {
    		g.drawImage(mainMenu,0,0,null);
			if (currentSelectionMain == 0) {
	    	 	//Pokedex
	    	 	g.drawImage(arrow, 335, 20, null);
	    	}
	    	else if (currentSelectionMain == 1) {
	    	 	//Pokemon
	    	 	g.drawImage(arrow, 335, 52, null);
	    	}
	    	else if (currentSelectionMain == 2) {
	    	 	//Bag
	    	 	g.drawImage(arrow, 335, 84, null);
	    	}
	    	else if (currentSelectionMain == 3) {
	    	 	//Pokegear
	    	 	g.drawImage(arrow, 335, 116, null);
	    	}
	    	else if (currentSelectionMain == 4) {
	    	 	//Gold
	    	 	g.drawImage(arrow, 335, 148, null);
	    	}
	    	else if (currentSelectionMain == 5) {
	    	 	//Save
	    	 	g.drawImage(arrow, 335, 180, null);
	    	}
	    	else if (currentSelectionMain == 6) {
	    	 	//Option
	    	 	g.drawImage(arrow, 335, 212, null);
	    	}
	    	else if (currentSelectionMain == 7) {
	    	 	//Exit
	    	 	g.drawImage(arrow, 335, 244, null);
	    	}
    	}
    	if (inPokeDex == true) {
    		g.drawImage(pokedex,0,0,null);
    	}
    	if (inPokemon == true) {
    		g.drawImage(pokemon,0,0,null);
		 	g.drawImage(partyscreenfirstbox,40,20,null);
		 	g.drawImage(partyscreenboxes,190,20,null);
		 	g.drawImage(partyscreenboxes,190,70,null);
		 	g.drawImage(partyscreenboxes,190,120,null);
		 	g.drawImage(partyscreenboxes,190,170,null);
		 	g.drawImage(partyscreenboxes,190,220,null);
		 	if (game.pokemonparty.length == 2) {
		 		g.drawImage(partyscreenboxes,190,20,null);
		 	}
		 	g.drawImage(game.playerPokemon1.getIcon(),45,30,null);
		 	g.drawString(game.playerPokemon1.getName(),80,120);
		 	if (cancelbutton == false) {
		 		g.drawImage(partyscreencancel,370,280,null);
		 	}
		 	else {
		 		g.drawImage(partyscreencancelselect,370,280,null);
		 	}
    	}
    	if (inBag == true) {
    		g.drawImage(bag,0,0,null);
    	}
    	if (inPokeGear == true) {
    		g.drawImage(pokegear,0,0,null);
    		if (currentSelectionPokeGear == 0) {
	    		g.drawImage(entryMap,0,0,null);
		    }
		    else if (currentSelectionPokeGear == 1) {
		    	g.drawImage(entryRadio,0,0,null);
		    }
		    else if (currentSelectionPokeGear == 2) {
		    	g.drawImage(entryPhone,0,0,null);
		    }
		    else if (currentSelectionPokeGear == 3) {
		    	g.drawImage(entryExit,0,0,null);
		    }
    	}
    	if (inTrainerCard == true) {
    		g.drawImage(trainercard,0,0,null);
    		g.drawImage(game.cardSprite,320,100,null);
		 	g.drawString("ID:     " + game.gold.getID(),295,54);
		 	g.drawString("Name:     " + game.gold.getName(),64,93);
		 	g.drawString("Money:                 $" + game.money,64,150);
		 	g.drawString("Pokedex:                      " + game.gold.getPokemonOwned(),64,183);
		 	if (game.seconds < 10) {
		 		g.drawString("Time:              0" + game.hours + ": 0" + game.minutes + ": 0" + game.seconds,64,213);
		 	}
		 	else {
		 		g.drawString("Time:              0" + game.hours + ": 0" + game.minutes + ": " + game.seconds,64,213);
		 	}
    	}
    	if (inSave == true) {
    		g.drawImage(save,0,0,null);
    		g.drawString("" + game.gold.getName(),100,68);
    		g.drawString("" + game.badges,100,101);
    		g.drawString("1",110,134);
    		g.drawString("0" + game.hours + ": 0" + game.minutes + ": " + game.seconds,76,166);
    		if (currentSelectionSave == 0) {
	    		g.drawImage(arrow, 394, 148, null);
		    }
		    else if (currentSelectionSave == 1) {
		    	g.drawImage(arrow, 394, 180, null);
		    }
    	}
    	if (inOption == true) {
    		g.drawImage(option,0,0,null);
    		if (currentSelectionOption == 0) {
	    		g.drawImage(arrow, 22, 85, null);
		    }
		    else if (currentSelectionOption == 1) {
		    	g.drawImage(arrow, 22, 117, null);
		    }
		    else if (currentSelectionOption == 2) {
		    	g.drawImage(arrow, 22, 149, null);
		    }
		    else if (currentSelectionOption == 3) {
		    	g.drawImage(arrow, 22, 181, null);
		    }
		    else if (currentSelectionOption == 4) {
		    	g.drawImage(arrow, 22, 213, null);
		    }
		    else if (currentSelectionOption == 5) {
		    	g.drawImage(arrow, 22, 245, null);
		    }
    	}
    	if (inExit == true) {
    		//Nothing
    	}
    }
}