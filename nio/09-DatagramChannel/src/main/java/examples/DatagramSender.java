package examples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramSender {

	public static void main(String[] args) throws IOException {
		System.out.println("Start sending...");
		new DatagramSender().launchSending();
	}

	public void launchSending() throws IOException {

		// 打开 DatagramChannel
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(8888));
		// 发送数据

		String newData = "New String to write to file..." + System.currentTimeMillis() + System.lineSeparator();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());
		buf.flip();
		int bytesSent = channel.send(buf, new InetSocketAddress("localhost", 9999));
		System.out.println("Send " + bytesSent + " bytes");
	}
}
