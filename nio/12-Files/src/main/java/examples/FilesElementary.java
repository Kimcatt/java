package examples;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesElementary {

	public static void main(String[] args) throws IOException {
		// Files.exists()
		String pathStr = "data/logging.properties";
		Path path = Paths.get(pathStr);

		boolean pathExists = Files.exists(path, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });

		System.out.println(pathStr + " exists? " + pathExists);

		// Files.createDirectory()
		path = Paths.get("data/subdir");

		try {
			Path newDir = Files.createDirectory(path);
		} catch (FileAlreadyExistsException e) {
			// the directory already exists.
		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}

		// Files.copy()
		Path sourcePath = Paths.get("data/logging.properties");
		Path destinationPath = Paths.get("data/logging-copy.properties");

		try {
			Files.copy(sourcePath, destinationPath);
		} catch (FileAlreadyExistsException e) {
			// destination file already exists
		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}

		// Files.copy() - Overwriting Existing Files
		Path sourcePath2 = Paths.get("data/logging.properties");
		Path destinationPath2 = Paths.get("data/logging-copy.properties");

		try {
			Files.copy(sourcePath2, destinationPath2, StandardCopyOption.REPLACE_EXISTING);
		} catch (FileAlreadyExistsException e) {
			// destination file already exists
		} catch (IOException e) {
			// something else went wrong
			e.printStackTrace();
		}

		// Files.move()
		Path sourcePath3 = Paths.get("data/logging-copy.properties");
		Path destinationPath3 = Paths.get("data/subdir/logging-moved.properties");

		try {
			Files.move(sourcePath3, destinationPath3, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// moving file failed.
			e.printStackTrace();
		}

		// Files.delete()
		Path path4 = Paths.get("data/subdir/logging-moved.properties");

		try {
			Files.delete(path4);
		} catch (IOException e) {
			// deleting file failed
			e.printStackTrace();
		}

		// Files.walkFileTree()
		
		Files.walkFileTree(Paths.get("data"), new FileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				System.out.println("pre visit dir:" + dir);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				System.out.println("visit file: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
				System.out.println("visit file failed: " + file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				System.out.println("post visit directory: " + dir);
				return FileVisitResult.CONTINUE;
			}
		});

		// Searching For Files
		Path rootPath = Paths.get("data");
		String fileToFind = File.separator + "README.txt";

		try {
			Files.walkFileTree(rootPath, new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					String fileString = file.toAbsolutePath().toString();
					System.out.println("pathString = " + fileString);

					if (fileString.endsWith(fileToFind)) {
						System.out.println("file found at path: " + file.toAbsolutePath());
						return FileVisitResult.TERMINATE;
					}
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Deleting Directories Recursively
		try {
		  Files.walkFileTree(Paths.get("data/to-delete"), new SimpleFileVisitor<Path>() {
		    @Override
		    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		      System.out.println("delete file: " + file.toString());
		      Files.delete(file);
		      return FileVisitResult.CONTINUE;
		    }

		    @Override
		    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		      Files.delete(dir);
		      System.out.println("delete dir: " + dir.toString());
		      return FileVisitResult.CONTINUE;
		    }
		  });
		} catch(IOException e){
		  e.printStackTrace();
		}

	}

}
