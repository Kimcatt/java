package examples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @date 2018.6.25
 */
public class ChannelToChannelTransfer {

	public static void main(String[] args) throws IOException {
		RandomAccessFile fromFile = new RandomAccessFile("data/fromFile.txt", "rw");
		FileChannel fromChannel = fromFile.getChannel();

		RandomAccessFile toFile = new RandomAccessFile("data/toFile.txt", "rw");
		FileChannel toChannel = toFile.getChannel();

		long position = 0;
		long count = fromChannel.size();

		//toChannel.transferFrom(fromChannel, position, count);
			
		fromChannel.transferTo(position, count, toChannel);

	}

}
