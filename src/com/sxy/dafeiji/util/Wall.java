package com.sxy.dafeiji.util;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.sxy.dafeiji.view.AirplaneClient;

public class Wall {
	public int x, y, w, h;
	private AirplaneClient tc;
	public Wall(int x,int y,int w, int h,AirplaneClient tc){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tc = tc;
	}
	public void draw(Graphics g){
		Color c = g.getColor();
		g.setColor(Color.gray);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}
	public Rectangle getRect(){
		return new Rectangle(x, y, w, h);
	}
}
