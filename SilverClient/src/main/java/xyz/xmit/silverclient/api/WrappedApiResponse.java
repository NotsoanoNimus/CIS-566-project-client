package xyz.xmit.silverclient.api;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public final class WrappedApiResponse<TData>
{
    public static class EncapsulatedMessageApiResponse
    {
        private String message;

        public EncapsulatedMessageApiResponse(String message)
        {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private boolean success = false;

    private TData data = null;

    private Class<TData> dataType;

    private EncapsulatedMessageApiResponse encapsulatedMessageApiResponse = null;

    public WrappedApiResponse() {}

    public WrappedApiResponse(String jsonResponseBody, Class<TData> clazz) throws IOException
    {
        var mapper = new ObjectMapper();
        var targetType = mapper.getTypeFactory().constructParametricType(WrappedApiResponse.class, clazz);
        var thisObject = mapper.<WrappedApiResponse<TData>>readValue(jsonResponseBody.getBytes(), targetType);

        this.success = thisObject.success;
        this.data = thisObject.data;
    }

    public WrappedApiResponse(String jsonResponseBody, Class<TData> clazz, boolean altIsSuccessful, String altText)
    {
        try {
            var mapper = new ObjectMapper();
            var targetType = mapper.getTypeFactory().constructParametricType(WrappedApiResponse.class, clazz);
            var thisObject = mapper.<WrappedApiResponse<TData>>readValue(jsonResponseBody.getBytes(), targetType);

            this.success = thisObject.success;
            this.data = thisObject.data;
        } catch (Exception ex) {
            // Now try parsing it as an encapsulated "message" response, which Laravel loes to emit.
            try {
                var mapper = new ObjectMapper();
                var thisObject = mapper.readValue(jsonResponseBody.getBytes(), EncapsulatedMessageApiResponse.class);

                this.success = false;
                this.encapsulatedMessageApiResponse = thisObject;
            } catch (Exception innerException) {
                System.out.println("Problem parsing generic, wrapped API response. Using alternate values.");
                innerException.printStackTrace();

                this.success = altIsSuccessful;
                this.encapsulatedMessageApiResponse = new EncapsulatedMessageApiResponse(altText);
            }
        }
    }

    public WrappedApiResponse(boolean isSuccess, TData data) {
        this.success = isSuccess;
        this.data = data;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public TData getData() {
        return this.data;
    }

    public void setData(TData data) {
        this.data = data;
    }

    public EncapsulatedMessageApiResponse getEncapsulatedMessageApiResponse() {
        return this.encapsulatedMessageApiResponse;
    }
}
