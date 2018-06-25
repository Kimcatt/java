package examples;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @date 2018.6.25
 */
public class ScatteringRead {

	public static void main(String[] args) throws IOException {
		
		RandomAccessFile file = new RandomAccessFile("data/nio-data.txt", "rw");
		FileChannel inChannel = file.getChannel();
		
		ByteBuffer header = ByteBuffer.allocate(13);
		ByteBuffer body   = ByteBuffer.allocate(1024);

		ByteBuffer[] bufferArray = { header, body };

		inChannel.read(bufferArray);
		
		System.out.println("#header");
		print(header);
		System.out.println("#body");
		print(body);
		
		file.close();
	}
	
	private static void print(ByteBuffer buf) {
		buf.flip(); // 切换到读模式
		while (buf.hasRemaining()) {
			System.out.print((char) buf.get());
		}
	}

}
