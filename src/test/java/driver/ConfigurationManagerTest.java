package driver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Properties;

import static org.mockito.BDDMockito.given;
import static driver.ConfigurationManager.RequiredField.*;
import static org.mockito.Mockito.mock;

class ConfigurationManagerTest {

    private static ConfigurationManager manager;

    @Mock
    private static Properties mockProperties = mock(Properties.class);

    @BeforeAll
    static void setupClass()
    throws Exception {
        String validExtensions = "jpg,png";
        String optionalKey = "optional";
        given(mockProperties.getProperty(EXTENSIONS.name())).willReturn(validExtensions);
        given(mockProperties.getProperty(SHUFFLE.name())).willReturn("false");
        given(mockProperties.getProperty(DURATION.name())).willReturn("3");
        given(mockProperties.getProperty(AUTOPLAY.name())).willReturn("true");

        given(mockProperties.getProperty(optionalKey)).willReturn("optionalValue");

        manager = ConfigurationManager.Builder.build(mockProperties);
    }

    @Test
    void givenInvalidKey_getProperty_throwsException() {
        // Given
        String invalidKey = "Invalid";

        // When
        Assertions.assertThrows(Exception.class, () -> manager.getProperty(invalidKey, String.class));

        // Then - throws Exception
    }

    @Test
    void givenValidKey_getProperty_returnsValue()
    throws Exception {
        String validKey = "optional";

        String property = manager.getProperty(validKey, String.class);

        Assertions.assertEquals("optionalValue", property);
    }

    @Test
    void givenValidExtenstions_getExtensions_returnsValidExtensions() {
        // Given

        // When
        List<String> validExtensions = manager.getExtensions();

        // Then
        Assertions.assertTrue(validExtensions.contains("jpg"));
        Assertions.assertTrue(validExtensions.contains("png"));
    }
}
