import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image.*;
import java.util.*;
import java.awt.geom.*;
import java.io.*;

public class Player extends Actor {

	private String name;
	private Image sprite;
	private int pokemonowned = 1;
	private int id;
	private int secretid;
	private Random r = new Random();

    public Player(int x, int y, String n, Image s) {
    	super(x,y);
    	name = n;
    	sprite = s;
    }
    
    public void createTrainerID() {
    	int a = r.nextInt(9) + 1;
    	int b = r.nextInt(9) + 1;
    	int c = r.nextInt(9) + 1;
    	int d = r.nextInt(9) + 1;
    	int e = r.nextInt(9) + 1;
    	id = a + b + c + d + e;
    }
    
    public void createSecretID() {
    	int a = r.nextInt(9) + 1;
    	int b = r.nextInt(9) + 1;
    	int c = r.nextInt(9) + 1;
    	int d = r.nextInt(9) + 1;
    	int e = r.nextInt(9) + 1;
    	secretid = a + b + c + d + e;
    }
    
    public int getOriginalX() {
    	return super.o_loc_x;
    }
    
    public int getOriginalY() {
    	return super.o_loc_y;
    }
    
    public int getCurrentX() {
    	return super.loc_x;
    }
    
    public int getCurrentY() {
    	return super.loc_y;
    }
    
    public void setCurrentX(int x) {
    	super.loc_x = x;
    }
    
    public void setCurrentY(int y) {
    	super.loc_y = y;
    }
    
    public void moveUp() {
    	super.loc_y--;
    }
    
    public void moveDown() {
    	super.loc_y++;
    }
    
    public void moveLeft() {
    	super.loc_x--;
    }
    
    public void moveRight() {
    	super.loc_x++;
    }
    
    public void setName(String input) {
    	name = input;
    }
    
    public String getName() {
    	return name;
    }
    
    public int getPokemonOwned() {
    	return pokemonowned;
    }
    
    public void setID(int input) {
    	id = input;
    }
    
    public int getID() {
    	return id;
    }
    
    public void setSprite(Image m) {
    	sprite = m;
    }
    
    public Image getSprite() {
    	return sprite;
    }
    
    public int crashTest(NPC other) {
    	if ((other.getCurrentY()+1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return 1;
    		}
    	}
    	if ((other.getCurrentY()-1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return 2;
    		}
    	}
    	if ((other.getCurrentX()+1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return 3;
    		}
    	}
    	if ((other.getCurrentX()-1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return 4;
    		}
    	}
    	return 0;
    }
    
    public int crashTest(StaticTile other) {
    	if (other == null) {
    		return 0;
    	}
    	if ((other.getCurrentY()+1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return 1;
    		}
    	}
    	if ((other.getCurrentY()-1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return 2;
    		}
    	}
    	if ((other.getCurrentX()+1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return 3;
    		}
    	}
    	if ((other.getCurrentX()-1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return 4;
    		}
    	}
    	return 0;
    }
    
    public int crashTest(Building other) {
    	if ((other.getCurrentY()+1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return 1;
    		}
    	}
    	if ((other.getCurrentY()-1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return 2;
    		}
    	}
    	if ((other.getCurrentX()+1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return 3;
    		}
    	}
    	if ((other.getCurrentX()-1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return 4;
    		}
    	}
    	return 0;
    }
   
}