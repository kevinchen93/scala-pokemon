import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Image.*;
import java.util.*;
import java.awt.geom.*;
import java.io.*;
import java.net.*;

public class Monsters {
	
	public String name;
	public int level, number, hp, attack, def, spAttack, spDef, spd;
	public int base_hp, base_attack, base_def, base_spAttack, base_spDef, base_spd;
	public int cur_HP, cur_attack, cur_def, cur_spAttack, cur_spDef, cur_spd;
	public int ev_HP, ev_attack, ev_def, ev_spAttack, ev_spDef, ev_spd;
	public double exp, cur_exp;
	private Image back_sprite, front_sprite, back_sprite_s, front_sprite_s, party_icon;
	
	private int normal = 0;
	private int fire = 1;
	private int water = 2;
	private int grass = 3;
	private int electric = 4;
	private int ice = 5;
	private int fighting = 6;
	private int poison = 7;
	private int ground = 8;
	private int flying = 9;
	private int psychic = 10;
	private int bug = 11;
	private int rock = 12;
	private int ghost = 13;
	private int dragon = 14;
	private int dark = 15;
	private int steel = 16;
	
	public Attacks move;
	public String move1 = "";
	public String move2 = "";
	public String move3 = "";
	public String move4 = "";
	public int attack_damage;
	
	public int statusEffect = 0;
	
	private boolean weak[]= new boolean[16];
	private boolean strong[]= new boolean[16];
	private boolean shiny = false;
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getHP() {
		return hp;
	}
	
	public void setCurrentHP(int i) {
		cur_HP = cur_HP-i;
	}
	
	public void healPokemon() {
		cur_HP = hp;
		cur_attack = attack;
		cur_def = def;
		cur_spAttack = spAttack;
		cur_spDef = spDef;
		cur_spd = spd;
		statusEffect = 0;
		System.out.println("Player's Pokemon have been healed back to full.");
	}
	
	public int getCurrentHP() {
		return cur_HP;
	}
	
	public Image getFrontSprite() {
		if (shiny == true) {
			return front_sprite_s;
		}
		else {
			return front_sprite;
		}
	}
	
	public Image getBackSprite() {
		if (shiny == true) {
			return back_sprite_s;
		}
		else {
			return back_sprite;
		}
	}
	
	public Image getIcon() {
		return party_icon;
	}
	
	public boolean[] getWeak() {
		return weak;
	}
	
	public boolean[] getStrong() {
		return strong;
	}
	
	public double getEXP() {
		return cur_exp;
	}
	
	public void levelUp() {
		level += 1;
		cur_exp = 0;
		exp = Math.pow(level,3);
	}
	
