package com.garytokman.tokmangary_ce01.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

// Gary Tokman
// MDF3 - 1610
// DataChangedReceiver

public class DataChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: Save store that data out then send out broad
        // TODO: delete records that match the passed in data all 3 points must match for th data to be gone send out broad
        // TODO: After save / delete = send out an ACTION_UPDATE_LIST broadcast
    }
}
