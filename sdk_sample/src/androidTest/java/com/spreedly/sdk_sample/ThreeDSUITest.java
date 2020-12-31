package com.spreedly.sdk_sample;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.ListView;

import com.spreedly.securewidgets.SecureCreditCardField;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.runner.AndroidJUnit4;

import static android.os.SystemClock.sleep;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ThreeDSUITest {

    @Rule
    public ActivityScenarioRule mActivityTestRule = new ActivityScenarioRule(MainActivity.class);

    @Test
    public void successTest() throws Exception {
        submit("1000", "Standard");

        waitForView(allOf(withText("Confirmar"), isDisplayed()), 5000);

        ViewInteraction appCompatEditText3 = onView(allOf(withType(EditText.class), isDisplayed()));
        appCompatEditText3.perform(scrollTo(), replaceText("123456"), closeSoftKeyboard());

        ViewInteraction confirmar = onView(allOf(withText("Confirmar"), isDisplayed()));
        confirmar.perform(scrollTo(), click());

        assertResult("success: Y");
    }

    @Test
    public void cancelTest() throws Exception {

        submit("1000", "Standard");

        waitForView(allOf(withText("Cancel"), isDisplayed()), 5000).perform(click());

        assertResult("cancelled");
    }

    @Test
    public void failedNoAuth1Test() throws Exception {

        submit("99.96", "Standard");

        assertResult("error: runtimeError(message: \"not_authenticated\")");
    }

    @Test
    public void failedNoAuth2Test() throws Exception {

        submit("99.97", "Standard");

        assertResult("error: runtimeError(message: \"not_authenticated\")");
    }

    @Test
    public void deniedTest() throws Exception {

        submit("99.98", "Standard");

        assertResult("error: runtimeError(message: \"not_authenticated\")");
    }

    @Test
    public void rejectedTest() throws Exception {

        submit("99.99", "Standard");

        assertResult("error: runtimeError(message: \"not_authenticated\")");
    }

    private void assertResult(String result) throws Exception {
        ViewInteraction textView = waitForView(allOf(withText(result), isDisplayed()), 5000);
        textView.check(matches(withText(result)));
    }

    private void submit(String amount, String mode) {

        ViewInteraction tabView = onView(
                allOf(withText("3DS2"), isDisplayed()));
        tabView.perform(click());


        onView(withSpinnerText("Standard")).perform(
                click()
        );

        onView(allOf(
                childOf(withType(ListView.class)),
                withText(mode)
        )).perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(childOf(withType(SecureCreditCardField.class)),
                        withType(EditText.class),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("5555 5555 5555 4444"), closeSoftKeyboard());

        ViewInteraction textInputEditText = onView(
                allOf(withHint("Amount"),
                        isDisplayed()));
        textInputEditText.perform(replaceText(amount), closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withText("Submit"),
                        isDisplayed()));
        appCompatButton.perform(click());
    }

    private static Matcher<View> childOf(final Matcher<View> parentMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child of parent");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && (parentMatcher.matches(parent) || matchesSafely((View) parent));
            }
        };
    }

    private static Matcher<View> hasChild(final Matcher<View> childMatcher) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("has child of ");
                childMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                if (childMatcher.matches(view)) {
                    return true;
                }
                if (view instanceof ViewGroup) {
                    ViewGroup group = (ViewGroup) view;
                    for (int i = 0; i < group.getChildCount(); i++) {
                        if (matchesSafely(group.getChildAt(i))) {
                            return true;
                        }
                    }
                }
                return false;
            }
        };
    }

    private static Matcher<View> withType(final Class base) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("of type " + base);
            }

            @Override
            public boolean matchesSafely(View view) {
                return base.isAssignableFrom(view.getClass());
            }
        };
    }

    private static ViewAction searchFor(Matcher<View> matcher) {

        return new ViewAction() {
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            public String getDescription() {
                return "searching for view " + matcher + " in the root view";
            }

            public void perform(UiController uiController, View view) {
                int tries = 0;
                Iterable<View> childViews = TreeIterables.breadthFirstViewTraversal(view);

                // Look for the match in the tree of childviews
                for (View it : childViews) {
                    tries++;
                    if (matcher.matches(it)) {
                        return;
                    }
                }

                throw new NoMatchingViewException.Builder()
                        .withRootView(view)
                        .withViewMatcher(matcher)
                        .build();
            }
        };
    }

    private static ViewInteraction waitForView(Matcher<View> viewMatcher, int timeout) throws Exception {
        // Derive the max tries
        final int maxTries = timeout / 250;

        for (int tries = 0; tries == 0 || tries < maxTries; tries++) {
            try {
                // Search the root for the view
                onView(isRoot()).perform(searchFor(viewMatcher));

                // If we're here, we found our view. Now return it
                return onView(viewMatcher);

            } catch (Exception e) {

                if (tries >= maxTries) {
                    throw e;
                }
                sleep(250);
            }
        }

        throw new Exception("Error finding a view matching " + viewMatcher);
    }
}
