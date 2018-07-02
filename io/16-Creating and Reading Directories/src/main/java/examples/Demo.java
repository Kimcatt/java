package examples;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @date 2018.7.2
 */
public class Demo {

	public static void main(String[] args) {
		demo();
	}

	private static void demo() {
		// 1. Listing a File System's Root Directories
		Iterable<Path> dirs = FileSystems.getDefault().getRootDirectories();
		for (Path name : dirs) {
			System.err.println(name);
		}

		// 2. Creating a Directory
		Path dir = Paths.get("data/log/20180702");
		try {
			// Files.createDirectory(dir);
			Files.createDirectories(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 3. Creating a Temporary Directory
		try {
			Path tempDirectory = Files.createTempDirectory(null);
			System.out.format("Temporary directory \"%s\" was created.%n", tempDirectory.toRealPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 4. Listing a Directory's Contents
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("data"))) {
			for (Path file : stream) {
				System.out.println(file.getFileName());
			}
		} catch (IOException | DirectoryIteratorException x) {
			// IOException can never be thrown by the iteration.
			// In this snippet, it can only be thrown by newDirectoryStream.
			System.err.println(x);
		}

		// 5. Filtering a Directory Listing By Using Globbing
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("data"), "*.{java,class,jar,xml}")) {
			for (Path entry : stream) {
				System.out.println(entry.getFileName());
			}
		} catch (IOException x) {
			// IOException can never be thrown by the iteration.
			// In this snippet, it can // only be thrown by newDirectoryStream.
			System.err.println(x);
		}

		// 6. Writing Your Own Directory Filter
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			public boolean accept(Path file) throws IOException {
				return Files.isDirectory(file);
			}
		};

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("data"), filter)) {
			for (Path entry : stream) {
				System.out.println(entry.getFileName());
			}
		} catch (IOException x) {
			System.err.println(x);
		}
	}

}
