package spreedlyclient.services;

import io.reactivex.rxjava3.core.Single;
import spreedlyclient.classes.CreditCardInfo;
import spreedlyclient.classes.CreditCardResult;
import spreedlyclient.classes.PaymentMethodFinal;
import spreedlyclient.classes.PaymentMethodResult;
import spreedlyclient.classes.Recache;
import spreedlyclient.classes.SpreedlySecureOpaqueString;
import spreedlyclient.classes.TransactionResult;
import spreedlyclient.services.utils.RequestHelper;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class CreditCardService implements SpreedlyClient<CreditCardInfo> {

    private final RequestHelper request;

    public SpreedlySecureOpaqueString createString() {
        return null;
    }

    public CreditCardService(String username, String password) {
        this.request = new RequestHelper(username, password);
    }

    public Single<TransactionResult<PaymentMethodResult>> tokenize(CreditCardInfo data) {
        return Single.fromCallable(() -> {
            PaymentMethodFinal paymentMethod = new PaymentMethodFinal(data);
            Gson gson = new Gson();
            String requestBody = gson.toJson(paymentMethod);
            String url = "/payment_methods.json";
            Map<String, Object> transactionResult = this.request.sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    public Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return Single.fromCallable(() -> {
            Recache recache = new Recache(cvv);
            Gson gson = new Gson();
            String requestBody = gson.toJson(recache);
            String url = "/payment_methods/" + token + "/recache.json";
            Map<String, Object> transactionResult = this.request.sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processCCMap(transactionResult);
            return finalResults;
        });
    }

    private TransactionResult<PaymentMethodResult> processCCMap(Map<String, Object> raw) {
        TransactionResult<PaymentMethodResult> transactionResult = null;
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        if (rawTransaction == null) {
            return this.request.processErrors(raw);
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
        this.request.close();
    }
}
