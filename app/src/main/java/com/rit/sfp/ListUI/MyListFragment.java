package com.rit.sfp.ListUI;

import android.app.ListFragment;
import android.content.Context;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.XmlRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Sfp on 9/20/2016.
 */
public class MyListFragment extends ListFragment {

    public ArrayList<String> itemsArrayList; //list of items
    public HashMap<String,ArrayList<String>> statesInfoList;
    private ArrayAdapter<String> itemsArrayAdapter;
    private ItemChangedListener itemChangedListener;

    //interface describing listener for changes to selected item
    public interface ItemChangedListener {
        //the selected item is changed
        public void onSelectedItemChanged(String name, Map<String,ArrayList<String>> stateMap);
    }

    //set the ItemChangedListener
    public void setItemChangedListener(ItemChangedListener listener) {
        itemChangedListener = listener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        XmlResourceParser xrp = getResources().getXml(R.xml.states);
        itemsArrayList = new ArrayList<String>();
        statesInfoList = new HashMap<>();
        ArrayList<String> infoList = new ArrayList<>();
        try {
            xrp.next();
            int eventType = xrp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG
                        && xrp.getName().equalsIgnoreCase("state")) {
                    String stateName = xrp.getAttributeValue(null,
                            "name");
                    String statePopulation = xrp.getAttributeValue(null,"population");
                    String stateCapital = xrp.getAttributeValue(null,"capital");
                    infoList.add(stateCapital);
                    infoList.add(statePopulation);
                    statesInfoList.put(stateName,infoList);
                    itemsArrayList.add(stateName);
                    int intValue = xrp.getAttributeIntValue(null, "order", 0);
                }
                eventType = xrp.next();
            }
        } catch (IOException e) {
            Log.d("TAG", "IO Exception");
        } catch (XmlPullParserException e) {
            Log.d("TAG", "XmlException");
        }
        setListAdapter(new ItemsArrayAdapter<String>(
                getActivity(),
                R.layout.list_item,
                itemsArrayList));

        ListView thisListView = getListView(); //get the Fragment's listview

        //allow one item to be selected at a time
        thisListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        thisListView.setBackgroundColor(Color.WHITE);
        thisListView.setOnItemClickListener(itemsOnItemClickListener);
    }

    //define the GUI components for each ListView item
    private static class ViewHolder {
        TextView itemTextView; //refers to the ListView item's TextView
    }

    //customer ArrayAdapter
    private class ItemsArrayAdapter<T> extends ArrayAdapter<String> {
        private Context context; //this Fragment's Activity's Context
        private LayoutInflater inflater;
        private List<String> items; //list of items

        //public constructor
        public ItemsArrayAdapter(Context context, int textViewResourceId,
                                 List<String> objects) {
            super(context, -1, objects); //-1 indicates we're customizing view
            this.context = context;
            this.items = objects;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        //get ListView item for the given position
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder; //holds reference to current item's GUI
            //if convertView is null, inflate GUI and create ViewHolder otherwise, get an existing ViewHolder
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                //set up ViewHOlder for this item
                viewHolder = new ViewHolder();
                viewHolder.itemTextView = (TextView) convertView.findViewById(R.id.text1);
                convertView.setTag(viewHolder);
            } else {
                //get the ViewHolder from the convertView's tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            String item = items.get(position); //get current item
            viewHolder.itemTextView.setText(item);

            return convertView;
        } //getView
    }//arrayadapter

    private AdapterView.OnItemClickListener itemsOnItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view,
                                        int position,
                                        long id) {
                    itemChangedListener.onSelectedItemChanged(((TextView)view).getText().toString(),
                            statesInfoList);
                }
            };

}
