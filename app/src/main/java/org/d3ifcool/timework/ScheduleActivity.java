package org.d3ifcool.timework;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class
ScheduleActivity extends AppCompatActivity {

    //variabel to recieved Intent.putExtra from ViewScheduleActivity
    private String idSchedule;
    private String nameSchedule;
    private String day;
    private String startTime;
    private String endTime;
    private int active;

    //end

    private ArrayList<Schedule> mCrashSchedule = new ArrayList<Schedule>();

    final  String[] days = {"Senin","Selasa","Rabu","Kamis","Jumat","Sabtu","Minggu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        Button databaseButton = (Button) findViewById(R.id.database_button);
        databaseButton.setText("Tambah");
        recievedData();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(this,MainActivity.class);
            intent.putExtra("current_tab",0);
            startActivity(intent);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }


    //method to recieved data from Intent.putExtra
    private void recievedData(){
        Bundle extra = getIntent().getExtras();
        if(extra== null) {
            //do nothing

        }else{
            idSchedule = extra.getString("id");
            nameSchedule = extra.getString("name") ;
            day = extra.getString("day");
            startTime = extra.getString("start");
            endTime = extra.getString("end");
            active = extra.getInt("active");
            showData();
        }


    }

    //show data from each component in layout
    private void showData() {

        EditText add =(EditText) findViewById(R.id.add) ;
        TextView daysTextView = (TextView) findViewById(R.id.days_textview);
        TextView startTextView = (TextView) findViewById(R.id.start_time_textview);
        TextView endTextView = (TextView) findViewById(R.id.end_time_textview);
        Switch activeSwitch = (Switch) findViewById(R.id.active);

        Button databaseButton = (Button) findViewById(R.id.database_button);
        databaseButton.setText("Update");

        add.setText(nameSchedule);
        daysTextView.setText(day);
        startTextView.setText(startTime);
        endTextView.setText(endTime);
        activeSwitch.setChecked(active==1 ? true:false );
    }




    public void startTime(View view) {
        AlertDialog.Builder showTimePicker = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_time,null);
        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker) ;
        showTimePicker.setView(dialogView);
        showTimePicker.setTitle("Set Time");
        showTimePicker.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        showTimePicker.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int hour, minute ;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour() ;
                    minute = timePicker.getMinute();
                }else {
                    hour = timePicker.getCurrentHour() ;
                    minute = timePicker.getCurrentMinute();
                }
                TextView startTime = (TextView) findViewById(R.id.start_time_textview) ;
                startTime.setText(hour  + ":" + minute );
            }
        });
        AlertDialog alertDialog = showTimePicker.create();
        alertDialog.show();
    }


    public void endTime(View view) {
        AlertDialog.Builder showTimePicker = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.date_time,null);
        showTimePicker.setView(dialogView);
        showTimePicker.setTitle("Set Time");

        final TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.time_picker) ;
        showTimePicker.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        showTimePicker.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int hour=0 , minute=0 ;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    hour = timePicker.getHour() ;
                    minute = timePicker.getMinute();
                }else {
                    hour = timePicker.getCurrentHour() ;
                    minute = timePicker.getCurrentMinute();
                }
                TextView startTime = (TextView) findViewById(R.id.end_time_textview) ;
                startTime.setText(hour  + ":" + minute );
            }
        });
        AlertDialog alertDialog = showTimePicker.create();
        alertDialog.show();
    }

    public void showListDays(View view) {
        AlertDialog.Builder listDaysBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.list_days,null);
        final ListView listDays = (ListView) dialogView.findViewById(R.id.days_listview) ;
        final ArrayList<String> arrayData = new ArrayList<String>();
        for (String data:days) {
           arrayData.add(data);
        }


        listDaysBuilder.setView(dialogView);
        listDaysBuilder.setTitle("Pilih Hari");

        listDays.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayData));

        final AlertDialog alertDialog = listDaysBuilder.create();
        alertDialog.show();


        listDays.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                TextView daysTextView = (TextView) findViewById(R.id.days_textview);
                daysTextView.setText(arrayData.get(position));
                alertDialog.cancel();
            }
        });


    }

    public void addToDatabase(View view) {

        EditText add =(EditText) findViewById(R.id.add) ;
        TextView daysTextView = (TextView) findViewById(R.id.days_textview);
        TextView startTextView = (TextView) findViewById(R.id.start_time_textview);
        TextView endTextView = (TextView) findViewById(R.id.end_time_textview);
        Switch active = (Switch) findViewById(R.id.active);

        Button databaseButton = (Button) findViewById(R.id.database_button);

        String s = add.getText().toString();
        String n = daysTextView.getText().toString();
        String h = startTextView.getText().toString();
        String m = endTextView.getText().toString();

        mCrashSchedule.clear();
        boolean crash = check(h,m);



        if(databaseButton.getText().equals("Tambah")){

            if(!crash) {

                if (convertToInt(h) < convertToInt(m)) {

                    DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
                    long check = databaseAdapter.addSchedule(new Schedule(s, s, n, h, m, active.isChecked() ? 1 : 0));
                    if (check == 1) {
                        Toast.makeText(this, "berhasil di buat", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "gagal dibuat", Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    this.finish();

                }else {
                    Toast.makeText(this,"jadwal yang anda buat melampui hari " + n ,Toast.LENGTH_SHORT).show();
                }


            }else if(crash){
                AlertDialog.Builder listScheduleBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.crash_layout,null);
                ListView listSchedule = (ListView) dialogView.findViewById(R.id.crash_list_view);
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this,mCrashSchedule);
                listSchedule.setAdapter(scheduleAdapter);

                listScheduleBuilder.setTitle("Jadwal Bentrok");
                listScheduleBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                listScheduleBuilder.setView(dialogView);

                AlertDialog alertDialog = listScheduleBuilder.create();
                alertDialog.show();
            }

        } else if (databaseButton.getText().equals("Update")){
            if(!crash) {
                Schedule schedule = new Schedule(idSchedule, s, n, h, m, active.isChecked() ? 1 : 0);
                DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
                int check = databaseAdapter.updateSchedule(schedule);

                Toast.makeText(this, schedule.toString(), Toast.LENGTH_LONG).show();
                if (check == 1) {
                    Toast.makeText(this, "gagal dibuat", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "succes di buat", Toast.LENGTH_LONG).show();
                }


                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                this.finish();
            }else{

                AlertDialog.Builder listScheduleBuilder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.crash_layout,null);
                ListView listSchedule = (ListView) dialogView.findViewById(R.id.crash_list_view) ;
                ScheduleAdapter scheduleAdapter = new ScheduleAdapter(this,mCrashSchedule);
                listSchedule.setAdapter(scheduleAdapter);


                listScheduleBuilder.setView(dialogView);

                AlertDialog alertDialog = listScheduleBuilder.create();
                alertDialog.show();


            }

        }


    }

    public void reset(View view){
        EditText add =(EditText) findViewById(R.id.add) ;
        TextView daysTextView = (TextView) findViewById(R.id.days_textview);
        TextView startTextView = (TextView) findViewById(R.id.start_time_textview);
        TextView endTextView = (TextView) findViewById(R.id.end_time_textview);
        Switch active = (Switch) findViewById(R.id.active);

        add.setText("");
        daysTextView.setText("Senin");
        startTextView.setText("00:00");
        endTextView.setText("00:00");
        active.setChecked(false);

    }

    public boolean check(String startTime ,String endTime) {
        boolean crash = false;
        ArrayList<Schedule> getAllSchedule = new ArrayList<>();

        try{
            DatabaseAdapter databaseAdapter =new DatabaseAdapter(this);
            getAllSchedule = databaseAdapter.getAllSchedule();
        }catch (Exception e) {

        }
        //convert time to int
        ///*
        ///*
        //
        for (int i= 0 ; i < getAllSchedule.size(); i++ ) {

            Schedule data = getAllSchedule.get(i);

            TextView daysTextView = (TextView) findViewById(R.id.days_textview);
            Switch activeSwitch = (Switch) findViewById(R.id.active);

            if(data.getDay().equals(daysTextView.getText().toString())
                    && data.getActive() == 1 && activeSwitch.isChecked()){

                if(convertToInt(startTime) >=convertToInt(data.getStartTime())
                        && convertToInt(startTime) <=convertToInt(data.getEndTime()) ||
                        convertToInt(endTime) >=convertToInt(data.getStartTime())
                                && convertToInt(endTime) <=convertToInt(data.getEndTime())) {
                    //Jadwal tabrakan
                    mCrashSchedule.add(data);
                    crash = true;

                }else if(convertToInt(data.getStartTime()) >= convertToInt(startTime)
                        && convertToInt(data.getStartTime()) <= convertToInt(endTime) ||
                        convertToInt(data.getEndTime()) >= convertToInt(startTime)
                                && convertToInt(data.getEndTime()) <= convertToInt(endTime)){
                    //Jadwal tabrakan
                    mCrashSchedule.add(data);
                    crash = true;

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
