package examples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelRead {

	public static void main(String[] args) throws IOException {
		// 创建并打开FileChannel
		RandomAccessFile aFile = new RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel inChannel = aFile.getChannel();
		// 从FileChannel读取数据
		ByteBuffer buf = ByteBuffer.allocate(48);
		int bytesRead = inChannel.read(buf);
		while(bytesRead != -1) {
			System.out.println("Read " + bytesRead + " bytes from channel");
			buf.flip();
			while(buf.hasRemaining()) {
				System.out.print((char)buf.get());
			}
			buf.clear();
			bytesRead = inChannel.read(buf);
		}
		aFile.close();
	}

}
