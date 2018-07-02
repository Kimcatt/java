package examples;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * @date 2018.7.2
 */
public class Demo {

	public static void main(String[] args) {
		demo();
	}

	private static void demo() {
		// 1. Determining MIME Type
		try {
			Path filename = Paths.get("data/data.txt");
			String type = Files.probeContentType(filename);
			if (type == null) {
				System.err.format("\"%s\" has an" + " unknown filetype.%n", filename);
			} else if (!type.equals("text/plain")) {
				System.err.format("\"%s\" is not" + " a plain text file.%n", filename);
			} else {
				System.out.format("\"%s\" is a" + " %s file.%n", filename, type);
			}
		} catch (IOException x) {
			System.err.println(x);
		}

		// 2. Default File System
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.*");
		
		//3. Path String Separator
//		String separator = File.separator;
		String separator = FileSystems.getDefault().getSeparator();
		
		//4. File System's File Stores
		for (FileStore store: FileSystems.getDefault().getFileStores()) {
			System.out.println(store);
		}
		for (Path path: FileSystems.getDefault().getRootDirectories()) {
			System.out.println(path);
		}
		try {
			FileStore store= Files.getFileStore(Paths.get("data/data.txt"));
			System.out.println(store + store.type());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
