package hu.urbanovics.doctusoft.logfilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class LogFilter {

    public static void main(String args[]) {
        File srcDir = new File(args[0]);
        String destDir = args[1];
        String configurationPath = args[2];

        ConfigurationLoader config = new ConfigurationLoader(configurationPath);

        List<File> files = Arrays.asList(srcDir.listFiles());

        long start = System.currentTimeMillis();
        multiThreaded(config, files, destDir);
        long end = System.currentTimeMillis();

        long millis = end - start;
        String elapsedTime = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        System.out.println(elapsedTime);
    }

    private static void multiThreaded(ConfigurationLoader config, List<File> files, String destDir) {
        List<LogPatternWriter> logPatternWriters = config.getConfigEntries().stream()
                .map(it -> new LogPatternWriter(it.name, it.patternValue, Paths.get(destDir, it.name)))
                .collect(Collectors.toList());

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ExecutorService threadPool = Executors.newFixedThreadPool(availableProcessors);
        int numberOfFiles = files.size();
        int plusProcessorPerFile = availableProcessors - numberOfFiles;

        files.stream().map(it -> {
            try {
                return Files.newBufferedReader(it.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            throw new RuntimeException();
        }).forEach(reader -> {
            threadPool.submit(new LogProcessor(reader, logPatternWriters));
            for (int i = 0; i < plusProcessorPerFile; i++) {
                threadPool.submit(new LogProcessor(reader, logPatternWriters));
            }
        });
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logPatternWriters.forEach(LogPatternWriter::close);
    }

}
