package examples;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @date 2018.6.25
 */
public class GatheringWrite {

	public static void main(String[] args) throws IOException {
		
		RandomAccessFile file = new  RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel wChannel = file.getChannel();
		
		ByteBuffer header = ByteBuffer.allocate(128);
		ByteBuffer body   = ByteBuffer.allocate(1024);
		
		header.put("Hello Java\n".getBytes());
		body.put("learn nio".getBytes());
		header.flip(); // 注意切换到读模式
		body.flip(); // 注意切换到读模式
		
		//写入Channel
		ByteBuffer[] bufferArray = { header, body };

		wChannel.write(bufferArray);
		wChannel.close();
	
	}

}
