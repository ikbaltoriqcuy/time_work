package org.d3ifcool.timework;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by cool on 2/26/2018.
 */

public class ScheduleAdapter extends ArrayAdapter<Schedule> {
    private Context context;
    private ArrayList<Schedule> mCrashSchedule = new ArrayList<Schedule>();

    public ScheduleAdapter(@NonNull Context context,  @NonNull ArrayList<Schedule> objects) {
        super(context, 0, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_schedule,parent,false);
        }


        final Schedule schedule = getItem(position);
        /*
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return false;
            }
        });
        */

        AlertDialog.Builder showContentOption = new AlertDialog.Builder(context);
        final LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.content_option,null);
        showContentOption.setView(dialogView);

        final AlertDialog alertDialog = showContentOption.create();

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                alertDialog.show();
                return false;
            }
        });

        Button edit = (Button) dialogView.findViewById(R.id.edit_button);
        Button hapus = (Button) dialogView.findViewById(R.id.hapus_button);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ScheduleActivity.class);
                intent.putExtra("id",schedule.getIdSchedule());
                intent.putExtra("name",schedule.getNameSchedule());
                intent.putExtra("day",schedule.getDay());
                intent.putExtra("start",schedule.getStartTime());
                intent.putExtra("end",schedule.getEndTime());
                intent.putExtra("active",schedule.getActive());
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });


        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                final AlertDialog.Builder messageAlert = new AlertDialog.Builder(context);
                View viewDeleteMessage = inflater.inflate(R.layout.delete_message,null);
                messageAlert.setView(viewDeleteMessage);
                messageAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //nothing
                    }
                });
                messageAlert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
                        databaseAdapter.deleteSchedule(schedule);

                        //refresh data
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                        ((Activity) context).finish();


                    }
                });

                AlertDialog alert = messageAlert.create();
                alert.show();
            }
        });


        final TextView nameScheduling = (TextView) convertView.findViewById(R.id.name_scheduling);
        TextView timeScheduling = (TextView) convertView.findViewById(R.id.time_scheduling);
        final Switch activeSwitch = (Switch) convertView.findViewById(R.id.active_switch);


        nameScheduling.setText(schedule.getNameSchedule());
        timeScheduling.setText(schedule.getStartTime() + " - " + schedule.getEndTime());
        activeSwitch.setChecked(schedule.getActive() ==1 ? true:false);
        activeSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);

                mCrashSchedule = new ArrayList<>();

                boolean crash = check(schedule);


                    if(activeSwitch.isChecked()) {

                        if (!crash) {

                            databaseAdapter.updateSchedule(new Schedule(schedule.getIdSchedule().toString(),
                                    schedule.getNameSchedule(),
                                    schedule.getDay(),
                                    schedule.getStartTime().toString(),
                                    schedule.getEndTime().toString(),
                                    1));

                        } else{

                            AlertDialog.Builder listScheduleBuilder = new AlertDialog.Builder(context);
                            LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View dialogView = inflater.inflate(R.layout.crash_layout,null);
                            ListView listSchedule = (ListView) dialogView.findViewById(R.id.crash_list_view);
                            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(context,mCrashSchedule);
                            listSchedule.setAdapter(scheduleAdapter);

                            listScheduleBuilder.setView(dialogView);

                            AlertDialog alertDialog = listScheduleBuilder.create();
                            alertDialog.show();
                            activeSwitch.setChecked(false);
                        }

                    }else{
                        databaseAdapter.updateSchedule(new Schedule(schedule.getIdSchedule().toString(),
                                schedule.getNameSchedule(),
                                schedule.getDay(),
                                schedule.getStartTime().toString(),
                                schedule.getEndTime().toString(),
                                0));
                    }


            }
        });


        return convertView;
    }


    public boolean check(Schedule schedule) {
        boolean crash = false;
        ArrayList<Schedule> getAllSchedule = new ArrayList<>();

        try{
            DatabaseAdapter databaseAdapter =new DatabaseAdapter(context);
            getAllSchedule = databaseAdapter.getAllSchedule();
        }catch (Exception e) {

        }
        //convert time to int
        ///*
        ///*
        //
        for (int i= 0 ; i < getAllSchedule.size(); i++ ) {
            Schedule data = getAllSchedule.get(i);

            if(data.getDay().equals(schedule.getDay())
                    && data.getActive() == 1){

                if (!data.getIdSchedule().equals(schedule.getIdSchedule())) {

                    if (convertToInt(schedule.getStartTime()) >= convertToInt(data.getStartTime())
                            && convertToInt(schedule.getStartTime()) <= convertToInt(data.getEndTime()) ||
                            convertToInt(schedule.getEndTime()) >= convertToInt(data.getStartTime())
                                    && convertToInt(schedule.getEndTime()) <= convertToInt(data.getEndTime())) {
                        //Jadwal tabrakan
                        mCrashSchedule.add(data);
                        crash = true;

                    } else if (convertToInt(data.getStartTime()) >= convertToInt(schedule.getStartTime() )
                            && convertToInt(data.getStartTime()) <=  convertToInt(schedule.getEndTime()) ||
                            convertToInt(data.getEndTime()) >= convertToInt(schedule.getStartTime()) &&
                                    convertToInt(data.getEndTime())<= convertToInt(schedule.getEndTime())) {
                        //Jadwal tabrakan
                        mCrashSchedule.add(data);
                        crash = true;

                    }
                }
            }


        }

        return crash;
    }

    public int convertToInt (String time) {
        String dataSplit[] = time.split(":");
        int dataTimeInt  = Integer.parseInt(dataSplit[0]+dataSplit[1]);

        return dataTimeInt;
    }
}
