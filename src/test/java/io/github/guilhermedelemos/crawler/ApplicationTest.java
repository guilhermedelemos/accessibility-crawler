package io.github.guilhermedelemos.crawler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    @Test
    public void instanceTest() {
        Application application = new Application();
        assertNotNull(application, "new Application() should not be null");
    }

    @Test
    public void mainTest() {
        Application.main(null);
        assertTrue(true);
    }

}
