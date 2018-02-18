package driver;

import driver.ConfigurationManager.RequiredField;

/**
 * This class will handle the other classes in the project
 */
public class SlideshowDriver {

    public static void main (String[] args)
    throws Exception {

        String propertiesPath = "../../Throne.properties";

        if (args.length == 1) {
            propertiesPath = args[0];
        }

        ConfigurationManager config = ConfigurationManager.Builder.build(propertiesPath);
        ImageAggregator aggregator = ImageAggregator.Builder.build(
                config.getValuesForPropertyField(RequiredField.EXTENSIONS),
                config.getValuesForPropertyField(RequiredField.DIRECTORIES));

    }



}
