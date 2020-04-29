package spreedlyclient.services;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import io.reactivex.rxjava3.core.Single;
import spreedlyclient.classes.BankAccountInfo;
import spreedlyclient.classes.BankAccountResult;
import spreedlyclient.classes.CreditCardResult;
import spreedlyclient.classes.PaymentMethodFinal;
import spreedlyclient.classes.PaymentMethodResult;
import spreedlyclient.classes.SpreedlySecureOpaqueString;
import spreedlyclient.classes.TransactionResult;
import spreedlyclient.services.utils.RequestHelper;

public class BankAccountService implements SpreedlyClient<BankAccountInfo> {
    private final RequestHelper request;

    public BankAccountService(String user, String password) {
        this.request = new RequestHelper(user, password);
    }

    @Override
    public SpreedlySecureOpaqueString createString() {
        return null;
    }

    public Single<TransactionResult<PaymentMethodResult>> tokenize(BankAccountInfo data) {
        return Single.fromCallable(() -> {
            PaymentMethodFinal paymentMethod = new PaymentMethodFinal(data);
            Gson gson = new Gson();
            String requestBody = gson.toJson(paymentMethod);
            String url = "/payment_methods.json";
            Map<String, Object> transactionResult = this.request.sendRequest(requestBody, url);
            TransactionResult<PaymentMethodResult> finalResults = processBAMap(transactionResult);
            return finalResults;
        });
    }

    @Override
    public Single<TransactionResult<PaymentMethodResult>> recache(String token, SpreedlySecureOpaqueString cvv) {
        return null;
    }

    @Override
    public void stop() throws IOException {
        this.request.close();
    }

    private TransactionResult<PaymentMethodResult> processBAMap(Map<String, Object> raw){
        TransactionResult<PaymentMethodResult> transactionResult = null;
        Map<String, Object> rawTransaction = (Map<String, Object>) raw.get("transaction");
        if (rawTransaction == null) {
            return this.request.processErrors(raw);
        }
        Map<String, Object> rawResult = (Map<String, Object>) rawTransaction.get("payment_method");
        BankAccountResult result = null;
        if (rawResult != null) {
            result = new BankAccountResult(
                    (String) rawResult.getOrDefault("token", ""),
                    (String) rawResult.getOrDefault("storage_state", ""),
                    (boolean) rawResult.getOrDefault("test", true),
                    (String) rawResult.getOrDefault("payment_method_type", ""),
                    (ArrayList) rawResult.getOrDefault("errors", null),
                    (String) rawResult.getOrDefault("bank_name", ""),
                    (String) rawResult.getOrDefault("account_type", ""),
                    (String) rawResult.getOrDefault("account_holder_type", ""),
                    (String) rawResult.getOrDefault("routing_number_display_digits", ""),
                    (String) rawResult.getOrDefault("account_number_display_digits", ""),
                    (String) rawResult.getOrDefault("account_holder_type", ""),
                    (String) rawResult.getOrDefault("routing_number", ""),
                    (String) rawResult.getOrDefault("account_number", ""),
                    (String) rawResult.getOrDefault("full_name", "")

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
}
