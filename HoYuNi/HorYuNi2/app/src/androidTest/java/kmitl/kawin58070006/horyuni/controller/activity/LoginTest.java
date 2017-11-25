package kmitl.kawin58070006.horyuni.controller.activity;


import android.os.SystemClock;
import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.kawin58070006.horyuni.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loginTest() {
        SystemClock.sleep(3000);
        ViewInteraction loginButton = onView(
                allOf(withId(R.id.loginButton), withText("Continue with Facebook"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        loginButton.perform(click());

        ViewInteraction circleImageView = onView(
                allOf(withId(R.id.imgProfile),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.costom_toolbaar),
                                        0),
                                2),
                        isDisplayed()));
        circleImageView.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(2);
        appCompatTextView.perform(click());

        ViewInteraction circleImageView2 = onView(
                allOf(withId(R.id.imgProfile),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.costom_toolbaar),
                                        0),
                                2),
                        isDisplayed()));
        circleImageView2.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatTextView2.perform(click());

    }

    @Test
    public void detailTest() {
        SystemClock.sleep(3000);
        ViewInteraction loginButton = onView(
                allOf(withId(R.id.loginButton), withText("Continue with Facebook"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        loginButton.perform(click());


        onData(anything())
                .inAdapterView(withId(R.id.listViewImage))
                .atPosition(0)
                .onChildView(withId(R.id.btn_detail))
                .perform(click());
        ViewInteraction circleImageView2 = onView(
                allOf(withId(R.id.imgProfile),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.costom_toolbaar),
                                        0),
                                2),
                        isDisplayed()));
        circleImageView2.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatTextView2.perform(click());


    }

    @Test
    public void detailOnSearchTest() {
        SystemClock.sleep(3000);
        ViewInteraction loginButton = onView(
                allOf(withId(R.id.loginButton), withText("Continue with Facebook"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        loginButton.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(withId(R.id.linearZone),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        3),
                                2),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.ZoneK1),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.btn_detail), withText("รายละเอียด >"),
                        childAtPosition(
                                allOf(withId(R.id.aaaa),
                                        childAtPosition(
                                                withClassName(is("android.support.constraint.ConstraintLayout")),
                                                7)),
                                0),
                        isDisplayed()));
        appCompatTextView.perform(click());

        pressBack();

        pressBack();

        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.ZoneK2),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        ViewInteraction appCompatImageView3 = onView(
                allOf(withId(R.id.ZoneK3),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatImageView3.perform(click());

        ViewInteraction appCompatImageView4 = onView(
                allOf(withId(R.id.ZoneWP),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView4.perform(click());

        ViewInteraction appCompatImageView5 = onView(
                allOf(withId(R.id.ZoneKJL),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageView5.perform(click());

        ViewInteraction appCompatImageView6 = onView(
                allOf(withId(R.id.ZoneHM),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatImageView6.perform(click());

        ViewInteraction appCompatImageView7 = onView(
                allOf(withId(R.id.ZoneRNP),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView7.perform(click());

        ViewInteraction appCompatImageView8 = onView(
                allOf(withId(R.id.ZonePapaMama),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageView8.perform(click());

        ViewInteraction appCompatImageView9 = onView(
                allOf(withId(R.id.ZoneKS),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        2),
                                0),
                        isDisplayed()));
        appCompatImageView9.perform(click());

        ViewInteraction circleImageView = onView(
                allOf(withId(R.id.imgProfile),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.costom_toolbaar),
                                        0),
                                2),
                        isDisplayed()));
        circleImageView.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatTextView2.perform(click());

    }

    @Test
    public void DeletePostTest() {
        SystemClock.sleep(3000);
        ViewInteraction loginButton = onView(
                allOf(withId(R.id.loginButton), withText("Continue with Facebook"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        loginButton.perform(click());

        ViewInteraction circleImageView = onView(
                allOf(withId(R.id.imgProfile),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.costom_toolbaar),
                                        0),
                                2),
                        isDisplayed()));
        circleImageView.perform(click());

        DataInteraction appCompatTextView = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView.perform(click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.btnDelete), withText("ลบ"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        8),
                                0),
                        isDisplayed()));
        appCompatButton.perform(click());

        DataInteraction appCompatTextView2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatTextView2.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.btnDelete), withText("ลบ"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.support.constraint.ConstraintLayout")),
                                        8),
                                0),
                        isDisplayed()));
        appCompatButton2.perform(click());

        DataInteraction appCompatTextView3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(0);
        appCompatTextView3.perform(click());

        ViewInteraction circleImageView2 = onView(
                allOf(withId(R.id.imgProfile),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.costom_toolbaar),
                                        0),
                                2),
                        isDisplayed()));
        circleImageView2.perform(click());

        DataInteraction appCompatTextView4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.select_dialog_listview),
                        childAtPosition(
                                withId(R.id.contentPanel),
                                0)))
                .atPosition(1);
        appCompatTextView4.perform(click());

    }


    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
