package com.spreedly.client;

import com.spreedly.client.models.ApplePayInfo;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.RecacheInfo;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.results.BankAccountResult;
import com.spreedly.client.models.results.CreditCardResult;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.results.TransactionResult;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Single;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

class SpreedlyClientImpl implements SpreedlyClient {
    @NonNull
    private final String credentials;
    @NonNull
    private final OkHttpClient httpClient;
    private final boolean test;

    SpreedlyClientImpl(@NonNull String user, @NonNull String password, boolean test) {
        this.credentials = "Basic " + safeBase64((user + ":" + password).getBytes());
        this.httpClient = new OkHttpClient();
        this.test = test;
    }

    private static String safeBase64(byte[] source) {
        try {
            Class<?> dtc = Class.forName("android.util.Base64");
            return (String) dtc.getMethod("encodeToString", byte[].class, int.class).invoke(null, source, 2);
        } catch (ReflectiveOperationException e) {
            try {
                Class<?> dtc = Class.forName("javax.xml.bind.DatatypeConverter");
                return (String) dtc.getMethod("printBase64Binary", byte[].class).invoke(null, new Object[]{
                        source
                });
            } catch (ReflectiveOperationException e2) {
                return "";
            }
        }
    }

    @Override
    @NonNull
    public SpreedlySecureOpaqueString createString(@NonNull String string) {
        SpreedlySecureOpaqueString spreedlySecureOpaqueString = new SpreedlySecureOpaqueString();
        spreedlySecureOpaqueString.append(string);
        return spreedlySecureOpaqueString;
    }

    @Override
    @NonNull
    public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod(@NonNull CreditCardInfo info, @Nullable String email, @Nullable JSONObject metadata) {
        return sendRequest(info.toJson(email, metadata), "/payment_methods.json").map(this::processCCMap);
    }

    @Override
    @NonNull
    public Single<TransactionResult<PaymentMethodResult>> createBankPaymentMethod(@NonNull BankAccountInfo info, @Nullable String email, @Nullable JSONObject metadata) {
        return sendRequest(info.toJson(email, metadata), "/payment_methods.json").map(this::processBAMap);
    }

    @Override
    public @NonNull Single<TransactionResult<PaymentMethodResult>> createGooglePaymentMethod(@NonNull GooglePayInfo info, @Nullable String email, @Nullable JSONObject metadata) {
        return sendRequest(info.toJson(email, metadata), "/payment_methods.json").map(this::processCCMap);
    }

    @Override
    public @NonNull Single<TransactionResult<PaymentMethodResult>> createApplePaymentMethod(@NonNull ApplePayInfo info, @Nullable String email, @Nullable JSONObject metadata) {
        return sendRequest(info.toJson(email, metadata), "/payment_methods.json").map(this::processCCMap);
    }

    @Override
    @NonNull
    public Single<TransactionResult<PaymentMethodResult>> recache(@NonNull String token, @NonNull SpreedlySecureOpaqueString cvv) {
        return sendRequest(new RecacheInfo(cvv).json, "/payment_methods/" + token + "/recache.json").map(this::processCCMap);
    }

    @NonNull TransactionResult<PaymentMethodResult> processCCMap(@NonNull JSONObject raw) {
        TransactionResult<PaymentMethodResult> transactionResult;
        JSONObject rawTransaction = raw.optJSONObject("transaction");
        if (rawTransaction == null) {
            rawTransaction = new JSONObject();
        }
        JSONObject rawResult = rawTransaction.optJSONObject("payment_method");
        CreditCardResult result = null;
        if (rawResult != null) {
            result = new CreditCardResult(
                    rawResult.optString("token"),
                    rawResult.optString("storage_state"),
                    rawResult.optBoolean("test", true),
                    rawResult.optString("payment_method_type"),
                    processErrors(rawResult.optJSONArray("errors")),
                    parseDate(rawTransaction.optString("created_at")),
                    parseDate(rawTransaction.optString("updated_at")),
                    rawResult.optString("email"),
                    rawResult.optString("last_four_digits"),
                    rawResult.optString("first_six_digits"),
                    rawResult.optString("verification_value"),
                    rawResult.optString("number"),
                    rawResult.optString("month"),
                    rawResult.optString("year")
            );
        }
        transactionResult = new TransactionResult<>(
                rawTransaction.optString("token"),
                parseDate(rawTransaction.optString("created_at")),
                parseDate(rawTransaction.optString("updated_at")),
                rawTransaction.optBoolean("succeeded", false),
                rawTransaction.optString("transaction_type"),
                rawTransaction.optBoolean("retained", false),
                rawTransaction.optString("state"),
                rawTransaction.optString("messageKey"),
                rawTransaction.optString("message"),
                processErrors(raw.optJSONArray("errors")),
                result
        );
        return transactionResult;
    }

    @NonNull TransactionResult<PaymentMethodResult> processBAMap(@NonNull JSONObject raw) {
        TransactionResult<PaymentMethodResult> transactionResult;
        JSONObject rawTransaction = raw.optJSONObject("transaction");
        if (rawTransaction == null) {
            rawTransaction = new JSONObject();
        }
        JSONObject rawResult = rawTransaction.optJSONObject("payment_method");
        BankAccountResult result = null;
        if (rawResult != null) {
            result = new BankAccountResult(
                    rawResult.optString("token"),
                    rawResult.optString("storage_state"),
                    rawResult.optBoolean("test", true),
                    rawResult.optString("payment_method_type"),
                    parseDate(rawTransaction.optString("created_at")),
                    parseDate(rawTransaction.optString("updated_at")),
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
        transactionResult = new TransactionResult<>(
                rawTransaction.optString("token"),
                parseDate(rawTransaction.optString("created_at")),
                parseDate(rawTransaction.optString("updated_at")),
                rawTransaction.optBoolean("succeeded", false),
                rawTransaction.optString("transaction_type"),
                rawTransaction.optBoolean("retained", false),
                rawTransaction.optString("state"),
                rawTransaction.optString("messageKey"),
                rawTransaction.optString("message"),
                processErrors(raw.optJSONArray("errors")),
                result
        );
        return transactionResult;
    }

    @NonNull
    private Single<JSONObject> sendRequest(@NonNull JSONObject requestBody, @NonNull String url) {
        String baseUrl = "https://core.spreedly.com/v1";
        final Call call = httpClient.newCall(new Request.Builder()
                .url(baseUrl + url)
                .method("POST", RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
                .header("Authorization", credentials)
                .build());

        return Single.create(emitter -> call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                emitter.onError(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try {
                    emitter.onSuccess(new JSONObject(Objects.requireNonNull(response.body()).string()));
                } catch (NullPointerException npe) {
                    emitter.onError(npe);
                }
            }
        }));
    }

    @Nullable
    private List<SpreedlyError> processErrors(@Nullable JSONArray errors) {
        if (errors == null) {
            return null;
        }
        ArrayList<SpreedlyError> spreedlyErrors = new ArrayList<>();
        for (int i = 0; i < errors.length(); i++) {
            JSONObject json = errors.getJSONObject(i);
            spreedlyErrors.add(new SpreedlyError(json.optString("attribute"), json.optString("key"), json.optString("message")));
        }
        return spreedlyErrors;
    }


    @Nullable Date parseDate(@Nullable String dateString) {
        if (dateString == null){
            return null;
        }
        dateString = dateString.replace('T', ' ');
        dateString = dateString.replace("Z", "+0000");
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ssZ", Locale.US).parse(dateString);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }
}
