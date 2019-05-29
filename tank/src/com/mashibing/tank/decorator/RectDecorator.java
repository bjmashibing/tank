package com.mashibing.tank.decorator;

import java.awt.Color;
import java.awt.Graphics;

import com.mashibing.tank.GameObject;

public class RectDecorator extends GODecorator {

	public RectDecorator(GameObject go) {
		super(go);
	}

	@Override
	public void paint(Graphics g) {
		this.x = go.x;
		this.y = go.y;
		
		go.paint(g);
		
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		g.drawRect(super.go.x, super.go.y, super.go.getWidth()+2, super.go.getHeight()+2);
		g.setColor(c);
	}
	
	
	@Override
	public int getWidth() {
		return super.go.getWidth();
	}

	@Override
	public int getHeight() {
		return super.go.getHeight();
	}

}
