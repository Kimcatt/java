package example;

import java.io.IOException;
import java.util.Scanner;

/**
 * @date 2018.7.7	
 */
public class ProcessBuilderExample {

	public static void main(String[] args) {
		ProcessBuilder pb = new ProcessBuilder("cmd","/c","ipconfig/all");
        try {
			Process process = pb.start();
			Scanner scanner = new Scanner(process.getInputStream(), "GBK");
			 
			while(scanner.hasNextLine()){
			    System.out.println(scanner.nextLine());
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
