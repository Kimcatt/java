package examples;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;

/**
 * @date 2018.7.2
 * @since JDK 8
 */
public class Demo {

	public static void main(String[] args) {
		demo();
		System.out.format("Done at %s%n", Calendar.getInstance().getTime());
	}
	
	private static void demo() {
		Path source = Paths.get("data/source.txt");
		Path target = Paths.get("data/target.txt");
		try {
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
