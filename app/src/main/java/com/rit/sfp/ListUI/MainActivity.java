package com.rit.sfp.ListUI;

import android.os.Bundle;
import android.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.app.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends Activity {
    private MyListFragment myListFragment;
    private FragmentTwo fragmentTwo;
    private Button listButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listButton = (Button) findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment("List");
            }
        });
        //would need to keep track of which fragment is being displayed for
        //rotation if desired in the appropriate methods
        loadFragment("List");
    }

    private void loadFragment(String which) {

        if (which.equals("List")) {
            //get the MyListFragment
            myListFragment = new MyListFragment();
            //set the change listener
            myListFragment.setItemChangedListener(itemChangedListener);
            //note: no transition or backstack - but clear backstack
            getFragmentManager().popBackStack(null,
                    getFragmentManager().POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.the_info, myListFragment);
            ft.commit();
        } else if (which.equals("Two")) {
            //get the Fragment Two Fragment
            fragmentTwo = new FragmentTwo();

            //note: no transition or backstack - but clear backstack
            getFragmentManager().popBackStack(null,
                    getFragmentManager().POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.the_info, fragmentTwo);
            ft.commit();
        }
    }


    //listener for list fragment
    private MyListFragment.ItemChangedListener itemChangedListener =
            new MyListFragment.ItemChangedListener() {
                @Override
                public void onSelectedItemChanged(String name, Map<String,ArrayList<String>> statesMap) {
                    //create and show the fragment

                    DetailFragment details = DetailFragment.newInstance(name, statesMap);
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.animator.fragment_animation_fade_in,
                            R.animator.fragment_animation_fade_out);
                    ft.replace(R.id.the_info, details);
                    ft.addToBackStack(null);
                    //optional name for this backstack state or null – needs to be just before the commit –
                    //if multiple “transactions”  (add/remove/replace in any combination) happen before “addToBackStack” ,
                    // they ALL are undone with one back button. if you use addToBackStack, the fragment is stopped and //resumed when the user navigates to it, if you don’t use addToBackStack it is destroyed.
                    ft.commit();
                }
            };
}
