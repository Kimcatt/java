package examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class CopyLines {

	public static void main(String[] args) throws IOException {
		BufferedReader reader = null;
        PrintWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("data/character-data.txt"));
            writer = new PrintWriter(new FileWriter("data/character-output.txt"));

            String l;
            while ((l = reader.readLine()) != null) {
                writer.println(l);
            }
            writer.println(Calendar.getInstance().getTime());
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
	}

}
