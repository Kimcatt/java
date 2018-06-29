package examples.echo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class EchoServer {

	private int port = 1234;
	
	private ByteBuffer localBuffer = ByteBuffer.allocate(1024);
	
	public static void main(String[] args) {
		try {
			new EchoServer(2222).launch();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public EchoServer(int port) {
		super();
		this.port = port;
	}

	public void launch() throws IOException {
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false);
		Selector selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);
		ssc.socket().bind(new InetSocketAddress(port));
		
		System.out.println("Echo Server started...");
		
		while (true) {
			int selected = selector.select();
			if (selected > 0) {
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while (iterator.hasNext()) {
					SelectionKey selectionKey = iterator.next();
					iterator.remove();
					if (selectionKey.isAcceptable()) {
						if (!selectionKey.isValid()) {
							continue;
						}
						ServerSocketChannel serverChannel = (ServerSocketChannel)selectionKey.channel();
						SocketChannel socketChannel = (SocketChannel) serverChannel.accept().configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						System.out.println("Got  connection from  " + socketChannel.getRemoteAddress());
					}
					
					if (selectionKey.isWritable()) {
						if (selectionKey.isValid()) {
							SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
							socketChannel.register(selector, SelectionKey.OP_READ);
							localBuffer.flip();
							if (localBuffer.hasRemaining()) {
								socketChannel.write(localBuffer);
							}
							localBuffer.clear();
						}
					}
					
					if (selectionKey.isReadable()) {
						if (!selectionKey.isValid()) {
							continue;
						}
						SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
						socketChannel.register(selector, SelectionKey.OP_WRITE);
						localBuffer.clear();
						int bytesRead = -1;
						try {
							bytesRead = socketChannel.read(localBuffer);
						} catch (Exception e) {
							System.out.println("Lost connection from  " + socketChannel.getRemoteAddress());
							socketChannel.close();
							selectionKey.cancel();
						}
						if (bytesRead == -1) {
							if (!socketChannel.socket().isClosed()) {
								System.out.println("Lost connection from  " + socketChannel.getRemoteAddress());
								socketChannel.close();
								selectionKey.cancel();
							}
						}
					}
				}
			}
		}
	}

}
