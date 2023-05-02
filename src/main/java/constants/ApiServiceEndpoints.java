package constants;

public class ApiServiceEndpoints {
    private ApiServiceEndpoints() {
        throw new IllegalStateException("Constants class");
    }

    public static final String GET_TOKEN = "v1/token";
    public static final String AIRLINES = "v2/airlines";
    public static final String PASSENGER = "v2/passenger";
    public static final String PASSENGER_BY_ID = "v2/passenger/%s";
}