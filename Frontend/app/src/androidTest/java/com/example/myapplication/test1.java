package com.example.myapplication;

import static android.os.SystemClock.sleep;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;



@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class test1{
//
    @Rule   // needed to launch the activity
    public ActivityTestRule<Login> activityRule = new ActivityTestRule<>(Login.class);

//
    @Test
    public void Ingame() throws InterruptedException {

        String username = "cc";
        String password = "cc";
        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        Intent newhome =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(newhome);
        sleep(5000);


        String lobbyNumber = "7";

        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.idJoin))
                .perform(typeText(lobbyNumber), closeSoftKeyboard());

        onView(withId(R.id.JoinB)).perform(click());

        Intent newhome3 =  new Intent(activityRule.getActivity(), PreGameLobby.class);
        activityRule.getActivity().startActivity(newhome3);
        sleep(5000);


        String itemToSelect2 = "SMG";
        onView(withId(R.id.add)).perform(click());
        // Click on the item in the Spinner
        onView(ViewMatchers.withText(itemToSelect2)).perform(ViewActions.click());


        onView(withId(R.id.next)).perform(click());


        Intent newhome4 =  new Intent(activityRule.getActivity(), MainActivity.class);
        activityRule.getActivity().startActivity(newhome4);
        sleep(5000);



    }
    @Test
    public void loginwithKK() throws InterruptedException {
        sleep(5000);

        String username = "kk";
        String password = "kk";
        //int resultString = 200;
        // Type in testString and send request
        onView(ViewMatchers.withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

//
//        onView(withId(R.id.login)).check(matches(isChecked()));
        onView(withId(R.id.password)).check(matches(withText("kk")));
        onView(withId(R.id.username)).check(matches(withText("kk")));

        onView(withId(R.id.login)).perform(click());
        Intent newhome =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(newhome);

        newhome.getClass().getName().matches("home.class");
        sleep(5000);


    }
    @Test
    public void joingame() throws InterruptedException {
        sleep(5000);
        String username = "cc";
        String password = "cc";
        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        Intent newhome =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(newhome);

        newhome.getClass().getName().matches("home.class");
        sleep(5000);


        String lobbyNumber = "7";

        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.idJoin))
                .perform(typeText(lobbyNumber), closeSoftKeyboard());

//        onView(withId(R.id.login)).check(matches(isChecked()));
        onView(withId(R.id.idJoin)).check(matches(withText("7")));
        onView(withId(R.id.JoinB)).perform(click());
        newhome.getClass().getName().matches("PreGameLobby.class");
        sleep(5000);


    }
    @Test
    public void StartGameisVisiable() throws InterruptedException {
        sleep(5000);
        String username = "cc";
        String password = "cc";
        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        Intent newhome =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(newhome);

        newhome.getClass().getName().matches("home.class");
        sleep(5000);


        String lobbyNumber = "7";

        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.idJoin))
                .perform(typeText(lobbyNumber), closeSoftKeyboard());

//        onView(withId(R.id.login)).check(matches(isChecked()));
        onView(withId(R.id.idJoin)).check(matches(withText("7")));
        onView(withId(R.id.JoinB)).perform(click());
        newhome.getClass().getName().matches("PreGameLobby.class");
        onView(withId(R.id.next)).check(matches((isEnabled())));
        sleep(5000);

    }

    @Test
    public void createGame() throws InterruptedException {
        sleep(5000);
        String username = "12";
        String password = "12";
        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        Intent newhome =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(newhome);

        newhome.getClass().getName().matches("home.class");
        sleep(5000);


        Intent newhome2 =  new Intent(activityRule.getActivity(), Createlobby.class);
        activityRule.getActivity().startActivity(newhome2);
        sleep(5000);
        onView(withId(R.id.spinner6)).perform(click());

        String itemToSelect = "Team Death Match";

        // Click on the item in the Spinner
        onView(ViewMatchers.withText(itemToSelect)).perform(ViewActions.click());

        onView(withId(R.id.spinner8)).perform(click());

        String itemToSelect2 = "Map1";

        // Click on the item in the Spinner
        onView(ViewMatchers.withText(itemToSelect2)).perform(ViewActions.click());

        onView(withId(R.id.spinner9)).perform(click());

        String itemToSelect3 = "2";

        // Click on the item in the Spinner
        onView(ViewMatchers.withText(itemToSelect3)).perform(ViewActions.click());

        onView(withId(R.id.Getcode)).perform(click());
        sleep(5000);
    }




    //CAL TESTS
    @Test
    public void SignUpCorrect(){
        Intent signup =  new Intent(activityRule.getActivity(), SignUp.class);
        activityRule.getActivity().startActivity(signup);
        sleep(5000);
        onView(withId(R.id.firstB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.lastB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.phoneB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.userB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.emailB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.passwordB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.confirmB)).perform(typeText("12"), closeSoftKeyboard());
        onView(withId(R.id.Create)).perform(click());
        Intent home =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(home);
        sleep(5000);
        //click on the create account button

        activityRule.getClass().getName().matches("home.class");
        sleep(5000);
    }



    @Test
    public void profile() throws InterruptedException {
        sleep(5000);
        String username = "kk";
        String password = "kk";
        //int resultString = 200;
        // Type in testString and send request
        onView(withId(R.id.username))
                .perform(typeText(username), closeSoftKeyboard());
        onView(withId(R.id.password))
                .perform(typeText(password), closeSoftKeyboard());

        onView(withId(R.id.login)).perform(click());
        Intent newhome =  new Intent(activityRule.getActivity(), home.class);
        activityRule.getActivity().startActivity(newhome);
        sleep(5000);



        String lobbyNumber = "Profile";
        onView(withId(R.id.spinner1)).perform(click());
        onView(ViewMatchers.withText(lobbyNumber)).perform(ViewActions.click());

        sleep(5000);

        Intent newhome8=  new Intent(activityRule.getActivity(), profile.class);
        activityRule.getActivity().startActivity(newhome8);
        newhome.getClass().getName().matches("profile.class");
        sleep(5000);

    }

}






