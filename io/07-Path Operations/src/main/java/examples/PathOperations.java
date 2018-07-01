package examples;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;


public class PathOperations {

	public static void main(String[] args) throws IOException {
		info();
		try {
			convert();
		} catch (Exception e) {
			e.printStackTrace();
		}
		join();
		pathBetweenTwoPaths();
	}
	
	private static void create(String[] args) {
		Path p1 = Paths.get("/tmp/foo");
		Path p2 = Paths.get(args[0]);
		Path p3 = Paths.get(URI.create("file:///Users/joe/FileTest.java"));
		
		Path p4 = FileSystems.getDefault().getPath("/users/sally");
		
		Path p5 = Paths.get(System.getProperty("user.home"),"logs", "foo.log");
	}
	
	private static void info() {
		// None of these methods requires that the file corresponding
		// to the Path exists.
		// Microsoft Windows syntax
		Path path = Paths.get("C:\\home\\joe\\foo");

		// Solaris syntax
		//Path path = Paths.get("/home/joe/foo");

		System.out.format("toString: %s%n", path.toString());
		System.out.format("getFileName: %s%n", path.getFileName());
		System.out.format("getName(0): %s%n", path.getName(0));
		System.out.format("getNameCount: %d%n", path.getNameCount());
		System.out.format("subpath(0,2): %s%n", path.subpath(0,2));
		System.out.format("getParent: %s%n", path.getParent());
		System.out.format("getRoot: %s%n", path.getRoot());
	}
	
	private static void convert() throws IOException {
		Path p1 = Paths.get("/home/logfile");
		// Result is file:///home/logfile
		System.out.format("%s%n", p1.toUri());
		System.out.format("%s%n", p1.toAbsolutePath());
		System.out.format("%s%n", p1.toRealPath());
	}
	
	private static void join() {
		// Microsoft Windows
		Path p1 = Paths.get("C:\\home\\joe\\foo");
		// Result is C:\home\joe\foo\bar
		System.out.format("%s%n", p1.resolve("bar"));
		// Result is /home/joe
		System.out.format("%s%n", Paths.get("foo").resolve("/home/joe"));
	}
	
	private static void pathBetweenTwoPaths() {
		Path p1 = Paths.get("joe");
		Path p2 = Paths.get("sally");
		
		// Result is ../sally
		System.out.format("%s%n", p1.relativize(p2));
		// Result is ../joe
		System.out.format("%s%n", p2.relativize(p1));
	}

}
