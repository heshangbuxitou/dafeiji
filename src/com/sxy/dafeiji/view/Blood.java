package com.sxy.dafeiji.view;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Blood {
		public static final int Blood_WIDTH = 32;
		public static final int Blood_HEIGHT = 32;
		
		int x,y;
		int step = 0;
		
		private boolean live = true;
		
		public boolean isLive() {
			return live;
		}

		public void setLive(boolean live) {
			this.live = live;
		}
		private int[][] pos= {
				{100,100} , {150,120} , {200,140}, {230,170},{260,160},{280,190},{320,250},{320,220},{310,180},{280,150},{251,130},
				{200,110},{170,105},{130,80},{100,100}
		};
		AirplaneClient tc;
		
		public Blood(){
			x = pos[step][0];
			y = pos[step][1];
		}
		
		public  void draw(Graphics g){
			if(!live) return ;
			g.drawImage(new ImageIcon("image/burst0.gif").getImage(), x, y,Blood_WIDTH, Blood_HEIGHT, null);
			move();
}

		private void move() {
			step++;
			if(step == pos.length){
				step = 0;
			}
			
				x = pos[step][0];
				y = pos[step][1];
			
		}
		public Rectangle getRect(){
			return new Rectangle(x, y, Blood_WIDTH, Blood_HEIGHT);
		}
}
