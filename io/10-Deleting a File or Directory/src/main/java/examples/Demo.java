package examples;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;

/**
 * @date 2018.7.2
 */
public class Demo {

	public static void main(String[] args) {
		demo();
		
		System.out.format("Done at %s%n", Calendar.getInstance().getTime());
	}
	
	public static void demo() {
		Path path = Paths.get("data/data.txt");
//		try {
//			Files.createFile(path);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		try {
//		    Files.delete(path);
		    Files.deleteIfExists(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    System.err.println(x);
		}
	}

}
