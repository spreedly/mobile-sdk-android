![Lint](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Lint%20check%20sources)
![Test](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Run%20Tests)
![Build](https://api.cirrus-ci.com/github/ergonlabs/spreedly-android.svg?test=Build%20Libraries)

# spreedly-android

Not actively maintained at this time. Contact Support with questions.

# Integration
All integration options require a Spreedly account and an environment key. See [Create Your API Credentials](https://docs.spreedly.com/basics/credentials/#environment-key) for details.

## Installation
We recommend using [Gradle](https://docs.gradle.org/current/userguide/userguide.html) to integrate the Spreedly SDK with your project. the `Spreedly` package provides basic, low-level APIs for custom integrations. The `Express` package provides custom controls and the Spreedly Express workflow, a prebuilt UI for collecting and selecting payment methods.

First add the following to your [repositories section of your build.gradle file](https://docs.gradle.org/current/userguide/declaring_repositories.html#sec:declaring_multiple_repositories):

    maven {
        url = uri('https://raw.githubusercontent.com/spreedly/mobile-sdk-android/maven/')
    }

Add the following [dependencies to your build.gradle file](https://docs.gradle.org/current/userguide/declaring_dependencies.html#declaring-dependencies):

    dependencies {
	    // core sdk
        implementation 'com.spreedly:client:0.1-beta'

	    // Express prebuilt UIs and controls
        implementation 'com.spreedly:express:0.1-beta'

		// SecureWidget customizable widgets
        implementation 'com.spreedly:securewidgets:0.1-beta'
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

```jvm
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
paymentOptions.setStoredCardList(paymentMethodItems);

//set merchant customizations such as page title, icon, and footer text (html)
paymentOptions.setMerchantTitle("Lucy's Shop");
paymentOptions.setMerchantIcon(R.drawable.your_icon);
paymentOptions.setMerchantText("<div style=\"text-align: center;\">\n" +  "Pass in your customized merchant text.</div>");

//set billing address
paymentOptions.setBillingAddress(new Address("Street 1", "Street 2", "City", "State", "Zip", "Country", null));
```

### Use `ExpressBuilder` to launch payment flow
In your activity or fragment, create a button to trigger the payment flow. Override `onActivityCreated`, set the on click listener of your button. Create your `ExpressBuilder` by calling your custom `getExpressBuilder()` method.  To launch an express fragment, call `ExpressBuilder.showDialog(@NonNull FragmentManager fm, @Nullable String tag, @NonNull Fragment target, @NonNull int requestCode))`:

```
@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
	//Other code for this activity goes here

	//Launch fragment on click:
	getView().findViewById(R.id.your_button_id).setOnClickListener(v -> {
        ExpressBuilder builder = getExpressBuilder();
        builder.showDialog(getParentFragmentManager(), null, this, 1000);
    });
}
```

### Get response from Spreedly after payment flow is completed
In your activity or fragment, override `onActivityResult`. Set conditionals checking that `requestCode` matches your set request code in `onActivityCreated`, and that the `resultCode` is `Activity.RESULT_OK`.

```
@Override
public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == 1000) {
        if (resultCode == Activity.RESULT_OK) {
	        //Do something with result data
            Log.i("Spreedly", "Token: " + data.getStringExtra(ExpressBuilder.EXTRA_PAYMENT_METHOD_TOKEN));
        }
    }
}
```

Express builder has the following StringExtras used to return information:

- `EXTRA_PAYMENT_METHOD_TOKEN` contains the result token.
- `EXTRA_PAYMENT_METHOD_TRANSACTION` contains the serialized transaction result.
- `EXTRA_STORED_PAYMENT_METHOD`contains the serialized stored payment method (`StoredCard`).

## Customization with `SecureForm`
 The `SecureForm` extends `LinearLayout`. It is useful for collecting payment method information on behalf of an activity or fragment, and handling the create payment method API call to Spreedly.

 The `SecureForm` expects expects to use the following custom views:

 - `SecureTextField` - An extended `TextInputLayout` with a `getText()` method that returns a `SpreedlySecureOpaqueString` instead of a `String`
 - `SecureCreditCardField` - An extended `SecureTextField` that validates, formats and masks a credit card number.
 - `SecureExpirationDate` - A `LinearLayout` that contains two spinners for month and year. Has a `getMonth()` and `getYear()` method that returns selected values.

### SecureForm Components
The `SecureForm` finds views based on expected ids.
 - A `TextInputLayout` with the id `spreedly_full_name` or two `TextInputLayout`s with ids `spreedly_first_name` and `spreedly_last_name`
 - For Credit Cards `SecureForm` expects:
 -- `SecureCreditCardNumber` with the id `spreedly_credit_card`.
 -- `SecureExpirationDate` with the id `spreedly_cc_expiration_date`.
 -- `SecureTextField` with the id `spreedly_cvv`.
 - For Bank Accounts `SecureForm` expects:
 -- `TextInputLayout` with the id `spreedly_ba_routing_number`
 -- `SecureTextField` with id `spreedly_ba_account_number`
 -- Either a `RadioGroup`, `Spinner` or `TextInputLayout` with the id `spreedly_ba_account_type`
 - To include shipping or billing addresses, use `TextInputLayout` with the following ids:
 -- For billing addresses:  `spreedly_address1`, `spreedly_address2`, `spreedly_city`, `spreedly_state`, `spreedly_zip`, `spreedly_country`(optional), `spreedly_phone_number`(optional)
 -- For shipping addresses: `spreedly_shipping_address1`, `spreedly_shipping_address2`, `spreedly_shipping_city`, `spreedly_shipping_state`, `spreedly_shipping_zip`, `spreedly_shipping_country` (optional), `spreedly_shipping_phone_number` (optional)

Example Credit Card Payment XML:
``` xml
<com.spreedly.securewidgets.SecureFormLayout xmlns:android="http://schemas.android.com/apk/res/android"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:layout_marginStart="@dimen/activity_horizontal_margin"
  android:layout_marginEnd="@dimen/activity_horizontal_margin"
  android:padding="16dp"
  android:gravity="center"
  android:id="@+id/credit_card_form"
  android:orientation="vertical">

    <com.google.android.material.textfield.TextInputLayout
	  android:id="@id/spreedly_full_name"
	  android:layout_width="match_parent"
	  android:layout_height="wrap_content">

        <com.google.android.material.textfield.TextInputEditText
		  android:layout_width="match_parent"
		  android:layout_height="wrap_content"
		  android:autofillHints="name"
		  android:hint="@string/name_hint"
		  android:inputType="text" />
    </com.google.android.material.textfield.TextInputLayout>

    <com.spreedly.securewidgets.SecureCreditCardField
	    android:id="@id/spreedly_credit_card_number"
		android:layout_width="match_parent"
		android:layout_height="wrap_content" />

    <com.spreedly.securewidgets.SecureTextField
		android:id="@id/spreedly_cvv"
		android:layout_width="match_parent"
	    android:layout_height="wrap_content" />

    <com.spreedly.securewidgets.SecureExpirationDate
		android:id="@id/spreedly_cc_expiration_date"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content" />

    <TextView
	    android:id="@+id/token"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:textColor="@android:color/holo_blue_dark"
	    android:textIsSelectable="true" />

    <TextView
	    android:id="@+id/error"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:textColor="@android:color/holo_red_dark"
	    android:textIsSelectable="true" />

    <Button
	    android:id="@id/spreedly_cc_submit"
	    android:layout_width="match_parent"
	    android:layout_height="wrap_content"
	    android:text="@string/submit" />

</com.spreedly.securewidgets.SecureFormLayout>
```

### SecureForm Methods

The `SecureForm` has the following methods:
 - `public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod()`: Captures data from user input (name, credit card number, cvv, expiration date and optionally billing and shipping address) and makes a `create credit card payment request` to the Spreedly API.
 - `Single<TransactionResult<PaymentMethodResult>> createBankAccountPaymentMethod()`: Captures data from user input (name, bank account number, routing number, account type and optionally billing and shipping address), and makes a `create bank account payment request` to the Spreedly API.
 - `public Single<TransactionResult<PaymentMethodResult>> createCreditCardPaymentMethod(@Nullable Address billingAddress, @Nullable Address shippingAddress)`: Behaves the same as `createCreditCardPaymentMethod()`except it doesn't not search for billing or shipping address components. Use this if addresses have been submitted on a different view.
 - `Single<TransactionResult<PaymentMethodResult>> createBankAccountPaymentMethod(@Nullable Address billingAddress, @Nullable Address shippingAddress)`: Behaves the same as `createBankAccountPaymentMethod()`except it doesn't not search for billing or shipping address components. Use this if addresses have been submitted on a different view.

## Full Customization with Core SDK
If you prefer a completely customized payment method collection and selection experience, you can use the Core SDK and `SpreedlyClient`
For example, to create a new credit card payment method with Spreedly, create and configure a `CreditCardInfo` object, and send is using `SpreedlyClient`

Constructors:

```
CreditCardInfo(@NonNull String: fullName, @NonNull SpreedlySecureOpaqueString: number, @Nullable SpreedlySecureOpaqueString: verificationValue, @NonNull int: year, @NonNull int: month);
```

```
SpreedlyClient.newInstance(@NonNull String envKey, @NonNull String envSecret,@NonNull boolean test)
```

Example code:

```jvm
CreditCardInfo info = new CreditCardInfo("Full Name", new SpreedlySecureOpaqueString("4111111111111111"), new SpreedlySecureOpaqueString("432"), 2025, 12);
SpreedlyClient client = SpreedlyClient.newInstance("your key", "your secret", true);
client.createCreditCardPaymentMethod(info, null, null).subscribe(new SingleObserver<TransactionResult<PaymentMethodResult>>() {
    @Override
  public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
  public void onSuccess(TransactionResult<PaymentMethodResult> trans) {
        try {
            if (trans.succeeded) {
                Log.i("Spreedly", "trans.result.token: " + trans.result.token);
                //do something with trans result
            } else {
                Log.e("Spreedly", "trans.message: " + trans.message);
               // do something with error
            }
        } finally {
          //finish
        }
    }

    @Override
  public void onError(@NonNull Throwable e) {
        Log.e("Spreedly", e.getMessage(), e);
        //do something with error
    }
});
```

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











