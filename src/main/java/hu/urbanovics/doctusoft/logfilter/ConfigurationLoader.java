package hu.urbanovics.doctusoft.logfilter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigurationLoader {

    private List<ConfigEntry> configEntries = new ArrayList<>();

    public ConfigurationLoader(String configFilePath) {
        readPatterns(configFilePath);
    }

    protected void readPatterns(String configFilePath) {
        try {
            Scanner scanner = new Scanner(new FileInputStream(configFilePath));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.length() > 0) {
                    String patternValue = line.replaceFirst(".*: ", "");
                    Pattern namePattern = Pattern.compile("(^.*): ");
                    Matcher nameMatcher = namePattern.matcher(line);
                    if (nameMatcher.find()) {
                        String name = nameMatcher.group(1);
                        configEntries.add(new ConfigEntry(name, patternValue));
                    } else {
                        throw new RuntimeException("Wrong entry in the configuration file");
                    }
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<ConfigEntry> getConfigEntries() {
        return configEntries;
    }

    public static class ConfigEntry {
        public final String name;
        public final String patternValue;

        ConfigEntry(String name, String pattern) {
            this.name = name;
            this.patternValue = pattern;
        }
    }
}
