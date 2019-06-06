package com.mashibing.tank;

import com.mashibing.tank.net.Client;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		TankFrame tf = TankFrame.INSTANCE;
		tf.setVisible(true);
		
		//connect to the server
		
		
/*		int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
		
		for(int i=0; i<initTankCount; i++) {
			tf.tanks.add(new Tank(50 + i*80, 200, Dir.DOWN, Group.BAD, tf));
		}*/
		//music
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
		
		new Thread(()-> {
			while(true) {
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tf.repaint();
			}
		}).start();
		
		//or you can new a thread to run this
		
		Client.INSTANCE.connect();
		
	}

}
