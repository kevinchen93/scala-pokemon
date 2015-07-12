import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.Graphics2D;
import java.awt.image.*;
import java.util.*;
import java.awt.geom.*;
import java.io.*;

public class NPC extends Actor {

	private String name;
	private String text;
	private String battleText;
	private Image[] Charset;
	private Image sprite;
	private Image battleSprite;
	private int location_x;
	private int location_y;
	private int dir = 1;

    public NPC(int x, int y, String n, String t, Image s, Image bs) {
    	super(x,y);
    	name = n;
    	text = t;
    	location_x = x;
	    location_y = y;
    	battleText = t;
    	battleSprite = bs;
    	sprite = s;
    }
    
    public int getOriginalX() {
    	return super.o_loc_x;
    }
    
    public int getOriginalY() {
    	return super.o_loc_y;
    }
    
    public int getCurrentX() {
    	return super.getX();
    }
    
    public int getCurrentY() {
    	return super.getY();
    }
    
    public void setDirection(int i) {
    	dir = i;
    }
    
    public int getDirection() {
    	return dir;
    }
    
    public int getWidth() {
    	return sprite.getWidth(null)/4;
    }
    
    public int getHeight() {
    	return sprite.getHeight(null)/4;
    }
    
    public void act() {
    	
    }
    
    public void move() {
    	
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
    
    public String getName() {
    	return name;
    }
    
    public Image getSprite() {
    	return sprite;
    }
    
    public Image getBattleSprite() {
    	return battleSprite;
    }
    
    public boolean getTalkable(Player other) {
    	if ((other.getCurrentY()+1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return true;
    		}
    	}
    	if ((other.getCurrentY()-1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return true;
    		}
    	}
    	if ((other.getCurrentX()+1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return true;
    		}
    	}
    	if ((other.getCurrentX()-1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return true;
    		}
    	}
    	return false;
    }
    
    public String getText(Player other) {
    	if ((other.getCurrentY()+1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return text;
    		}
    	}
    	if ((other.getCurrentY()-1) == getCurrentY()) {
    		if ((other.getCurrentX()) == getCurrentX()) {
    			return text;
    		}
    	}
    	if ((other.getCurrentX()+1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return text;
    		}
    	}
    	if ((other.getCurrentX()-1) == getCurrentX()) {
    		if ((other.getCurrentY()) == getCurrentY()) {
    			return text;
    		}
    	}
    	return "";
    }
    
    public int getTextLength() {
    	return text.length();
    }
    
}