package com.mashibing.tank.net;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class TankStopMsg extends Msg {

public static final MsgType TYPE = MsgType.TankStop;
	
	UUID id;
	int x, y;
	public TankStopMsg(UUID id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}
	
	public TankStopMsg() {
		
	}
	
	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = null;
		DataOutputStream dos = null; 
		byte[] bytes = null;
		try {
			baos = new ByteArrayOutputStream();
			dos = new DataOutputStream(baos);
			dos.writeInt(TYPE.ordinal());
			dos.writeLong(id.getMostSignificantBits());
			dos.writeLong(id.getLeastSignificantBits());
			dos.writeInt(x);
			dos.writeInt(y);
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
	public void parse(byte[] bytes) {
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
		try {
			//TODO:先读TYPE信息，根据TYPE信息处理不同的消息
			//略过消息类型
			dis.readInt();
			
			this.id = new UUID(dis.readLong(), dis.readLong());
			this.x = dis.readInt();
			this.y = dis.readInt();
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
	
	@Override
	public void handle() {
	/*	if (this.id.equals(GameModel.getPlayer().getId()))
			return;

		GameObject object = GameModel.findByUUID(this.id);

		if (object != null && object instanceof Player) {
			Player p = (Player) object;
			p.stop();
			p.x = this.x;
			p.y = this.y;
		}*/
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(this.getClass().getName())
			   .append("[")
			   .append("uuid=" + id + " | ")
			   .append("x=" + x + " | ")
			   .append("y=" + y + " | ")
			   .append("]");
		return builder.toString();
	}

	@Override
	public MsgType getMsgType() {
		return MsgType.TankStop;
	}

	


}
