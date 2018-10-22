package com.flashapps.smartgate.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.flashapps.smartgate.R;


/**
 * Created by thomasbeerten on 02/03/16.
 */
public class Helper_Fragments {

    public static void replaceFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static void replaceFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, boolean animation, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /*public static void replaceFragmentUpWards(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, boolean animation, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animation == true)
            fragmentTransaction.setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down);
        if (activity instanceof CampaignDetailActivity)
            fragmentTransaction.replace(R.id.container, fragment, fragment.getTag());
        else
            fragmentTransaction.replace(R.id.activity_main, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void addFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment, TAG);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(TAG);
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  public static void addFragment(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, boolean animation, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animation == true)
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, 0, 0, R.anim.slide_out_right);
        fragmentTransaction.add(R.id.container, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addFragmentUpwards(AppCompatActivity activity, Fragment fragment, boolean addToBackstack, boolean animation, String TAG) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (animation == true)
            fragmentTransaction.setCustomAnimations(R.anim.slide_up, 0, 0, R.anim.slide_down);
        if (activity instanceof CampaignDetailActivity)
            fragmentTransaction.add(R.id.container, fragment, fragment.getTag());
        else
            fragmentTransaction.add(R.id.activity_main, fragment, fragment.getTag());
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(fragment.getTag());
        }
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    public static void removeFragment(AppCompatActivity activity, String TAG) {

        Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(TAG);
        if (fragment != null) {
            activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }

    public static Fragment getFragment(AppCompatActivity activity, String TAG) {

        return activity.getSupportFragmentManager().findFragmentByTag(TAG);
    }
}