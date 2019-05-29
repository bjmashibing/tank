package com.mashibing.tank.strategy;

import java.io.Serializable;

import com.mashibing.tank.Tank;

public interface FireStrategy extends Serializable {
	void fire(Tank t);
}
