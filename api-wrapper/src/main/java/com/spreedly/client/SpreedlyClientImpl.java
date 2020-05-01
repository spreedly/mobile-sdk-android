package com.spreedly.client;

import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.results.CreditCardResult;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.client.models.transactions.PaymentMethodFinal;
import com.spreedly.client.models.transactions.Recache;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.xml.bind.DatatypeConverter;

import io.reactivex.rxjava3.core.Single;

public class SpreedlyClientImpl implements SpreedlyClient {
    private final String credentials;
    private final CloseableHttpAsyncClient httpClient;
    private final String baseUrl = "https://core.spreedly.com/v1";

    public SpreedlyClientImpl(String user, String password) {
        this.credentials = "Basic " + DatatypeConverter.printBase64Binary((user + ":" + password).getBytes());
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
            String requestBody = new JSONObject(paymentMethod).toString();
            String url = "/payment_methods.json";
            JSONObject transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public Single<TransactionResult<PaymentMethodResult>> createBankPaymentMethod(BankAccountInfo info) {
        return Single.fromCallable(() -> {
            PaymentMethodFinal paymentMethod = new PaymentMethodFinal(info);
            String requestBody = new JSONObject(paymentMethod).toString();
            String url = "/payment_methods.json";
            JSONObject transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processBAMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return Single.fromCallable(() -> {
            Recache recache = new Recache(cvv);
            String requestBody = new JSONObject(recache).toString();
            String url = "/payment_methods/" + token + "/recache.json";
            JSONObject transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public void close() throws IOException {

    }


    TransactionResult<PaymentMethodResult> processCCMap(JSONObject raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        JSONObject rawTransaction = raw.getJSONObject("transaction");
        if (rawTransaction == null) {
            return processErrors(raw);
        }
        JSONObject rawResult = rawTransaction.getJSONObject("payment_method");
        CreditCardResult result = null;
        if (rawResult != null) {
            result = new CreditCardResult(
                    rawResult.optString("token"),
                    rawResult.optString("storage_state"),
                    rawResult.optBoolean("test", true),
                    (String) rawResult.get("payment_method_type"),
                    (ArrayList) rawResult.get("errors"),
                                        /*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                    new Date(),
                    new Date(),
                    // TODO parse dates
                    (String) rawResult.get("email"),
                    (String) rawResult.get("last_four_digits"),
                    (String) rawResult.get("first_six_digits"),
                    (String) rawResult.get("verification_value"),
                    (String) rawResult.get("number"),
                    rawResult.get("month").toString(),
                    rawResult.get("year").toString()
            );
        }
        transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.get("token"),
/*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                // Todo: parse dates
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

    TransactionResult<PaymentMethodResult> processBAMap(JSONObject raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        if (rawTransaction == null) {
            return processErrors(raw);
        }
        Map<String, Object> rawResult = (Map<String, Object>) rawTransaction.get("payment_method");
        BankAccountResult result = null;
        if (rawResult != null) {
            result = new BankAccountResult(
                    (String) rawResult.get("token"),
                    (String) rawResult.get("storage_state"),
                    (boolean) rawResult.get("test"),
                    (String) rawResult.get("payment_method_type"),
                    /*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                    new Date(),
                    new Date(),
                    // TODO parse dates
                    (String) rawResult.get("email"),
                    (ArrayList) rawResult.get("errors"),
                    (String) rawResult.get("bank_name"),
                    (String) rawResult.get("account_type"),
                    (String) rawResult.get("account_holder_type"),
                    (String) rawResult.get("routing_number_display_digits"),
                    (String) rawResult.get("account_number_display_digits"),
                    (String) rawResult.get("routing_number"),
                    (String) rawResult.get("account_number"),
                    (String) rawResult.get("first_name"),
                    (String) rawResult.get("last_name"),
                    (String) rawResult.get("full_name")
            );
        }
        transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.get("token"),
/*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                new Date(),
                new Date(),
                // TODO parse dates
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

    private JSONObject sendRequest(String requestBody, String url) throws IOException, ExecutionException, InterruptedException {
        httpClient.start();
        HttpPost request = new HttpPost(baseUrl + url);
        request.setEntity(new StringEntity(requestBody));
        request.setHeader("Authorization", credentials);
        request.setHeader("Content-Type", "application/json");
        Future<HttpResponse> future = httpClient.execute(request, null);
        HttpResponse response = future.get();
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        return new JSONObject(requestBody);

    }

    private TransactionResult<PaymentMethodResult> processErrors(JSONObject raw) {
        ArrayList<Map<String, String>> errors = (ArrayList) raw.get("errors");
        ArrayList<SpreedlyError> spreedlyErrors = new ArrayList<SpreedlyError>();
        for (Map<String, String> error :
                errors
        ) {
            spreedlyErrors.add(new SpreedlyError(error.get("attribute"), error.get("key"), error.get("message")));
        }
        TransactionResult<PaymentMethodResult> result = new TransactionResult<PaymentMethodResult>(spreedlyErrors);
        return result;
    }

}
