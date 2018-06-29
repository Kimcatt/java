package examples.bio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class BIOServer {

	private int port = 5566;

	public static void main(String[] args) {
		try {
			new BIOServer().launch();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void launch() throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		//监听端口${port}进来的TCP连接
		ssc.socket().bind(new InetSocketAddress(port));
		
		System.out.println(String.format("BIO Server start at port %s...", port));
		while(true) {
			//阻塞，直到有一个请求进来
			SocketChannel sc = ssc.accept();
			
			// 开启一个新的线程来处理这个请求，然后在 while 循环中继续监听 ${port}端口
            SocketHandler handler = new SocketHandler(sc);
            new Thread(handler).start();
			
		}
	}

}
