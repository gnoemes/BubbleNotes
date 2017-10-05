package com.gnoemes.bubblenotes.ui_espresso;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.gnoemes.bubblenotes.App;
import com.gnoemes.bubblenotes.R;
import com.gnoemes.bubblenotes.repo.local.RepoDi;
import com.gnoemes.bubblenotes.ui.main.MainDrawerActivity;
import com.gnoemes.bubblenotes.util.EspressoIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by kenji1947 on 03.10.2017.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class NotesListUiTest {
    @Rule
    public ActivityTestRule<MainDrawerActivity> mainDrawerActivityActivityTestRule
            = new ActivityTestRule<MainDrawerActivity>(MainDrawerActivity.class);

    @Before
    public void registerIdlingResource() {
        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
        clearDb();
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    public void unregisterIdlingResource() {
        Espresso.unregisterIdlingResources(EspressoIdlingResource.getIdlingResource());
    }
    private void clearDb() {
        ((App) (mainDrawerActivityActivityTestRule.getActivity().getApplication()))
                .clearAllEntities();
    }

    @Test
    public void clickAddTaskButton_opensAddTaskUi() {
        // Click on the add task button
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.nameEditText)).perform(typeText("asd"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());

        onView(withId(R.id.listRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.nameEditText)).check(matches(withText("asd")));

//        // Check if the add task screen is displayed
//        onView(withId(R.id.add_task_title)).check(matches(isDisplayed()));
    }
}
