package com.rit.sfp.ListUI;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sfp on 9/20/2016.
 */
public class DetailFragment extends Fragment {
    private String itemName;
    private String stateName;
    private String stateCapital;
    private String statePopulation;
    public static DetailFragment newInstance(String name, Map<String,ArrayList<String>> stateMap) {
        DetailFragment myFragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString("NAME",name);
        args.putString("CAPITAL",stateMap.get(name).get(0));
        args.putString("POPULATION",stateMap.get(name).get(1));
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle args = getArguments();
        //itemName=args.getString("ITEM","Unknown");
        itemName = (String) args.get("ITEM");
        stateName = (String) args.get("NAME");
        stateCapital = (String) args.get("CAPITAL");
        statePopulation = (String) args.get("POPULATION");
        //Inflate the layout for the THIS fragment
        View myView = inflater.inflate(R.layout.detail_fragment, container, false);
        ((TextView) myView.findViewById(R.id.textViewName)).setText(stateName);
        ((TextView) myView.findViewById(R.id.textViewCaptial)).setText(stateCapital);
        ((TextView) myView.findViewById(R.id.textViewPopulation)).setText(statePopulation);

        return myView;

    }


}

