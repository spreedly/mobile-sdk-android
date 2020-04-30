import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.spreedly.client.SpreedlyClientImpl;
import com.spreedly.client.models.CreditCardInfo;
import com.spreedly.client.models.results.PaymentMethodResult;
import com.spreedly.client.models.results.SpreedlyError;
import com.spreedly.client.models.SpreedlySecureOpaqueString;
import com.spreedly.client.models.results.TransactionResult;
import com.spreedly.client.SpreedlyClient;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CreditCardServiceTest {
    SpreedlyClient client = null;
    TransactionResult<PaymentMethodResult> result = null;
    CreditCardInfo cc = null;
    CreditCardInfo badCC = null;
    private TransactionResult<PaymentMethodResult> badResult;
    TransactionResult<PaymentMethodResult> recacheResult;

    @Before
    public void initialize(){
        client =  new SpreedlyClientImpl(TestCredentials.getUser(), TestCredentials.getPassword());
        cc = new CreditCardInfo();
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        cc.retained = true;
        client.createCreditCardPaymentMethod(cc).subscribe((res) -> result = res).dispose();
        badCC = new CreditCardInfo();
        badCC.number = "5555555555554444";
        badCC.cvv = "432";
        badCC.month = "3";
        client.createCreditCardPaymentMethod(badCC).subscribe((res) -> badResult = res).dispose();
        client.recache(result.result.token, new SpreedlySecureOpaqueString("423")).subscribe((res) -> recacheResult = res).dispose();

    }

    @After
    public void stop() throws IOException {
        client.close();
    }

    //Credit Card Tokenization Tests
    @Test
    public void TokenizeSucceeds(){
        assertTrue(result.succeeded);
    }

    @Test
    public void TokenizeHasToken(){
        assertNotNull(result.token);
    }

    @Test
    public void TokenHasPaymentResult() {
        assertNotNull(result.result);
    }
    @Test
    public void TokenizeHasPaymentToken(){
        assertNotNull(result.result.token);
    }

    @Test
    public void isCreditCard(){
        assertEquals("credit_card", result.result.paymentMethodType);
    }

    @Test
    public void badCreditCardFails(){
        assertFalse(badResult.succeeded);
    }

    @Test
    public void badCreditCardHasErrors(){
        assertTrue(badResult.errors.size() > 0);
    }

    @Test
    public void badCreditCardIsMissingYear(){
        ArrayList<SpreedlyError> errors = badResult.errors;
        SpreedlyError yearError = errors.get(0);
        assertEquals("year", yearError.attribute);
    }

    //Credit Card Recache Tests
    @Test
    public void RecacheSucceeds() {
        assertTrue(recacheResult.succeeded);
    }
}
