import classes.CreditCardInfo;
import classes.PaymentMethodResult;
import classes.TransactionResult;
import services.CreditCardService;
import services.SpreedlyClient;

public class Sandbox {
    public static void main(String[] args) {
        System.out.println("Running!");
        Start start = new Start(new CreditCardService());
        start.run();
    }
}

class Start {
    private final SpreedlyClient client;

    public Start(SpreedlyClient client) {
        this.client = client;
    }

    public void run() {
        CreditCardInfo cc = new CreditCardInfo();
        cc.fullName = "Joe Jones";
        cc.number = "5555555555554444";
        cc.cvv = "432";
        cc.month = "3";
        cc.year = "2032";
        try {
            TransactionResult<PaymentMethodResult> tokenize = this.client.tokenize(cc);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
