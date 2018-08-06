package com.sxy.dafeiji.util;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.sxy.dafeiji.view.AirplaneClient;

public class Explode {
	int x, y;
	private boolean live = true;
	private AirplaneClient tc;
	
	
	//int[] diameter = {4, 7, 12, 18, 26, 31, 49, 30, 14, 6};
	int step = 0;
	
	public Explode(int x,int y,AirplaneClient tc){
		this.x = x;
		this.y = y;
		this.tc = tc;
		
	}
	
	public void draw(Graphics g){
		if(!live) {
			tc.explodes.remove(this);
			return;
		}
		if(step == 15){
			live = false;
			step = 0;
			return;
			
		}
		//Color c = g.getColor();
		//g.setColor(Color.orange);
		//g.fillOval(x, y, diameter[step], diameter[step]);
		//g.setColor(c);
		g.drawImage(new ImageIcon("image/largeBurst"+step+".gif").getImage(), x, y, 30, 30, null);
		
		step++;
	}
}
