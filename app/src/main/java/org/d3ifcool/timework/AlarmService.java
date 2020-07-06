package org.d3ifcool.timework;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by cool on 2/28/2018.
 */

public class AlarmService extends Service {

    private boolean mIsOpen = false;
    private String currentEndTime;
    private ArrayList<Schedule> mCurrSchedule;
    private int delay =0;
    private int countDown;



    private int convertToInt (String time) {
        String dataSplit[] = time.split(":");
        int dataTimeInt  = Integer.parseInt(dataSplit[0]+dataSplit[1]);

        return dataTimeInt;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try{
            delay = intent.getExtras().getInt("delay");
        }catch (Exception e){

        }
        CheckTime checkTime = new CheckTime(intent,this);
        Thread thread = checkTime;
        thread.start();
        return START_STICKY ;
    }


    public class CheckTime extends Thread {
        private  Intent intent;
        private Context context;

        public CheckTime(Intent intent, Context context) {
            this.intent = intent;
            this.context = context;

            Log.i(" Make It" , "true");

        }

        @Override
        public void run() {

            countDown = delay * 60;

            Calendar c = Calendar.getInstance();

            int day = c.get(Calendar.DAY_OF_WEEK);
            String currentDay = "";

            switch (day){
                case Calendar.SUNDAY  :currentDay = "Minggu";break;
                case Calendar.MONDAY  : currentDay = "Senin";break;
                case Calendar.TUESDAY  : currentDay = "Selasa";break;
                case Calendar.WEDNESDAY  : currentDay = "Rabu";break;
                case Calendar.THURSDAY : currentDay = "Kamis";break;
                case Calendar.FRIDAY  : currentDay = "Jumat";break;
                case Calendar.SATURDAY  : currentDay = "Sabtu";break;
            }




            while (true) {

                CurrentSchedule currentSchedule = new CurrentSchedule();
                currentSchedule.setCurrentSchedule(context,currentDay);
                mCurrSchedule = currentSchedule.getmCurrentSchedule();

                if(countDown !=0) {
                    countDown--;
                }

                Log.i("Delay" , String.valueOf(countDown));

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Calendar cal = Calendar.getInstance();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String time = simpleDateFormat.format(cal.getTime());



                //Log.i ("now",currentDay+" "+time);


                for(int n=0; n <mCurrSchedule.size() ; n++) {

                    if ( convertToInt(time) >= convertToInt(mCurrSchedule.get(n).getStartTime()) &&
                            convertToInt(time) <=  convertToInt(mCurrSchedule.get(n).getEndTime())
                            && !mIsOpen && mCurrSchedule.get(n).getActive() == 1 && countDown == 0) {

                        mIsOpen = true;


                        Intent intent1 = new Intent(context, LockActivity.class);
                        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);


                        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(false);

                        stopSelf();

                    }

                }


            }

        }
    }

}