package config;

import org.aeonbits.owner.Config;

@org.aeonbits.owner.Config.Sources("classpath:auth.properties")
public interface AuthConfig extends Config {

    @Key("login_url")
    String loginUrl();

    @Key("user_name")
    String userName();

    @Key("password")
    String password();

    @Key("scope")
    String scope();

    @Key("grant_type")
    String grantType();

    @Key("client_id")
    String clientId();

}