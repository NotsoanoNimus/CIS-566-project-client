package xyz.xmit.silverclient.api;

import xyz.xmit.silverclient.api.request.*;
import xyz.xmit.silverclient.models.BaseModel;
import xyz.xmit.silverclient.models.HomeScreenData;
import xyz.xmit.silverclient.models.Person;
import xyz.xmit.silverclient.utilities.SilverUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * DESIGN PATTERN: Facade (Structural)
 * <br /><br />
 * This class is designed to hide the complexities of the underlying HttpApiClient class.
 * It does so by providing wrapped and reusable functionalities which would otherwise be
 * hugely redundant in code elsewhere within the client project.
 * <br /><br />
 * All the underlying "raw" API functions which directly send HTTP requests without error
 * handling are handled safely in this function. Functions have clear and specific names
 * which detail what they do, along with thorough method comments.
 */
public final class ApiFacade
{
    /**
     * Send a username and password to the remote server to attempt authentication. This completes
     * the singleton Authentication Context used in the HttpApiClient class and also fails authentication
     * attempts as gracefully as possible. Special note: an API key is provided in response to a
     * successful authentication request and set on the global singleton.
     *
     * @param username The username to authenticate with.
     * @param password The password to authenticate with.
     * @return The API response from the server, containing either a failure or success message.
     */
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

    /**
     * Primary request to load the client dashboard data, usually just for the first time. This loads
     * data for all three tabs into a single HomeScreenData object.
     *
     * @return The deserialized JSON blob received from the server when successfully fetching the dashboard.
     */
    public static WrappedApiResponse<HomeScreenData> loadDashboard()
    {
        try {
            var resp = HttpApiClient.getInstance().RawHttpRequest(
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

    /**
     * Request to unbanned a given person.
     */
    public static void unbanPerson(Person person)
    {
        try {
            var resp = HttpApiClient.getInstance().RawHttpRequest(
                    new GenericGetRequest<>().setHostUrl("unban?personId=" + person.id), String.class);

            if (resp == null || !resp.getSuccess()) {
                throw new Exception("empty or non-success API response for unban");
            }
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem unbanning that user.", "Failed to Unban");
        }
    }

    /**
     * Runs a GET HTTP request against a specified API URI and expects a list of models in return.
     *
     * @param endpoint The host URI to use for fetching the list of models.
     * @param modelClass The class type of the incoming Model.
     * @return A list of models from the specified API endpoint.
     * @param <TModel> The entity type to return. Must be a Model class.
     */
    public static <TModel> WrappedApiResponse<TModel> fetchModelList(String endpoint, Class<TModel> modelClass)
    {
        try {
            var resp = HttpApiClient.getInstance().RawHttpRequest(
                    new GenericGetRequest<>().setHostUrl(endpoint), modelClass);

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for model listing");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem loading data.", "Failed to Fetch");
        }

        return null;
    }

    /**
     * Send a GET request to the API to retrieve a specific model instance. This method exists to handle API
     * failures as gracefully as possible without crashing the client application.
     * @param modelId The ID of the model to fetch.
     * @param blankModelClass The Class of the model as a direct parameter.
     * @param showsAlert Whether to show an alert on failure.
     * @return The API response.
     * @param <TId> The type of ID to use when fetching the model.
     * @param <TModel> The model type to fetch.
     */
    public static <TId, TModel extends BaseModel<TId>> WrappedApiResponse<TModel> safeApiGet(
            TId modelId,
            Class<TModel> blankModelClass,
            boolean showsAlert)
    {
        try {
            var resp = HttpApiClient.getInstance().RawGetRequest(modelId, blankModelClass);

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for dashboard");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            if (showsAlert) {
                SilverUtilities.ShowAlert("There was a problem loading the requested entity.", "Failed to Fetch");
            }
        }

        return null;
    }

    /**
     * Send a request to the API for a particular model type. This method exists to handle API
     * failures as gracefully as possible without crashing the client application.
     * @param method The HTTP method to use for the request (POST, PUT, or DELETE only).
     * @param model The model to use.
     * @param blankModelClass The Class of the model as a direct parameter.
     * @param showsAlert Whether to show an alert on failure.
     * @return The API response.
     * @param <TModel> The model type to use.
     */
    public static <TModel extends BaseModel<?>> WrappedApiResponse<TModel> safeApiRequest(
            String method,
            TModel model,
            Class<TModel> blankModelClass,
            boolean showsAlert)
    {
        try {
            WrappedApiResponse<TModel> resp;

            switch (method) {
                case "POST":
                    resp = HttpApiClient.getInstance().RawPostRequest(model, blankModelClass);
                    break;
                case "PUT":
                    resp = HttpApiClient.getInstance().RawPutRequest(model, blankModelClass);
                    break;
                case "DELETE":
                    resp = HttpApiClient.getInstance().RawDeleteRequest(model, blankModelClass);
                    break;
                default:
                    return null;
            }

            if (resp == null || resp.getData() == null) {
                throw new Exception("null API response for POST request");
            }

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            if (showsAlert) {
                SilverUtilities.ShowAlert("There was a problem contacting the API.", "Failed to Fetch");
            }
        }

        return null;
    }
}
