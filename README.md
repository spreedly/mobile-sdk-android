![Lint](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Lint%20check%20sources)
![Test](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Run%20Tests)
![Build](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Build%20Libraries)

# spreedly-android


# Lint

Run

    ./gradlew lint


# Coverage

A coverage report is regularly posted [here](https://ergonlabs.github.io/spreedly-docs/coverage/java/core-sdk/index.html).

To see this locally run:

    ./gradlew test

Then open [build/core-sdk/reports/jacoco/test/html/index.html](build/core-sdk/reports/jacoco/test/html/index.html)

# Docs

To build docs use:

    rm -rf docs
    ./gradlew alljavadoc


# Integration
All integration options require a Spreedly account and an environment key. See [Create Your API Credentials](https://docs.spreedly.com/basics/credentials/#environment-key) for details.
## Installation
We recommend using [Gradle](https://docs.gradle.org/current/userguide/userguide.html) to integrate the Spreedly SDK with your project. the `Spreedly` package provides basic, low-level APIs for custom integrations. The `Express` package provides custom controls and the Spreedly Express workflow, a prebuilt UI for collecting and selecting payment methods.

Add the following dependencies to your [gradle build file](https://docs.gradle.org/current/userguide/declaring_dependencies.html#declaring-dependencies):

    dependencies {
	    // core sdk
	    implementation com.spreedly.client

	    // Express prebuilt UIs and controls
	    implementation com.spreedly.express
    }
## Express
Collect and select payment methods with the SDK's Express tools.
Use this integration if you want a ready-made UI that:

 - Accepts cards and bank accounts (where available).
 - Can display saved payment methods for reuse.
 - Supports limited customization of headers, footers, and payment methods allowed.
 - Displays full-screen view controllers to select or add payment methods.
### Create and Configure `ExpressBuilder`
To begin the express work flow, create a new instance of `PaymentOptions` and set desired parameters. Next create `SpreedlyClient` with your environment key. Set test to true for test environments, false for production environments. Create and return your `ExpressBuilder`.
```
public ExpressBuilder getExpressBuilder() {
	//Create payment options
	PaymentOptions paymentOptions = new PaymentOptions();
	//Create Spreedly Client
	SpreedlyClient client = SpreedlyClient.newInstance("your enviroment key", "your secret key", true);
	//Create and return your ExpressBuilder
	return new ExpressBuilder(client, options);
}
```
To customize your `ExpressBuilder`, set properties on 	`paymentOptions` before creating the `ExpressBuilder`:
```
// sets text on submit button
paymentOptions.setButtonText("Pay now");

// choose a payment type:ALL, NEW_CARD_ONLY,  BANK_ONLY,  CARDS_ONLY,  NEW_CARD_AND_BANK
paymentOptions.setPaymentType(PaymentType.ALL);

//pass in a list of StoredCard for previously saved payment methods. Only use with ALL and CARDS_ONLY
paymentOptions.setStoredCardList(storedCards);

//set merchant customizations such as page title, icon, and footer text (html)
paymentOptions.setMerchantTitle("Lucy's Shop");
paymentOptions.setMerchantIcon(R.drawable.your_icon);
paymentOptions.setMerchantText("<div style=\"text-align: center;\">\n" +  "Pass in your customized merchant text.</div>");

//set billing address
paymentOptions.setBillingAddress(new Address("Street 1", "Street 2", "City", "State", "Zip", "Country", null));
```

### Use `ExpressBuilder` to launch payment flow
To launch an express fragment, call `ExpressBuilder.showDialog(@NonNull FragmentManager fm, @Nullable String tag, @NonNull Fragment target, @NonNull int requestCode))`:
```
@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
	//Other code for this activity goes here

	//Launch fragment on click:
	getView().findViewById(R.id.pay_now_fragment).setOnClickListener(v -> {
        ExpressBuilder builder = getExpressBuilder();
        builder.showDialog(getParentFragmentManager(), null, this, 1000);
    });
}
```
### Get response from Spreedly after payment flow is completed
```
@Override
public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1000) {
        if (resultCode == Activity.RESULT_OK) {
            Log.i("Spreedly", "Token: " + data.getStringExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN));
        }
    }
}
```





