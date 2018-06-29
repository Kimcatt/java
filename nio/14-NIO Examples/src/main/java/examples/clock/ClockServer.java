package examples.clock;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * @author panteng
 * @description 时钟服务器
 * @date 17-3-10.
 */
public class ClockServer {

	public static void main(String[] arges) throws Exception {
		char name = 'A';
		// 创建通道,并设置非阻塞
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		// 创建选择器，并为通道绑定感兴趣的事件
		Selector selector = Selector.open();
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT).attach("主监听通道");
		System.out.println("Clock Server start...");
		// 通道绑定端口号
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 4700);
		serverSocketChannel.socket().bind(inetSocketAddress);
		// 开始轮询通道事件
		while (true) {
			// 可以是阻塞，非阻塞，也可以设置超时
			int readyChannels = selector.selectNow();
			if (readyChannels > 0) {
				Set<SelectionKey> readyKeys = selector.selectedKeys();
				Iterator iterator = readyKeys.iterator();
				while (iterator.hasNext()) {
					SelectionKey readyKey = (SelectionKey) iterator.next();
					iterator.remove();
					if (readyKey.isAcceptable()) {
						if (!readyKey.isValid()) {
							continue;
						}
						ServerSocketChannel readyChannel = (ServerSocketChannel) readyKey.channel();
						// System.out.println("Acceptor socket " + readyChannel.hashCode());
						SocketChannel socketChannel = (SocketChannel) readyChannel.accept().configureBlocking(false);
						socketChannel.register(selector, (SelectionKey.OP_READ | SelectionKey.OP_WRITE)).attach(name);
						System.out.println("User " + name + " from " + socketChannel.getRemoteAddress() + " connected");
						name++;
					}

					if (readyKey.isWritable()) {
						SocketChannel readyChannel = (SocketChannel) readyKey.channel();
						if (readyKey.isValid()) {
							ByteBuffer buffer = ByteBuffer.allocate(512);
							buffer.put((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\r\n")
									.getBytes());
							buffer.flip();
							readyChannel.write(buffer);
						}

					}

					if (readyKey.isReadable()) {
						if (!readyKey.isValid()) {
							continue;
						}
						SocketChannel readyChannel = (SocketChannel) readyKey.channel();
						ByteBuffer buffer = ByteBuffer.allocate(512);
						
						int bytesRead = -1;
						try {
							bytesRead = readyChannel.read(buffer);
						} catch (Exception e) {
							System.out.println("User " + readyKey.attachment() + " from " + readyChannel.getRemoteAddress() + " disconnected");
							readyChannel.close();
							readyKey.cancel();
						}
						if (bytesRead == -1 && !readyChannel.socket().isClosed()) {
							System.out.println("User " + readyKey.attachment() + " from " + readyChannel.getRemoteAddress() + " disconnected");
							readyChannel.close();
							readyKey.cancel();
						} else if (bytesRead > 0) {
							System.out.println(readyKey.attachment() + " " + getString(buffer));
						}
						
					}
				}
			}
			Thread.sleep(1000);
		}
	}

	/**
	 * ByteBuffer 转换 String
	 * 
	 * @param buffer
	 * @return
	 */
	public static String getString(ByteBuffer buffer) {
		String string = "";
		try {
			for (int i = 0; i < buffer.position(); i++) {
				string += (char) buffer.get(i);
			}
			return string;
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

}
