package driver;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Properties;

import static org.mockito.BDDMockito.given;

import static org.mockito.Matchers.anyString;
import static driver.ConfigurationManager.RequiredField.*;
import static org.mockito.Mockito.mock;

public class ConfigurationManagerTest {

    private static ConfigurationManager manager;

    @Mock
    private static Properties mockProperties = mock(Properties.class);


    @BeforeClass
    public static void setupClass()
    throws Exception {
        given(mockProperties.getProperty(EXTENSIONS.name())).willReturn(anyString());
        given(mockProperties.getProperty(SHUFFLE.name())).willReturn(anyString());
        given(mockProperties.getProperty(DURATION.name())).willReturn(anyString());
        given(mockProperties.getProperty(AUTOPLAY.name())).willReturn(anyString());

        manager = ConfigurationManager.Builder.build(mockProperties);
    }

    @Test(expected=Exception.class)
    public void givenInvalidKey_getProperty_throwsException()
    throws Exception {

        // Given
        String invalidKey = "Invalid";

        // When
        manager.getProperty(invalidKey, String.class);

        // Then - throws Exception
    }
}
