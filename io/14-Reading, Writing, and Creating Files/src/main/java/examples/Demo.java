package examples;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @date 2018.7.2
 */
public class Demo {

	public static void main(String[] args) {
		demo();
	}

	private static void demo() {
		// 1. Commonly Used Methods for Small Files
		// 1.1 Reading All Bytes or Lines from a File
		Path file = Paths.get("data/lang.txt");
		byte[] fileArray;
		try {
			fileArray = Files.readAllBytes(file);
			System.out.format("Content of file \"%s\": %n%s%n", file, new String(fileArray, Charset.forName("UTF-8")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 1.2 Writing All Bytes or Lines to a File
		try {
			file = Paths.get("data/lang.txt");
			byte[] buf = Arrays.toString(new String[] { "c", "c++", "c#", "java", "javascript", "python" })
					.replaceAll(",", System.lineSeparator()).replaceAll("\\[|\\]", "").replaceAll(" ", "").getBytes();
			Files.write(file, buf);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 2. Buffered I/O Methods for Text Files
		// 2.1 Reading a File by Using Buffered Stream I/O
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
			System.out.println("Content of file \"" + file + "\":");
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line.toUpperCase(Locale.CHINESE));
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		// 2.2 Writing a File by Using Buffered Stream I/O
		charset = Charset.forName("US-ASCII");
		String s = Arrays.toString(new String[] { "c", "c++", "c#", "java", "javascript", "typescript", "python" })
				.replaceAll(",", System.lineSeparator()).replaceAll("\\[|\\]", "").replaceAll(" ", "");
		try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
			writer.write(s, 0, s.length());
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

		// 3. Methods for Unbuffered Streams and Interoperable with java.io APIs
		// 3.1 Reading a File by Using Stream I/O
		file = Paths.get("data/testdata.txt");
		try (InputStream in = Files.newInputStream(file);
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			System.out.format("Content of file \"%s:\"%n", file);
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException x) {
			System.err.println(x);
		}

		// 3.2 Creating and Writing a File by Using Stream I/O
		// Convert the string to a
		// byte array.
		byte data[] = "Hello World! ".getBytes();
		Path p = Paths.get("./data/logfile.txt");

		try (OutputStream out = new BufferedOutputStream(
				Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
			out.write(data, 0, data.length);
		} catch (IOException x) {
			System.err.println(x);
		}

		// 4. Methods for Channels and ByteBuffers
		// 4.1 Reading and Writing Files by Using Channel I/O
		try (SeekableByteChannel sbc = Files.newByteChannel(Paths.get("data/lang.txt"))) {
			System.out.format("Reading data from file \"data/lang.txt:\"%n");
			ByteBuffer buf = ByteBuffer.allocate(10);

			// Read the bytes with the proper encoding for this platform. If
			// you skip this step, you might see something that looks like
			// Chinese characters when you expect Latin-style characters.
			String encoding = System.getProperty("file.encoding");
			while (sbc.read(buf) > 0) {
				buf.flip();
				System.out.print(Charset.forName(encoding).decode(buf));
				buf.clear();
			}
		} catch (IOException x) {
			System.out.println("caught exception: " + x);
		}

		/*
		 * // Written for UNIX and other POSIX file systems // Create the set of options
		 * for appending to the file. Set<OpenOption> options = new
		 * HashSet<OpenOption>(); options.add(StandardOpenOption.APPEND);
		 * options.add(StandardOpenOption.CREATE);
		 * 
		 * // Create the custom permissions attribute. Set<PosixFilePermission> perms =
		 * PosixFilePermissions.fromString("rw-r-----");
		 * FileAttribute<Set<PosixFilePermission>> attr =
		 * PosixFilePermissions.asFileAttribute(perms);
		 * 
		 * // Convert the string to a ByteBuffer. data = "Hello World! ".getBytes();
		 * ByteBuffer bb = ByteBuffer.wrap(data);
		 * 
		 * try (SeekableByteChannel sbc =
		 * Files.newByteChannel(Paths.get("./data/permissions.log"), options, attr)) {
		 * sbc.write(bb); } catch (IOException x) {
		 * System.out.println("Exception thrown: " + x); }
		 */

		// 5. Methods for Creating Regular and Temporary Files
		// 5.1 Creating Files
		file = Paths.get("data/tmp.txt");
		try {
			// Create the empty file with default permissions, etc.
			Files.createFile(file);
		} catch (FileAlreadyExistsException x) {
			System.err.format("file named %s" + " already exists%n", file);
		} catch (IOException x) {
			// Some other sort of failure, such as permissions.
			System.err.format("createFile error: %s%n", x);
		}

		// 5.2 Creating Temporary Files
		try {
			Path tempFile = Files.createTempFile(null, ".myapp");
			System.out.format("The temporary file" + " has been created: %s%n", tempFile);
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}

}
