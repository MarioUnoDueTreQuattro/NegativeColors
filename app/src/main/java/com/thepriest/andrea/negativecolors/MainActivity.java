package com.thepriest.andrea.negativecolors;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_main);
/*
        Intent negintent = new Intent("com.samsung.settings.action.NEGATIVECOLOR_OFF");
        this.sendBroadcast(new Intent("android.intent.action.NEGATIVECOLOR_OFF"));
        this.sendBroadcast(negintent);
*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        toggleWidgetMode(this);
        finish();
    }

    private void toggleWidgetMode(Context context) {
        Intent intent;
        boolean bl = Settings.System.getInt((ContentResolver)context.getContentResolver(), (String)"greyscale_mode", (int)0) != 0;
        if (!getWidgetMode(context)) {
            //Toast.makeText((Context)context, "getWidgetMode",Toast.LENGTH_SHORT).show();
            boolean bl2 = Settings.System.getInt((ContentResolver)context.getContentResolver(), (String)"color_blind", (int)0) != 0;
            if (bl2) {
                //Toast.makeText((Context)context, "greyscale_mode",Toast.LENGTH_SHORT).show();
            }
            Settings.System.putInt((ContentResolver)context.getContentResolver(), (String)"color_blind", (int)0);
            Settings.System.putInt((ContentResolver)context.getContentResolver(), (String)"high_contrast", (int)1);
            if (bl) {
                intent = new Intent("com.samsung.settings.colorblind.ColorBlindMultiUserReceiver");
            } else {
                intent = new Intent("com.samsung.settings.action.NEGATIVECOLOR_ON");
                context.sendBroadcast(new Intent("android.intent.action.NEGATIVECOLOR_ON"));
            }
        } else {
            //Toast.makeText((Context)context, "NOTgetWidgetMode",Toast.LENGTH_SHORT).show();
            Settings.System.putInt((ContentResolver)context.getContentResolver(), (String)"high_contrast", (int)0);
            if (bl) {
                intent = new Intent("com.samsung.settings.colorblind.ColorBlindMultiUserReceiver");
            } else {
                intent = new Intent("com.samsung.settings.action.NEGATIVECOLOR_OFF");
                context.sendBroadcast(new Intent("android.intent.action.NEGATIVECOLOR_OFF"));
            }
        }
        context.sendBroadcast(intent);
    }
    private static boolean getWidgetMode(Context context) {
        int n = Settings.System.getInt((ContentResolver)context.getContentResolver(), (String)"high_contrast", (int)0);
        boolean bl = false;
        if (n != 0) {
            bl = true;
        }
        return bl;
    }
}
