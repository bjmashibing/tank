package com.mashibing.tank;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.UUID;

import com.mashibing.tank.net.Client;
import com.mashibing.tank.net.TankDieMsg;

public class Bullet {
	private static final int SPEED = 6;
	
	public static int WIDTH = ResourceMgr.bulletD.getWidth();

	public static int HEIGHT = ResourceMgr.bulletD.getHeight();

	private UUID id = UUID.randomUUID();
	private UUID playerId;

	Rectangle rect = new Rectangle();

	private int x, y;

	private Dir dir;

	private boolean living = true;

	TankFrame tf = null;

	private Group group = Group.BAD;

	public Bullet(UUID playerId, int x, int y, Dir dir, Group group, TankFrame tf) {
		this.playerId = playerId;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.group = group;
		this.tf = tf;
		
		rect.x = this.x;
		rect.y = this.y;
		rect.width = WIDTH;
		rect.height = HEIGHT;
				
	}

	public void collideWith(Tank tank) {
		if(this.playerId.equals(tank.getId())) return;
		//System.out.println("bullet rect:" + this.rect);
		//System.out.println("tank rect:" + tank.rect);
		if(this.living && tank.isLiving() && this.rect.intersects(tank.rect)) {
			tank.die();
			this.die();
			Client.INSTANCE.send(new TankDieMsg(this.id, tank.getId()));
		}
		
	}

	public void die() {
		this.living = false;
	}

	public Dir getDir() {
		return dir;
	}
	public Group getGroup() {
		return group;
	}
	public UUID getId() {
		return id;
	}
	
	public UUID getPlayerId() {
		return playerId;
	}
	
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	
	public boolean isLiving() {
		return living;
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
		
		//update rect
		rect.x = this.x;
		rect.y = this.y;
		
		if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
		
	}
	public void paint(Graphics g) {
		if(!living) {
			tf.bullets.remove(this);
		}
		
		switch(dir) {
		case LEFT:
			g.drawImage(ResourceMgr.bulletL, x, y, null);
			break;
		case UP:
			g.drawImage(ResourceMgr.bulletU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(ResourceMgr.bulletR, x, y, null);
			break;
		case DOWN:
			g.drawImage(ResourceMgr.bulletD, x, y, null);
			break;
		}
		
		move();
	}
	
	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	public void setGroup(Group group) {
		this.group = group;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public void setLiving(boolean living) {
		this.living = living;
	}
	
	public void setPlayerId(UUID playerId) {
		this.playerId = playerId;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
