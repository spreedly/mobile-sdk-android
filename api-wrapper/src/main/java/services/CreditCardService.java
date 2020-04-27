package services;

import classes.*;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;

public class CreditCardService implements SpreedlyClient<CreditCardInfo> {
    private final String username;
    private final String password;
    String baseUrl = "https://core.spreedly.com/v1";
    public SpreedlySecureOpaqueString createString() {
        return null;
    }
    private CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();

    public CreditCardService(String username, String password){
        this.username = username;
        this.password = password;
    }

    public TransactionResult<PaymentMethodResult> tokenize(CreditCardInfo data) {
        PaymentMethodFinal payment = new PaymentMethodFinal(data);
        String credentials = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        Gson gson = new Gson();
        String requestBody = gson.toJson(payment);

        try {
            this.httpClient.start();
            HttpPost request = new HttpPost(baseUrl + "/payment_methods.json");
            request.setEntity(new StringEntity(requestBody));
            request.setHeader("Authorization", credentials);
            request.setHeader("Content-Type", "application/json");
            Future<HttpResponse> future = httpClient.execute(request, null);
            HttpResponse response = future.get();
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            Gson responseGson = new Gson();
            Map<String, Object> transactionResult = responseGson.fromJson(responseBody, Map.class);
            return processMap(transactionResult);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransactionResult<PaymentMethodResult> processMap(Map<String, Object> raw) {
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        Map<String, Object> rawResult = (Map<String, Object>) rawTransaction.get("payment_method");
        CreditCardResult result = new CreditCardResult(
                (String) rawResult.get("token"),
                (String) rawResult.get("storage_state"),
                (boolean) rawResult.get("test"),
                (String) rawResult.get("payment_method_type"),
                (ArrayList) rawResult.get("errors"),
                (String) rawResult.get("last_four_digits"),
                (String) rawResult.get("first_six_digits"),
                (String) rawResult.get("verification_value"),
                (String) rawResult.get("number"),
                rawResult.get("month").toString(),
                rawResult.get("year").toString()
        );
        TransactionResult<PaymentMethodResult> transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.get("token"),
/*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                new Date(),
                new Date(),
                (boolean) rawTransaction.get("succeeded"),
                (String) rawTransaction.get("transaction_type"),
                (boolean) rawTransaction.get("retained"),
                (String) rawTransaction.get("state"),
                (String) rawTransaction.get("messageKey"),
                (String) rawTransaction.get("message"),
                result
        );

        return transactionResult;

    }

    public Future<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return null;
    }
}
