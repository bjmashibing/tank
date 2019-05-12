package com.mashibing.tank.abstractfactory;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import com.mashibing.tank.Audio;
import com.mashibing.tank.Bullet;
import com.mashibing.tank.DefaultFireStrategy;
import com.mashibing.tank.Dir;
import com.mashibing.tank.FireStrategy;
import com.mashibing.tank.Group;
import com.mashibing.tank.PropertyMgr;
import com.mashibing.tank.ResourceMgr;
import com.mashibing.tank.TankFrame;

public class RectTank extends BaseTank {

	private static final int SPEED = 2;
	public static int WIDTH = ResourceMgr.goodTankU.getWidth();

	public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

	public Rectangle rect = new Rectangle();

	private Random random = new Random();

	int x, y;

	Dir dir = Dir.DOWN;

	private boolean moving = true;
	TankFrame tf = null;
	private boolean living = true;
	Group group = Group.BAD;

	FireStrategy fs;

	public RectTank(int x, int y, Dir dir, Group group, TankFrame tf) {
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

		if (group == Group.GOOD) {
			String goodFSName = (String) PropertyMgr.get("goodFS");

			try {
				fs = (FireStrategy) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			fs = new DefaultFireStrategy();
		}
	}

	public void fire() {
		// fs.fire(this);

		int bX = this.x + RectTank.WIDTH / 2 - Bullet.WIDTH / 2;
		int bY = this.y + RectTank.HEIGHT / 2 - Bullet.HEIGHT / 2;

		Dir[] dirs = Dir.values();
		for (Dir dir : dirs) {
			tf.gf.createBullet(bX, bY, dir, group, tf);
		}

		if (group == Group.GOOD)
			new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
	}

	public Dir getDir() {
		return dir;
	}

	public int getX() {
		return x;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public int getY() {
		return y;
	}

	public boolean isMoving() {
		return moving;
	}

	private void move() {

		if (!moving)
			return;

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

		if (this.group == Group.BAD && random.nextInt(100) > 95)
			this.fire();

		if (this.group == Group.BAD && random.nextInt(100) > 95)
			randomDir();

		boundsCheck();
		// update rect
		rect.x = this.x;
		rect.y = this.y;

	}

	private void boundsCheck() {
		if (this.x < 2)
			x = 2;
		if (this.y < 28)
			y = 28;
		if (this.x > TankFrame.GAME_WIDTH - RectTank.WIDTH - 2)
			x = TankFrame.GAME_WIDTH - RectTank.WIDTH - 2;
		if (this.y > TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 2)
			y = TankFrame.GAME_HEIGHT - RectTank.HEIGHT - 2;
	}

	private void randomDir() {

		this.dir = Dir.values()[random.nextInt(4)];
	}

	public void paint(Graphics g) {
		if (!living)
			tf.tanks.remove(this);

		Color c = g.getColor();
		g.setColor(group == Group.GOOD ? Color.RED : Color.BLUE);
		g.fillRect(x, y, 40, 40);
		g.setColor(c);
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
