package examples;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class DatagramReceiver {

	public static void main(String[] args) throws IOException {
		System.out.println("Start receiving...");
		new DatagramReceiver().launchReceiving();
	}
	
	public void launchReceiving() throws IOException {
		// 打开 DatagramChannel
		DatagramChannel channel = DatagramChannel.open();
		channel.socket().bind(new InetSocketAddress(9999));

		// 接收数据
		ByteBuffer buf = ByteBuffer.allocate(48);
		while (true) {
			buf.clear();
			channel.receive(buf);
			buf.flip();
			while (buf.hasRemaining()) {
				System.out.print((char)buf.get());
			}
		}
	}

}
