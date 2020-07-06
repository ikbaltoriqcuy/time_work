package org.d3ifcool.timework;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class LockActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        TimeTick timeTick = new TimeTick(this,getCurrentSchedule());
        Thread thread = timeTick;
        thread.start();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent= new Intent(this,AlarmService.class).putExtra("delay",1);
            this.finish();

        }
        return super.onKeyDown(keyCode, event);
    }

    public Schedule getCurrentSchedule(){
        Schedule schedule = null;
        try{
            DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
            schedule = databaseAdapter.getSchedule("current_active");
        }catch (Exception e){

        }
        return schedule;
    }

    @Override
    protected void onPause() {
        startService(new Intent(this,AlarmService.class).putExtra("delay",1));
        this.finish();
        super.onPause();
    }




    class TimeTick extends Thread{
        private Schedule schedule;
        private Context context;


        public TimeTick(Context context,Schedule sechedule){
            this.context = context;
            this.schedule = sechedule;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String time = simpleDateFormat.format(cal.getTime());

                //if (time.equals(schedule.getEndTime())){
                    //((Activity) context).finish();
                    //try {
                        //DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
                        //databaseAdapter.deleteSchedule(schedule);
                    //}catch (Exception e){

                    //}
               // }

            }
        }
    }

}
