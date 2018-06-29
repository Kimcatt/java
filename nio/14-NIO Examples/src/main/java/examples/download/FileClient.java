package examples.download;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @date 2018.6.29
 * 
 * @param <T>
 */
public class FileClient<T> implements Callable<T> {

	private Selector selector = null;
	private String serverFileName;
	private String localFileName;
	private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
	private FileChannel fileChannel;

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		long timeMillis = System.currentTimeMillis();
		for(int i = 0; i < 100; i++) {
			executorService.submit(new FileClient<Object>("D:/assets/cat.jpg", "D:/download/" + timeMillis + "/" + i + ".jpg"));
		}
		executorService.shutdown();
	}

	public FileClient(String serverFileName, String localFileName) {
		super();
		this.serverFileName = serverFileName;
		this.localFileName = localFileName;
	}

	public void boot() throws IOException, InterruptedException {
		if (selector == null)
			selector = Selector.open();
		SocketChannel channel = SocketChannel.open();
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress("localhost", 1234));
		SelectionKey key = channel.register(selector, SelectionKey.OP_READ);

		while (!channel.finishConnect()) {
			
		}
		byteBuffer.clear();
		byteBuffer.put(serverFileName.getBytes());
		byteBuffer.flip();
		channel.write(byteBuffer);
		
		while (true) {
			int selected = selector.select();
			if (selected > 0) {
				Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
				while(iterator.hasNext()) {
					SelectionKey selectionKey = iterator.next();
					if (selectionKey.isReadable()) {
						SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
						byteBuffer.clear();
						if (!socketChannel.isConnected())
							return;
						// 向本机下载文件创建文件channel
						Path path = Paths.get(localFileName);
						if (!Files.exists(path.getParent())) {
							Files.createDirectory(path.getParent());
						}
						if (fileChannel == null)
							fileChannel = new RandomAccessFile(localFileName, "rw").getChannel();
						int r = socketChannel.read(byteBuffer);
						// 如果文件下载完毕,则关掉channel,同时关掉socketChannel
						/*
						while (r > 0) {
							byteBuffer.flip();
							// 写到下载文件中
							fileChannel.write(byteBuffer);
							r = socketChannel.read(byteBuffer);
						}
						*/
						if (r == -1) {
							if (fileChannel != null)
								fileChannel.close();
							channel.close();
							selectionKey.cancel();
							return;
						} else {
							byteBuffer.flip();
							// 写到下载文件中
							fileChannel.write(byteBuffer);
						}
					}
					iterator.remove();
				}
			}
			Thread.sleep(100);
		}
	}



	@Override
	public T call() throws Exception {
		boot();
		return null;
	}

}
