package com.mashibing.tank.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
	public static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	
	public void serverStart() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(1);
		EventLoopGroup workerGroup = new NioEventLoopGroup(2);
		
		try {
			ServerBootstrap b = new ServerBootstrap();
			ChannelFuture f = b.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ChannelPipeline pl = ch.pipeline();
						pl.addLast(new MsgEncoder());
						pl.addLast(new MsgDecoder());
						pl.addLast(new ServerChildHandler());
					}
				})
				.bind(8888)
				.sync();
			
			ServerFrame.INSTANCE.updateServerMsg("server started!");
			
			f.channel().closeFuture().sync(); //close()->ChannelFuture
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	

}

class ServerChildHandler extends ChannelInboundHandlerAdapter { //SimpleChannleInboundHandler Codec
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Server.clients.add(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ServerFrame.INSTANCE.updateClientMsg(msg.toString());
		Server.clients.writeAndFlush(msg);
		
		/*ByteBuf buf = null;
		try {
			buf = (ByteBuf)msg;
			
			byte[] bytes = new byte[buf.readableBytes()];
			buf.getBytes(buf.readerIndex(), bytes);
			String s = new String(bytes);
			
			if(s.equals("_bye_")) {
				System.out.println("�ͻ���Ҫ���˳�");
				Server.clients.remove(ctx.channel());
				ctx.close();
			} else {
				Server.clients.writeAndFlush(msg);
			}*/
			
			//System.out.println(buf);
			//System.out.println(buf.refCnt());
		/*} finally {
			//if(buf != null && buf) ReferenceCountUtil.release(buf);
			//System.out.println(buf.refCnt());
		}*/
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		//ɾ�������쳣�Ŀͻ���channle�����ر�����
		Server.clients.remove(ctx.channel());
		ctx.close();
	}
	
	
}





