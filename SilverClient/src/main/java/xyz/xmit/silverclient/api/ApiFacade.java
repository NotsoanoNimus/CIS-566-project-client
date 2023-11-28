package xyz.xmit.silverclient.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.xmit.silverclient.api.request.GenericGetRequest;
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
            System.out.println(new ObjectMapper().writeValueAsString(resp));

            return resp;
        } catch (Exception ex) {
            ex.printStackTrace();

            SilverUtilities.ShowAlert("There was a problem loading data for the dashboard.", "Failed to Fetch", true);
        }

        return null;
    }
}
