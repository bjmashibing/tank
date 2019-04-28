package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;

public class Bullet {
	private static final int SPEED = 1;
	private static int WIDTH = 30, HEIGHT = 30;
	
	private int x, y;
	private Dir dir;
	
	public Bullet(int x, int y, Dir dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		
		move();
	}
	
	private void move() {
		
		switch (dir) {
		case LEFT:
			x -= SPEED;
			break;
		case UP:
			y -= SPEED;
			break;
		case RIGHT:
			x += SPEED;
			break;
		case DOWN:
			y += SPEED;
			break;
		}
	}
}
