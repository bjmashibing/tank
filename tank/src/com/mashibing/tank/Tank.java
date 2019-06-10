package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.UUID;

import com.mashibing.tank.net.Client;
import com.mashibing.tank.net.TankDirChangedMsg;
import com.mashibing.tank.net.TankJoinMsg;
import com.mashibing.tank.net.TankStartMovingMsg;

public class Tank {
	private static final int SPEED = 2;
	public static int WIDTH = ResourceMgr.goodTankU.getWidth();

	public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
	
	private UUID id = UUID.randomUUID();
	
	Rectangle rect = new Rectangle();

	private Random random = new Random();
	private int x, y;
	
	private Dir dir = Dir.DOWN;

	private boolean moving = false;

	private TankFrame tf = null;

	private boolean living = true;
	private Group group = Group.BAD;
	public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
		super();
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
	public Tank(TankJoinMsg msg) {
		this.x = msg.x;
		this.y = msg.y;
		this.dir = msg.dir;
		this.moving = msg.moving;
		this.group = msg.group;
		this.id = msg.id;
	}
	
	private void boundsCheck() {
		if (this.x < 2) x = 2;
		if (this.y < 28) y = 28;
		if (this.x > TankFrame.GAME_WIDTH- Tank.WIDTH -2) x = TankFrame.GAME_WIDTH - Tank.WIDTH -2;
		if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2 ) y = TankFrame.GAME_HEIGHT -Tank.HEIGHT -2;
	}
	
	public void die() {
		this.living = false;
	}
	public void fire() {
		int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
		int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
		
		tf.bullets.add(new Bullet(bX, bY, this.dir, this.group, this.tf));
		
		if(this.group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
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
		
		//save the oldDir for TankDirChangedMsg
		//oldDir = dir;
		
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
		
		if(this.group == Group.BAD && random.nextInt(100) > 95) 
			this.fire();
		
		if(this.group == Group.BAD && random.nextInt(100) > 95)
			randomDir();
		
		boundsCheck();
		//update rect
		rect.x = this.x;
		rect.y = this.y;
		
	}

	public void paint(Graphics g) {
		if(!living) tf.tanks.remove(this);
		//uuid on head
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.drawString(id.toString(), this.x, this.y - 10);
		g.setColor(c);
		
		switch(dir) {
		case LEFT:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
			break;
		case UP:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
			break;
		case RIGHT:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
			break;
		case DOWN:
			g.drawImage(this.group == Group.GOOD? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
			break;
		}
	
		move();
		

	
	}
	
	private void randomDir() {
		
		this.dir = Dir.values()[random.nextInt(4)];
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

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	

}
