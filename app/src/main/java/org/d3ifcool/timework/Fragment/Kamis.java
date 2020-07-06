package org.d3ifcool.timework.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.d3ifcool.timework.DatabaseAdapter;
import org.d3ifcool.timework.R;
import org.d3ifcool.timework.Schedule;
import org.d3ifcool.timework.ScheduleAdapter;

import java.util.ArrayList;

/**
 * Created by cool on 2/24/2018.
 */

public class Kamis extends ListFragment {
    public Kamis() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_kamis, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(getActivity());
        ArrayList<Schedule> listSchedule = new ArrayList<>();
        ArrayList<Schedule> list = new ArrayList<>();

        try{
            listSchedule = databaseAdapter.getAllSchedule();
            for (Schedule data:listSchedule) {
                if (data.getDay().equals("Kamis")) {
                    list.add(data);
                }
            }

        }catch (Exception e) {

        }

        if(listSchedule.size() ==0 ){

        }else{
            setListAdapter(new ScheduleAdapter(getActivity(),list));
        }

    }
}
