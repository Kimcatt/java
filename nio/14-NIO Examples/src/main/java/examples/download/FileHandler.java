package examples.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class FileHandler implements Handler {

	private StringBuilder message;
	private boolean writeOK = true;
	private ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
	private FileChannel fileChannel;
	private String fileName;

	private Selector selector = null;

	public FileHandler(Selector selector) {
		super();
		this.selector = selector;
	}

	@Override
	public void handle(SelectionKey key) throws IOException {
		if (key.isReadable()) {
			SocketChannel socketChannel = (SocketChannel) key.channel();
			if (writeOK)
				message = new StringBuilder();
			while (true) {
				byteBuffer.clear();
				int r = socketChannel.read(byteBuffer);
				if (r == 0)
					break;
				if (r == -1) {
					socketChannel.close();
					key.cancel();
					return;
				}
				message.append(new String(byteBuffer.array(), 0, r));
			}
			// 将接收到的信息转化成文件名,以映射到服务器上的指定文件
			if (writeOK && validateFile(message)) {
				socketChannel.register(selector, SelectionKey.OP_WRITE, this);
				writeOK = false;
			}
		}

		// 向客户端写数据
		if (key.isWritable()) {
			if (!key.isValid())
				return;
			SocketChannel socketChannel = (SocketChannel) key.channel();
			if (fileChannel == null)
				fileChannel = new FileInputStream(fileName).getChannel();
			byteBuffer.clear();
			int w = fileChannel.read(byteBuffer);
			// 如果文件已写完,则关掉key和socket
			if (w == -1) {
				fileName = null;
				fileChannel.close();
				fileChannel = null;
				writeOK = true;
				System.out.println(String.format("%-5s ", "Close") +  "connection " + String.format("%-5s", "to") + socketChannel.getRemoteAddress());
				socketChannel.close();
				key.cancel();
				return;
			}
			byteBuffer.flip();
			socketChannel.write(byteBuffer);
		}
	}

	private boolean validateFile(StringBuilder message) {
		String msg = message.toString();
		try {
			File f = new File(msg);
			if (!f.exists())
				return false;
			fileName = msg;
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
