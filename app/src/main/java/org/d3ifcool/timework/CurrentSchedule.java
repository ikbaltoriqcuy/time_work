package org.d3ifcool.timework;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by cool on 3/3/2018.
 */

public class CurrentSchedule {
    private ArrayList<Schedule> mCurrentScheule;

    public CurrentSchedule() {
        mCurrentScheule  =new ArrayList<>();
    }

    public void setCurrentSchedule (Context context,String day) {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context);
        ArrayList<Schedule> curr = databaseAdapter.getAllSchedule();

        for (Schedule data:curr){
            if (data.getDay().equals(day) && data.getActive() == 1){
                mCurrentScheule.add(data);
            }
        }
    }

    public ArrayList<Schedule> getmCurrentSchedule() {
        return mCurrentScheule;
    }
}
