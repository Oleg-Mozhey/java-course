package config;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:config.properties")
public interface ServiceConfig extends Config {

    @Key("api_base_url")
    String apiBaseUrl();

}