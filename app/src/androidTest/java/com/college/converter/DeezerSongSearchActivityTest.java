package com.college.converter;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class DeezerSongSearchActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void deezerActivityTest1() {

        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("q"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.artistName), withText("Q"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Q")));
    }

    @Test
    public void deezerActivityTest2() {
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("a"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.artistName), withText("Le A"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Le A")));
    }

    @Test
    public void deezerActivityTest3() {
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Taylor Swift"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.artistName), withText("Taylor Swift"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Taylor Swift")));
    }

    @Test
    public void deezerActivityTest4() {
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Bryan"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.artistName), withText("Brya"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Brya")));
    }

    @Test
    public void deezerActivityTest5() {
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("ED"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.artistName), withText("Ed Sheeran"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Ed Sheeran")));
    }

    @Test
    public void deezerTrackListActivityTest6() {
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Tammy"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.mRecyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.songTitle), withText("Can You Picture Yourself"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Can You Picture Yourself")));
    }

    @Test
    public void deezerSongDetailActivityTest7() {
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction bottomNavigationItemView = onView(withId(R.id.forth_id));
        bottomNavigationItemView.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.nameInput),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("Tammy"), closeSoftKeyboard());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.searchButton), withText("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton.perform(click());
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.mRecyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(1, click()));
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.trackRecyclerView),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(0, click()));
        try {
            Thread.sleep(5929);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ViewInteraction textView = onView(
                allOf(withId(R.id.title2), withText("Can You Picture Yourself"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.FrameLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("Can You Picture Yourself")));
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
