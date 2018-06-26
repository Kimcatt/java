package examples;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

/**
 * @date 2018.6.26
 */
public class AsynchronousFileChannelElementary {

	public static void main(String[] args) throws IOException {

		// 1. Reading Data
		// 1.1 Reading Data Via a Future
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(Paths.get("data/test.xml"),
				StandardOpenOption.READ);

		ByteBuffer buffer = ByteBuffer.allocate(1024);
		long position = 0;

		Future<Integer> operation = fileChannel.read(buffer, position);

		while (!operation.isDone()) {

		}

		buffer.flip();
		byte[] data = new byte[buffer.limit()];
		buffer.get(data);
		System.out.println(new String(data));

		buffer.clear();

		// 1.2 Reading Data Via a CompletionHandler
		fileChannel.read(buffer, position, buffer, new CompletionHandler<Integer, ByteBuffer>() {
			@Override
			public void completed(Integer result, ByteBuffer buf) {
				System.out.println("result = " + result);

				buf.flip();
				byte[] data = new byte[buf.limit()];
				buf.get(data);
				System.out.println(new String(data));
				buf.clear();
				
				try {
					fileChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				try {
					fileChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		
		// 2. Writing Data
		// 2.1 Writing Data Via a Future
		Path path = Paths.get("data/test-write.txt");
		if (!Files.exists(path)) {
			Files.createFile(path);
		}
		AsynchronousFileChannel writeChannel = AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);

		ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
		long writePos = 0;

		writeBuffer.put(new String("test data " + System.currentTimeMillis()).getBytes());
		writeBuffer.flip();

		Future<Integer> writeOperation = writeChannel.write(writeBuffer, writePos);
		buffer.clear();

		while (!writeOperation.isDone())
			;
		writeChannel.close();
		
		System.out.println("Write done");

		// 2.2 Writing Data Via a CompletionHandler
		Path writePath = Paths.get("data/test-write-callback.txt");
		if (!Files.exists(writePath)) {
			Files.createFile(writePath);
		}

		ByteBuffer writeBuf = ByteBuffer.allocate(1024);
		long pos = 0;

		writeBuf.put(new String("test data - " + System.currentTimeMillis()).getBytes());
		writeBuf.flip();
		AsynchronousFileChannel outChannel = AsynchronousFileChannel.open(writePath, StandardOpenOption.WRITE);
		outChannel.write(writeBuf, pos, writeBuf, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer result, ByteBuffer attachment) {
				System.out.println("bytes written: " + result);
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment) {
				System.out.println("Write failed");
				try {
					outChannel.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				exc.printStackTrace();
			}
		});

	}

}
