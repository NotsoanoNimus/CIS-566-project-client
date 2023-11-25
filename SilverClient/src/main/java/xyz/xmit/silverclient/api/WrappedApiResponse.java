package xyz.xmit.silverclient.api;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.xmit.silverclient.models.BaseModelTimestamps;

import java.io.IOException;

public final class WrappedApiResponse
{
    private static class EncapsulatedMessageApiResponse
    {
        private String message;

        public String getMessage() {
            return this.message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    private boolean success = false;

    private String data = null;

    public WrappedApiResponse() {}

    public WrappedApiResponse(String jsonResponseBody)
            throws DatabindException, StreamReadException, IOException
    {
        var mapper = new ObjectMapper();

        var thisObject = mapper.readValue(jsonResponseBody.getBytes(), WrappedApiResponse.class);
        this.success = thisObject.success;
        this.data = thisObject.data;
    }

    public WrappedApiResponse(String jsonResponseBody, boolean altIsSuccessful, String altText)
    {
        try {
            var mapper = new ObjectMapper();
            var thisObject = mapper.readValue(jsonResponseBody.getBytes(), WrappedApiResponse.class);

            this.success = thisObject.success;
            this.data = thisObject.data;
        } catch (Exception ex) {
            // Now try parsing it as an encapsulated "message" response, which Laravel loes to emit.
            try {
                var mapper = new ObjectMapper();
                var thisObject = mapper.readValue(jsonResponseBody.getBytes(), EncapsulatedMessageApiResponse.class);

                this.success = false;
                this.data = thisObject.getMessage();
            } catch (Exception innerException) {
                System.out.println("Problem parsing generic, wrapped API response. Using alternate values.");
                innerException.printStackTrace();

                this.success = altIsSuccessful;
                this.data = altText;
            }
        }
    }

    public WrappedApiResponse(boolean isSuccess, String data) {
        this.success = isSuccess;
        this.data = data;
    }

    public <T extends BaseModelTimestamps<?>> T unwrapData() {
        // Deserialize 'data' JSON
        return null;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
