package driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ConfigurationManager {

    public enum RequiredField {
        SHUFFLE,
        DURATION,
        AUTOPLAY,
        EXTENSIONS
    }

    private Properties configs;

    private ConfigurationManager(Properties configs) {
        this.configs = configs;
    }

    /**
     * This is intended only for EXTENSIONS
     * @param extensions EXTENSIONS enum
     * @return The configuration field for use with ImageAggregator
     */
    public List<String> getExtensions(RequiredField extensions) {
        List<String> validExtensions = new LinkedList<>();
        String value = configs.getProperty(extensions.name());
        if (null != value) {
            validExtensions.addAll(Arrays.asList(value.split(",")));
        }
        return validExtensions;
    }


    public void changePropertyValue(String key, String value) {
        configs.setProperty(key, value);
    }

    // This would be my ideal, I'd like to make something like this happen on Strings -> Integer, String -> Boolean
    public <T> T getProperty(RequiredField key, Class<T> type)
    throws UnsupportedOperationException {
        return type.cast(configs.getProperty(key.name()));
    }

    public String getProperty(RequiredField key) {
        return configs.getProperty(key.name());
    }

    public static class Builder {

        public static ConfigurationManager build(String path)
        throws Exception {
            Properties configuration = new Properties();

            if (!Files.isDirectory(Paths.get(path))) {
                throw new FileNotFoundException("Path to .properties is invalid");
            }
            configuration.load(new FileReader(path));

            verifyConfigurationFields(configuration);
            return new ConfigurationManager(configuration);
        }

        private static void verifyConfigurationFields(Properties config)
        throws Exception {
            Set<String> configurationFields = fieldsAsSet();

            for (String key : configurationFields) {
                if (config.getProperty(key) == null) {
                    throw new Exception("Missing required configuration key: " + key);
                }
            }
        }

        private static Set<String> fieldsAsSet() {
            Set<String> configurationFields = new HashSet<>();
            for (RequiredField field : RequiredField.values()) {
                configurationFields.add(field.name());
            }
            return configurationFields;
        }
    }
}
