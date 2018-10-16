package br.com.stanzione.agiletest.profile;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.stanzione.agiletest.App;
import br.com.stanzione.agiletest.Configs;
import br.com.stanzione.agiletest.R;
import br.com.stanzione.agiletest.di.ApplicationComponent;
import br.com.stanzione.agiletest.di.DaggerApplicationComponent;
import br.com.stanzione.agiletest.di.MockNetworkModule;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ProfileActivityTest {

    @Rule
    public ActivityTestRule<ProfileActivity> activityRule = new ActivityTestRule<>(ProfileActivity.class, true, false);

    private String username = "lestanzione";

    @Test
    public void onActivityStartShouldShowUsername() {

        Intent intent = new Intent();
        intent.putExtra(Configs.ARG_USERNAME, username);

        activityRule.launchActivity(intent);

        onView(withId(R.id.collapsingToolbar))
                .check(matches(withContentDescription(username)));

    }

}