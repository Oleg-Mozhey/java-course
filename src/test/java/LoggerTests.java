import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

public class LoggerTests {
    Logger LOGGER = LogManager.getLogger();

    @Test
    void logTest() {
        LOGGER.debug("Debug message");
        LOGGER.info("Info message");
        LOGGER.warn("Warn message");
        LOGGER.error("Error message");
        LOGGER.fatal("Fatal message");
    }
}
