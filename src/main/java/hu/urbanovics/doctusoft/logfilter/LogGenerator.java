package hu.urbanovics.doctusoft.logfilter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Random;

public class LogGenerator {

    private static final String words[] = {"alma", "körte", "répa", "zeller"};

    private static final int NUMBER_OF_FILES = 6;

    private static final int NUMBER_OF_ROWS = 50000000;

    public static void main(String[] args) throws IOException {
        Random random = new Random();
        BufferedWriter ow = null;

        for (int fileCounter = 0; fileCounter < NUMBER_OF_FILES; fileCounter++) {
            try {
                OutputStreamWriter writer = new FileWriter("test" + fileCounter);
                ow = new BufferedWriter(writer);
                for (int i = 0; i < NUMBER_OF_ROWS; i++) {
                    ow.write(words[random.nextInt(4)]);
                    ow.write("\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (ow != null) {
                    ow.close();
                }
            }
        }
    }

}
