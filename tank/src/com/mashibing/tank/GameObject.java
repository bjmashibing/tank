package com.mashibing.tank;

import java.awt.Graphics;

public abstract class GameObject {
	public int x, y;
	
	public abstract void paint(Graphics g);
	public abstract int getWidth();
	public abstract int getHeight();
}
