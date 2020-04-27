import classes.*;
import org.junit.Before;
import org.junit.Test;
import services.CreditCardService;
import services.SpreedlyClient;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreditCardServiceTest {
    SpreedlyClient<CreditCardInfo> client = new CreditCardService();

    @Before

    @Test
    public void TokenizeSucceeds(){
        CreditCardInfo cc = new CreditCardInfo();
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        TransactionResult<PaymentMethodResult> result = client.tokenize(cc);
        assertTrue(result.isSucceeded());
    }


}
