package examples.download;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class FileServer {

	private int port = 8888;
	
	public static AtomicInteger COUNTER = new AtomicInteger(0);
	
	public static void main(String[] args) throws IOException, InterruptedException {
		try {
			new FileServer(1234).launch();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Server shutdown.");
	}
	
	

	public FileServer(int port) {
		super();
		this.port = port;
	}

	public void launch() throws IOException, InterruptedException {
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("File Server started...");
		
		while (true) {
			int selected = selector.select();
			if (selected > 0) {
				Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
				while (keyIterator.hasNext()) {
					SelectionKey key = keyIterator.next();
					if (key.isValid()) {
						process(key, selector);
					}
					keyIterator.remove();
				}
			}

			Thread.sleep(100);
		}
	}

	private void process(SelectionKey key, Selector selector) throws IOException {
		if (key.isAcceptable()) {
			ServerSocketChannel channel = (ServerSocketChannel) key.channel();
			SocketChannel socketChannel = channel.accept();
			System.out.println(String.format("%-5s ", "Got") +  "connection " + String.format("%-5s", "from") + socketChannel.getRemoteAddress() + " #" + COUNTER.incrementAndGet());
			
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ, new FileHandler(selector));
		}
	
		if(key.isReadable() || key.isWritable()) {
			final Handler handle = (Handler)key.attachment();
			if(handle != null) {
				handle.handle(key);
			}
		}

	}

}
