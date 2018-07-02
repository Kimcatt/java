package examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Demo {

	public static void main(String[] args) {
		demo();
	}

	private static void demo() {
		// 1. Creating a Symbolic Link
		Path newLink = Paths.get("data/log");
		Path target = Paths.get("data/log.txt");
		try {
		    Files.createSymbolicLink(newLink, target);
		} catch (IOException x) {
		    System.err.println(x);
		} catch (UnsupportedOperationException x) {
		    // Some file systems do not support symbolic links.
		    System.err.println(x);
		}
		
		// 2. Creating a Hard Link
		newLink = Paths.get("data/hl");
		try {
		    Files.createLink(newLink, target);
		} catch (IOException x) {
		    System.err.println(x);
		} catch (UnsupportedOperationException x) {
		    // Some file systems do not
		    // support adding an existing
		    // file to a directory.
		    System.err.println(x);
		}
		
		// 3. Detecting a Symbolic Link
		// Files.isSymbolicLink(file)
		
		// 4. Finding the Target of a Link
		// Files.readSymbolicLink(link)

	}

}
