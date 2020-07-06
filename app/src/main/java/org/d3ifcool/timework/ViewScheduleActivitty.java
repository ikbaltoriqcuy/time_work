package org.d3ifcool.timework;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.d3ifcool.timework.SlidingTab.SlidingTabLayout;
import org.d3ifcool.timework.SlidingTab.ViewPagerAdapter;

import static android.app.PendingIntent.getActivity;

public class ViewScheduleActivitty extends AppCompatActivity{
    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence[] Titles = {"Senin" ,"Selasa" ,"Rabu" ,"Kamis","Jumat","Sabtu","Minggu"};
    int Numboftabs =7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule_activitty);


        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // Creating The Toolbar and setting it as the Toolbar for the activity

       // toolbar = (Toolbar) findViewById(R.id.tool_bar);
        //setSupportActionBar(toolbar);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles,Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorAccent);
            }

            @Override
            public int getDividerColor(int position) {
                return getResources().getColor(R.color.colorTextTab);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);

    }

    public void addSchedule(View view) {
        Intent taskActivity = new Intent(this,ScheduleActivity.class);
        startActivity(taskActivity);
        this.finish();
    }
}
