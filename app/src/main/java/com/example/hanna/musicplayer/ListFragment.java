package com.example.hanna.musicplayer;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Hanna on 22.11.2016.
 */

public class ListFragment extends android.support.v4.app.ListFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ListFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ListFragment newInstance(int sectionNumber) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.list_fragment, container, false);
        // Create an array of string to be data source of the ListFragment
        String[] datasource={"English","French","Khmer","Japanese","Russian","Chinese","English","French","Khmer","Japanese","Russian","Chinese","English","French","Khmer","Japanese","Russian","Chinese","English","French","Khmer","Japanese","Russian","Chinese"};
        // Create ArrayAdapter object to wrap the data source
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),R.layout.rowlayout,R.id.txtitem,datasource);
        // Bind adapter to the ListFragment
        setListAdapter(adapter);
        //  Retain the ListFragment instance across Activity re-creation
        setRetainInstance(true);

        return rootView;
    }

    public void onListItemClick(ListView l, View view, int position, long id){
        ViewGroup viewg=(ViewGroup)view;
        TextView tv=(TextView)viewg.findViewById(R.id.txtitem);
        Toast.makeText(getActivity(), tv.getText().toString(),Toast.LENGTH_LONG).show();
    }

}
