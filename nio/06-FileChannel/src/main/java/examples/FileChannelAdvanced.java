package examples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelAdvanced {

	public static void main(String[] args) throws IOException {
		RandomAccessFile file = new RandomAccessFile("data/nio-output.txt", "rw");
		FileChannel channel = file.getChannel();
		String newData = "New String to write to file..." + System.currentTimeMillis() + System.lineSeparator();

		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip();

		while(buf.hasRemaining()) {
			channel.write(buf);
		}
		
		//position
		buf.position(0);
		while(buf.hasRemaining()) {
			channel.write(buf);
		}
		
		//size
		long fileSize = channel.size();
		
		//truncate
		channel.truncate(fileSize >> 1);
		
		//force
		channel.force(true);
		
		channel.close();

	}

}
