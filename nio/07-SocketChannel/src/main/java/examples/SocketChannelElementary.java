package examples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @date 2018.6.26
 */
public class SocketChannelElementary {

	/**
	 * 示例代码片段，无完整示例
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		
		
		//打开 SocketChannel
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));
		//关闭 SocketChannel
		socketChannel.close();
		//从 SocketChannel 读取数据
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = socketChannel.read(buf);
		//写入 SocketChannel
		String newData = "New String to write to file..." + System.currentTimeMillis();

		/*ByteBuffer buf = ByteBuffer.allocate(48);*/
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip();

		while(buf.hasRemaining()) {
			socketChannel.write(buf);
		}
		
		//配置非阻塞模式
		socketChannel.configureBlocking(false);
		
		
		//connect
		socketChannel.configureBlocking(false);
		socketChannel.connect(new InetSocketAddress("http://jenkov.com", 80));

		while(! socketChannel.finishConnect() ){
		    //wait, or do something else...
		}
		
		//write
		
		//read
	}

}
