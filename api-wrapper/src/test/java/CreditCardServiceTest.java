import classes.*;
import org.junit.Before;
import org.junit.Test;
import services.CreditCardService;
import services.SpreedlyClient;

import static org.junit.Assert.*;

public class CreditCardServiceTest {
    SpreedlyClient<CreditCardInfo> client = null;
    TransactionResult<PaymentMethodResult> result = null;
    CreditCardInfo cc = null;
    @Before
    public void initialize(){
        client =  new CreditCardService(secret.getUser(), secret.getPassword());
        cc = new CreditCardInfo();
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        result = client.tokenize(cc);
    }
    @Test
    public void TokenizeSucceeds(){
        assertTrue(result.isSucceeded());
    }

    @Test
    public void TokenizeHasToken(){
        assertNotNull(result.getToken());
    }

    @Test
    public void TokenHasPaymentResult() {
        assertNotNull(result.getResult());
    }
    @Test
    public void TokenizeHasPaymentToken(){
        assertNotNull(result.getResult().getToken());
    }

    @Test
    public void isCreditCard(){
        assertEquals("credit_card", result.getResult().getPaymentMethodType());
    }
}
