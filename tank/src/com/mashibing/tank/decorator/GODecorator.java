package com.mashibing.tank.decorator;

import java.awt.Graphics;

import com.mashibing.tank.GameObject;

public abstract class GODecorator extends GameObject {
	
	GameObject go;
	
	public GODecorator(GameObject go) {
		
		this.go = go;
	}

	@Override
	public abstract void paint(Graphics g);

}
