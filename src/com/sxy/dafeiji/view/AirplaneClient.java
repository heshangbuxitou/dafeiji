package com.sxy.dafeiji.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.sxy.dafeiji.util.Explode;
import com.sxy.dafeiji.util.PlayAudio;

public class AirplaneClient extends Frame {
	public static final int GAME_WIDTH = 400;
	public static final int GAME_HEIGHT = 500;
	
	public int grade = 0;
	
	Airplane myAirplane = new Airplane(200, 400,true,Airplane.Direction.STOP, this);
	
	public List<Explode> explodes = new ArrayList<Explode>();
	List<Missile> missiles = new ArrayList<Missile>();
	List<Airplane> airplanes = new ArrayList<Airplane>();
	//Blood b = new Blood();
	
	Image offScreenImage = null;
	//Wall w1 = new Wall(100, 100, 20, 300, this);
	//Wall w2 = new Wall(200, 200, 300, 20, this);
	
	public void paint(Graphics g) {
		/**g.drawString("missiles count:"+missiles.size(), 10, 50);
		g.drawString("explodes count:"+explodes.size(), 10, 60);
		g.drawString("tanks count:"+airplanes.size(), 10,70);
		g.drawString("myTank life:"+myTank.getLife(), 10, 80);*/
		
    if(myAirplane.getLife() <= 0){
			
			g.setFont(new Font("Courier", Font.BOLD, 20));
			g.drawImage(new ImageIcon("image/gameover.jpg").getImage(), 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
			g.drawString("获得分数:"+grade,100, 300);
			g.drawString("恭喜你获得称号:小学生",100, 320);
			g.drawString("重新开始请按F2", 120, 400);
			
			return ;
			
		}
		
		Color c = g.getColor();
		g.setColor(Color.white);
		g.drawString("获得分数:"+grade, 10, 50);
		g.setColor(c);
		
		
		if(airplanes.size() == 0){
			for(int i=0; i<8; i++){
				airplanes.add(new Airplane(40*(i+1), 0, false,Airplane.Direction.D, this));
			}
		}
		
		for(int i=0; i<missiles.size(); i++){
			Missile m = missiles.get(i);
			m.hitTank(myAirplane);
			m.hitTanks(airplanes);
			//m.hitWall(w1);
			//m.hitWall(w2);
			m.draw(g);
		}
		for(int i=0; i<explodes.size(); i++){
			Explode e = explodes.get(i);
			e.draw(g);
		}
		for(int i=0; i<airplanes.size(); i++){
			Airplane t = airplanes.get(i);
			//t.collidesWithWall(w1);
			//t.collidesWithWall(w2);
			t.collidesWithTank(airplanes);
			t.draw(g);
		}
		
		//w1.draw(g);
		//w2.draw(g);
		//b.draw(g);
		//myAirplane.eat(b);
		myAirplane.draw(g);
	}
	
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		/**Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.green);
		gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScreen.setColor(c);*/
		gOffScreen.drawImage(new ImageIcon("image/xk6.jpg").getImage(), 0, 0, GAME_WIDTH, GAME_HEIGHT, null);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void lauchFrame() {
		
		new PlayAudio("music/fire.wav", true).start();
		
		for(int i=0; i<8; i++){
			airplanes.add(new Airplane(40*(i+1), 0, false,Airplane.Direction.D, this));
		}
		
		this.setLocation(400, 100);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("飞机大战");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
		//this.setBackground(Color.GRAY);
		
		this.addKeyListener(new KeyMonitor());
		
		setVisible(true);
		
		new Thread(new PaintThread()).start();
	}

	public static void main(String[] args) {
		AirplaneClient tc = new AirplaneClient();
		tc.lauchFrame();
	}
	
	private class PaintThread implements Runnable {

		public void run() {
			while(true) {
				repaint();
				try {
					Thread.sleep(70);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private class KeyMonitor extends KeyAdapter {

		public void keyReleased(KeyEvent e) {
			myAirplane.keyReleased(e);
		}

		public void keyPressed(KeyEvent e) {
			myAirplane.keyPressed(e);
		}
		
	}
}













