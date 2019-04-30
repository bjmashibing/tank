package com.mashibing.tank;

import java.awt.Graphics;

public class Tank {
	private static final int SPEED = 5;
	public static int WIDTH = ResourceMgr.tankD.getWidth();

	public static int HEIGHT = ResourceMgr.tankD.getHeight();

	private int x, y;

	private Dir dir = Dir.DOWN;

	private boolean moving = false;
	private TankFrame tf = null;
	private boolean living = true;
	
	public Tank(int x, int y, Dir dir, TankFrame tf) {
		super();
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.tf = tf;
	}
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		tf.bullets.add(new Bullet(bX, bY, this.dir, this.tf));
	}
	
	public Dir getDir() {
		return dir;
	}
	
	public int getX() {
		return x;
	}
	
	
	public int getY() {
		return y;
	}

	public boolean isMoving() {
		return moving;
	}

	private void move() {
		
		if(!moving) return ;
		
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

	public void paint(Graphics g) {
		if(!living) tf.tanks.remove(this);
		
		switch(dir) {
		case LEFT:
			g.drawImage(ResourceMgr.tankL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.tankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.tankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.tankD, x, y, null);
			break;
		}
	
		move();
	
	}


	public void setDir(Dir dir) {
		this.dir = dir;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
	public void die() {
		this.living = false;
	}
	
	

}
