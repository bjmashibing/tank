package com.mashibing.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

import com.mashibing.tank.Dir;
import com.mashibing.tank.Tank;
import com.mashibing.tank.TankFrame;



public class TankStartMovingMsg extends Msg {

	
	UUID id;
	int x, y;

	Dir dir;

	public TankStartMovingMsg() {
		
	}

	public TankStartMovingMsg(UUID id, int x, int y, Dir dir) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public TankStartMovingMsg(Tank tank) {
		this.id = tank.getId();
		this.x = tank.getX();
		this.y = tank.getY();
		this.dir = tank.getDir();
	}

	public Dir getDir() {
		return dir;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.TankStartMoving;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	@Override
	public void handle() {
		if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
			return;

		Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);

		if (t != null) {
			t.setMoving(true);
			t.setX(this.x);
			t.setY(this.y);
			t.setDir(dir);
		}
	}
	
	@Override
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			
			this.id = new UUID(dis.readLong(), dis.readLong());
			this.x = dis.readInt();
			this.y = dis.readInt();
			this.dir = Dir.values()[dis.readInt()];
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void setDir(Dir dir) {
		this.dir = dir;
	}
	
	public void setId(UUID id) {
		this.id = id;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null; 
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.flush();
			bytes = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if(dos != null) {
					dos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return bytes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName())
			   .append("[")
			   .append("uuid=" + id + " | ")
			   .append("x=" + x + " | ")
			   .append("y=" + y + " | ")
			   .append("dir=" + dir + " | ")
			   .append("]");
		return builder.toString();
	}

}
