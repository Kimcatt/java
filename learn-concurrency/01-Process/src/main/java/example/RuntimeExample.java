package example;

import java.io.IOException;
import java.util.Scanner;

/**
 * @date 2018.7.7
 */
public class RuntimeExample {

	public static void main(String[] args) {
		try {
			String cmd = "cmd " + "/c " + "ipconfig/all";
			Process process = Runtime.getRuntime().exec(cmd);
			Scanner scanner = new Scanner(process.getInputStream(), "GBK");

			while (scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
