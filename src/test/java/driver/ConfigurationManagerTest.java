package driver;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Properties;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;


public class ConfigurationManagerTest {

    private ConfigurationManager manager;

    @Mock
    private Properties mockProperties;


    @BeforeClass
    public void setupClass()
    throws Exception {


    }

    @Test
    public void givenInvalidKey_getProperty_throwsException() {
        String invalidKey = "Invalid";


    }
}
