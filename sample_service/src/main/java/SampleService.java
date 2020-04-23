import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class SampleService {
    public static final String username = "xxx"; // TODO: update user and password with env variables
    public static final String password = "xxxx";
    public static final String base = "https://core.spreedly.com/v1";

    public static String getGateway() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        Gson gatewayRequestGson = new Gson();
        String gatewayRequest = gatewayRequestGson.toJson(new GatewayRequest("test"));
        RequestBody body = RequestBody.create(mediaType, gatewayRequest);
        String credential = Credentials.basic(username, password);
        Request request = new Request.Builder()
                .url("https://core.spreedly.com/v1/gateways.json")
                .method("POST", body)
                .addHeader("Host", "core.spreedly.com")
                .addHeader("Authorization", credential)
                .addHeader("Content-Type", "application/json")
                .build();
        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();
        Gson gson = new Gson();
        GatewayResponse json = gson.fromJson(responseBody, GatewayResponse.class);
        return json.gateway.token;

    }

    class GatewayResponse {
        GatewayDetails gateway;
        class GatewayDetails {
            String token;
            String[] payment_methods;
        }
    }

    static class GatewayRequest{
        GatewayRequest(String gatewayType){
            this.gateway = new GatewayType();
            this.gateway.gateway_type = gatewayType;
        }
        GatewayType gateway;
        class GatewayType {
            String gateway_type;
        }
    }
}
