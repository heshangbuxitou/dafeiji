package com.sxy.dafeiji.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import com.sxy.dafeiji.util.Wall;

public class Airplane {
	public static final int XSPEED = 8;
	public static final int YSPEED = 8;
	
	private boolean good;
	private int life = 100;
	
	BloodBar bar = new BloodBar();
	
	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	private int oldX,oldY;
	
	public boolean isGood() {
		return good;
	}

	private boolean live = true;
	private int step = random.nextInt(12)+5;
	
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
	public static final int width = 40;
	public static final int heigth = 36;
	
	private int x, y;
	AirplaneClient tc = null;
	
	private static Random random = new Random();
	
	private boolean bL=false, bU=false, bR=false, bD = false;
	enum Direction {L, LU, U, RU, R, RD, D, LD, STOP};
	
	private Direction dir = Direction.STOP;
	private Direction pdDir = Direction.U;

	public Airplane(int x, int y,boolean good) {
		this.x = x;
		this.y = y;
		this.oldX = x;
		this.oldY = y;
		this.good = good;
	}
	
	public Airplane(int x,int y,boolean good,Direction dir,AirplaneClient tc){
		this(x, y, good);
		this.dir = dir;
		this.tc=tc;
	}
	
	public void draw(Graphics g) {
		if(good && this.live) bar.draw(g);
		if(!live) {
			if(!good){
				tc.airplanes.remove(this);
			}
			return;
		}
		
		//Color c = g.getColor();
		if(good) g.drawImage(new ImageIcon("image/this.gif").getImage(), x, y, width, heigth, null);
		else g.drawImage(new ImageIcon("image/enemyA.gif").getImage(), x, y, 30, 30, null);
		//g.fillOval(x, y, width, heigth);
		//g.setColor(c);
		
		/**switch(pdDir) {
		case L:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x , y + Airplane.heigth/2);
			break;
		case LU:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x , y );
			break;
		case U:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x + Airplane.width/2, y );
			break;
		case RU:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x + Airplane.width, y);
			break;
		case R:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x + Airplane.width , y + Airplane.heigth/2);
			break;
		case RD:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x + Airplane.width, y + Airplane.heigth);
			break;
		case D:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x + Airplane.width/2, y + Airplane.heigth);
			break;
		case LD:
			g.drawLine(x + Airplane.width/2 , y + Airplane.heigth/2, x , y + Airplane.heigth);
			break;
		}*/
		move();
	}
	
	void move() {
		
		this.oldX = x;
		this.oldY = y;
		
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
		case STOP:
			break;
		}
		if(dir!=Direction.STOP) pdDir = dir ;
		if(good) pdDir = Direction.U;
		
		if( x < 0 )  x = 0;
		if( y < 30 && good) y = 30;
		if(x + Airplane.width > AirplaneClient.GAME_WIDTH) x = AirplaneClient.GAME_WIDTH - Airplane.width;
		if(y  > AirplaneClient.GAME_HEIGHT && !good) tc.airplanes.remove(this);     //y= AirplaneClient.GAME_HEIGHT - Airplane.heigth;
		else if(y + Airplane.heigth > AirplaneClient.GAME_HEIGHT && good) y= AirplaneClient.GAME_HEIGHT - Airplane.heigth;
	
