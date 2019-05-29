package com.mashibing.tank.cor;

import com.mashibing.tank.GameObject;

public interface Collider {
	boolean collide(GameObject o1, GameObject o2);
}
