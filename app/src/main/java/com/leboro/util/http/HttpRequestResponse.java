package com.leboro.util.http;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class HttpRequestResponse {

    private final int responseStatusCode;

    private final String responseBody;

    public HttpRequestResponse(int responseStatusCode, String responseBody) {
        this.responseStatusCode = responseStatusCode;
        this.responseBody = responseBody;
    }

    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HttpRequestResponse that = (HttpRequestResponse) o;

        return new EqualsBuilder()
                .append(responseStatusCode, that.responseStatusCode)
                .append(responseBody, that.responseBody)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(responseStatusCode)
                .append(responseBody)
                .toHashCode();
    }
}
