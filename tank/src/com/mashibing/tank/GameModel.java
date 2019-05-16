package com.mashibing.tank;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.mashibing.tank.cor.ColliderChain;

public class GameModel {

	private static final GameModel INSTANCE = new GameModel();
	
	static {
		INSTANCE.init();
	}

	Tank myTank;

	// List<Bullet> bullets = new ArrayList<>();
	// List<Tank> tanks = new ArrayList<>();
	// List<Explode> explodes = new ArrayList<>();
	ColliderChain chain = new ColliderChain();

	private List<GameObject> objects = new ArrayList<>();

	public static GameModel getInstance() {
		return INSTANCE;
	}

	private GameModel() {}

	private void init() {
		// 初始化主战坦克
		myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD);

		int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

		// 初始化敌方坦克
		for (int i = 0; i < initTankCount; i++) {
			new Tank(50 + i * 80, 200, Dir.DOWN, Group.BAD);
		}

		// 初始化墙
		add(new Wall(150, 150, 200, 50));
		add(new Wall(550, 150, 200, 50));
		add(new Wall(300, 300, 50, 200));
		add(new Wall(550, 300, 50, 200));
	}

	public void add(GameObject go) {
		this.objects.add(go);
	}

	public void remove(GameObject go) {
		this.objects.remove(go);
	}

	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.WHITE);
		// g.drawString("子弹的数量:" + bullets.size(), 10, 60);
		// g.drawString("敌人的数量:" + tanks.size(), 10, 80);
		// g.drawString("爆炸的数量:" + explodes.size(), 10, 100);
		g.setColor(c);

		myTank.paint(g);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).paint(g);
		}

		// 互相碰撞
		for (int i = 0; i < objects.size(); i++) {
			for (int j = i + 1; j < objects.size(); j++) { // Comparator.compare(o1,o2)
				GameObject o1 = objects.get(i);
				GameObject o2 = objects.get(j);
				// for
				chain.collide(o1, o2);
			}
		}

		// for (int i = 0; i < bullets.size(); i++) {
		// for (int j = 0; j < tanks.size(); j++)
		// bullets.get(i).collideWith(tanks.get(j));
		// }

	}

	public Tank getMainTank() {
		return myTank;
	}

}
