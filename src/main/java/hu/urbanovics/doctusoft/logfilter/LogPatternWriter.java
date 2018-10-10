package hu.urbanovics.doctusoft.logfilter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogPatternWriter {

//    private final String name;
    private final String pattern;
//    private final Path outputFile;
    private BufferedWriter writer;

    LogPatternWriter(String name, String pattern, Path outputFile) {
//        this.name = name;
        this.pattern = pattern;
//        this.outputFile = outputFile;
        try {
            writer = Files.newBufferedWriter(outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private synchronized BufferedWriter getWriter() throws IOException {
//        if (writer == null) {
//            writer = Files.newBufferedWriter(outputFile);
//        }
//        return writer;
//    }

    public void process(final String line) {
        if (line.matches(pattern)) {
            try {
                writer.write(line + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        close();
    }
}
