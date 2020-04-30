package com.spreedly.client;

import com.google.gson.Gson;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.CreditCardResult;
import com.spreedly.client.models.transactions.PaymentMethodFinal;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.transactions.Recache;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.results.TransactionResult;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import io.reactivex.rxjava3.core.Single;

public class SpreedlyClientImpl implements SpreedlyClient {
    private final String credentials;
    private final CloseableHttpAsyncClient httpClient;
    private final String baseUrl = "https://core.spreedly.com/v1";

    public SpreedlyClientImpl(String user, String password){
        this.credentials = "Basic " + Base64.getEncoder().encodeToString((user + ":" + password).getBytes());
        this.httpClient = HttpAsyncClients.createDefault();
    }
    @Override
    public SpreedlySecureOpaqueString createString() {
        return null;
    }

    @Override
    public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod(CreditCardInfo info) {
        return Single.fromCallable(() -> {
            PaymentMethodFinal paymentMethod = new PaymentMethodFinal(info);
            Gson gson = new Gson();
            String requestBody = gson.toJson(paymentMethod);
            String url = "/payment_methods.json";
            Map<String, Object> transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public Single<TransactionResult<PaymentMethodResult>> createBankPaymentMethod(BankAccountInfo info) {
        return Single.fromCallable(() -> {
            PaymentMethodFinal paymentMethod = new PaymentMethodFinal(info);
            Gson gson = new Gson();
            String requestBody = gson.toJson(paymentMethod);
            String url = "/payment_methods.json";
            Map<String, Object> transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processBAMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return Single.fromCallable(() -> {
            Recache recache = new Recache(cvv);
            Gson gson = new Gson();
            String requestBody = gson.toJson(recache);
            String url = "/payment_methods/" + token + "/recache.json";
            Map<String, Object> transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public void close() throws IOException {

    }


    TransactionResult<PaymentMethodResult> processCCMap(Map<String, Object> raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        if (rawTransaction == null) {
            return processErrors(raw);
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
                                        /*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                    new Date(),
                    new Date(),
                    // TODO parse dates
                    (String) rawResult.getOrDefault("email", ""),
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
                // Todo: parse dates
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

    TransactionResult<PaymentMethodResult> processBAMap(Map<String, Object> raw){
        TransactionResult<PaymentMethodResult> transactionResult = null;
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        if (rawTransaction == null) {
            return processErrors(raw);
        }
        Map<String, Object> rawResult = (Map<String, Object>) rawTransaction.get("payment_method");
        BankAccountResult result = null;
        if (rawResult != null) {
            result = new BankAccountResult(
                    (String) rawResult.getOrDefault("token", ""),
                    (String) rawResult.getOrDefault("storage_state", ""),
                    (boolean) rawResult.getOrDefault("test", true),
                    (String) rawResult.getOrDefault("payment_method_type", ""),
                    /*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                    new Date(),
                    new Date(),
                    // TODO parse dates
                    (String) rawResult.getOrDefault("email", ""),
                    (ArrayList) rawResult.getOrDefault("errors", null),
                    (String) rawResult.getOrDefault("bank_name", ""),
                    (String) rawResult.getOrDefault("account_type", ""),
                    (String) rawResult.getOrDefault("account_holder_type", ""),
                    (String) rawResult.getOrDefault("routing_number_display_digits", ""),
                    (String) rawResult.getOrDefault("account_number_display_digits", ""),
                    (String) rawResult.getOrDefault("routing_number", ""),
                    (String) rawResult.getOrDefault("account_number", ""),
                    (String) rawResult.getOrDefault("first_name", ""),
                    (String) rawResult.getOrDefault("last_name", ""),
                    (String) rawResult.getOrDefault("full_name", "")
            );
        }
        transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.get("token"),
/*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                new Date(),
                new Date(),
                // TODO parse dates
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

    private Map sendRequest(String requestBody, String url) throws IOException, ExecutionException, InterruptedException {
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
    private TransactionResult<PaymentMethodResult> processErrors(Map<String, Object> raw){
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

}
