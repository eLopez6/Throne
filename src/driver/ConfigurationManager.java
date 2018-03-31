package driver;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class ConfigurationManager {

    enum RequiredField {
        SHUFFLE,
        DURATION,
        AUTOPLAY,
        EXTENSIONS
    }

    private Properties configs;

    private ConfigurationManager(Properties configs) {
        this.configs = configs;
    }

    Properties getProperties() {
        return (Properties) configs.clone();
    }

    /**
     * This is intended only for DIRECTORIES and EXTENSIONS
     * @param field DIRECTORIES or EXTENSIONS
     * @return The configuration field for use with ImageAggregator
     */
    List<String> getValuesForPropertyField(RequiredField field) {
        List<String> configuredValues = new LinkedList<>();
        String value = configs.getProperty(field.name());
        if (null != value) {
            configuredValues.addAll(Arrays.asList(value.split(",")));
        }
        return configuredValues;
    }

    // Method support to come:
    // Modify the properties from the application
    // That might need a method for each field? Bleaugh...

    static class Builder {

        static ConfigurationManager build(String path)
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
