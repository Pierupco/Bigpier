package com.pier.api.web.response;


public class Response<T> {
    private boolean success;
    private T body;
    @SuppressWarnings(value = {"unchecked"})
    public static final Response FAILED_RESPONSE = new Response(false, null);

    public Response(T body) {
        this(true, body);
    }

    public Response(boolean success, T body) {
        this.success = success;
        this.body = body;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static Response<ResponseError> getErrorResponse(final ResponseError error)  {
        return new Response<ResponseError>(false, error);
    }
}
