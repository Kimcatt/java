package examples;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.UserPrincipal;
import java.util.Calendar;

/**
 * @date 2018.7.2
 */
public class FileOperations {

	public static void main(String[] args) {
		demo();
		
		System.out.format("Done at %s", Calendar.getInstance().getTime());
	}
	
	
	public static void demo() {
		//Releasing System Resources
		
		//Releasing System Resources
		Charset charset = Charset.forName("US-ASCII");
		String s = "java";
		Path filePath = Paths.get("data.txt");
		BufferedWriter writer = null;
		try {
			writer = Files.newBufferedWriter(filePath, charset);
		    writer.write(s, 0, s.length());
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		} finally {
			try {
				if (writer != null)
					writer.close();
			} catch (Exception e) {
				
			}
		}
		
		//Varargs
		Path source = Paths.get("source.txt");
		Path target = Paths.get("target.txt");
		try {
			//Files.copy(source, target, StandardCopyOption.ATOMIC_MOVE);
			Files.move(source,
			           target,
			           /*StandardCopyOption.REPLACE_EXISTING,*/
			           /*StandardCopyOption.COPY_ATTRIBUTES*/
			           StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Atomic Operations
		
		//Method Chaining
		ByteBuffer buf = ByteBuffer.wrap("java".getBytes());
		Path file = Paths.get("data.txt");
		String value = Charset.defaultCharset().decode(buf).toString();
		try {
			UserPrincipal group =
			    file.getFileSystem().getUserPrincipalLookupService().
			         lookupPrincipalByName("me");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
