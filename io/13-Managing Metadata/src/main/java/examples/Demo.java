package examples;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.nio.file.attribute.UserPrincipal;
import java.util.Set;

/**
 * @date 2018.7.2
 */
public class Demo {

	public static void main(String[] args) {
		demo();
	}

	private static void demo() {
		// Basic File Attributes
		Path file = Paths.get("data/data.txt");
		BasicFileAttributes attr = null;
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			System.out.println("creationTime: " + attr.creationTime());
			System.out.println("lastAccessTime: " + attr.lastAccessTime());
			System.out.println("lastModifiedTime: " + attr.lastModifiedTime());

			System.out.println("isDirectory: " + attr.isDirectory());
			System.out.println("isOther: " + attr.isOther());
			System.out.println("isRegularFile: " + attr.isRegularFile());
			System.out.println("isSymbolicLink: " + attr.isSymbolicLink());
			System.out.format("size: %s bytes%n", attr.size());
		} catch (IOException e) {
		}

		// Setting Time Stamps
		file = Paths.get("data/data.txt");
		try {
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			long currentTime = System.currentTimeMillis();
			FileTime ft = FileTime.fromMillis(currentTime);
			Files.setLastModifiedTime(file, ft);
			System.out.format("The last modified time of file %s is set to %s.%n", file, ft);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// DOS File Attributes
		// DOS file attributes are also supported on file systems other than DOS, such
		// as Samba.
		try {
			file = Paths.get("data/dostest.txt");
			System.out.println("Reading dos attributes of file " + file);
			DosFileAttributes dosAttr = Files.readAttributes(file, DosFileAttributes.class);
			System.out.println("isReadOnly is " + dosAttr.isReadOnly());
			System.out.println("isHidden is " + dosAttr.isHidden());
			System.out.println("isArchive is " + dosAttr.isArchive());
			System.out.println("isSystem is " + dosAttr.isSystem());

			// Set a DOS attribute
			Files.setAttribute(file, "dos:hidden", true); // set to hidden
		} catch (UnsupportedOperationException x) {
			System.err.println("DOS file" + " attributes not supported:" + x);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// POSIX File Permissions
		/*
		try {
			PosixFileAttributes posixAttr = Files.readAttributes(file, PosixFileAttributes.class);
			System.out.format("%s %s %s%n", posixAttr.owner().getName(), posixAttr.group().getName(),
					PosixFilePermissions.toString(posixAttr.permissions()));

			// The following code snippet reads the attributes from one file and creates a
			// new file, assigning the attributes from the original file to the new file.
			FileAttribute<Set<PosixFilePermission>> attrs = PosixFilePermissions
					.asFileAttribute(posixAttr.permissions());
			Files.createFile(file, attrs);

			Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rw-------");
			attrs = PosixFilePermissions.asFileAttribute(perms);
			Files.setPosixFilePermissions(file, perms);
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/

		// Setting a File or Group Owner
		file = Paths.get("data/data.txt");
		try {
			UserPrincipal owner = file.getFileSystem().getUserPrincipalLookupService().lookupPrincipalByName("sally");
			Files.setOwner(file, owner);

			GroupPrincipal group = file.getFileSystem().getUserPrincipalLookupService()
					.lookupPrincipalByGroupName("green");
			Files.getFileAttributeView(file, PosixFileAttributeView.class).setGroup(group);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// User-Defined File Attributes
		file = Paths.get("data/data.txt");
		try {
			// write
			UserDefinedFileAttributeView view = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			view.write("user.mimetype", Charset.defaultCharset().encode("text/html"));
			// read
			view = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			String name = "user.mimetype";
			ByteBuffer buf = ByteBuffer.allocate(view.size(name));
			view.read(name, buf);
			buf.flip();
			String value = Charset.defaultCharset().decode(buf).toString();
			System.out.format("%s attribute %s: %s%n", file, name, value);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// File Store Attributes
		file = Paths.get("data/data.txt");
		file = Paths.get("/");
		try {
			System.out.println(file.toRealPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			FileStore store = Files.getFileStore(file);

			long total = store.getTotalSpace() / 1024;
			long used = (store.getTotalSpace() - store.getUnallocatedSpace()) / 1024;
			long avail = store.getUsableSpace() / 1024;
			System.out.format("total: %s G, used: %s G, avail: %s G%n", total / 1024 / 1024, used / 1024 / 1024, avail / 1024 / 1024);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
