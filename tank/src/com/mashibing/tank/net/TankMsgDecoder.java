package com.mashibing.tank.net;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class TankMsgDecoder extends ByteToMessageDecoder{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<8) return; //TCP 拆包 粘包的问题
		
		//in.markReaderIndex();
		
		int x = in.readInt();
		int y = in.readInt();
		
		out.add(new TankMsg(x, y));
	}

}
