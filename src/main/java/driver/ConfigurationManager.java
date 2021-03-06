package driver;

import java.io.*;
import java.util.*;


public class ConfigurationManager {

    public enum RequiredField {
        SHUFFLE,
        DURATION,
        AUTOPLAY,
        EXTENSIONS
    }

    private Properties configs;
    private String propertiesPath;

    private ConfigurationManager(Properties configs, String path) {
        this.configs = configs;
        propertiesPath = path;
    }

    /**
     * Do not use, only for testing right now
     * @param configs
     */
    private ConfigurationManager(Properties configs) {
        this.configs = configs;
    }

    /**
     * This is intended only for EXTENSIONS
     * @return The extensions configuration field for use with ImageAggregator
     */
    public List<String> getExtensions() {
        List<String> validExtensions = new LinkedList<>();
        String value = configs.getProperty(RequiredField.EXTENSIONS.name());
        if (null != value) {
            validExtensions.addAll(Arrays.asList(value.split(",")));
        }
        return validExtensions;
    }

    public void changePropertyValue(String key, String value)
    throws Exception {
        File file = new File(propertiesPath);
        file = new File(file.getAbsolutePath() + "/Throne.properties");
        FileInputStream input = new FileInputStream(file);
        Properties props = new Properties();
        props.load(input);
        input.close();

        FileOutputStream output = new FileOutputStream(file);
        props.setProperty(key, value);
        props.store(output, null);
        output.close();
    }

    /**
     * Writes Throne.properties with changes in one update operation.
     * @param values changed values in Settings page
     */
    public void savePropertyValues(List<String> values)
    throws Exception {
        File file = new File(propertiesPath);
        file = new File(file.getAbsolutePath() + "/Throne.properties");
        FileInputStream input = new FileInputStream(file);
        Properties props = new Properties();
        props.load(input);
        input.close();

        FileOutputStream output = new FileOutputStream(file);
        props.setProperty("AUTOPLAY", values.get(0));
        props.setProperty("DURATION", values.get(1));
        props.setProperty("SHUFFLE", values.get(2));
        props.setProperty("EXTENSIONS", values.get(3));
        props.store(output, null);
        output.close();
    }

    public <T> T getProperty(String key, Class<T> type)
    throws Exception {
        String property = configs.getProperty(key);
        if (property == null) {
            throw new Exception("Invalid key supplied");
        }

        return type.cast(property);
    }

    public String getProperty(RequiredField key) {
        return configs.getProperty(key.name());
    }

    public boolean isAutoplay() {
        return Boolean.parseBoolean(getProperty(RequiredField.AUTOPLAY));
    }

    public boolean isShuffle() {
        return Boolean.parseBoolean(getProperty(RequiredField.SHUFFLE));
    }

    public int getAutoplayDuration() {
        return Integer.parseInt(getProperty(RequiredField.DURATION));
    }

    public static class Builder {

        /**
         * Creates a ConfigurationManager with the properties set in Throne.properties
         * @return new ConfigurationManager
         * @throws Exception Throne.properties not found
         */
        public static ConfigurationManager buildDefault()
        throws Exception {
            String path = System.getProperty("user.dir");
            Properties configuration = getPropertiesFromPath(path);
            verifyConfigurationFields(configuration);
            return new ConfigurationManager(configuration, path);
        }

        /**
         * Creates a ConfigurationManager with the properties set in Throne.properties
         * @param path relative path from user.dir for the Throne.properties
         * @return new ConfigurationManager
         * @throws Exception Throne.properties not found
         */
        public static ConfigurationManager build(String path)
                throws Exception {
            Properties configuration = getPropertiesFromPath(path);
            verifyConfigurationFields(configuration);
            return new ConfigurationManager(configuration, path);
        }

        /**
         * This should be used only for unit testing.
         * @param properties valid properties file
         * @return new ConfigurationManager
         * @throws Exception Throne.properties not found
         */
        public static ConfigurationManager buildDefault(Properties properties)
        throws Exception {
            verifyConfigurationFields(properties);
            return new ConfigurationManager(properties);
        }

        private static Properties getPropertiesFromPath(String path)
        throws IOException {
            Properties configuration = new Properties();

            File file = new File(path + "/Throne.properties");
            if (file.isDirectory()) {
                throw new FileNotFoundException("Path to .properties is invalid, directory");
            }
            configuration.load(new FileReader(file));
            return configuration;
        }

        private static void verifyConfigurationFields(Properties config)
        throws Exception {
            Set<String> requiredConfigurationFields = requiredFieldsAsSet();

            for (String key : requiredConfigurationFields) {
                if (config.getProperty(key) == null) {
                    throw new Exception("Missing required configuration key: " + key);
                }
            }
        }

        private static Set<String> requiredFieldsAsSet() {
            Set<String> configurationFields = new HashSet<>();
            for (RequiredField field : RequiredField.values()) {
                configurationFields.add(field.name());
            }
            return configurationFields;
        }
    }
}
