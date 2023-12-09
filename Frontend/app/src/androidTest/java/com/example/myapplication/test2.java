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
public class test2{

    @Rule   // needed to launch the activity
    public ActivityTestRule<Login> activityRule = new ActivityTestRule<>(Login.class);

//
//    //CAL TESTS
//    @Test
//    public void SignUpCorrect(){
//        Intent signup =  new Intent(activityRule.getActivity(), SignUp.class);
//        activityRule.getActivity().startActivity(signup);
//        onView(withId(R.id.firstB)).perform(typeText("46"), closeSoftKeyboard());
//        onView(withId(R.id.lastB)).perform(typeText("46"), closeSoftKeyboard());
//        onView(withId(R.id.phoneB)).perform(typeText("46"), closeSoftKeyboard());
//        onView(withId(R.id.userB)).perform(typeText("46"), closeSoftKeyboard());
//        onView(withId(R.id.emailB)).perform(typeText("46"), closeSoftKeyboard());
//        onView(withId(R.id.passwordB)).perform(typeText("46"), closeSoftKeyboard());
//        onView(withId(R.id.confirmB)).perform(typeText("46"), closeSoftKeyboard());
//
//        Intent home =  new Intent(activityRule.getActivity(), home.class);
//        activityRule.getActivity().startActivity(home);
//        //click on the create account button
//        onView(withId(R.id.Create)).perform(click());
//        activityRule.getClass().getName().matches("home.class");
//        sleep(5000);
//    }
    @Test
    public void SignUpIncorrect() {
        sleep(5000);
        Intent signup = new Intent(activityRule.getActivity(), SignUp.class);
        activityRule.getActivity().startActivity(signup);
        onView(withId(R.id.firstB)).perform(typeText("Bobby"), closeSoftKeyboard());
        onView(withId(R.id.lastB)).perform(typeText("Hill"), closeSoftKeyboard());
        onView(withId(R.id.phoneB)).perform(typeText("555-555-5555"), closeSoftKeyboard());
        onView(withId(R.id.userB)).perform(typeText("bb"), closeSoftKeyboard());
        onView(withId(R.id.emailB)).perform(typeText("bb@b.com"), closeSoftKeyboard());
        onView(withId(R.id.passwordB)).perform(typeText("bb"), closeSoftKeyboard());
        onView(withId(R.id.confirmB)).perform(typeText("BBBBBBBBBBBBBBBBBB"), closeSoftKeyboard());

        Intent home = new Intent(activityRule.getActivity(), SignUp.class);
        activityRule.getActivity().startActivity(home);
        //click on the create account button
        onView(withId(R.id.Create)).perform(click());
        activityRule.getClass().getName().matches("SignUp.class");
    }

//
//    @Test
//    public void backButtonWorks(){
//        Intent signup =  new Intent(activityRule.getActivity(), SignUp.class);
//        activityRule.getActivity().startActivity(signup);
//        Intent login =  new Intent(activityRule.getActivity(), Login.class);
//        activityRule.getActivity().startActivity(login);
//        //click on the create account button
//        onView(withId(R.id.Back1)).perform(click());
//        login.getClass().getName().matches("Login.class");
//        sleep(5000);
//
//    }
//
//    @Test
//    public void backButtonWorks(){
//        Intent signup =  new Intent(activityRule.getActivity(), SignUp.class);
//        activityRule.getActivity().startActivity(signup);
//        Intent login =  new Intent(activityRule.getActivity(), Login.class);
//        activityRule.getActivity().startActivity(login);
//        //click on the create account button
//        onView(withId(R.id.Back1)).perform(click());
//        activityRule.getClass().getName().matches("Login.class");
//        sleep(5000);
//    }
}






