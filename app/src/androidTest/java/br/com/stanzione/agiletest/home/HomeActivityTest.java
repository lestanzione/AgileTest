package br.com.stanzione.agiletest.home;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;

import br.com.stanzione.agiletest.App;
import br.com.stanzione.agiletest.R;
import br.com.stanzione.agiletest.di.ApplicationComponent;
import br.com.stanzione.agiletest.di.DaggerApplicationComponent;
import br.com.stanzione.agiletest.di.MockNetworkModule;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class HomeActivityTest {

    @Rule
    public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class, true, false);

    private MockWebServer server = new MockWebServer();

    @Before
    public void setUp() throws Exception {
        setUpServer();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    public void setUpServer() {
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .networkModule(new MockNetworkModule(server))
                .build();

        ((App) getTargetContext().getApplicationContext())
                .setApplicationComponent(applicationComponent);
    }

    @Test
    public void onEmptyUsernameShouldShowMessage() throws IOException {

        server.enqueue(new MockResponse()
                .setBody(readFile("user_not_found_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.searchButton))
                .perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_user_not_found)))
                .check(matches(isDisplayed()));

    }

    @Test
    public void onUserNotFoundShouldShowMessage() throws IOException {

        server.enqueue(new MockResponse()
                .setBody(readFile("user_not_found_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.gitNameEditText))
                .perform(typeText("_"));

        pressBack();

        onView(withId(R.id.searchButton))
                .perform(click());

        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.message_user_not_found)))
                .check(matches(isDisplayed()));

    }

    @Test
    public void onRegularUserShouldPass() throws IOException {

        server.enqueue(new MockResponse()
                .setBody(readFile("repositories_list_response.json")));

        activityRule.launchActivity(new Intent());

        onView(withId(R.id.gitNameEditText))
                .perform(typeText("lestanzione"));

        pressBack();

        onView(withId(R.id.searchButton))
                .perform(click());

    }

    private String readFile(String fileName) throws IOException {
        InputStream stream = InstrumentationRegistry.getContext()
                .getAssets()
                .open(fileName);

        Source source = Okio.source(stream);
        BufferedSource buffer = Okio.buffer(source);

        return buffer.readUtf8();
    }

}