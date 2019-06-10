package com.mashibing.tank.net;

public abstract class Msg {
	
	public abstract void handle();
	public abstract byte[] toBytes();
	public abstract void parse(byte[] bytes);
	public abstract MsgType getMsgType();
	
}
