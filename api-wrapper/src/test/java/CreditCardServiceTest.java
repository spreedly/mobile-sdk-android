import classes.*;
import org.junit.Before;
import org.junit.Test;
import services.CreditCardService;
import services.SpreedlyClient;

import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

public class CreditCardServiceTest {
    SpreedlyClient<CreditCardInfo> client = null;
    TransactionResult<PaymentMethodResult> result = null;
    CreditCardInfo cc = null;
    @Before
    public void initialize(){
        client =  new CreditCardService();
        cc = new CreditCardInfo();
    }
    @Test
    public void TokenizeSucceeds(){
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        result = client.tokenize(cc);
        assertTrue(result.isSucceeded());
    }

    @Test
    public void TokenizeHasToken(){
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        result = client.tokenize(cc);
        assertNotNull(result.getToken());
    }

    @Test
    public void TokenizeHasPaymentToken(){
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        result = client.tokenize(cc);
        assertNotNull(result.getResult().getToken());
    }


}
