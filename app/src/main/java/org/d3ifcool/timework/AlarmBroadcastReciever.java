package org.d3ifcool.timework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by cool on 3/2/2018.
 */

public class AlarmBroadcastReciever extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(new Intent(context.getApplicationContext(), AlarmService.class));
    }
}
