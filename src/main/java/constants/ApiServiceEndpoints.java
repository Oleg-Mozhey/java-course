package constants;

public class ApiServiceEndpoints {
    private ApiServiceEndpoints() {
        throw new IllegalStateException("Constants class");
    }

    public static final String GET_TOKEN = "v1/token";
    public static final String AIRLINES = "v2/airlines";
}