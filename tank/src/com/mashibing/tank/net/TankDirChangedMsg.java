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



public class TankDirChangedMsg extends Msg {
	
	UUID id;
	Dir dir;

	int x, y;

	public TankDirChangedMsg() {
		
	}

	public TankDirChangedMsg(Tank t) {
		this.id = t.getId();
		this.dir = t.getDir();
		this.x = t.getX();
		this.y = t.getY();
	}

	public TankDirChangedMsg(UUID id, int x, int y , Dir dir) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Dir getDir() {
		return dir;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.TankDirChanged;
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
			t.setDir(this.dir);
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
