package hu.urbanovics.doctusoft.logfilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class LogProcessor implements Runnable {

    private BufferedReader reader;
    private List<LogPatternWriter> writers;

    LogProcessor(BufferedReader reader, List<LogPatternWriter> writers) {
        this.reader = reader;
        this.writers = writers;
    }

    @Override
    public void run() {
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String finalLine = line;
                writers.forEach(it -> it.process(finalLine));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


