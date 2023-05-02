package config;

import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public class Config {
    private Config() {
        throw new IllegalStateException("Utility class");
    }

    public static Map<String, String> getEnvironment() {
        String environment = System.getProperty("env");

        if (environment == null) {
            System.setProperty("env", "test");
        }

        Map<String, String> apiEnv = new HashMap<>();
        apiEnv.put("env", System.getProperty("env"));

        return apiEnv;
    }

    public static ServiceConfig getServiceConfig() {
        return ConfigFactory.create(ServiceConfig.class, getEnvironment());
    }

    public static AuthConfig getAuthConfig() {
        return ConfigFactory.create(AuthConfig.class, getEnvironment());
    }


    public static Map<String, String> getTestEnvironment() {
        String environment = System.getProperty("prop");
        System.out.println("prop " + environment);

        if (environment == null) {
            System.setProperty("prop", "t");
        }

        Map<String, String> apiEnv = new HashMap<>();
        apiEnv.put("prop", System.getProperty("prop"));

        System.out.println("apiEnv " + apiEnv);

        return apiEnv;
    }
}