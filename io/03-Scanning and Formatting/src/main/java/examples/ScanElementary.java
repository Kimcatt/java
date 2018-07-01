package examples;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class ScanElementary {

	public static void main(String[] args) throws IOException {
		scanGenericFile();
		scanLocaledSpecifiedFile();
        
    }
	
	private static void scanGenericFile() throws FileNotFoundException {
		Scanner s = null;

        try {
            s = new Scanner(new BufferedReader(new FileReader("data/example.txt")));
            
            //定义新的分隔符
            s.useDelimiter("\r\n");

            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
	}
	
	private static void scanLocaledSpecifiedFile() throws IOException {
		Scanner s = null;
        double sum = 0;

        try {
            s = new Scanner(new BufferedReader(new FileReader("data/usnumbers.txt")));
            s.useLocale(Locale.US);

            while (s.hasNext()) {
                if (s.hasNextDouble()) {
                    sum += s.nextDouble();
                } else {
                    s.next();
                }   
            }
        } finally {
            s.close();
        }

        System.out.println(sum);
	}
}