		if(!good){
			Direction[] dirs = Direction.values();
			if(step == 0){
				int rn = 0;
				step = random.nextInt(12)+5;
				  rn = random.nextInt(dirs.length);
				if(rn > 0 && rn < 4) rn += 3;
				dir = dirs[rn];
			}
			step--;
			
			if(random.nextInt(40)>33) this.fire();
		}
	}
	
	private void stay(){
		x = this.oldX;
		y = this.oldY;
		
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_F2:
			if(!this.live){
				this.live = true;
				this.life = 100;
				this.x = 200;
				this.y = 400;
				tc.grade = 0;
				for(int i=0; i< tc.airplanes.size(); i++){
					Airplane p = tc.airplanes.get(i);
					p.live = false;
				}
				for(int i=0; i< tc.missiles.size(); i++){
					Missile m = tc.missiles.get(i);
					m.setLive(false);
				}
				for(int i=0; i<8; i++){
					tc.airplanes.add(new Airplane(40*(i+1), 0, false,Airplane.Direction.D, this.tc));
				}
			};
			break;
		case KeyEvent.VK_CONTROL:
			fire();
			break;
		case KeyEvent.VK_LEFT :
			bL = true;
			break;
		case KeyEvent.VK_UP :
			bU = true;
			break;
		case KeyEvent.VK_RIGHT :
			bR = true;
			break;
		case KeyEvent.VK_DOWN :
			bD = true;
			break;
		}
		locateDirection();
	}
	
	 public Missile fire() {
		 if(!live) return null;
		 int x = this.x + Airplane.width/2-Missile.width/2;
		 int y = this.y + Airplane.heigth/2-Missile.height/2;
		 
		 Missile m=new Missile(x, y, good, pdDir,this.tc);
		 tc.missiles.add(m);
		 
		 return m;
	}
	 public Missile fire(Direction dir) {
		 if(!live) return null;
		 int x = this.x + Airplane.width/2-Missile.width/2;
		 int y = this.y + Airplane.heigth/2-Missile.height/2;
		 
		 Missile m=new Missile(x, y, good, dir,this.tc);
		 tc.missiles.add(m);
		 
		 return m;
	 }

	void locateDirection() {
		if(bL && !bU && !bR && !bD) dir = Direction.L;
		else if(bL && bU && !bR && !bD) dir = Direction.LU;
		else if(!bL && bU && !bR && !bD) dir = Direction.U;
		else if(!bL && bU && bR && !bD) dir = Direction.RU;
		else if(!bL && !bU && bR && !bD) dir = Direction.R;
		else if(!bL && !bU && bR && bD) dir = Direction.RD;
		else if(!bL && !bU && !bR && bD) dir = Direction.D;
		else if(bL && !bU && !bR && bD) dir = Direction.LD;
		else if(!bL && !bU && !bR && !bD) dir = Direction.STOP;
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		
		case KeyEvent.VK_LEFT :
			bL = false;
			break;
		case KeyEvent.VK_UP :
			bU = false;
			break;
		case KeyEvent.VK_RIGHT :
			bR = false;
			break;
		case KeyEvent.VK_DOWN :
			bD = false;
			break;
		case KeyEvent.VK_A:
			superFire();
			break;
		}
		locateDirection();		
	}
	public Rectangle getRect(){
		return new Rectangle(x, y, width, heigth);
	}
	public boolean collidesWithWall(Wall w){
		if( this.live && this.getRect().intersects(w.getRect())){
			this.stay();
			return true;
		}
		return false;
	}
	public boolean collidesWithTank(List<Airplane> tanks){
		for(int i=0; i<tanks.size(); i++){
			Airplane t = tanks.get(i);
			if(this != t && this.live &&t.isLive() && this.getRect().intersects(t.getRect())){
				this.stay();
				t.stay();
				return true;
			}
		}
		return false;
	}
	private void superFire(){
		Direction[] dirs = Direction.values();
		for(int i=0; i<8; i++){
			fire(dirs[i]);
		}
	}
	
	private class BloodBar{
		public void draw(Graphics g){
			Color c = g.getColor();
			g.setColor(Color.red);
			g.drawRect(280, 450, width*2, 20);
			int w = width*life/100;
			g.fillRect(280, 450, w*2, 20);
			g.setColor(c);
		}
	}
	public boolean eat(Blood b){
		if(this.live && b.isLive() && this.getRect().intersects(b.getRect())){
			this.life = 100;
			b.setLive(false);
			return true;
		}
		return false;
	}
	
}
