package com.mashibing.tank.observer;

import com.mashibing.tank.Tank;

public class TankFireHandler implements TankFireObserver {

	@Override
	public void actionOnFire(TankFireEvent e) {
		Tank t = e.getSource();
		t.fire();
	}

}
