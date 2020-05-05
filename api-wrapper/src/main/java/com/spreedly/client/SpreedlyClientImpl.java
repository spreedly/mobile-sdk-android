package com.spreedly.client;

import com.spreedly.client.models.ApplePayInfo;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.results.CreditCardResult;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.client.models.transactions.Recache;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.xml.bind.DatatypeConverter;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Single;

public class SpreedlyClientImpl implements SpreedlyClient {
    @NonNull final String credentials;
    @NonNull final CloseableHttpAsyncClient httpClient;
    @NonNull final String baseUrl = "https://core.spreedly.com/v1";

    public SpreedlyClientImpl(@NonNull String user, @NonNull String password) {
        this.credentials = "Basic " + DatatypeConverter.printBase64Binary((user + ":" + password).getBytes());
        this.httpClient = HttpAsyncClients.createDefault();
    }

    @Override
    @NonNull public SpreedlySecureOpaqueString createString(@NonNull String string) {
        SpreedlySecureOpaqueString spreedlySecureOpaqueString = new SpreedlySecureOpaqueString();
        spreedlySecureOpaqueString.append(string);
        return spreedlySecureOpaqueString;
    }

    @Override
    @NonNull public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod(@NonNull CreditCardInfo info) {
        return Single.fromCallable(() -> {
            String url = "/payment_methods.json";
            JSONObject transactionResult = sendRequest(info.encode(), url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    @NonNull public Single<TransactionResult<PaymentMethodResult>> createBankPaymentMethod(@NonNull BankAccountInfo info) {
        return Single.fromCallable(() -> {
            String url = "/payment_methods.json";
            JSONObject transactionResult = sendRequest(info.encode(), url);
            TransactionResult<PaymentMethodResult> finalResults = processBAMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public @NonNull Single<TransactionResult<PaymentMethodResult>> createGooglePaymentMethod(@NonNull GooglePayInfo info) {
        return Single.fromCallable(() -> {
            String url = "/payment_methods.json";
            String string = info.encode();
            JSONObject transactionResult = sendRequest(info.encode(), url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public @NonNull Single<TransactionResult<PaymentMethodResult>> createApplePaymentMethod(@NonNull ApplePayInfo info) {
        return Single.fromCallable(() -> {
            String url = "/payment_methods.json";
            JSONObject transactionResult = sendRequest(info.encode(), url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    @NonNull public Single<TransactionResult<PaymentMethodResult>> recache(@NonNull String token, @NonNull SpreedlySecureOpaqueString cvv) {
        return Single.fromCallable(() -> {
            Recache recache = new Recache(cvv);
            String requestBody = new JSONObject(recache).toString();
            String url = "/payment_methods/" + token + "/recache.json";
            JSONObject transactionResult = sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    @NonNull TransactionResult<PaymentMethodResult> processCCMap(@NonNull JSONObject raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        JSONObject rawTransaction = raw.optJSONObject("transaction");
        if (rawTransaction == null) {
            return new TransactionResult<PaymentMethodResult>(processErrors(raw.optJSONArray("errors")));
        }
        JSONObject rawResult = rawTransaction.getJSONObject("payment_method");
        CreditCardResult result = null;
        if (rawResult != null) {
            result = new CreditCardResult(
                    rawResult.optString("token"),
                    rawResult.optString("storage_state"),
                    rawResult.optBoolean("test", true),
                    rawResult.optString("payment_method_type"),
                    processErrors(rawResult.optJSONArray("errors")),
                                        /*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                    new Date(),
                    new Date(),
                    // TODO parse dates
                    rawResult.optString("email"),
                    rawResult.optString("last_four_digits"),
                    rawResult.optString("first_six_digits"),
                    rawResult.optString("verification_value"),
                    rawResult.optString("number"),
                    rawResult.optString("month"),
                    rawResult.optString("year")
            );
        }
        transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.optString("token"),
                // Todo: parse dates
                new Date(),
                new Date(),
                rawTransaction.optBoolean("succeeded", false),
                rawTransaction.optString("transaction_type"),
                rawTransaction.optBoolean("retained", false),
                rawTransaction.optString("state"),
                rawTransaction.optString("messageKey"),
                rawTransaction.optString("message"),
                result
        );
        return transactionResult;
    }

    @NonNull TransactionResult<PaymentMethodResult> processBAMap(@NonNull JSONObject raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        JSONObject rawTransaction =  raw.optJSONObject("transaction");
        if (rawTransaction == null) {
            return new TransactionResult<PaymentMethodResult>(processErrors(raw.optJSONArray("errors")));
        }
        JSONObject rawResult = rawTransaction.optJSONObject("payment_method");
        BankAccountResult result = null;
        if (rawResult != null) {
            result = new BankAccountResult(
                    rawResult.optString("token"),
                    rawResult.optString("storage_state"),
                    rawResult.optBoolean("test", true),
                    rawResult.optString("payment_method_type"),
                    new Date(),
                    new Date(),
                    rawResult.optString("email"),
                    processErrors(rawResult.optJSONArray("errors")),
                    rawResult.optString("bank_name"),
                    rawResult.optString("account_type"),
                    rawResult.optString("account_holder_type"),
                    rawResult.optString("routing_number_display_digits"),
                    rawResult.optString("account_number_display_digits"),
                    rawResult.optString("routing_number"),
                    rawResult.optString("account_number"),
                    rawResult.optString("first_name"),
                    rawResult.optString("last_name"),
                    rawResult.optString("full_name")
            );
        }
        transactionResult = new TransactionResult<PaymentMethodResult>(
                (String) rawTransaction.optString("token"),
/*                (Date) ((String) rawTransaction.get("created_at")),
                (Date) rawTransaction.get("updated_at"),*/
                new Date(),
                new Date(),
                // TODO parse dates
                (boolean) rawTransaction.optBoolean("succeeded", false),
                rawTransaction.optString("transaction_type"),
                (boolean) rawTransaction.optBoolean("retained", false),
                rawTransaction.optString("state"),
                rawTransaction.optString("messageKey"),
                rawTransaction.optString("message"),
                result
        );
        return transactionResult;
    }

    @NonNull private JSONObject sendRequest(@NonNull String requestBody, @NonNull String url) throws IOException, ExecutionException, InterruptedException {
        httpClient.start();
        HttpPost request = new HttpPost(baseUrl + url);
        request.setEntity(new StringEntity(requestBody));
        request.setHeader("Authorization", credentials);
        request.setHeader("Content-Type", "application/json");
        Future<HttpResponse> future = httpClient.execute(request, null);
        HttpResponse response = future.get();
        int statusCode = response.getStatusLine().getStatusCode();
        String responseBody = EntityUtils.toString(response.getEntity());
        return new JSONObject(responseBody);

    }

    @NonNull private ArrayList processErrors(@NonNull JSONArray errors) {
        ArrayList<SpreedlyError> spreedlyErrors = new ArrayList<SpreedlyError>();
        for (Object error :
                errors
        ) {
            JSONObject json = new JSONObject(error.toString());
            spreedlyErrors.add(new SpreedlyError(json.optString("attribute"), json.optString("key"), json.optString("message")));
        }
        return spreedlyErrors;
    }

    @Override
    public void close() throws IOException {
        this.httpClient.close();
    }
}
