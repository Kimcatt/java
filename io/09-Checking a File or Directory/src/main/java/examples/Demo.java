package examples;

import java.io.IOException;
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
	
	public static void demo() {
		Path file = Paths.get("data/data.txt");
		//Verifying the Existence of a File or Directory
		boolean exists = Files.exists(file);
		System.out.format("The file %s exists? %s%n", file.toAbsolutePath(), exists ? "yes" : "no");
		
		//Checking File Accessibility
		boolean isRegularExecutableFile = Files.isRegularFile(file) &
		         Files.isReadable(file) & Files.isExecutable(file);
		System.out.format("The file data.txt is regular and readable and executable? %s%n", isRegularExecutableFile ? "yes" : "no");
		
		//Checking Whether Two Paths Locate the Same File
		Path p1 = Paths.get("data/data.txt");
		Path p2 =  Paths.get("./data/data.txt");
		try {
			boolean sameFile = Files.isSameFile(p1, p2);
			if (sameFile) {
			    System.out.format("File %s and %s are the same file? %s%n", p1, p2, sameFile ? "yes" : "no");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
