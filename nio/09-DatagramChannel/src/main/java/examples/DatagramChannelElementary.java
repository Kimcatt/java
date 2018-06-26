package examples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @date 2018.6.26
 */
public class DatagramChannelElementary {

	public static void main(String[] args) throws IOException {

	}

	public static void launchReceiving() throws IOException {
		// 打开 DatagramChannel
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(9999));

		// 接收数据
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		channel.receive(buf);
	}

	public static void launchSending() throws IOException {

		// 打开 DatagramChannel
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(8888));
		// 发送数据

		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();
		int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 9999));
	}

	public static void launchConnection() throws IOException {
		// 打开 DatagramChannel
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(9999));
		channel.connect(new InetSocketAddress("jenkov.com", 80));

		// 读写
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = channel.read(buf);
		int bytesWritten = channel.write(buf);
	}

}
