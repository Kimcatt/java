package examples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @date 2018.6.25
 */
public class FileChannelWrite {

	public static void main(String[] args) throws IOException {
		
		RandomAccessFile file = new RandomAccessFile("data/nio-output.txt", "rw");
		FileChannel channel = file.getChannel();
		String newData = "New String to write to file..." + System.currentTimeMillis();

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip();

		while(buf.hasRemaining()) {
			channel.write(buf);
		}
		
		channel.close();
	}

}
