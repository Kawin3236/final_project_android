package kmitl.kawin58070006.horyuni.controller.activity;


import android.os.SystemClock;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kmitl.kawin58070006.horyuni.R;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void loginLogoutTest() {
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.loginButton), withText("Continue with Facebook"))).perform(click());
        onView(allOf(withId(R.id.imgProfile))).perform(click());
        onView(withText("ยกเลิก")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        onView(allOf(withId(R.id.imgProfile))).perform(click());
        onView(withText("ออกจากระบบ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }

    @Test
    public void ViewDetailTest() {
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.loginButton), withText("Continue with Facebook"))).perform(click());
        onView(withId(R.id.listViewImage)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_detail)));
        onView(allOf(withId(R.id.imgProfile))).perform(click());
        onView(withText("ออกจากระบบ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());

    }

    @Test
    public void ViewMyPostDetailTest() {
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.loginButton), withText("Continue with Facebook"))).perform(click());
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.imgProfile))).perform(click());
        onView(withText("โพสต์ของฉัน")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.listViewImage2)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.btn_detail)));
        onView(allOf(withId(R.id.imgProfile))).perform(click());
        onView(withText("ออกจากระบบ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());


    }

    @Test
    public void ViewZoneTest() {
        SystemClock.sleep(1000);
        onView(allOf(withId(R.id.loginButton), withText("Continue with Facebook"))).perform(click());
        onView(withId(R.id.linearZone)).perform(click());
        onView(withId(R.id.ZoneK1)).perform(click());
        pressBack();
        onView(withId(R.id.ZoneK2)).perform(click());
        onView(withId(R.id.ZoneK3)).perform(click());
        onView(withId(R.id.ZoneWP)).perform(click());
        pressBack();
        onView(withId(R.id.ZoneKJL)).perform(click());
        pressBack();
        onView(withId(R.id.ZoneHM)).perform(click());
        pressBack();
        onView(withId(R.id.ZoneRNP)).perform(click());
        pressBack();
        onView(withId(R.id.ZonePapaMama)).perform(click());
        onView(withId(R.id.ZoneKS)).perform(click());

        onView(allOf(withId(R.id.imgProfile))).perform(click());
        onView(withText("ออกจากระบบ")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click());
    }












    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}
