package org.d3ifcool.timework.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.d3ifcool.timework.DatabaseAdapter;
import org.d3ifcool.timework.R;
import org.d3ifcool.timework.Schedule;
import org.d3ifcool.timework.ScheduleAdapter;

import java.util.ArrayList;

public class Senin extends ListFragment {

    public Senin() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_senin, container, false);

        //ListView listView = (ListView) v.findViewById(R.id.list_quote);

        //DatabaseAdapter databaseAdapter = new DatabaseAdapter(getActivity());
        //listSchedule.add(new Schedule("Sd","sd","rt","ds","e3",1));
        //ArrayList<Schedule> listSchedule = new ArrayList<>();


        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
                if (data.getDay().equals("Senin")) {
                    list.add(data);
                }
            }

        }catch (Exception e) {
            Toast.makeText(getActivity(),"Error to show",Toast.LENGTH_LONG).show();
        }

        if(listSchedule.size() ==0 ){

        }else{
            setListAdapter(new ScheduleAdapter(getActivity(),list));
        }

    }

}
