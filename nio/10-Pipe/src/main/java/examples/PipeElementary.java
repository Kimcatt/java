package examples;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @date 2018.6.26
 */
public class PipeElementary {

	public static void main(String[] args) throws IOException {
		//创建管道
		Pipe pipe = Pipe.open();
		
		//向管道写数据
		Pipe.SinkChannel sinkChannel = pipe.sink();

		String newData = "New String to write to file..." + System.currentTimeMillis();
		ByteBuffer buf = ByteBuffer.allocate(48);
		buf.clear();
		buf.put(newData.getBytes());

		buf.flip();

		while(buf.hasRemaining()) {
		    sinkChannel.write(buf);
		}
		
		//从管道读取数据
		Pipe.SourceChannel sourceChannel = pipe.source();

		buf = ByteBuffer.allocate(48);
		int bytesRead = sourceChannel.read(buf);

	}

}
