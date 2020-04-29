import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import spreedlyCleint.classes.CreditCardInfo;
import spreedlyCleint.classes.PaymentMethodResult;
import spreedlyCleint.classes.SpreedlyError;
import spreedlyCleint.classes.SpreedlySecureOpaqueString;
import spreedlyCleint.classes.TransactionResult;
import spreedlyClient.services.CreditCardService;
import spreedlyClient.services.SpreedlyClient;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreditCardServiceTest {
    SpreedlyClient<CreditCardInfo> client = null;
    TransactionResult<PaymentMethodResult> result = null;
    CreditCardInfo cc = null;
    CreditCardInfo badCC = null;
    private TransactionResult<PaymentMethodResult> badResult;
    TransactionResult<PaymentMethodResult> recacheResult;

    @Before
    public void initialize(){
        client =  new CreditCardService(TestCredentials.getUser(), TestCredentials.getPassword());
        cc = new CreditCardInfo();
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        cc.retained = true;
        client.tokenize(cc).subscribe((res) -> result = res);
        badCC = new CreditCardInfo();
        badCC.number = "5555555555554444";
        badCC.cvv = "432";
        badCC.month = "3";
        client.tokenize(badCC).subscribe((res) -> badResult = res);
        client.recache(result.getResult().getToken(), new SpreedlySecureOpaqueString("423")).subscribe((res) -> recacheResult = res);

    }

    @After
    public void stop() throws IOException {
        client.stop();
    }

    //Credit Card Tokenization Tests
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

    @Test
    public void badCreditCardFails(){
        assertFalse(badResult.isSucceeded());
    }

    @Test
    public void badCreditCardHasErrors(){
        assertTrue(badResult.getErrors().size() > 0);
    }

    @Test
    public void badCreditCardIsMissingYear(){
        ArrayList<SpreedlyError> errors = badResult.getErrors();
        SpreedlyError yearError = errors.get(0);
        assertEquals("year", yearError.getAttribute());
    }

    //Credit Card Recache Tests
    @Test
    public void RecacheSucceeds() {
        assertTrue(recacheResult.isSucceeded());
    }
}
