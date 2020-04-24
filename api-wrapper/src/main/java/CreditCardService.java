import classes.*;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.Future;

public class CreditCardService implements SpreedlyClient<CreditCardInfo> {
    String baseUrl = "https://core.spreedly.com/v1";
    String user = "";
    String password = "";
    public SpreedlySecureOpaqueString createString() {
        return null;
    }

    public Future<TransactionResult<PaymentMethodResult>> tokenize(CreditCardInfo data) {
        PaymentMethodFinal payment = new PaymentMethodFinal(data);
        String credentials = "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
        Gson gson = new Gson();
        String requestBody = gson.toJson(payment);
        CloseableHttpClient httpClient = HttpClientBuilder.create().build(); //Use this instead

        try {
            HttpPost request = new HttpPost(baseUrl + "/payment_methods.json");
            request.setEntity(new StringEntity(requestBody));
            request.setHeader("Authorization", credentials);
            request.setHeader("Content-Type", "application/json");
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            httpClient.close();
            Gson responseGson = new Gson();
            Map<String, Object> transactionResult = responseGson.fromJson(responseBody, Map.class);
            return processMap(transactionResult);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Future<TransactionResult<PaymentMethodResult>> processMap(Map<String, Object> raw) {
        TransactionResult<PaymentMethodResult> transactionResult = new TransactionResult<PaymentMethodResult>();
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        transactionResult.setToken((String) rawTransaction.getOrDefault("token", ""));

        return (Future<TransactionResult<PaymentMethodResult>>) transactionResult;

    }

    public Future<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return null;
    }
}
