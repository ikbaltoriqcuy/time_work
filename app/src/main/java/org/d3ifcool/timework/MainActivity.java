package org.d3ifcool.timework;

import android.app.ActionBar;
import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.widget.TabHost;
import android.widget.Toolbar;


public class MainActivity extends ActivityGroup {

    private int mCurrentTab = 0 ;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
            Account account = databaseAdapter.getAccount();
            if(account == null) {
                Intent intent = new Intent(this,CreateProfileActivity.class);
                startActivity(intent);
            }
        }catch (Exception e) {
            Intent intent = new Intent(this,CreateProfileActivity.class);
            startActivity(intent);
        }

        Bundle extra = getIntent().getExtras();
        if(extra== null) {
            mCurrentTab = 0;
        }else{
            mCurrentTab = extra.getInt(getString(R.string.current_tab));
        }

        Intent intentActivity1,intentActivity2,intentActivity3;
        tabHost = (TabHost) findViewById(R.id.tabHost);
        TabHost.TabSpec tabSpec1,tabSpec2,tabSpec3;
        tabHost.setup(getLocalActivityManager());

        //create TabSpec "Home" using tabHost

        tabSpec1 = tabHost.newTabSpec(getString(R.string.tab_home));
        tabSpec1.setIndicator(getString(R.string.tab_home));
        intentActivity1= new Intent(this,ViewScheduleActivitty.class);
        tabSpec1.setContent(intentActivity1);
        tabHost.addTab(tabSpec1);

        //end

        //create TabSpec "Quotes" using tabHost

        tabSpec2 = tabHost.newTabSpec(getString(R.string.tab_quotes));
        tabSpec2.setIndicator(getString(R.string.tab_quotes));
        intentActivity2 = new Intent(this,QuotesActivity.class);
        tabSpec2.setContent(intentActivity2);
        tabHost.addTab(tabSpec2);

        //end


        //create TabSpec "MyAccount" using tabHost

        tabSpec3 = tabHost.newTabSpec(getString(R.string.tab_account));
        tabSpec3.setIndicator(getString(R.string.tab_account));
        intentActivity3 = new Intent(this,AccountAcivity.class);
        tabSpec3.setContent(intentActivity3);
        tabHost.addTab(tabSpec3);

        //end

        //set tab which one you want to open first time 0 or 1 or 2
        tabHost.setCurrentTab(mCurrentTab);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {

            }
        });

        startService(new Intent(this,AlarmService.class));


    }

    //@Override
    //protected void onStop() {
        //startService(new Intent(this,AlarmService.class));
        //super.onStop();
    //}


    //@Override
    //protected void onDestroy() {
        //super.onDestroy();
        //Intent broadcastIntent = new Intent("org.d3ifcool.timework.RestartService");
        //sendBroadcast(broadcastIntent);

    //}

    @Override
    protected void onStop() {
        super.onStop();
        Intent broadcastIntent = new Intent("org.d3ifcool.timework.RestartService");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Intent intent = new Intent(this,CreateProfileActivity.class);
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("RestartService"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float lastX = 0.0f;
        switch (event.getAction()) {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN: {
                lastX = event.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = event.getX();

                // if left to right swipe on screen
                if (lastX < currentX) {

                    switchTabs(false);
                }

                // if right to left swipe on screen
                if (lastX > currentX) {
                    switchTabs(true);
                }

                break;
            }
        }
        return false;
    }

    public void switchTabs(boolean direction) {
        if (direction) // true = move left
        {
            if (tabHost.getCurrentTab() == 0)
                tabHost.setCurrentTab(tabHost.getTabWidget().getTabCount() - 1);
            else
                tabHost.setCurrentTab(tabHost.getCurrentTab() - 1);
        } else
        // move right
        {
            if (tabHost.getCurrentTab() != (tabHost.getTabWidget()
                    .getTabCount() - 1))
                tabHost.setCurrentTab(tabHost.getCurrentTab() + 1);
            else
                tabHost.setCurrentTab(0);
        }
    }
}
