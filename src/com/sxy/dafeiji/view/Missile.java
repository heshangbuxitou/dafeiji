package com.sxy.dafeiji.view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import javax.swing.ImageIcon;

import com.sxy.dafeiji.util.Explode;
import com.sxy.dafeiji.util.Wall;

public class Missile {
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;
	
	public static final int width = 10;
	public static final int height = 10;
	
	
	int x, y;
	Airplane.Direction dir;
	
	private boolean good;
	private boolean live = true;
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
	private AirplaneClient tc;
	
	public Missile(int x, int y, Airplane.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	public Missile(int x, int y,boolean good, Airplane.Direction dir,AirplaneClient tc){
		this( x, y, dir );
		this.tc = tc;
		this.good = good;
	}
	
	public void draw(Graphics g) {
		if(!live) {
			tc.missiles.remove(this);
			return;
		}	
		if(!good) g.drawImage(new ImageIcon("image/ballRed.gif").getImage(), x, y, width, height, null);
		else g.drawImage(new ImageIcon("image/ballSilver.gif").getImage(), x, y, width, height, null);
		//Color c = g.getColor();
		//g.setColor(Color.RED);
		//g.fillOval(x, y, width, height);
		//g.setColor(c);
		
		move();
	}
	
	

	private void move() {
		
		switch(dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		}
		if(x<0 || y<0 || x > AirplaneClient.GAME_WIDTH || y > AirplaneClient.GAME_HEIGHT ){
			live = false;
		}
	}
	
	public boolean islive(){
		return live;
	}
	public Rectangle getRect(){
		return new Rectangle(x, y, width, height);
	}
	public boolean hitTank(Airplane t){
		if(this.live && this.getRect().intersects(t.getRect()) && t.isLive() && this.good!=t.isGood()){
			if(!good){
				t.setLife(t.getLife()-20);
				if(t.getLife() <= 0) t.setLive(false);
			}
			else{
				t.setLive(false);
				this.tc.grade += 500;
			}
			
			this.live = false;
			Explode e = new Explode(x, y, this.tc);
			tc.explodes.add(e);
			return true;
			}
		
		return false;
	}
	public boolean hitTanks(List<Airplane> tanks){
		for(int i=0; i<tanks.size(); i++){
			if(hitTank(tanks.get(i))){
				return true;
			}
		}
		return false;
	}
	public boolean hitWall(Wall w){
		if( this.live && this.getRect().intersects(w.getRect())){
			this.live = false;
			return true;
		}
		return false;
	}
	
}
