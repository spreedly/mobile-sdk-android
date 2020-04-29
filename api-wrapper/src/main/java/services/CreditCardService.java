package services;

import classes.*;
import io.reactivex.rxjava3.core.Single;

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
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;

public class CreditCardService implements SpreedlyClient<CreditCardInfo> {
    String baseUrl = "https://core.spreedly.com/v1";
    private CloseableHttpAsyncClient httpClient = null;
    private String credentials;


    public SpreedlySecureOpaqueString createString() {
        return null;
    }

    public CreditCardService(String username, String password) {
        this.credentials = "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
        this.httpClient = HttpAsyncClients.createDefault();
    }

    public Single<TransactionResult<PaymentMethodResult>> tokenize(CreditCardInfo data) {
        return Single.fromCallable(() -> {
        PaymentMethodFinal payment = new PaymentMethodFinal(data);
        Gson gson = new Gson();
        String requestBody = gson.toJson(payment);
            httpClient.start();
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
            TransactionResult<PaymentMethodResult> finalResults = processMap(transactionResult);
            return finalResults;
        });
    }

    public Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return Single.fromCallable(() -> {
            Recache recache = new Recache(cvv);
            Gson gson = new Gson();
            String requestBody = gson.toJson(recache);
            httpClient.start();
            HttpPost request = new HttpPost(baseUrl + "/payment_methods/" + token + "/recache.json");
            request.setEntity(new StringEntity(requestBody));
            request.setHeader("Authorization", credentials);
            request.setHeader("Content-Type", "application/json");
            Future<HttpResponse> future = httpClient.execute(request, null);
            HttpResponse response = future.get();
            int statusCode = response.getStatusLine().getStatusCode();
            String responseBody = EntityUtils.toString(response.getEntity());
            Gson responseGson = new Gson();
            Map<String, Object> transactionResult = responseGson.fromJson(responseBody, Map.class);
            TransactionResult<PaymentMethodResult> finalResults = processMap(transactionResult);
            return finalResults;
        });
    }

    private TransactionResult<PaymentMethodResult> processMap(Map<String, Object> raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        if (rawTransaction == null) {
            ArrayList<Map<String, String>> errors = (ArrayList) raw.get("errors");
            ArrayList<SpreedlyError> spreedlyErrors = new ArrayList<SpreedlyError>();
            for (Map<String, String> error:
                 errors
            ) {
                spreedlyErrors.add(new SpreedlyError(error.getOrDefault("attribute", ""), error.getOrDefault("key", ""), error.getOrDefault("message", "")));
            }
            transactionResult = new TransactionResult<PaymentMethodResult>(spreedlyErrors);
            return transactionResult;
        }
        Map<String, Object> rawResult = (Map<String, Object>) rawTransaction.get("payment_method");
        CreditCardResult result = null;
        if (rawResult != null) {
            result = new CreditCardResult(
                    (String) rawResult.getOrDefault("token", ""),
                    (String) rawResult.getOrDefault("storage_state", ""),
                    (boolean) rawResult.getOrDefault("test", true),
                    (String) rawResult.getOrDefault("payment_method_type", ""),
                    (ArrayList) rawResult.getOrDefault("errors", null),
                    (String) rawResult.getOrDefault("last_four_digits", ""),
                    (String) rawResult.getOrDefault("first_six_digits", ""),
                    (String) rawResult.getOrDefault("verification_value", ""),
                    (String) rawResult.getOrDefault("number", ""),
                    rawResult.getOrDefault("month", "").toString(),
                    rawResult.getOrDefault("year", "").toString()
            );
        }
        transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.get("token"),
/*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                new Date(),
                new Date(),
                (boolean) rawTransaction.getOrDefault("succeeded", false),
                (String) rawTransaction.getOrDefault("transaction_type", ""),
                (boolean) rawTransaction.getOrDefault("retained", false),
                (String) rawTransaction.getOrDefault("state", ""),
                (String) rawTransaction.getOrDefault("messageKey", ""),
                (String) rawTransaction.getOrDefault("message", ""),
                result
        );
        return transactionResult;
    }

    public void stop() throws IOException {
        httpClient.close();
    }

}
