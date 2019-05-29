package com.mashibing.tank.observer;

import com.mashibing.tank.Tank;

public class TankFireEvent {
	Tank tank;
	
	public Tank getSource() {
		return tank;
	}
	
	public TankFireEvent(Tank tank) {
		this.tank = tank;
	}

}
