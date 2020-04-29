package spreedlyclient.services.utils;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import spreedlyclient.classes.PaymentMethodResult;
import spreedlyclient.classes.SpreedlyError;
import spreedlyclient.classes.TransactionResult;

public class RequestHelper {
    private final String credentials;
    private final CloseableHttpAsyncClient httpClient;
    String baseUrl = "https://core.spreedly.com/v1";

    public RequestHelper(String username, String password){
        this.credentials = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        this.httpClient = HttpAsyncClients.createDefault();
    }
    public Map sendRequest(String requestBody, String url) throws IOException, ExecutionException, InterruptedException {
        httpClient.start();
        HttpPost request = new HttpPost(baseUrl + url);
        request.setEntity(new StringEntity(requestBody));
        request.setHeader("Authorization", credentials);
        request.setHeader("Content-Type", "application/json");
        Future<HttpResponse> future = httpClient.execute(request, null);
        HttpResponse response = future.get();
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        Gson responseGson = new Gson();
        return responseGson.fromJson(responseBody, Map.class);

    }
    public TransactionResult<PaymentMethodResult> processErrors(Map<String, Object> raw){
        ArrayList<Map<String, String>> errors = (ArrayList) raw.get("errors");
        ArrayList<SpreedlyError> spreedlyErrors = new ArrayList<SpreedlyError>();
        for (Map<String, String> error :
                errors
        ) {
            spreedlyErrors.add(new SpreedlyError(error.getOrDefault("attribute", ""), error.getOrDefault("key", ""), error.getOrDefault("message", "")));
        }
        TransactionResult<PaymentMethodResult> result = new TransactionResult<PaymentMethodResult>(spreedlyErrors);
        return result;
    }

    public void close() throws IOException {
        this.httpClient.close();
    }
}
