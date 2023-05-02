package utils;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Class contains simple templates for working with rest api by rest assured
 */

@Slf4j
public class RestHelper {

    @Setter
    protected String url;
    protected Map<String, String> cookies;
    protected Map<String, String> headers;
    @Setter
    protected ContentDefaultEnum contentDefaultEnum = ContentDefaultEnum.JSON;
    private RequestSpecification requestSpecification;

    public RestHelper() {

    }

    public RestHelper(String baseUrl) {
        setUrl(baseUrl);
    }

    /**
     * @param path path to method
     * @return The response of a request made by REST Assured.
     */
    public Response get(String path) {
        return get(path, null);
    }

    /**
     * @param path   path to method
     * @param params query params
     * @return The response of a request made by REST Assured.
     */
    public Response get(String path, Map<String, ?> params) {
        setUpSpecification(params);
        return requestSpecification.get(path);
    }

    /**
     * @param path path to method
     * @return The response of a request made by REST Assured.
     */
    public Response post(String path) {
        return post(path, null, null);
    }

    /**
     * @param path path to method
     * @param body request body
     * @return The response of a request made by REST Assured.
     */
    public Response post(String path, Object body) {
        return post(path, null, body);
    }

    /**
     * @param path   path to method
     * @param body   request body
     * @param params query params
     * @return The response of a request made by REST Assured.
     */
    public Response post(String path, Map<String, ?> params, Object body) {
        setUpSpecification(params, body);
        return requestSpecification.post(path);
    }

    /**
     * @param path       path to method
     * @param formParams FORM PARAMS
     * @return The response of a request made by REST Assured.
     */
    public Response postUrlEncoded(String path, Map<String, String> formParams) {
        ContentDefaultEnum before = contentDefaultEnum;
        contentDefaultEnum = ContentDefaultEnum.X_WWW_FORM_URLENCODED;

        setUpSpecification();
        if (formParams != null) {
            requestSpecification.formParams(formParams);
        }

        contentDefaultEnum = before;
        return requestSpecification.urlEncodingEnabled(true).post(path);
    }

    /**
     * @param path path to method
     * @param body request body
     * @return The response of a request made by REST Assured.
     */
    public Response put(String path, Object body) {
        return put(path, null, body);
    }

    /**
     * @param path   path to method
     * @param body   request body
     * @param params query params
     * @return The response of a request made by REST Assured.
     */
    public Response put(String path, Map<String, ?> params, Object body) {
        setUpSpecification(params, body);
        return requestSpecification.put(path);
    }

    /**
     * @param path path to method
     * @return The response of a request made by REST Assured.
     */
    public Response delete(String path) {
        return delete(path, null, null);
    }

    /**
     * @param path   path to method
     * @param params query params
     * @return The response of a request made by REST Assured.
     */
    public Response delete(String path, Map<String, ?> params) {
        return delete(path, params, null);
    }

    /**
     * @param path   path to method
     * @param body   request body
     * @param params query params
     * @return The response of a request made by REST Assured.
     */
    public Response delete(String path, Map<String, ?> params, Object body) {
        setUpSpecification(params, body);
        return requestSpecification.delete(path);
    }

    private void setUpSpecification() {
        setUpSpecification(null, null);
    }

    private void setUpSpecification(Map<String, ?> params) {
        setUpSpecification(params, null);
    }

    private void setUpSpecification(Map<String, ?> params, Object body) {
        requestSpecification = buildRequestSpecification();
        if (params != null) {
            requestSpecification.queryParams(params);
        }
        if (body != null) {
            requestSpecification.body(body);
        }
    }

    protected RequestSpecification buildRequestSpecification() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAccept(ContentType.JSON).setContentType(ContentType.JSON);
        if (cookies != null) {
            requestSpecBuilder.addCookies(cookies);
        }
        if (headers != null) {
            requestSpecBuilder.addHeaders(headers);
        }

        if (contentDefaultEnum.equals(ContentDefaultEnum.X_WWW_FORM_URLENCODED)) {
            requestSpecBuilder.setAccept(ContentType.JSON).setContentType("application/x-www-form-urlencoded");
        }

        return buildRequestSpecification(requestSpecBuilder);
    }

    protected RequestSpecification buildRequestSpecification(RequestSpecBuilder requestSpecBuilder) {
        return given(requestSpecBuilder.setBaseUri(url).
                log(LogDetail.ALL).addFilter(new AllureRestAssured()).
                addFilter(new ResponseLoggingFilter()).build());
    }
}