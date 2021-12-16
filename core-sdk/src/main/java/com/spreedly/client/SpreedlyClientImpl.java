package com.spreedly.client;

import com.spreedly.client.models.ApplePayInfo;
import com.spreedly.client.models.BankAccountInfo;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.GooglePayInfo;
import com.spreedly.client.models.PaymentMethodInfo;
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
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

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

class SpreedlyClientImpl implements SpreedlyClient, Serializable {
    @NonNull
    private final boolean test;
    @NonNull
    private final String key;
    @Nullable
    private final String secret;
    @Nullable
    private String platformData;

    private final String authenticatedURL = "/payment_methods.json";
    private final String unauthenticatedURL = "/payment_methods/restricted.json";

    SpreedlyClientImpl(@NonNull String key, @Nullable String secret, boolean test) {
        this.key = key;
        this.secret = secret;
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
    public Single<TransactionResult<CreditCardResult>> createCreditCardPaymentMethod(@NonNull CreditCardInfo info) {
        boolean authenticated = shouldDoAuthenticatedRequest(info);
        String url = authenticated ? authenticatedURL : unauthenticatedURL;
        return sendRequest(info.toJson(), url, authenticated).map(this::processCCMap);
    }

    private boolean shouldDoAuthenticatedRequest(@NonNull PaymentMethodInfo info) {
        return info.retained != null && info.retained && secret != null;
    }

    @Override
    @NonNull
    public Single<TransactionResult<BankAccountResult>> createBankPaymentMethod(@NonNull BankAccountInfo info) {
        boolean authenticated = shouldDoAuthenticatedRequest(info);
        String url = authenticated ? authenticatedURL : unauthenticatedURL;
        return sendRequest(info.toJson(), url, authenticated).map(this::processBAMap);
    }

    @Override
    public @NonNull Single<TransactionResult<PaymentMethodResult>> createGooglePaymentMethod(@NonNull GooglePayInfo info) {
        boolean authenticated = shouldDoAuthenticatedRequest(info);
        String url = authenticated ? authenticatedURL : unauthenticatedURL;
        return sendRequest(info.toJson(), url, authenticated).map(this::processCCMap);
    }

    @Override
    public @NonNull Single<TransactionResult<PaymentMethodResult>> createApplePaymentMethod(@NonNull ApplePayInfo info) {
        boolean authenticated = shouldDoAuthenticatedRequest(info);
        String url = authenticated ? authenticatedURL : unauthenticatedURL;
        return sendRequest(info.toJson(), url, authenticated).map(this::processCCMap);
    }

    @Override
    @NonNull
    public Single<TransactionResult<PaymentMethodResult>> recache(@NonNull String token, @NonNull SpreedlySecureOpaqueString cvv) {
        return sendRequest(new RecacheInfo(cvv).toJson(), "/payment_methods/" + token + "/recache.json", true).map(this::processCCMap);
    }

    @NonNull <T extends PaymentMethodResult> TransactionResult<T> processCCMap(@NonNull JSONObject raw) {
        TransactionResult<T> transactionResult;
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
                    rawResult.optBoolean("test", test),
                    rawResult.optString("payment_method_type"),
                    processErrors(rawResult.optJSONArray("errors")),
                    parseDate(rawTransaction.optString("created_at")),
                    parseDate(rawTransaction.optString("updated_at")),
                    rawResult.optString("email"),
                    rawResult.optString("last_four_digits"),
                    rawResult.optString("first_six_digits"),
                    rawResult.optString("verification_value"),
                    rawResult.optString("card_type"),
                    rawResult.optString("number"),
                    rawResult.optString("month"),
                    rawResult.optString("year")
            );
        }
        transactionResult = new TransactionResult<T>(
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
                (T) result
        );
        return transactionResult;
    }

    @NonNull TransactionResult<BankAccountResult> processBAMap(@NonNull JSONObject raw) {
        TransactionResult<BankAccountResult> transactionResult;
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
    private Single<JSONObject> sendRequest(@NonNull JSONObject requestBody, @NonNull String url, boolean authenticated) {
        String baseUrl = "https://core.spreedly.com/v1";
        Request.Builder builder = new Request.Builder().url(baseUrl + url);
        if (!authenticated) {
            requestBody.put("environment_key", key);
        } else {
            builder.header("Authorization", getCredentials());
        }
        requestBody.put("platform-meta", getPlatformData());
        Call call = new OkHttpClient().newCall(builder
                .method("POST", RequestBody.create(requestBody.toString(), MediaType.parse("application/json")))
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
        if (dateString == null) {
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

    @Nullable
    public String getCredentials() {
        String raw = key + ":" + secret;
        return "Basic " + safeBase64(raw.getBytes());
    }

    @NonNull
    public String getPlatformData() {
        if (platformData != null)
            return platformData;

        final JSONObject data = new JSONObject();

        data.put("core-version", BuildInfo.VERSION);
        data.put("platform", "java");

        Properties properties = System.getProperties();
        setFromProperty(data, properties, "locale", "user.locale");

        JSONObject os = new JSONObject();
        setFromProperty(os, properties, "name", "os.name");
        setFromProperty(os, properties, "arch", "os.arch");
        setFromProperty(os, properties, "version", "os.version");
        data.put("os", os);

        JSONObject git = new JSONObject();
        git.put("branch", BuildInfo.GIT_BRANCH);
        git.put("tag", BuildInfo.GIT_TAG);
        git.put("commit", BuildInfo.GIT_COMMIT);
        data.put("git", git);

        try {
            Class Build = Class.forName("android.os.Build");
            JSONObject device = new JSONObject();
            device.put("model", Build.getField("MODEL").get(null));
            device.put("manufacturer", Build.getField("MANUFACTURER").get(null));
            device.put("device", Build.getField("DEVICE").get(null));
            device.put("brand", Build.getField("BRAND").get(null));
            data.put("device", device);
        } catch (Exception e) {
        }

        final byte[] bytes = StandardCharsets.UTF_8.encode(data.toString()).array();
        return safeBase64(bytes);
    }

    public void setFromProperty(JSONObject data, Properties properties, String jskey, String propkey) {
        try {
            data.put(jskey, properties.getProperty(propkey, "unknown"));
        } catch (Exception e) {
        }
    }

    public void setPlatformData(@Nullable String data) {
        platformData = data;
    }
}
