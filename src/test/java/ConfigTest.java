import de.mgeo.json.api.configs.AppProperties;
import de.mgeo.json.api.configs.BuildProperties;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertTrue;

public class ConfigTest {

    AppProperties appProperties;

    @Autowired
    BuildProperties buildProperties;

    public ConfigTest() {
        appProperties = new AppProperties();
        buildProperties = new BuildProperties();
    }

    @Test
    public void ConfigIsVerison() {
        System.out.println(buildProperties.getAppVersion());
        assertTrue("X".equals(buildProperties.getAppVersion()));
    }

    @Test
    public void ConfigIsDatafile() {
        appProperties.setDataDir("./test/data");
        appProperties.setDataFile("test-entries.json");
        assertTrue("./test/data/test-entries.json".equals(appProperties.getDataFilePath()));
    }
}
