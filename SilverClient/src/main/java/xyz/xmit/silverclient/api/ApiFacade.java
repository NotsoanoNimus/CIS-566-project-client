package xyz.xmit.silverclient.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.xmit.silverclient.api.request.BaseApiRequest;
import xyz.xmit.silverclient.api.request.GenericGetRequest;
import xyz.xmit.silverclient.api.request.GenericPostRequest;
import xyz.xmit.silverclient.models.BaseModel;
import xyz.xmit.silverclient.models.HomeScreenData;
import xyz.xmit.silverclient.utilities.SilverUtilities;

public final class ApiFacade
{
    public static WrappedApiResponse<String> login(String username, String password)
    {
        var apiInstance = HttpApiClient.getInstance();
        String apiKey;
        WrappedApiResponse<String> responseObject;

        try {
            var connection = HttpConnectionFactory.CreateSecure(
                    new GenericGetRequest().setHostUrl("delegate"),
                    new ApiAuthenticationContext(username, password));

            responseObject = apiInstance.readWrappedApiResponseOrDie(connection, String.class);

            if (!responseObject.getSuccess()) {
                return responseObject;
            }
        } catch (Exception ex) {
            System.out.println("Failed to establish an authentication request.");
            ex.printStackTrace();

            return new WrappedApiResponse<>(false, "An error occurred while trying to authenticate.");
        }

        apiKey = responseObject.getData();

        apiInstance.setAuthenticationContext(new ApiAuthenticationContext(username, password, apiKey));
        System.out.println("Logged in as: " + username);

        return new WrappedApiResponse<>(true, "Success!");
    }

    public static WrappedApiResponse<HomeScreenData> loadDashboard()
    {
        try {
            var resp = HttpApiClient.getInstance().GetAsync(
                    new GenericGetRequest<>().setHostUrl("dashboard"), HomeScreenData.class);

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for dashboard");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem loading data for the dashboard.", "Failed to Fetch", true);
        }

        return null;
    }

    public static <TModel extends BaseModel<?>> WrappedApiResponse<TModel> handleApiPost(
            TModel model,
            Class<TModel> blankModelClass)
    {
        try {
            var resp = HttpApiClient.getInstance().PostAsync(model, blankModelClass);

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for POST request");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem contacting the API.", "Failed to Fetch");
        }

        return null;
    }

    public static <TModel extends BaseModel<?>> WrappedApiResponse<TModel> handleApiPut(
            TModel model,
            Class<TModel> blankModelClass)
    {
        try {
            var resp = HttpApiClient.getInstance().PutAsync(model, blankModelClass);

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for POST request");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem contacting the API.", "Failed to Fetch");
        }

        return null;
    }

    public static <TModel extends BaseModel<?>> WrappedApiResponse<TModel> handleApiDelete(
            TModel model,
            Class<TModel> blankModelClass)
    {
        try {
            var resp = HttpApiClient.getInstance().PutAsync(model, blankModelClass);

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for POST request");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem contacting the API.", "Failed to Fetch");
        }

        return null;
    }
}
