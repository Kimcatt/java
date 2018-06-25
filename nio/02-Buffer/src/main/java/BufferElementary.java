import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @date 2018.6.25
 */
public class BufferElementary {

	static int LF = 10; //换行符  
    static int CR = 13; //回车符  
	
	public static void main(String[] args) throws IOException {
		RandomAccessFile aFile = new RandomAccessFile("data/nio-data-cn.txt", "rw");
		FileChannel inChannel = aFile.getChannel();

		// 创建Buffer
		ByteBuffer buf = ByteBuffer.allocate(48);

		int bytesRead = inChannel.read(buf); // 读取到Buffer.
		if (bytesRead != -1) {
			buf.flip(); // 切换到读模式
			while (buf.hasRemaining()) {
				byte curByte = buf.get();
				System.out.print((char)curByte); 
			}
			buf.clear(); // 清空Buffer
		}
		aFile.close();

	}

}
