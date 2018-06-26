package examples;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @date 2018.6.26
 */
public class PathElementary {

	public static void main(String[] args) {
		//创建绝对路径
		Path path = Paths.get("D:\\data\\myfile.txt");
		
		//创建相对路径
		Path projects = Paths.get("D:\\data", "projects");
		System.out.println(projects.toAbsolutePath());
		Path file     = Paths.get("D:\\data", "projects\\a-project\\myfile.txt");
		System.out.println(file.toAbsolutePath());

		//当前路径
		Path currentDir = Paths.get(".");
		System.out.println(currentDir.toAbsolutePath());
		System.out.println(currentDir.normalize().toAbsolutePath());
		currentDir = Paths.get("D:\\data\\projects\\.\\a-project");
		System.out.println(currentDir.toAbsolutePath());
		System.out.println(currentDir.normalize().toAbsolutePath());
		
		//父路径
		Path parentDir = Paths.get("..");
		System.out.println(parentDir.toAbsolutePath());
		System.out.println(parentDir.normalize().toAbsolutePath());
		
		//相对路径中的'.'和'..'
		Path path1 = Paths.get("d:\\data\\projects", ".\\a-project");
		System.out.println(path1.toAbsolutePath());
		Path path2 = Paths.get("d:\\data\\projects\\a-project",
		                       "..\\another-project");
		System.out.println(path2.toAbsolutePath());
		
		//normalize
		String originalPath =
		        "d:\\data\\projects\\a-project\\..\\another-project";

		Path path3 = Paths.get(originalPath);
		System.out.println("path3 = " + path3);

		Path path4 = path3.normalize();
		System.out.println("path4 = " + path4);
		
		
		
	}

}
