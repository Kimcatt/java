package examples;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyCharacters {

	public static void main(String[] args) throws IOException {
		FileReader fileReader = null;
        FileWriter fileWriter = null;

        try {
            fileReader = new FileReader("data/character-data.txt");
            fileWriter = new FileWriter("data/character-output.txt");

            int c;
            while ((c = fileReader.read()) != -1) {
                fileWriter.write(c);
            }
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
        }

	}

}