	public double getLevelEXP() {
		return exp;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void create(int n) {
		//loadPokemon();
		if (n == 4) {
			//Charmander
			name = "Charmander";
			level = 5;
			number = 4;
			back_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/004b.png");
			front_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/004.png");
			back_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/004sb.png");
			front_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/004s.png");
			party_icon = Toolkit.getDefaultToolkit().createImage("Graphics/Icons/icon004.png");
			hp = 20;
			cur_HP = 20;
			exp = 200;
			cur_exp = 0;
			attack = 14;
			cur_attack = 14;
			def = 15;
			cur_def = 15;
			spAttack = 40;
			spDef = 10;
			move1 = "Ember";
			move2 = "Scratch";
			move3 = "Tail Whip";
			move4 = "Fire Spin";
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
		else if (n == 220) {
			//Swinub
			name = "Swinub";
			level = 3;
			number = 220;
			back_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/220b.png");
			front_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/220.png");
			back_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/220sb.png");
			front_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/220s.png");
			party_icon = Toolkit.getDefaultToolkit().createImage("Graphics/Icons/icon220.png");
			hp = 15;
			cur_HP = 15;
			exp = 200;
			cur_exp = 0;
			attack = 15;
			cur_attack = 15;
			def = 20;
			cur_def = 20;
			spAttack = 40;
			spDef = 10;
			move1 = "Icy Wind";
			move2 = "Scratch";
			move3 = "Dig";
			move4 = "Growl";
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
		else if (n == 158) {
			//Totodile
			name = "Totodile";
			level = 5;
			number = 158;
			back_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/158b.png");
			front_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/158.png");
			back_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/158sb.png");
			front_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/158s.png");
			party_icon = Toolkit.getDefaultToolkit().createImage("Graphics/Icons/icon158.png");
			hp = 25;
			cur_HP = 25;
			exp = 113;
			cur_exp = 0;
			attack = 16;
			cur_attack = 16;
			def = 30;
			cur_def = 30;
			spAttack = 40;
			spDef = 10;
			move1 = "Water Gun";
			move2 = "Scratch";
			move3 = "Tail Whip";
			move4 = "Toxic";
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
		else if (n == 25) {
			//Pikachu
			name = "Pikachu";
			level = 5;
			number = 25;
			back_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/025b.png");
			front_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/025.png");
			back_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/025sb.png");
			front_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/025s.png");
			party_icon = Toolkit.getDefaultToolkit().createImage("Graphics/Icons/icon025.png");
			hp = 23;
			cur_HP = 23;
			exp = 200;
			cur_exp = 0;
			attack = 12;
			cur_attack = 12;
			def = 10;
			cur_def = 10;
			spAttack = 40;
			spDef = 10;
			cur_exp = 0;
			move1 = "Thundershock";
			move2 = "Quick Attack";
			move3 = "Tail Whip";
			move4 = "Thunderwave";
			/*for(int x=0; x<16; x++) {
				weak[x]=false;
				strong[x]=false;
			}
			weak[ground]=true;
			weak[rock]=true;
			strong[water]=true;
			strong[flying]=true;*/
		}
		else if (n == 198) {
			//Murkrow
			name = "Murkrow";
			level = 4;
			number = 198;
			back_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/198b.png");
			front_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/198.png");
			back_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/198sb.png");
			front_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/198s.png");
			party_icon = Toolkit.getDefaultToolkit().createImage("Graphics/Icons/icon198.png");
			hp = 19;
			cur_HP = 19;
			exp = 200;
			cur_exp = 0;
			attack = 13;
			cur_attack = 13;
			def = 15;
			cur_def = 15;
			spAttack = 10;
			spDef = 10;
			move1 = "Peck";
			move2 = "Pursuit";
			move3 = "Quick Attack";
			move4 = "Growl";
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
		else {
			//MissingNo
			name = "MissingNo";
			level = 255;
			number = 0;
			back_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/000b.png");
			front_sprite = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/000.png");
			back_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/000sb.png");
			front_sprite_s = Toolkit.getDefaultToolkit().createImage("Graphics/Battlers/000s.png");
			party_icon = Toolkit.getDefaultToolkit().createImage("Graphics/Icons/icon000.png");
			hp = 255;
			attack = 255;
			exp = 200;
			cur_exp = 0;
			cur_attack = 255;
			def = 255;
			cur_def = 255;
			def = 255;
			spAttack = 255;
			spDef = 255;
			move1 = "Sky Attack";
			move2 = "Flamethrower";
			move3 = "Tackle";
			move4 = "Hyper Beam";
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
	
	public void loadPokemon() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("Data/pokemon.txt"));
			String line = "";
			StringTokenizer tokens = new StringTokenizer(line);
			number = Integer.parseInt(reader.readLine());
				System.out.println("" + number);
			name = reader.readLine();
				System.out.println("" + name);
			String internalname = reader.readLine();
				System.out.println("" + internalname);
			String kind = reader.readLine();
				System.out.println("" + kind);
			String pokedexEntry = reader.readLine();
				System.out.println("" + pokedexEntry);
			String type1 = reader.readLine();
				System.out.println("" + type1);
			String type2 = reader.readLine();
				System.out.println("" + type2);
			line = reader.readLine();
			tokens = new StringTokenizer(line);
			base_hp = Integer.parseInt(tokens.nextToken());
				System.out.println("" + base_hp);
			base_attack = Integer.parseInt(tokens.nextToken());
				System.out.println("" + base_attack);
			base_spAttack = Integer.parseInt(tokens.nextToken());
				System.out.println("" + base_spAttack);
			base_def = Integer.parseInt(tokens.nextToken());
				System.out.println("" + base_def);
			base_spDef = Integer.parseInt(tokens.nextToken());
				System.out.println("" + base_spDef);
			base_spd = Integer.parseInt(tokens.nextToken());
				System.out.println("" + base_spd);
			int rareness = Integer.parseInt(reader.readLine());
				System.out.println("" + rareness);
			int baseEXP = Integer.parseInt(reader.readLine());
				System.out.println("" + baseEXP);
			int happiness = Integer.parseInt(reader.readLine());
				System.out.println("" + happiness);
			String growthrate = reader.readLine();
				System.out.println("" + growthrate);
			int stepstohatch = Integer.parseInt(reader.readLine());
				System.out.println("" + stepstohatch);
			String color = reader.readLine();
				System.out.println("" + color);
			String habitat = reader.readLine();
				System.out.println("" + habitat);
			line = reader.readLine();
			tokens = new StringTokenizer(line);
			ev_HP = Integer.parseInt(tokens.nextToken());
				System.out.println("" + ev_HP);
			ev_attack = Integer.parseInt(tokens.nextToken());
				System.out.println("" + ev_attack);
			ev_spAttack = Integer.parseInt(tokens.nextToken());
				System.out.println("" + ev_spAttack);
			ev_def = Integer.parseInt(tokens.nextToken());
				System.out.println("" + ev_def);
			ev_spDef = Integer.parseInt(tokens.nextToken());
				System.out.println("" + ev_spDef);
			ev_spd = Integer.parseInt(tokens.nextToken());
				System.out.println("" + ev_spd);
			String abilities = reader.readLine();
				System.out.println("" + abilities);
			String compatibility = reader.readLine();
				System.out.println("" + compatibility);
			double height = Double.parseDouble(reader.readLine());
				System.out.println("" + height);
			double weight = Double.parseDouble(reader.readLine());
				System.out.println("" + weight);
			String genderRate = reader.readLine();
				System.out.println("" + genderRate);
			String moves = reader.readLine();
				System.out.println("" + moves);
			String eggmoves = reader.readLine();
				System.out.println("" + eggmoves);
			line = reader.readLine();
			tokens = new StringTokenizer(line);
			String evolutionName = tokens.nextToken();
				System.out.println("" + evolutionName);
			String evolutionType = tokens.nextToken();
				System.out.println("" + evolutionType);
			int evolutionLevel = Integer.parseInt(tokens.nextToken());
				System.out.println("" + evolutionLevel);
			int battleOffsetPlayer = Integer.parseInt(reader.readLine());
				System.out.println("" + battleOffsetPlayer);
			int battleOffsetEnemy = Integer.parseInt(reader.readLine());
				System.out.println("" + battleOffsetEnemy);
			int battleAltitude = Integer.parseInt(reader.readLine());
				System.out.println("" + battleAltitude);
			reader.close();
	 	}
	 	catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException i) {
			i.printStackTrace();
		}
	 }
}