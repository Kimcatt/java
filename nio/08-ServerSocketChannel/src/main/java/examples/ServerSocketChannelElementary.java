package examples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @date 2018.6.26
 */
public class ServerSocketChannelElementary {

	public static void main(String[] args) throws IOException {
		
	}
	
	public static void launchNonBlockingServer() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(9999));
		serverSocketChannel.configureBlocking(false);

		while(true){
		    SocketChannel socketChannel =
		            serverSocketChannel.accept();

		    if(socketChannel != null){
		        //do something with socketChannel...
		    }
		}
	}
	
	public static void launchBlockingServer() throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

		serverSocketChannel.socket().bind(new InetSocketAddress(9999));

		while(true){
		    SocketChannel socketChannel =
		            serverSocketChannel.accept();

		    //do something with socketChannel...
		}
	}

}
